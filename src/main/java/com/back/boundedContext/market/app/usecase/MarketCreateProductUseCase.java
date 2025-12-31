package com.back.boundedContext.market.app.usecase;

import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Product;
import com.back.boundedContext.market.out.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCreateProductUseCase {
    private final ProductRepository productRepository;

    public Product createProduct(
            MarketMember seller,
            String sourceTypeCode,
            Long sourceId,
            String name,
            String description,
            long price, //금액은 단위가 커질수있기 때문에 int -> long
            long salePrice
    ) {
        Product product = new Product(
                seller,
                sourceTypeCode,
                sourceId,
                name,
                description,
                price,
                salePrice
        );

        return productRepository.save(product);
    }
}