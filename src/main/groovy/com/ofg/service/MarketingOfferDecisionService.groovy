package com.ofg.service

import com.ofg.offer.beans.MarketingOfferRequestBean
import com.ofg.offer.beans.MarketingOfferTamplate
import org.springframework.stereotype.Component

import static com.ofg.offer.beans.MarketingOfferTamplate.FRAUD_CLIENT
import static com.ofg.offer.beans.MarketingOfferTamplate.GOOD_CLIENT

@Component
public class MarketingOfferDecisionService {
    
    public MarketingOfferTamplate createOffer(MarketingOfferRequestBean requestBean) {
        if (requestBean.decision.equals('true')) {
            return GOOD_CLIENT
        }
        return FRAUD_CLIENT
    }
}
