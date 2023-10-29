package com.example.product_service_proxy.models;

import com.example.product_service_proxy.dtos.RatingDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseModel{
   private String title;
   private double price;
   private String description;
   private Categories category;
   private String image;
   private RatingDto rating;
}
