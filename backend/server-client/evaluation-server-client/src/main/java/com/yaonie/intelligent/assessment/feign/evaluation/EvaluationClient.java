package com.yaonie.intelligent.assessment.feign.evaluation;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.app.AppQueryRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-13 16:20
 * @Author 武春利
 * @CreateTime 2024-09-13
 * @ClassName AppClient
 * @Project backend
 * @Description : App的Client
 */
@FeignClient(value = "evaluation-server", path = "/api/evaluation-server")
public interface EvaluationClient {
    /**
     * 分页获取应用列表（封装类）
     *
     * @param appQueryRequest
     * @return
     */
    @PostMapping("/user/list/page/vo")
    BaseResponse<Page<UserVO>> listAppVOByPage(@RequestBody AppQueryRequest appQueryRequest);

}
