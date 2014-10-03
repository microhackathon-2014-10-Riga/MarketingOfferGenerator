package com.ofg.db

import com.codahale.metrics.Counter
import com.codahale.metrics.MetricRegistry
import com.ofg.offer.beans.MarketingOfferRequestBean
import com.ofg.offer.beans.MarketingOfferTamplate
import com.ofg.offer.beans.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import java.util.concurrent.ConcurrentHashMap

import static com.ofg.offer.beans.MarketingOfferTamplate.UNKNOWN_CLIENT

@Component
public class Repository {

    @Autowired
    MetricRegistry metricRegistry

    Counter noClientCounter

    @PostConstruct
    void init() {
        noClientCounter = metricRegistry.counter('marketing.offer.not.found')
    }

    private ConcurrentHashMap<String, MarketingOfferTamplate> decisions = new ConcurrentHashMap<>()

    public void insertNewMarketingOffer(MarketingOfferRequestBean requestBean, MarketingOfferTamplate tamplate) {
        decisions.put(requestBean.person.toString(), tamplate)
    }

    public MarketingOfferTamplate getMarketingOffer(Person person) {
        def template = decisions.get(person.toString())
        if (!template) {
            noClientCounter.inc()
            return UNKNOWN_CLIENT
        }
        return template
    }
}
