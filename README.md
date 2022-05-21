# RsaTools
Rsa加密解密工具集

## 字段说明LogisticsTrack
| 属性字段 | 属性类型 | 属性说明 | 是否必须 |
| --- | --- | --- | --- |
|sortId|Long|编号（推荐序列1,2,3,4,5........商户也可根据自己实际情况传输不重复数字，这个字段的数据商城不会记录，只参考）|【必填】|
|ordermainId|String|主订单编号(目前没有购物车功能，只赋值 ordermainId 订单号)|【必填】|
|orderId|String|子主订单编号(目前没有购物车功能，此字段暂不赋值(不使用))|
|logisticsNo|String|物流单号|【必填】|
|expressName|String|快递公司名称|【必填】|
|logisticsTypeCode|String|物流类型  编码值<br>编码   名称   描述<br>1 - 发货 - 商家给客户发货<br>2 - 退货 - 客户退货给商家<br>3 - 补发 - 商家补发快递-物流丢失损坏等|【必填】|
|logisticsTypeName|String|物流类型  名称<br>编码   名称  描述<br>1 - 发货 - 商家给客户发货<br>2 - 退货 - 客户退货给商家<br>3 - 补发 - 商家补发快递-物流丢失损坏等|【必填】|
|originAddress|String|出发地|【必填】|
|destinationsAddress|String|目的地|【必填】|
|logisticsStatusCode|String|物流状态 状态码<br>状态值 名称     描述<br>00  -   揽件 -    快件已由快递公司揽收<br>01  -   在途 -    快件处于运输过程中<br>02  -   签收 -    正常签收或者退回签收<br>03  -   异常 -    物流异常（具体异常信息可在物流实体类的备注中说明）|【必填】|
|logisticsStatusName|String|物流状态 状态值<br>状态值 名称     描述<br>00  -   揽件 -    快件已由快递公司揽收<br>01  -   在途 -    快件处于运输过程中<br>02  -   签收 -    正常签收或者退回签收<br>03  -   异常 -    物流异常（具体异常信息可在物流实体类的备注中说明）|【必填】|
|logisticsUpdateTime|LocalDateTime|物流信息的更新日期|【必填】|
|logisticsDetail|String|物流更新详情|【必填】|
|logisticsLocation|String|物流当前位置|【必填】|
|logisticsRemark|String|备注信息|
|signDate|Long|包装单条物流数据时间戳(不需要设置，没有set方法，构造方法中自动赋值)|
|backMap|Map<String,Object>|备用存储数据字段（目前阶段暂不使用）|
