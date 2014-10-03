package com.ofg.db

import com.ofg.offer.beans.MarketingOfferRequestBean
import com.ofg.offer.beans.MarketingOfferTamplate
import com.ofg.offer.beans.Person
import org.springframework.stereotype.Component

import java.util.concurrent.ConcurrentHashMap

import static com.ofg.offer.beans.MarketingOfferTamplate.UNKNOWN_CLIENT

@Component
public class Repository {

    private ConcurrentHashMap<String, MarketingOfferTamplate> decisions = new ConcurrentHashMap<>()

    public void insertNewMarketingOffer(MarketingOfferRequestBean requestBean, MarketingOfferTamplate tamplate) {
        decisions.put(requestBean.person.toString(), tamplate)
    }

    public MarketingOfferTamplate getMarketingOffer(Person person) {
        def template = decisions.get(person.toString())
        if (!template) {
            return UNKNOWN_CLIENT
        }
        return template
    }
}
