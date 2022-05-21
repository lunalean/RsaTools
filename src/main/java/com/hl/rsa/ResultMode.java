package com.hl.rsa;

import com.hl.rsa.config.ResponseCode;

/**
 * <p>描述：接收响应数据结构（返回数据）
 * <p>时间：2021/5/21
 * @author hh
 */
public class ResultMode {
    private String code;
    private String message;
    private Long time;
    private Object data;

    public ResultMode() {
    }

    public ResultMode(String code, String message, Long time, Object data) {
        this.code = code;
        this.message = message;
        this.time = time;
        this.data = data;
    }

    /**
     * 默认返回状态：成功： 0000
     * @return ResultMode 返回实体类
     */
    public static ResultMode OK(){
        long time = System.currentTimeMillis();
        return new ResultMode(ResponseCode.OK.getCode(),ResponseCode.OK.getMessage(),time,"");
    }
    /**
     * 默认返回状态：成功： 0000
     * @param message 指定返回说明
     * @return ResultMode 返回实体类
     */
    public static ResultMode OK(String message){
        long time = System.currentTimeMillis();
        return new ResultMode(ResponseCode.OK.getCode(),message,time,"");
    }
    /**
     * 默认返回状态：成功： 0000
     * @param data 指定返回数据
     * @return ResultMode 返回实体类
     */
    public static ResultMode OK(Object data){
        long time = System.currentTimeMillis();
        return new ResultMode(ResponseCode.OK.getCode(),ResponseCode.OK.getMessage(),time,data);
    }
    /**
     * 默认返回状态：成功： 0000
     * @param message 自定义提示信息
     * @param data 指定返回数据
     * @return ResultMode 返回实体类
     */
    public static ResultMode OK(String message,Object data){
        long time = System.currentTimeMillis();
        return new ResultMode(ResponseCode.OK.getCode(),message,time,data);
    }

    /**
     * 返回状态：失败(代码 9999——网络异常)
     * @return ResultMode 返回实体类
     */
    public static ResultMode ERROR(){
        long time = System.currentTimeMillis();
        return new ResultMode(ResponseCode.ERROR99.getCode(),ResponseCode.ERROR99.getMessage(),time,"");
    }

    /**
     * 返回状态：失败
     * @param responseCode 错误代码
     * @return ResultMode 返回实体类
     */
    public static ResultMode ERROR(ResponseCode responseCode){
        long time = System.currentTimeMillis();
        return new ResultMode(responseCode.getCode(),responseCode.getMessage(),time,"");
    }
    /**
     * 返回状态：失败
     * @param responseCode 错误代码
     * @param data 自定义数据
     * @return ResultMode 返回实体类
     */
    public static ResultMode ERROR(ResponseCode responseCode,Object data){
        long time = System.currentTimeMillis();
        return new ResultMode(responseCode.getCode(),responseCode.getMessage(),time,data);
    }
    /**
     * 返回状态：失败
     * @param code 自定义错误code码值
     * @param message 自定义提示信息
     * @param data 自定义数据
     * @return ResultMode 返回实体类
     */
    public static ResultMode ERROR(String code,String message,Object data){
        long time = System.currentTimeMillis();
        return new ResultMode(code,message,time,data);
    }
    /**
     * 返回状态：失败
     * @param responseCode 错误代码
     * @param message 自定义提示信息
     * @return ResultMode 返回实体类
     */
    public static ResultMode ERROR(ResponseCode responseCode,String message){
        long time = System.currentTimeMillis();
        return new ResultMode(responseCode.getCode(),message,time,"");
    }
    /**
     * 返回状态：失败
     * @param message 自定义提示信息
     * @return ResultMode 返回实体类
     */
    public static ResultMode ERROR(String message){
        long time = System.currentTimeMillis();
        return new ResultMode(ResponseCode.ERROR.getCode(),message,time,"");
    }
    /**
     * 返回状态：失败
     * @param message 自定义提示信息
     * @param data 自定义数据
     * @return ResultMode 返回实体类
     */
    public static ResultMode ERROR(String message,Object data){
        long time = System.currentTimeMillis();
        return new ResultMode(ResponseCode.ERROR.getCode(),message,time,data);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultMode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
