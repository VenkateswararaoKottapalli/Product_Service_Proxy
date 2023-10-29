package com.example.product_service_proxy.controllers;

import com.example.product_service_proxy.clients.fakestore.dtos.FakeStoreProductDto;
import com.example.product_service_proxy.dtos.ProductDto;
import com.example.product_service_proxy.models.Categories;
import com.example.product_service_proxy.models.Product;
import com.example.product_service_proxy.services.FakeStoreProductService;
import com.example.product_service_proxy.services.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductServiceInterface productServiceInterface;
    @GetMapping("/{productid}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable("productid") Long id){
        try {
            MultiValueMap<String,String> header=new LinkedMultiValueMap<>();
            header.add("Hi","Hello");
            header.add("authcode","authentication code");
            if (id < 1)
                throw new IllegalArgumentException("Something went wrong");
            Product product = productServiceInterface.getSingleProduct(id);
            ProductDto productDto=getProductDto(product);
            ResponseEntity<ProductDto> responseEntity=new ResponseEntity<>(productDto,header,HttpStatus.OK);
            return responseEntity;
        }
        catch (Exception e){
           // return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        List<Product> products=productServiceInterface.getAllProducts();

        List<ProductDto> productList = new ArrayList<>();
        for (Product productDto : products) {
            ProductDto product = new ProductDto();
            product.setId(productDto.getId());
            product.setTitle(productDto.getTitle());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
           /* Categories categories = new Categories();
            categories.setName(productDto.getCategory());*/
            product.setCategory(productDto.getCategory().getName());
            product.setImage(productDto.getImage());
            product.setRating(productDto.getRating());
            productList.add(product);
        }
        return productList;
    }

    @PostMapping()
    public ResponseEntity<ProductDto> addNewProduct(@RequestBody FakeStoreProductDto productDto){
        Product product= productServiceInterface.addNewProduct(productDto);
        ProductDto productDto1=getProductDto(product);
        ResponseEntity<ProductDto> responseEntity=new ResponseEntity<>(productDto1,HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id){
        Product product= productServiceInterface.deleteProduct(id);
        ProductDto productDto=getProductDto(product);
        ResponseEntity<ProductDto> responseEntity=new ResponseEntity<>(productDto,HttpStatus.OK);
        return responseEntity;
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id,@RequestBody FakeStoreProductDto product){
        Product product1= productServiceInterface.updateProduct(id,product);

        return product1;
    }

    public ProductDto getProductDto(Product productDto){
        ProductDto product=new ProductDto();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
      /*  Categories categories=new Categories();
        categories.setName(productDto.getCategory());*/
        product.setCategory(productDto.getCategory().getName());
        product.setImage(productDto.getImage());
        product.setRating(productDto.getRating());

        return product;
    }
//    @ExceptionHandler({IllegalArgumentException.class,NullPointerException.class})
//    public ResponseEntity<String> handleException(Exception e){
//        return new ResponseEntity<>("Printing error message from seperate exception handler",HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
