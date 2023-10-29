package com.example.product_service_proxy.services;

import com.example.product_service_proxy.clients.IClientProductDto;
import com.example.product_service_proxy.clients.fakestore.dtos.FakeStoreProductDto;
import com.example.product_service_proxy.dtos.ProductDto;
import com.example.product_service_proxy.models.Product;

import java.util.List;

public interface ProductServiceInterface {
    Product getSingleProduct(Long id);

    List<Product> getAllProducts();

    Product addNewProduct(IClientProductDto productDto);

    Product updateProduct(Long id,FakeStoreProductDto product);

    Product deleteProduct(Long id);
}
