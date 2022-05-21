package com.hl.rsa;

/**
 * @Time： 2021/5/21
 * @Author： hh
 * @Description： 物流轨迹  接收请求数据结构（发送）
 */
public final class SendMode {

    /**
     * 商户ID【必填】
     */
    private String vendorId;
    /**
     * 签名sign
     */
    private String sign;
    /**
     * 时间
     */
    private Long time;
    /**
     * 物流信息数据（加密后的数据）
     */
    private String data;


    public String getVendorId() {
        return vendorId;
    }
    /**
     * <p>【必填】<p>
     * 商户ID
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getSign() {
        return sign;
    }
    /**
     * <p>【必填】<p>
     * 签名sign
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getTime() {
        return time;
    }
    /**
     * 时间
     */
    public void setTime(Long time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }
    /**
     * 物流信息数据（加密后的数据）
     */
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SendMode{" +
                "vendorId='" + vendorId + '\'' +
                ", sign='" + sign + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public SendMode(String vendorId, String sign, Long time, String data) {
        this.vendorId = vendorId;
        this.sign = sign;
        this.time = time;
        this.data = data;
    }

    public SendMode() {
    }
}
