package com.teach.javafx.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse {  //DataResponse：响应用户请求打包返回的信息

    private int code;
    private String message;
    private Object data;

    public static DataResponse ok() {  //告知响应成功
        return new DataResponse(200,"ok",null);
    }

    public static DataResponse success(Object data) {  //返回响应参数
        return new DataResponse(200,"success",data);
    }

    public static DataResponse result(Object data) {  //返回多个结果
        return new DataResponse(200,"result",data);
    }

    public static DataResponse failure(int code, String msg) {  //告知操作失败，并返回原因
        return new DataResponse(code, msg,null);
    }

    public static DataResponse error(int code, Object data) {  //告知出现错误,并返回错误
        return new DataResponse(code,"error",data);
    }

    public static DataResponse noContent() {  //接收了请求，但没找到返回值
        return new DataResponse(204,"No Content",null);
    }

    public static DataResponse notFound() {  //告知资源未找到错误
        return new DataResponse(404, "Not Found",null);
    }
}
