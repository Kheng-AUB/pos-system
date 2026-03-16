package com.kheng.pos.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseApiResponse <T>{
    protected String code = "1";
    protected String status = "fail";
    protected String msg = "";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String msgDev;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected T data;

    public BaseApiResponse() {}

    public void isError(String code, String status, String msg) {
        this.code = code;
        this.status = status;
        this.msg = msg;
    }

    public void isError(String code, String status,String msg, String msgDev) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.msgDev = msgDev;
    }

    public void isSuccess() {
        this.code = "0";
        this.status = "success";
    }

    public void notFound(String msg) {
        this.code = "404";
        this.msg = "Resource not found";
        this.msgDev = msg;
    }

}
