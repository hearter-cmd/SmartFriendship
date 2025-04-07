package com.yaonie.intelligent.assessment.ai.controller;


import com.yaonie.intelligent.assessment.ai.domain.model.dto.match.MilvusAddDto;
import com.yaonie.intelligent.assessment.ai.service.MatchService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-02-04
 */
@RestController
@RequestMapping("/match")
public class MatchController {
    @Resource
    private MatchService matchService;

    @GetMapping("/doMatch")
    public BaseResponse<List<UserVO>> doMatch() {
        return ResultUtils.success(matchService.doMatch());
    }

    @GetMapping("/doMatchByGroup")
    public BaseResponse<List<GroupInfo>> doMatchByGroup() {
        return ResultUtils.success(matchService.doMatchByGroup());
    }

    @PostMapping("/add")
    public BaseResponse<?> add(@RequestBody MilvusAddDto milvusAddDto) {
        matchService.doAdd(milvusAddDto);
        return ResultUtils.success(null);
    }

}
