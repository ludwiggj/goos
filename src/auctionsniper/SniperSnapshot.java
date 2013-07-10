package auctionsniper;

import com.google.common.base.Objects;

import static auctionsniper.SniperState.BIDDING;
import static auctionsniper.SniperState.JOINING;
import static auctionsniper.SniperState.WINNING;

public class SniperSnapshot {
  public static final int INITIAL_LAST_PRICE = 0;
  public static final int INITIAL_LAST_BID = 0;

  public final String itemId;
  public final int lastPrice;
  public final int lastBid;
  public final SniperState state;

  public SniperSnapshot(String itemId, int lastPrice, int lastBid, SniperState state) {
    this.itemId = itemId;
    this.lastPrice = lastPrice;
    this.lastBid = lastBid;
    this.state = state;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof SniperSnapshot) {
      final SniperSnapshot other = (SniperSnapshot) obj;
      return Objects.equal(itemId, other.itemId)
          && lastPrice == other.lastPrice
          && lastBid == other.lastBid;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(itemId, lastPrice, lastBid);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("itemId", itemId)
        .add("lastPrice", lastPrice)
        .add("lastBid", lastBid)
        .toString();
  }

  public static SniperSnapshot joining(String itemId) {
    return new SniperSnapshot(itemId, INITIAL_LAST_PRICE, INITIAL_LAST_BID, JOINING);
  }

  public SniperSnapshot bidding(int newLastPrice, int newLastBid) {
    return new SniperSnapshot(itemId, newLastPrice, newLastBid, BIDDING);
  }

  public SniperSnapshot winning(int newLastPrice) {
    return new SniperSnapshot(itemId, newLastPrice, lastBid, WINNING);
  }

  public SniperSnapshot setState(String state) {
    return new SniperSnapshot(itemId, lastPrice, lastBid, SniperState.valueOf(state.toUpperCase()));
  }
}