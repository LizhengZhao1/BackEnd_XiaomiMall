package com.zlz.e_commerce.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterForm {
//    @NotBlank//用于String判断空格
//    @NotEmpty 用于集合
//    @NoNull
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;
}
