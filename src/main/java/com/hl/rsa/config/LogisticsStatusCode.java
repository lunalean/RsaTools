package com.hl.rsa.config;

/**
 * <p>描述：订单状态描述
 * <p>时间：2021/5/21
 * @author hh
 */
public enum LogisticsStatusCode {
    /**
     * "00", "揽件", "快件已由快递公司揽收"
     */
    COLLECT("00", "揽件", "快件已由快递公司揽收"),
    /**
     * "01", "在途", "快件正在运输过程中"
     */
    DELIVERY("01", "在途", "快件正在运输过程中"),
    /**
     * "02", "签收", "客户签收"
     */
    SIGN_FOR("02", "签收", "客户签收"),
    /**
     * "03", "异常", "物流在运送途中异常或客户拒收等"
     */
    EXCEPTION("03", "异常", "物流异常");


    /**
     * 码值
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 仅用于状态说明（具体异常信息可在物流实体类的备注中说明）
     */
    private String annotation;

    LogisticsStatusCode(String code, String name, String annotation) {
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
