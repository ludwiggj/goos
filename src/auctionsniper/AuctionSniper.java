package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
  public static final String SNIPER_ID = "sniper";
  public static final String SNIPER_PASSWORD = "sniper";
  private final SniperListener sniperListener;
  private final Auction auction;

  public AuctionSniper(Auction auction, SniperListener sniperListener) {
    this.sniperListener = sniperListener;
    this.auction = auction;
  }

  public void auctionClosed() {
    sniperListener.sniperLost();
  }

  public void currentPrice(int price, int increment, PriceSource priceSource) {
    switch (priceSource) {
      case FromSniper:
        sniperListener.sniperWinning();
        break;
      case FromOtherBidder:
        auction.bid(price + increment);
        sniperListener.sniperBidding();
        break;
    }
  }
}
