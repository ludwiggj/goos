package auctionsniper;

import auctionsniper.ui.SniperState;

import static auctionsniper.AuctionEventListener.PriceSource.*;

public class AuctionSniper implements AuctionEventListener {
  public static final String SNIPER_ID = "sniper";
  public static final String SNIPER_PASSWORD = "sniper";
  private final SniperListener sniperListener;
  private final Auction auction;
  private String itemId;
  private boolean isWinning = false;

  public AuctionSniper(Auction auction, SniperListener sniperListener, String itemId) {
    this.sniperListener = sniperListener;
    this.auction = auction;
    this.itemId = itemId;
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
      final int bid = price + increment;
      auction.bid(bid);
      sniperListener.sniperBidding(new SniperState(itemId, price, bid));
    }
  }
}