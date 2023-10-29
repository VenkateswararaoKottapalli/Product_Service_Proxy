package com.example.product_service_proxy.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto {
    private double rate;
    private int count;
}
