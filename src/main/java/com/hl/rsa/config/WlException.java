package com.hl.rsa.config;

/**
 * <p>描述：物流数据传输准用异常类
 * <p>时间：2021/5/21
 * @author hh
 */
public class WlException extends RuntimeException{

    public WlException(String message) {
        super(message);
    }

    public WlException(Throwable cause) {
        super(cause);
    }



}
