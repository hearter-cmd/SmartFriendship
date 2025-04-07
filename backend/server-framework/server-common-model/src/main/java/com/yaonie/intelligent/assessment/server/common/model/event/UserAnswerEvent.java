package com.yaonie.intelligent.assessment.server.common.model.event;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.UserAnswer;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-04-05
 */
@Getter
public class UserAnswerEvent extends ApplicationEvent {
    private final UserAnswer userAnswer;
    public UserAnswerEvent(UserAnswer userAnswer) {
        super(userAnswer);
        this.userAnswer = userAnswer;
    }
}
