package com.itsu.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.itsu.entity.ExcelData;
import com.itsu.entity.ExportData;
import com.itsu.mapper.ExportDataMapper;
import com.itsu.service.CrawlerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Resource
    private ExportDataMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveExportData(List<ExportData> data) {
        if (CollUtil.isNotEmpty(data))
            mapper.batchInsert(data);
    }

    @Override
    public void transformDownload(HttpServletResponse response) throws IOException {
        List<ExportData> exportDatas = mapper.selectList(null);
        List<ExportData> treeList = new ArrayList<>();
        for (ExportData exportData : exportDatas) {
            if (exportData.getParent() == null) {
                ExportData data = new ExportData();
                data.setId(exportData.getId());
                data.setHref(exportData.getHref());
                data.setName(exportData.getName());
                data.setParent(exportData.getParent());
                data.setChildren(transform(exportData.getName(), exportDatas));
                treeList.add(data);
            }
        }
//        String filename = System.getProperty("user.dir") + File.separator + "企查查爬虫数据.xlsx";
//        File file = FileUtil.newFile(filename);
//        FileUtil.writeString(JSONUtil.toJsonPrettyStr(treeList), file, Charset.defaultCharset());
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("企查查爬虫数据.xlsx", "UTF-8"));
        EasyExcel.write(response.getOutputStream(), ExcelData.class).sheet("爬虫数据").doWrite(getExcelData(treeList));

//        try (ServletOutputStream outputStream = response.getOutputStream();
//             BufferedInputStream inputStream = FileUtil.getInputStream(file)) {
//            byte[] cache = new byte[1024];
//            int len = 0;
//            while ((len = inputStream.read(cache)) > 0) {
//                outputStream.write(cache, 0, len);
//            }
//        } catch (IOException e) {
//            throw e;
//        }
    }

    protected Collection<ExcelData> getExcelData(List<ExportData> treeList) {
        List<ExcelData> excelDatas = new ArrayList<>();
        int id = 1;
        for (ExportData exportData : treeList) {
            List<ExportData> sons = exportData.getChildren();
            for (ExportData son : sons) {
                for (ExportData grandson : son.getChildren()) {
                    ExcelData excelData = new ExcelData();
                    excelData.setId(id);
                    excelData.setName(exportData.getName());
                    excelData.setHref(exportData.getHref());
                    excelData.setSonName(son.getName());
                    excelData.setSonHref(son.getHref());
                    excelData.setGrandsonName(grandson.getName());
                    excelData.setGrandsonHref(grandson.getHref());
                    excelDatas.add(excelData);
                    id++;
                }
            }
        }
        return excelDatas;
    }

    protected List<ExportData> transform(String parent, List<ExportData> exportDatas) {
        List<ExportData> list = new ArrayList<>();
        for (ExportData exportData : exportDatas) {
            if (parent.equals(exportData.getParent())) {
                ExportData data = new ExportData();
                data.setId(exportData.getId());
                data.setName(exportData.getName());
                data.setHref(exportData.getHref());
                data.setParent(parent);
                data.setChildren(transform(exportData.getName(), exportDatas));
                list.add(data);
            }
        }
        return list;
    }
}
