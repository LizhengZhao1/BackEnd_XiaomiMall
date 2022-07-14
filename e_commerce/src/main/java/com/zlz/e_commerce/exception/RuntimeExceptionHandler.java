package com.zlz.e_commerce.exception;

import com.zlz.e_commerce.enums.ResponseEnum;
import com.zlz.e_commerce.vo.ResponseVo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

import static com.zlz.e_commerce.enums.ResponseEnum.ERROR;
import static com.zlz.e_commerce.enums.ResponseEnum.NEED_LOGIN;

@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseVo handle(RuntimeException e) {
        return ResponseVo.error(ERROR, e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandle() {
        return ResponseVo.error(NEED_LOGIN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVo notValidExceptionHandle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Objects.requireNonNull(bindingResult.getFieldError().getField());
        return ResponseVo.error(ResponseEnum.PARAM_ERROR,
                bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage());
    }
}
