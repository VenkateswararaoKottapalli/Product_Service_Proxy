package com.example.product_service_proxy.clients.fakestore.dtos;

import com.example.product_service_proxy.clients.IClientProductDto;
import com.example.product_service_proxy.dtos.RatingDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FakeStoreProductDto implements IClientProductDto {
    private Long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private RatingDto rating;
}
