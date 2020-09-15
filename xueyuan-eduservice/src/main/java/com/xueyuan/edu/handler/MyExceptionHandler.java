package com.xueyuan.edu.handler;

import com.xueyuan.edu.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
//异常处理
public class MyExceptionHandler {

    //对所有异常进行处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error();
    }

    //对特定异常进行处理
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public R error(NullPointerException e) {
        e.printStackTrace();
        return R.error().message("空指针异常了！！！");
    }

    //对自定义异常进行处理
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R error(MyException e) {
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
