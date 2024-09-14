package com.yaonie.intelligent_assessment_server.feign.evaluation;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent_assessment_server.common.BaseResponse;
import com.yaonie.intelligent_assessment_server.model.dto.app.AppQueryRequest;
import com.yaonie.intelligent_assessment_server.model.dto.file.UploadFileRequest;
import com.yaonie.intelligent_assessment_server.model.vo.AppVO;
import com.yaonie.intelligent_assessment_server.model.vo.UserVO;
import feign.Body;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-13 16:20
 * @Author 武春利
 * @CreateTime 2024-09-13
 * @ClassName AppClient
 * @Project backend
 * @Description : TODO
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
