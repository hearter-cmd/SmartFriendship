package com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.enums;


import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSBlack;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSFriendApply;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSLoginSuccess;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSMemberChange;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSMessage;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSMsgIdUrl;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSMsgMark;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSMsgRecall;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSOlineOfflineNotify;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 14:12
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName WSRespTypeEnum
 * @Project backend
 * @Description : 
 */
@AllArgsConstructor
@Getter
public enum WSRespTypeEnum {
    LOGIN_URL( 1, WSMsgIdUrl.class),
    LOGIN_SCAN_SUCCESS( 2,  null),
    LOGIN_SUCCESS( 3, WSLoginSuccess.class),
    MESSAGE( 4, WSMessage.class),
    ONLINE_OFFLINE_NOTIFY( 5, WSOlineOfflineNotify.class),
    INVALIDATE_TOKEN( 6,  null),
    BLACK( 7, WSBlack.class),
    MARK( 8, WSMsgMark.class),
    RECALL( 9, WSMsgRecall.class),
    APPLY( 10, WSFriendApply.class),
    MEMBER_CHANGE( 11, WSMemberChange.class);

    private final Integer type;
    private final Class<?> dataClass;

    private static Map<Integer, WSRespTypeEnum> typeCache;


}
