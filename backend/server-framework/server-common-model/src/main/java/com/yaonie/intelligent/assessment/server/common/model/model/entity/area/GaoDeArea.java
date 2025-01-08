package com.yaonie.intelligent.assessment.server.common.model.model.entity.area;


import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-18 14:49
 * @Author 武春利
 * @CreateTime 2024-09-18
 * @ClassName GaoDeResp
 * @Project backend
 * @Description : 高德返回结果
 */
@Data
@Builder
public class GaoDeArea implements Serializable {
    /**
     * 返回结果状态值
     * 值为0或1,0表示失败；1表示成功
     */
    private int status;
    /**
     * 返回状态说明
     * 返回状态说明，status 为0时，info 返回错误原因，否则返回“OK”。
     */
    private String info;
    /**
     * 状态码
     * 返回状态说明,10000代表正确,详情参阅 info 状态表
     */
    private String infocode;
    /**
     * 省份名称
     * 若为直辖市则显示直辖市名称；
     * 如果在局域网 IP 网段内，则返回“局域网”；
     * 非法 IP 以及国外 IP 则返回空
     */
    private String province;
    /**
     * 城市名称
     * 若为直辖市则显示直辖市名称；
     * 如果为局域网网段内 IP 或者非法 IP 或国外 IP，则返回空
     */
    private String city;
    /**
     * 城市的 adcode 编码
     * adcode 信息可参考 城市编码表 获取
     */
    private String adcode;
    /**
     * 所在城市矩形区域范围
     * 所在城市范围的左下右上对标对
     */
    private String rectangle;

    @Serial
    private static final long serialVersionUID = 1L;

}
