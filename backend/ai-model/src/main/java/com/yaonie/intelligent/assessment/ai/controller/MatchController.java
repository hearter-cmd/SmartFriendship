package com.yaonie.intelligent.assessment.ai.controller;


import com.yaonie.intelligent.assessment.ai.domain.model.dto.match.MilvusAddDto;
import com.yaonie.intelligent.assessment.ai.service.MatchService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@AllArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping("/doMatch")
    public BaseResponse<?> doMatch() {
        return ResultUtils.success(matchService.doMatch());
    }

    @PostMapping("/add")
    public BaseResponse<?> add(@RequestBody MilvusAddDto milvusAddDto) {
        matchService.doAdd(milvusAddDto);
        return ResultUtils.success(null);
    }
}
