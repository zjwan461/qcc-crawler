package com.itsu.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.itsu.entity.ExportData;
import com.itsu.mapper.ExportDataMapper;
import com.itsu.service.CrawlerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
}
