package com.itsu.service;

import com.itsu.entity.ExportData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CrawlerService {

    void saveExportData(List<ExportData> data);

    void transformDownload(HttpServletResponse response) throws UnsupportedEncodingException, IOException;
}
