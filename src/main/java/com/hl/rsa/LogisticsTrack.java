package com.hl.rsa;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>描述：单条物流信息数据实体列
 * <p>时间：2021/5/21
 * @author hh
 */
public final class LogisticsTrack {

    private static final long serialVersionUID = 1L;
    /**
     * 【必填】
     * 编号（序列1,2,3,4,5........）
     */
    private Long sortId;
    /**
     * 【必填】
     * 主订单编号(目前没有购物车功能，只赋值 ordermainId 订单号)
     */
    private String ordermainId;
    /**
     * 子主订单编号(目前没有购物车功能，此字段暂不赋值(不使用))
     */
    private String orderId;
    /**
     * 【必填】
     * 物流单号
     */
    private String logisticsNo;
    /**
     * 【必填】
     * 快递公司名称
     */
    private String expressName;
    /**
     * 【必填】
     * 物流类型  编码值
     * 编码   名称  描述
     * 1 - 消费 - 商家给客户发货
     * 2 - 退货 - 客户退货给商家
     * 3 - 补发 - 商家补发快递-物流丢失损坏等
     */
    private String logisticsTypeCode;
    /**
     * 【必填】
     * 物流类型   名称
     * 编码   名称  描述
     * 1 - 消费 - 商家给客户发货
     * 2 - 退货 - 客户退货给商家
     * 3 - 补发 - 商家补发快递-物流丢失损坏等
     */
    private String logisticsTypeName;
    /**
     * 【必填】
     * 出发地
     */
    private String originAddress;
    /**
     * 【必填】
     * 目的地
     */
    private String destinationsAddress;
    /**
     * 【必填】
     * 物流状态 状态码
     *
     * 状态值	名称	    描述
     * 00	 -   揽件	-    快件已由快递公司揽收
     * 01	 -   在途	-    快件处于运输过程中
     * 02	 -   签收	-    正常签收或者退回签收
     * 03	 -   异常	-    物流在运送途中异常
     */
    private String logisticsStatusCode;
    /**
     * 【必填】
     * 物流状态 名称
     * 状态值	名称	    描述
     * 00	 -   揽件	-    快件已由快递公司揽收
     * 01	 -   在途	-    快件处于运输过程中
     * 02	 -   签收	-    正常签收或者退回签收
     * 03	 -   异常	-    物流在运送途中异常
     */
    private String logisticsStatusName;
    /**
     * 【必填】
     * 物流信息的更新日期(yyyy-mm-dd hh:mm:ss)
     */
    private LocalDateTime logisticsUpdateTime;
    /**
     * 【必填】
     * 物流更新详情
     */
    private String logisticsDetail;
    /**
     * 【必填】
     * 物流当前位置
     */
    private String logisticsLocation;
    /**
     * 备注信息
     */
    private String logisticsRemark;
    /**
     * 包装单条物流数据时间戳(设置默认值为当前时间),添加 get 方法
     */
    private Long signDate;
    /**
     * 备用字段
     */
    private Map<String,Object> backMap;


    public LogisticsTrack() {
        //纳秒
        this.signDate = System.nanoTime();
    }

    public Long getSortId() {
        return sortId;
    }
    /**
     * <p>【必填】<p>
     * 编号（序列1,2,3,4,5........）
     */
    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    public String getOrdermainId() {
        return ordermainId;
    }
    /**
     * <p>【必填】<p>
     * 主订单编号(目前没有购物车功能，只赋值 ordermainId 订单号)
     */
    public void setOrdermainId(String ordermainId) {
        this.ordermainId = ordermainId;
    }

    public String getOrderId() {
        return orderId;
    }
    /**
     * 子主订单编号(目前没有购物车功能，此字段暂不赋值(不使用))
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }
    /**
     * <p>【必填】<p>
     * 物流单号
     */
    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getExpressName() {
        return expressName;
    }
    /**
     * <p>【必填】<p>
     * 快递公司名称
     */
    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getLogisticsTypeCode() {
        return logisticsTypeCode;
    }
    /**
     * <p>【必填】<br>
     * 物流类型  编码值<p>
     * 编码   名称  描述<br>
     * 1 - 消费 - 商家给客户发货<br>
     * 2 - 退货 - 客户退货给商家<br>
     * 3 - 补发 - 商家补发快递-物流丢失损坏等<br>
     */
    public void setLogisticsTypeCode(String logisticsTypeCode) {
        this.logisticsTypeCode = logisticsTypeCode;
    }

    public String getLogisticsTypeName() {
        return logisticsTypeName;
    }
    /**
     * <p>【必填】<br>
     * 物流类型   名称<p>
     * 编码   名称  描述<br>
     * 1 - 消费 - 商家给客户发货<br>
     * 2 - 退货 - 客户退货给商家<br>
     * 3 - 补发 - 商家补发快递-物流丢失损坏等<br>
     */
    public void setLogisticsTypeName(String logisticsTypeName) {
        this.logisticsTypeName = logisticsTypeName;
    }

    public String getOriginAddress() {
        return originAddress;
    }
    /**
     * <p>【必填】<p>
     * 出发地
     */
    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationsAddress() {
        return destinationsAddress;
    }
    /**
     *  <p>【必填】<p>
     * 目的地
     */
    public void setDestinationsAddress(String destinationsAddress) {
        this.destinationsAddress = destinationsAddress;
    }

    public String getLogisticsStatusCode() {
        return logisticsStatusCode;
    }
    /**
     * <p>【必填】<br>
     * 物流状态 状态值<p>
     *
     * 状态值	名称	    描述<br>
     * 00	 -   揽件	-    快件已由快递公司揽收<br>
     * 01	 -   在途	-    快件处于运输过程中<br>
     * 02	 -   签收	-    正常签收或者退回签收<br>
     * 03	 -   异常	-    物流在运送途中异常<br>
     */
    public void setLogisticsStatusCode(String logisticsStatusCode) {
        this.logisticsStatusCode = logisticsStatusCode;
    }

    public String getLogisticsStatusName() {
        return logisticsStatusName;
    }
    /**
     * <p>【必填】<br>
     * 物流状态——名称<p>
     * 状态值	名称	    描述<br>
     * 00 -   揽件  -  快件已由快递公司揽收<br>
     * 01 -   在途  -  快件处于运输过程中<br>
     * 02 -   签收  -  正常签收或者退回签收<br>
     * 03 -   异常  -  物流在运送途中异常<br>
     */
    public void setLogisticsStatusName(String logisticsStatusName) {
        this.logisticsStatusName = logisticsStatusName;
    }

    public LocalDateTime getLogisticsUpdateTime() {
        return logisticsUpdateTime;
    }
    /**
     * <p>【必填】<p>
     * 物流信息的更新日期(yyyy-mm-dd hh:mm:ss)
     */
    public void setLogisticsUpdateTime(LocalDateTime logisticsUpdateTime) {
        this.logisticsUpdateTime = logisticsUpdateTime;
    }

    public String getLogisticsDetail() {
        return logisticsDetail;
    }
    /**
     * <p>【必填】<p>
     * 物流更新详情
     */
    public void setLogisticsDetail(String logisticsDetail) {
        this.logisticsDetail = logisticsDetail;
    }

    public String getLogisticsLocation() {
        return logisticsLocation;
    }
    /**
     * <p>【必填】<p>
     * 物流当前位置
     */
    public void setLogisticsLocation(String logisticsLocation) {
        this.logisticsLocation = logisticsLocation;
    }

    public String getLogisticsRemark() {
        return logisticsRemark;
    }
    /**
     * 备注信息
     */
    public void setLogisticsRemark(String logisticsRemark) {
        this.logisticsRemark = logisticsRemark;
    }
    /**
     * 包装单条物流数据时间戳(设置默认值为当前时间),添加 get 方法
     */
    public Long getSignDate() {
        return signDate;
    }

    public Map<String, Object> getBackMap() {
        return backMap;
    }
    /**
     * 备用字段
     */
    public void setBackMap(Map<String, Object> backMap) {
        this.backMap = backMap;
    }

    @Override
    public String toString() {
        return "LogisticsTrack{" +
                "sortId=" + sortId +
                ", ordermainId='" + ordermainId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", logisticsNo='" + logisticsNo + '\'' +
                ", expressName='" + expressName + '\'' +
                ", logisticsTypeCode='" + logisticsTypeCode + '\'' +
                ", logisticsTypeName='" + logisticsTypeName + '\'' +
                ", originAddress='" + originAddress + '\'' +
                ", destinationsAddress='" + destinationsAddress + '\'' +
                ", logisticsStatusCode='" + logisticsStatusCode + '\'' +
                ", logisticsStatusName='" + logisticsStatusName + '\'' +
                ", logisticsUpdateTime=" + logisticsUpdateTime +
                ", logisticsDetail='" + logisticsDetail + '\'' +
                ", logisticsLocation='" + logisticsLocation + '\'' +
                ", logisticsRemark='" + logisticsRemark + '\'' +
                ", signDate=" + signDate +
                ", backMap=" + backMap +
                '}';
    }
}
