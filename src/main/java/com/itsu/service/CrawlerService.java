package com.itsu.service;

import com.itsu.entity.ExportData;

import java.util.List;

public interface CrawlerService {

    void saveExportData(List<ExportData> data);
}
