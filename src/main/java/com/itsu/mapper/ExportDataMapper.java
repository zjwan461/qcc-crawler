package com.itsu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsu.entity.ExportData;

import java.util.List;

public interface ExportDataMapper extends BaseMapper<ExportData> {

    void batchInsert(List<ExportData> list);
}
