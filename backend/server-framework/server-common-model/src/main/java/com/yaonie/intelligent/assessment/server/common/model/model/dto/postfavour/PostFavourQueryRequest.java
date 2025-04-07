package com.yaonie.intelligent.assessment.server.common.model.model.dto.postfavour;

import com.yaonie.intelligent.assessment.server.common.model.model.common.PageRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.post.PostQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 帖子收藏查询请求
 *
 * @author yaonie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostFavourQueryRequest extends PageRequest implements Serializable {

    /**
     * 帖子查询请求
     */
    private PostQueryRequest postQueryRequest;

    /**
     * 用户 id
     */
    private Long userId;

    @Serial
    private static final long serialVersionUID = 1L;
}