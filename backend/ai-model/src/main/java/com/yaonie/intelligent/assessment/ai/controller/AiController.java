package com.yaonie.intelligent.assessment.ai.controller;


import com.yaonie.intelligent.assessment.ai.domain.enums.AiConfigTypeEnum;
import com.yaonie.intelligent.assessment.ai.domain.model.vo.AiInfoVo;
import com.yaonie.intelligent.assessment.ai.service.AiService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-08
 */
@Tag(name = "AI聚合层")
@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private AiService aiService;
    @Resource
    private ZhiPuAiChatModel zhiPuAiChatModel;

    @Operation(summary = "切换AI配置文件")
    @PostMapping("/replace")
    public BaseResponse<Object> editAiConfig(AiConfigTypeEnum aiType, String apiKey) {
        aiService.editAiConfig(aiType, apiKey);
        return ResultUtils.success("切换成功！");
    }

    @Operation(summary = "获取当前AI信息")
    @GetMapping("/current")
    public BaseResponse<AiInfoVo> getCurrentAiInfo() {
        AiInfoVo currentAiInfo = aiService.getCurrentAiInfo(AiConfigTypeEnum.ZHI_PU_CHAT);
        return ResultUtils.success(currentAiInfo);
    }

    @Operation(summary = "测试")
    @GetMapping("/test")
    public String test() {
        String call = zhiPuAiChatModel.call("你好");
        return call;
    }

}
