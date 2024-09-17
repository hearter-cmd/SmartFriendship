package com.yaonie.intelligent.assessment.server.springbootinit.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.AiGenerateQuestionRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.QuestionContextDto;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.QuestionQueryRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.App;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.Question;
import com.yaonie.intelligent.assessment.server.common.model.model.enums.AppTypeEnum;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.QuestionVO;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import com.yaonie.intelligent.assessment.server.springbootinit.mapper.QuestionMapper;
import com.yaonie.intelligent.assessment.server.springbootinit.service.AppService;
import com.yaonie.intelligent.assessment.server.springbootinit.service.QuestionService;
import com.yaonie.intelligent.assessment.server.springbootinit.service.UserService;
import com.yaonie.intelligent.assessment.server.springbootinit.utils.SqlUtils;
import com.yaonie.intelligent.assessment.server.springbootinit.utils.ZhiPuUtils;
import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Flowable;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 题目服务实现
 *
 * @author 77160
 */
@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Resource
    private UserService userService;

    @Resource
    private ZhiPuUtils zhiPuUtils;

    @Resource
    private AppService appService;

    /**
     * 校验数据
     *
     * @param question 题目
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validQuestion(Question question, boolean add) {
        ThrowUtils.throwIf(question == null, ErrorCode.PARAMS_ERROR);
        String questionContent = question.getQuestionContent();
        Long appId = question.getAppId();
        Long userId = question.getUserId();
        // 创建数据时，参数不能为空
        if (add) {
            // todo 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(questionContent), ErrorCode.PARAMS_ERROR, "题目内容不能为空");
            ThrowUtils.throwIf(appId == null, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        }
        // 修改数据时，有参数则校验
        // todo 补充校验规则
        ThrowUtils.throwIf(StringUtils.isBlank(questionContent), ErrorCode.PARAMS_ERROR, "问题不能为空");
        ThrowUtils.throwIf(appId == null || appId < 0, ErrorCode.PARAMS_ERROR, "应用ID非法");
        if (userId != null) {
            User user = userService.getById(userId);
            ThrowUtils.throwIf(user == null, ErrorCode.PARAMS_ERROR, "用户不存在");
        }
    }

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest 查询条件
     * @return 查询条件
     */
    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if (questionQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = questionQueryRequest.getId();
        QuestionContextDto questionContent = questionQueryRequest.getQuestionContent();
        Long appId = questionQueryRequest.getAppId();
        Long notId = questionQueryRequest.getNotId();
        String searchText = questionQueryRequest.getSearchText();
        Long userId = questionQueryRequest.getUserId();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();
        // 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("questionContent", searchText).or());
        }
        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(appId), "appId", appId);
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionContent), "questionContent", JSONUtil.toJsonStr(questionContent));
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取题目封装
     *
     * @param question
     * @param request
     * @return
     */
    @Override
    public QuestionVO getQuestionVO(Question question, HttpServletRequest request) {
        // 对象转封装类
        QuestionVO questionVO = QuestionVO.objToVo(question);
        // 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Long userId = question.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        questionVO.setUser(userVO);
        // endregion

        return questionVO;
    }

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request) {
        List<Question> questionList = questionPage.getRecords();
        Page<QuestionVO> questionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if (CollUtil.isEmpty(questionList)) {
            return questionVOPage;
        }
        // 对象列表 => 封装对象列表
        List<QuestionVO> questionVOList = questionList.stream().map(question -> {
            return QuestionVO.objToVo(question);
        }).collect(Collectors.toList());

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionList.stream().map(Question::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        questionVOList.forEach(questionVO -> {
            Long userId = questionVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionVO.setUser(userService.getUserVO(user));
        });
        // endregion

        questionVOPage.setRecords(questionVOList);
        return questionVOPage;
    }

    /**
     * 生成题目部分
     */
    private static final String GENERATE_QUESTION_SYSTEM_MESSAGE = "你是一位出题专家，按以下格式我会给你如下信息：\n" +
            "```\n" +
            "应用名称，\n" +
            "【【【应用描述】】】，\n" +
            "应用类别，\n" +
            "要生成的题目数，\n" +
            "每个题目的选项数\n" +
            "```\n" +
            "\n" +
            "请你根据上述信息，按照以下步骤来出题：\n" +
            "1. 要求：题目和选项尽可能地短，题目不要包含序号，每题的选项数以我提供的为主，题目不能重复\n" +
            "2. 严格按照下面的 json 格式输出题目和选项\n" +
            "3. 应用描述就是主要用途\n" +
            "```\n" +
            "[{\"options\":[{\"value\":\"选项内容\",\"key\":\"A\"},{\"value\":\"\",\"key\":\"B\"}],\"title\":\"题目标题\"}]\n" +
            "```\n" +
            "title 是题目，options 是选项，每个选项的 key 按照英文字母序（比如 A、B、C、D）以此类推，value 是选项内容\n" +
            "4. 检查题目是否包含序号，若包含序号则去除序号\n" +
            "5. 返回的题目列表格式必须为 JSON 数组\n" +
            "6. 如果题目中包含特殊字符，请删除特殊字符\n" +
            "...7. 坚决不要反问我任何问题, 只需要直接给出结果...";

    private String getGenerateQuestionUserMessage(App app, int questionNumber, int optionNumber) {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(app.getAppName()).append("\n");
        userMessage.append(app.getAppDesc()).append("\n");
        userMessage.append(AppTypeEnum.getEnumByValue(app.getAppType()).getText()).append("类").append("\n");
        userMessage.append(questionNumber).append("\n");
        userMessage.append(optionNumber).append("\n");
//        if (StringUtils.isNotBlank(sampleData)) {
//            userMessage.append("，|||").append(sampleData).append("|||").append("\n");
//        }
        return userMessage.toString();
    }

    @Override
    public List<QuestionContextDto> generateQuestionByAi(AiGenerateQuestionRequest aiGenerateQuestionRequest) {
//        QuestionQueryRequest questionQueryRequest = new QuestionQueryRequest();
//        questionQueryRequest.setAppId(aiGenerateQuestionRequest.getAppId());
//        Question list = getOne(getQueryWrapper(questionQueryRequest));
//        String sampleData = "aaa";/*JSONUtil.toJsonStr(list.getQuestionContent());*/

        Long appId = aiGenerateQuestionRequest.getAppId();
        // 获取参数
        int questionNumber = aiGenerateQuestionRequest.getQuestionNumber();
        int optionNumber = aiGenerateQuestionRequest.getOptionNumber();
        App app = appService.getById(appId);
        // 获取prompt
        //  sampleData
        String userMessage = getGenerateQuestionUserMessage(app, questionNumber, optionNumber);
        String result = zhiPuUtils.doRequest(GENERATE_QUESTION_SYSTEM_MESSAGE, userMessage, null);
        int start = result.indexOf("[");
        int end = result.lastIndexOf("]");
        result = result.substring(start, end+1);
        System.out.println(result);
        return JSONUtil.toList(result, QuestionContextDto.class);
    }

    @Override
    public Flowable<ModelData> generateQuestionByAiStream(AiGenerateQuestionRequest aiGenerateQuestionRequest) {
        // 1. 获取需要的数据
        Long appId = aiGenerateQuestionRequest.getAppId();
        int questionNumber = aiGenerateQuestionRequest.getQuestionNumber();
        int optionNumber = aiGenerateQuestionRequest.getOptionNumber();
        // 2. 通过appId获取APP对象
        App app = appService.getById(appId);
        // 3. 获取生成题目的prompt
        String generateQuestionUserMessage = getGenerateQuestionUserMessage(app, questionNumber, optionNumber);
        // 4. 通过AI生成流式数据
        return zhiPuUtils.doStreamRequest(GENERATE_QUESTION_SYSTEM_MESSAGE, generateQuestionUserMessage, null);
    }

    @Override
    public void removeByAppId(Long appId) {
        QuestionQueryRequest questionQueryRequest = new QuestionQueryRequest();
        questionQueryRequest.setAppId(appId);
        remove(getQueryWrapper(questionQueryRequest));
    }
    // 生成题目结束



}
