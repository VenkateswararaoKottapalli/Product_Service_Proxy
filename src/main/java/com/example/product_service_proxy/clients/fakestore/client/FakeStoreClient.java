package com.example.product_service_proxy.clients.fakestore.client;

import com.example.product_service_proxy.clients.IClientProductDto;
import com.example.product_service_proxy.clients.fakestore.dtos.FakeStoreProductDto;
import com.example.product_service_proxy.dtos.ProductDto;
import com.example.product_service_proxy.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreClient {
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

//    public <T> ResponseEntity<T> postForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<FakeStoreProductDto> responseType, Long uriVariables) throws RestClientException {
//        RestTemplate restTemplate= restTemplateBuilder.requestFactory(
//                HttpComponentsClientHttpRequestFactory.class
//        ).build();
//        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
//        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
//        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
//    }

    public <T> ResponseEntity<T> postForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<FakeStoreProductDto> responseType, Long uriVariables) throws RestClientException {
        RestTemplate restTemplate= restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
    public ResponseEntity<FakeStoreProductDto> getSingleProduct(Long id){
        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> productDto=restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto
                .class,id);
        return productDto;
    }

    public List<FakeStoreProductDto> getAllProducts(){
        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> productsList=restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class);
        return Arrays.asList(productsList.getBody());
    }

    public IClientProductDto addNewProduct(IClientProductDto productDto){
        RestTemplate restTemplate=restTemplateBuilder.build();
       restTemplate.postForEntity("https://fakestoreapi.com/products",productDto, ProductDto.class);
       return productDto;
    }

    public ResponseEntity<FakeStoreProductDto> deleteProduct(Long id){
        RestTemplate restTemplate=restTemplateBuilder.build();
        restTemplate.delete("https://fakestoreapi.com/products/{id}",id, ProductDto.class);
        return getSingleProduct(id);
    }

    public FakeStoreProductDto updateProduct(Long id, FakeStoreProductDto product){
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto=new FakeStoreProductDto();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImage());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setCategory(product.getCategory());

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity=
                postForEntity(HttpMethod.PATCH,
                        "https://fakestoreapi.com/products/{id}",
                        product,
                        FakeStoreProductDto.class,
                        id);

          return fakeStoreProductDtoResponseEntity.getBody();
    }
}
