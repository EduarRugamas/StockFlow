package com.eduar.dev.inventory_service.service;

import com.eduar.dev.inventory_service.wrapper.enums.ProductCategory;
import com.eduar.dev.inventory_service.wrapper.exceptions.GlobalException;
import com.eduar.dev.inventory_service.wrapper.exceptions.GlobalExceptionHandler;
import com.eduar.dev.inventory_service.wrapper.exceptions.ProductNotFoundException;

import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduar.dev.inventory_service.dto.request.CreateProductRequest;
import com.eduar.dev.inventory_service.dto.response.ProductResponse;
import com.eduar.dev.inventory_service.entity.Product;
import com.eduar.dev.inventory_service.repository.ProductRepository;


@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse CreateProduct(CreateProductRequest request) {

        String normalizedSku = normalizeSku(request.sku());

        validateUniqueSku(normalizedSku);

        Product product = Product.builder()
                                .sku(normalizedSku)
                                .name(request.name())
                                .category(request.category())
                                .currentStock(request.currentStock())
                                .minStock(request.minStock())
                                .unitPrice(request.unitPrice())
                                .build();

        Product newProduct = productRepository.save(product);

        return new ProductResponse(
                    newProduct.getId(), 
                    normalizedSku, 
                    newProduct.getName(), 
                    newProduct.getCategory(), 
                    newProduct.getCurrentStock(), 
                    newProduct.getMinStock(), 
                    newProduct.getUnitPrice()
        );
    }

    public Page<ProductResponse> findAll(ProductCategory category, Pageable pageable) {
        Page<Product> products;
        
         if (category == null) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.findByCategory(
                    category,
                    pageable
            );
        }

        return products.map(product -> new ProductResponse(
                            product.getId(), 
                            product.getSku(), 
                            product.getName(), 
                            product.getCategory(), 
                            product.getCurrentStock(), 
                            product.getMinStock(), 
                            product.getUnitPrice())
                        );
    }

    public ProductResponse findByProductId(Long productId) {
        
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id: " + productId));

        return new ProductResponse(
            product.getId(), 
            product.getSku(), 
            product.getName(), 
            product.getCategory(), 
            product.getCurrentStock(), 
            product.getMinStock(), 
            product.getUnitPrice()
        );
    }


    private void validateUniqueSku(String sku) {
        if (productRepository.existsBySkuIgnoreCase(sku)) {
            throw new GlobalException(
                    "Ya existe un producto con el SKU: " + sku
            );
        }
    }

    private String normalizeSku(String sku) {
        return sku
                .trim()
                .toUpperCase(Locale.ROOT);
    }
    

}
