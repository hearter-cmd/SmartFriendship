package com.yaonie.intelligent.assessment.server.springbootinit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent.assessment.server.common.holder.UserHolder;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.appLike.AppGiveLikeAllDto;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.appLike.AppGiveLikeDto;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.AppLike;
import com.yaonie.intelligent.assessment.server.springbootinit.event.AppLikeEvent;
import com.yaonie.intelligent.assessment.server.springbootinit.service.AppLikeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * app点赞信息(AppLike)表控制层
 *
 * @author makejava
 * @since 2024-10-13 18:10:55
 */
@RestController
@RequestMapping("appLike")
public class AppLikeController {
    /**
     * 服务对象
     */
    @Resource
    private AppLikeService appLikeService;
    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public BaseResponse<Page<AppLike>> selectAll() {
        Page<AppLike> page = new Page<>();
        return ResultUtils.success(this.appLikeService.page(page, new QueryWrapper<>()));
    }

    /**
     * 新增数据
     */
    @PostMapping
    public void giveLike(@RequestBody AppGiveLikeDto appGiveLikeDto, HttpServletRequest request) {
        User loginUser = UserHolder.getUser();
        AppGiveLikeAllDto appGiveLikeAllDto = new AppGiveLikeAllDto();
        appGiveLikeAllDto.setUserId(loginUser.getId());
        appGiveLikeAllDto.setAppId(appGiveLikeDto.getAppId());
        publisher.publishEvent(new AppLikeEvent(appGiveLikeAllDto));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public BaseResponse<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return ResultUtils.success(this.appLikeService.removeByIds(idList));
    }
}

