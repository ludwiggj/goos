package auctionsniper;

import static auctionsniper.AuctionEventListener.PriceSource.*;

public class AuctionSniper implements AuctionEventListener {
  public static final String SNIPER_ID = "sniper";
  public static final String SNIPER_PASSWORD = "sniper";
  private final SniperListener sniperListener;
  private final Auction auction;
  private boolean isWinning = false;
  private SniperSnapshot snapshot;

  public AuctionSniper(String itemId, Auction auction, SniperListener sniperListener) {
    this.sniperListener = sniperListener;
    this.auction = auction;
    this.snapshot = SniperSnapshot.joining(itemId);
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
      snapshot = snapshot.winning(price);
    } else {
      final int bid = price + increment;
      auction.bid(bid);
      snapshot = snapshot.bidding(price, bid);
    }
    sniperListener.sniperStateChanged(snapshot);
  }
}