package com.hl.rsa.config;

/**
 * <p>描述：返回状态码对应值
 * <p>时间：2021/5/21
 * @author hh
 */
public enum ResponseCode {
    OK("0000", "更新成功"),
    ERROR("1111", ""),
    ERROR01("0001", "签名校验失败"),
    ERROR02("0002", "请求参数解密失败"),
    ERROR03("0003", "订单号与已存在物流单号不匹配"),
    ERROR04("0004", "订单状态校验失败，订单状态不是已发货状态"),
    ERROR05("0005", "订单号不存在"),
    ERROR06("0006", "参数校验失败"),
    ERROR07("0007", "商户不存在"),
    ERROR15("0015", "请求参数为空"),
    ERROR16("0016", "商户ID不能为空"),
    ERROR17("0017", "请求数据为空"),
    ERROR18("0018", "物流密文数据时间戳超时"),
    ERROR19("0019", "获取签约商户公钥为空"),
    ERROR20("0020", "非物流签约商户"),
    ERROR21("0021", "当前请求账户非物流签约账户"),
    ERROR22("0022", "物流中含有非当前商户订单"),


    ERROR08("0008", "公钥为空"),
    ERROR09("0009", "私钥为空"),
    ERROR10("0010", "加密值为null"),
    ERROR11("0011", "解密值为空"),
    ERROR12("0012", "加签值为空"),
    ERROR13("0013", "验签值为null"),
    ERROR14("0014", "签名为空"),

    ERROR99("9999", "网络异常");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
