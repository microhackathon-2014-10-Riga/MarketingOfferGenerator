package com.ofg.offer.beans

public class MarketingOfferTamplate {
    
    String marketingOffer

    public static final MarketingOfferTamplate GOOD_CLIENT = new MarketingOfferTamplate("Please have another loan")
    
    public static final MarketingOfferTamplate UNKNOWN_CLIENT = new MarketingOfferTamplate("We do not now you")
    
    public static final MarketingOfferTamplate FRAUD_CLIENT = new MarketingOfferTamplate("You should go to jail!")
    
    MarketingOfferTamplate(String marketingOffer) {
        this.marketingOffer = marketingOffer
    }
}
