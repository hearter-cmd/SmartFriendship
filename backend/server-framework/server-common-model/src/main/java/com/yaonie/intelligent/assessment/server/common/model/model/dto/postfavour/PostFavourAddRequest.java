package com.yaonie.intelligent.assessment.server.common.model.model.dto.postfavour;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 帖子收藏 / 取消收藏请求
 *
 * @author yaonie
 */
@Data
public class PostFavourAddRequest implements Serializable {

    /**
     * 帖子 id
     */
    private Long postId;

    @Serial
    private static final long serialVersionUID = 1L;
}