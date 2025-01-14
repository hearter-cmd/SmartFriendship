package com.yaonie.intelligent.assessment.server.springbootinit.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent.assessment.server.common.holder.UserHolder;
import com.yaonie.intelligent.assessment.server.common.model.annotation.AuthCheck;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.DeleteRequest;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.AiGenerateQuestionRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.QuestionAddRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.QuestionContextDto;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.QuestionEditRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.QuestionQueryRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.QuestionUpdateRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.Question;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.QuestionVO;
import com.yaonie.intelligent.assessment.server.springbootinit.service.QuestionService;
import com.yaonie.intelligent.assessment.system.service.UserService;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 题目接口
 *
 * @author 77160
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    private Scheduler vipScheduler;


    // region 增删改查

    /**
     * 创建题目
     *
     * @param questionAddRequest 题目请求
     * @param request 请求
     * @return 新题目id
     */
    @PostMapping("/add")
    
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(questionAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 在此处将实体类和 DTO 进行转换
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        String questionContent = JSONUtil.toJsonStr(questionAddRequest.getQuestionContent());
        question.setQuestionContent(questionContent);
        // 数据校验
        questionService.validQuestion(question, true);
        // 填充默认值
        User loginUser = UserHolder.getUser();
        question.setUserId(loginUser.getId());
        // 写入数据库
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newQuestionId = question.getId();
        return ResultUtils.success(newQuestionId);
    }

    /**
     * 删除题目
     *
     * @param deleteRequest 删除请求
     * @param request 请求
     * @return 是否删除成功
     */
    @PostMapping("/delete")
    
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = UserHolder.getUser();
        long id = deleteRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestion.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = questionService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新题目（仅管理员可用）
     *
     * @param questionUpdateRequest 更新请求
     * @return 是否更新成功
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 在此处将实体类和 DTO 进行转换
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest, question);
        String questionContent = JSONUtil.toJsonStr(questionUpdateRequest.getQuestionContent());
        question.setQuestionContent(questionContent);
        // 数据校验
        questionService.validQuestion(question, false);
        // 判断是否存在
        long id = questionUpdateRequest.getId();
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = questionService.updateById(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取题目（封装类）
     *
     * @param id 题目 id
     * @return 题目
     */
    @GetMapping("/get/vo")
    public BaseResponse<QuestionVO> getQuestionVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Question question = questionService.getById(id);
        ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(questionService.getQuestionVO(question, request));
    }

    /**
     * 分页获取题目列表（仅管理员可用）
     *
     * @param questionQueryRequest 查询请求
     * @return 题目列表
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Question>> listQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 查询数据库
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionPage);
    }

    /**
     * 分页获取题目列表（封装类）
     *
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                               HttpServletRequest request) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        // 获取封装类
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage, request));
    }

    /**
     * 分页获取当前登录用户创建的题目列表
     *
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listMyQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(questionQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = UserHolder.getUser();
        questionQueryRequest.setUserId(loginUser.getId());
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        // 获取封装类
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage, request));
    }

    /**
     * 编辑题目（给用户使用）
     *
     * @param questionEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editQuestion(@RequestBody QuestionEditRequest questionEditRequest, HttpServletRequest request) {
        if (questionEditRequest == null || questionEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 在此处将实体类和 DTO 进行转换
        Question question = new Question();
        BeanUtils.copyProperties(questionEditRequest, question);
        String questionContent = JSONUtil.toJsonStr(questionEditRequest.getQuestionContent());
        question.setQuestionContent(questionContent);
        // 数据校验
        questionService.validQuestion(question, false);
        User loginUser = UserHolder.getUser();
        // 判断是否存在
        long id = questionEditRequest.getId();
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldQuestion.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = questionService.updateById(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 AI 生成题目（给用户使用）
     * @param aiGenerateQuestionRequest
     * @param request
     * @return
     */
    @PostMapping("/ai/generate")
    
    public BaseResponse<List<QuestionContextDto>> addQuestionByAi(@RequestBody AiGenerateQuestionRequest aiGenerateQuestionRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(aiGenerateQuestionRequest == null, ErrorCode.PARAMS_ERROR);
        List<QuestionContextDto> questionContextDtoList = questionService.generateQuestionByAi(aiGenerateQuestionRequest);

        return ResultUtils.success(questionContextDtoList);
    }

    /**
     * 根据 AI 生成题目（给用户使用）
     * @param aiGenerateQuestionRequest 对题目的要求封装
     * @param isVip 是否是 vip
     * @param request HttpServletRequest
     * @return 流式题目数据
     */
    @GetMapping("/ai/generate/sse")
    
    public SseEmitter generateQuestionByAiStream(AiGenerateQuestionRequest aiGenerateQuestionRequest, Boolean isVip, HttpServletRequest request) {
        ThrowUtils.throwIf(aiGenerateQuestionRequest == null, ErrorCode.PARAMS_ERROR);
        SseEmitter sseEmitter = new SseEmitter(0L);

        Flux<String> questionFlowable = questionService.generateQuestionByAiStream(aiGenerateQuestionRequest);

        AtomicInteger count = new AtomicInteger();
        StringBuilder result = new StringBuilder();
        Scheduler scheduler = Schedulers.io();
        if (isVip) {
            scheduler = vipScheduler;
        }
        questionFlowable
                // 去除特殊字符
                .map(modelData -> modelData.replaceAll("\\s", ""))
                // 过滤空字符串
                .filter(StringUtils::isNotBlank)
                // 将多个字符变为字符
                .flatMap(modelData -> {
                    ArrayList<Character> characters = new ArrayList<>();
                    for (char ch : modelData.toCharArray()) {
                        characters.add(ch);
                    }
                    return Flowable.fromIterable(characters);
                })
                // 进行字符匹配
                .doOnNext(modelData -> {
                    if ('{' == modelData) {
                        result.append(modelData);
                        // count ++
                        count.getAndIncrement();
                    } else if ('}' == modelData) {
                        result.append(modelData);
                        // count --
                        if (count.decrementAndGet() == 0) {
                            log.info("当前问题: {}", result.toString());
                            try {
                                sseEmitter.send(result.toString());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            result.setLength(0);
                        }
                    } else if (count.get() != 0) {
                        result.append(modelData);
                    }
                })
                .subscribeOn((reactor.core.scheduler.Scheduler) scheduler)
                .doOnError(error -> {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, error.getMessage());
                })
                .doOnComplete(sseEmitter::complete)
                .subscribe();

        return sseEmitter;
    }



    // endregion
}
