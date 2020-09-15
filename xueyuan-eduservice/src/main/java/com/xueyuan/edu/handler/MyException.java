package com.xueyuan.edu.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor   //无参构造器
@AllArgsConstructor  //有参构造器
public class MyException extends RuntimeException {
    private Integer code;
    private String message;
}
