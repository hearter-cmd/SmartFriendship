package com.yaonie.intelligent.assessment.server.search_server.esdao;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.App;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-14 21:57
 * @Author 武春利
 * @CreateTime 2024-09-14
 * @ClassName AppEsDao
 * @Project backend
 * @Description : ES应用数据访问层
 */
public interface AppEsDao extends ElasticsearchRepository<App, Long> {
    List<App> findByUserId(Long userId);
}
