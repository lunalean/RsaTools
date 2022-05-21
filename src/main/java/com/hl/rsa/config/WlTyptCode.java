package com.hl.rsa.config;

/**
 * <p>描述：物流类型枚举类
 * <p>时间：2021/5/21
 *
 * @author hh
 */
public enum WlTyptCode {

    SEND("1","发货","商家给客户发货"),
    BACK("2","退货","客户退货给商家"),
    RESEND("3","补发","商家补发快递-物流丢失损坏等");

    /**
     * 码值
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 仅用于状态说明（物流状态描述）
     */
    private String annotation;

    WlTyptCode(String code, String name, String annotation) {
        this.code = code;
        this.name = name;
        this.annotation = annotation;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getAnnotation() {
        return annotation;
    }
}
