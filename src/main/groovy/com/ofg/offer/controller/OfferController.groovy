package com.ofg.offer.controller

import com.codahale.metrics.Counter
import com.codahale.metrics.MetricRegistry
import com.ofg.db.Repository
import com.ofg.offer.beans.MarketingOfferRequestBean
import com.ofg.offer.beans.MarketingOfferTamplate
import com.ofg.offer.beans.Person
import com.ofg.service.MarketingOfferDecisionService
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.annotation.PostConstruct
import javax.validation.constraints.NotNull

import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.PUT

@Slf4j
@RestController
@RequestMapping('/api/marketing')
@Api(value = "offer", description = "Marketing offer proposals")
class OfferController {

    @Autowired
    MarketingOfferDecisionService decisionService

    @Autowired
    Repository repository

    @Autowired
    MetricRegistry metricRegistry

    Counter createOfferCounter

    Counter getOfferCounter

    @PostConstruct
    void init(){
        createOfferCounter = metricRegistry.counter('marketing.create.offer')
        getOfferCounter = metricRegistry.counter('marketing.get.offer')
    }
    
    @RequestMapping(value = '/{loanApplicationId}', method = PUT)
    @ApiOperation(value = "Marketing offer proposals for loan application", notes = "Will decide what to offer for the client")
    void createOffer(@PathVariable @NotNull String loanApplicationId, @RequestBody @NotNull MarketingOfferRequestBean requestBean) {
        createOfferCounter.inc()
        repository.insertNewMarketingOffer(requestBean, decisionService.createOffer(requestBean))
    }

    @RequestMapping(value = '/{firstName}_{lastName}', method = GET)
    @ApiOperation(value = "Marketing offer proposals retrieving for client", notes = "Will return decision")
    MarketingOfferTamplate getOffer(@PathVariable @NotNull String firstName, @PathVariable @NotNull String lastName) {
        getOfferCounter.inc()
        repository.getMarketingOffer(new Person(firstName: firstName, lastName: lastName))
    }
}
