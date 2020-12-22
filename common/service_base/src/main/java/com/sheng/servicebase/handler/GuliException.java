package com.sheng.servicebase.handler;

import com.sun.xml.internal.ws.wsdl.parser.RuntimeWSDLParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException {
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "对应的异常信息")
    private String msg;
}
