package com.back.boundedContext.market.domain.policy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarketPolicy {
    public static double PRODUCT_PAYOUT_RATE;

    @Value("${custom.market.product.payoutRate}")
    public void setProductPayoutRate(double rate) {
        PRODUCT_PAYOUT_RATE = rate;
    }

    public static long calculatePayoutFee(long salePrice, double payoutRate) {
        return salePrice - calculateFee(salePrice, payoutRate);
    }

    public static long calculateFee(long salePrice, double payoutRate) {
        return Math.round(salePrice * payoutRate / 100);
    }
}
