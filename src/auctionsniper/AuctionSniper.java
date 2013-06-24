package auctionsniper;

import static auctionsniper.AuctionEventListener.PriceSource.*;

public class AuctionSniper implements AuctionEventListener {
  public static final String SNIPER_ID = "sniper";
  public static final String SNIPER_PASSWORD = "sniper";
  private final SniperListener sniperListener;
  private final Auction auction;
  private boolean isWinning = false;

  public AuctionSniper(Auction auction, SniperListener sniperListener) {
    this.sniperListener = sniperListener;
    this.auction = auction;
  }

  public void auctionClosed() {
    if (isWinning) {
      sniperListener.sniperWon();
    } else {
      sniperListener.sniperLost();
    }
  }

  public void currentPrice(int price, int increment, PriceSource priceSource) {
    isWinning = priceSource == FromSniper;
    if (isWinning) {
      sniperListener.sniperWinning();
    } else {
      auction.bid(price + increment);
      sniperListener.sniperBidding();
    }
  }
}
