package com.yaonie.intelligent.assessment.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.system.domain.entity.SysDictData;
import com.yaonie.intelligent.assessment.system.mapper.SysDictDataMapper;
import com.yaonie.intelligent.assessment.system.service.SysDictDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-11
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return List.of();
    }

    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return "";
    }

    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return null;
    }

    @Override
    public void deleteDictDataByIds(Long[] dictCodes) {

    }

    @Override
    public int insertDictData(SysDictData dictData) {
        return 0;
    }

    @Override
    public int updateDictData(SysDictData dictData) {
        return 0;
    }
}
