package com.TestInterfaceDevelopMapper.model;

import lombok.Data;

@Data
public class ResultObject<T> {
    private String code; //返回码，0成功
    private String msg;  //返回描述
    private T data;      //返回数据
    private Long count;  //分页查询的总记录数

    //无参构造
    public ResultObject(){

    }

    //全参构造
    private ResultObject(String code, String msg, T data, Long count) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    //返回成功
    public static ResultObject<Object> success(){
        return new ResultObject<>("0","success",null,null);
    }
    //重载，返回指定成功信息
    public static ResultObject<Object> success(String mag){
        return new ResultObject<>("0",mag,null,null);
    }

    //返回失败
    public static ResultObject<Object> fail(){
        return new ResultObject<>("-1","fail",null,null);
    }
    //重载，返回指定失败信息
    public static ResultObject<Object> fail(String msg){
        return new ResultObject<>("-1",msg,null,null);
    }
}
