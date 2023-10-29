package com.example.product_service_proxy.services;

import com.example.product_service_proxy.clients.IClientProductDto;
import com.example.product_service_proxy.clients.fakestore.client.FakeStoreClient;
import com.example.product_service_proxy.clients.fakestore.dtos.FakeStoreProductDto;
import com.example.product_service_proxy.dtos.ProductDto;
import com.example.product_service_proxy.models.Categories;
import com.example.product_service_proxy.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductServiceInterface {
    //@Autowired
    private RestTemplateBuilder restTemplateBuilder;
    private FakeStoreClient fakeStoreClient;
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder, FakeStoreClient fakeStoreClient){
        this.restTemplateBuilder=restTemplateBuilder;
        this.fakeStoreClient=fakeStoreClient;
       // restTemplateBuilder.rootUri("https://fakestoreapi.com/products");
    }
    @Override
    public Product getSingleProduct(Long id){
//        RestTemplate restTemplate=restTemplateBuilder.build();
       ResponseEntity<FakeStoreProductDto> productDto=fakeStoreClient.getSingleProduct(id);
        Product product=getProduct(productDto.getBody());
        return product;
    }
    @Override
    public List<Product> getAllProducts() {
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        ResponseEntity<ProductDto[]> productDtos = restTemplate.getForEntity("https://fakestoreapi.com/products", ProductDto[].class);
//        List<Product> productList = new ArrayList<>();

        List<FakeStoreProductDto> productDtos=fakeStoreClient.getAllProducts();
        List<Product> productList = new ArrayList<>();
        for (FakeStoreProductDto productDto : productDtos) {
            Product product = new Product();
            product.setId(productDto.getId());
            product.setTitle(productDto.getTitle());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            Categories categories = new Categories();
            categories.setName(productDto.getCategory());
            product.setCategory(categories);
            product.setImage(productDto.getImage());
            product.setRating(productDto.getRating());
            productList.add(product);
        }

        return productList;
    }

    @Override
    public Product addNewProduct(IClientProductDto productDto){
//       RestTemplate restTemplate=restTemplateBuilder.build();
//        restTemplate.postForEntity("https://fakestoreapi.com/products",productDto,ProductDto.class);
//       Product product=getProduct(productDto);
        IClientProductDto productDto1=fakeStoreClient.addNewProduct(productDto);
        Product product=getProduct((FakeStoreProductDto)productDto);
       return product;
    }

    @Override
    public Product updateProduct(Long id,FakeStoreProductDto product){
        FakeStoreProductDto fakeStoreProductDto= fakeStoreClient.updateProduct(id,product);
        Product product1=getProduct(fakeStoreProductDto);
        return product1;
    }
    @Override
    public Product deleteProduct(Long id){
        ResponseEntity<FakeStoreProductDto> productDto=fakeStoreClient.deleteProduct(id);
        Product product=getProduct(productDto.getBody());
        return product;
    }

    public Product getProduct(FakeStoreProductDto productDto){
        Product product=new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        Categories categories=new Categories();
        categories.setName(productDto.getCategory());
        product.setCategory(categories);
        product.setImage(productDto.getImage());
        product.setRating(productDto.getRating());

        return product;
    }
}
