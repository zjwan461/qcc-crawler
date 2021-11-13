package com.itsu.controller;

import com.itsu.entity.ExportData;
import com.itsu.service.CrawlerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
public class MyController {

    @Resource
    private CrawlerService crawlerService;

    @PostMapping("/saveData")
    public String saveData(@RequestBody List<ExportData> data) {
        try {
            crawlerService.saveExportData(data);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("/download")
    public String download(HttpServletResponse response) {
        try {
            crawlerService.transformDownload(response);
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
