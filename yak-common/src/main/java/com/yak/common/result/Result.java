package com.yak.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "统一相应结果")
public class Result<T> {
    @ApiModelProperty(value = "状态码", example = "200")
    private int code;

    @ApiModelProperty(value = "提示信息", example = "success")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    // 快速构建成功响应
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    // 快速构建失败响应
    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    // 新增无数据成功方法
    public static Result<Void> success() {
        Result<Void> result = new Result<>();
        result.setCode(200);
        result.setMsg("success");
        return result;
    }

    // 链式调用支持（可选）
    public Result<T> msg(String customMsg) {
        this.msg = customMsg;
        return this;
    }
}
