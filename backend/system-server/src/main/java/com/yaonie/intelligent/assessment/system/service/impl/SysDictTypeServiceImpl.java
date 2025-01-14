package com.yaonie.intelligent.assessment.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.system.domain.entity.SysDictData;
import com.yaonie.intelligent.assessment.system.domain.entity.SysDictType;
import com.yaonie.intelligent.assessment.system.mapper.SysDictTypeMapper;
import com.yaonie.intelligent.assessment.system.service.SysDictTypeService;
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
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {
    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        return List.of();
    }

    @Override
    public List<SysDictType> selectDictTypeAll() {
        return List.of();
    }

    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        return List.of();
    }

    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return null;
    }

    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return null;
    }

    @Override
    public void deleteDictTypeByIds(Long[] dictIds) {

    }

    @Override
    public void loadingDictCache() {

    }

    @Override
    public void clearDictCache() {

    }

    @Override
    public void resetDictCache() {

    }

    @Override
    public int insertDictType(SysDictType dictType) {
        return 0;
    }

    @Override
    public int updateDictType(SysDictType dictType) {
        return 0;
    }

    @Override
    public boolean checkDictTypeUnique(SysDictType dictType) {
        return false;
    }
}
