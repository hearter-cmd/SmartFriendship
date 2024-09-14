package com.yaonie.intelligent.assessment.server.chat_server.controller;


import com.yaonie.intelligent.assessment.server.chat_server.entity.dto.SysSettingDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-13 11:53
 * @Author 武春利
 * @CreateTime 2024-09-13
 * @ClassName SystemController
 * @Project backend
 * @Description : TODO
 */
@Service
public class SystemController extends ABaseController{


    @GetMapping("/getSysSettings")
    public SysSettingDto getSysSettings(){
        return null;
    }



}
