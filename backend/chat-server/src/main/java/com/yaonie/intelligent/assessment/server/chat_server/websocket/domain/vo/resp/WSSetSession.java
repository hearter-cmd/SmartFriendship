package com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-18 23:45
 * @Description : TODO
 */
@Data
@AllArgsConstructor
public class WSSetSession implements Serializable {
    private String session;

    @Serial
    private static final long serialVersionUID = 1L;
}
