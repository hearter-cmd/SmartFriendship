package com.yaonie.intelligent.assessment.server.search_server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.model.entity.evaluation.Question;
import com.yaonie.intelligent.assessment.server.search_server.mapper.QuestionMapper;
import com.yaonie.intelligent.assessment.server.search_server.service.QuestionService;
import org.springframework.stereotype.Service;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 17:56
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName QuestionServiceImpl
 * @Project backend
 * @Description : TODO
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
}
