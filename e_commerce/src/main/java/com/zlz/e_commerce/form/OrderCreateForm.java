package com.zlz.e_commerce.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}
