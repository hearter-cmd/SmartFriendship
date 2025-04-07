package com.yaonie.intelligent.assessment.ai.service;


import com.yaonie.intelligent.assessment.ai.domain.model.po.CustomDocument;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-03-12
 */
@Service
public interface GroupVectorService {

    public void insert(String id, List<String> tags);

    public List<CustomDocument> similaritySearch(String keywords);

}
