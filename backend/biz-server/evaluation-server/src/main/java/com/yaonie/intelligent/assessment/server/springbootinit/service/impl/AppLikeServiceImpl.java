package com.yaonie.intelligent.assessment.server.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.AppLike;
import com.yaonie.intelligent.assessment.server.springbootinit.mapper.AppLikeDao;
import com.yaonie.intelligent.assessment.server.springbootinit.service.AppLikeService;
import org.springframework.stereotype.Service;

/**
 * app点赞信息(AppLike)表服务实现类
 *
 * @author makejava
 * @since 2024-10-13 18:10:57
 */
@Service("appLikeService")
public class AppLikeServiceImpl extends ServiceImpl<AppLikeDao, AppLike> implements AppLikeService {
}

