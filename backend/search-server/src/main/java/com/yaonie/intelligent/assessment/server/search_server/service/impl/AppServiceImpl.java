package com.yaonie.intelligent.assessment.server.search_server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.App;
import com.yaonie.intelligent.assessment.server.search_server.mapper.AppMapper;
import com.yaonie.intelligent.assessment.server.search_server.service.AppService;
import org.springframework.stereotype.Service;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 18:11
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName AppServiceImpl
 * @Project backend
 * @Description : 应用服务实现
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {
}
