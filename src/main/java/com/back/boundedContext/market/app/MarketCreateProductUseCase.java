package com.back.boundedContext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Product;
import com.back.boundedContext.market.out.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketCreateProductUseCase {
	private final ProductRepository productRepository;

	public Product createProduct(
		MarketMember seller,
		String sourceTypeCode,
		int sourceId,
		String name,
		String description,
		long price,
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
