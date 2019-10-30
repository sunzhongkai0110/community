package com.szk.community.controller.dto;

import lombok.Data;

/**
 * dto: data transform object数据传输对象
 * 参数多于两个时,不要使用形参传入,而是封装成一个对象
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
