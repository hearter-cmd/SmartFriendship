package com.yaonie.intelligent_assessment_server.search_server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent_assessment_server.model.entity.App;
import com.yaonie.intelligent_assessment_server.search_server.mapper.AppMapper;
import com.yaonie.intelligent_assessment_server.search_server.service.AppService;
import org.springframework.stereotype.Service;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 18:11
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName AppServiceImpl
 * @Project backend
 * @Description : TODO
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {
}
