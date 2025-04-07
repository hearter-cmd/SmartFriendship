package com.yaonie.intelligent.assessment.ai.event.listener;


import com.yaonie.intelligent.assessment.ai.domain.model.dto.match.MilvusAddDto;
import com.yaonie.intelligent.assessment.ai.service.MatchService;
import com.yaonie.intelligent.assessment.server.common.model.event.UpdateTagEvent;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-03-23
 */
@Component
public class UpdateTagListener {
    @Resource
    private MatchService matchService;

    @EventListener(classes = {UpdateTagEvent.class})
    public void updateTag(UpdateTagEvent event) {
        MilvusAddDto milvusAddDto = new MilvusAddDto();
        milvusAddDto.setTags(event.getTags());
        matchService.refresh(milvusAddDto);
    }



}
