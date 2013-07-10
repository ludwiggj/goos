package auctionsniper;

import com.google.common.base.Objects;

public class SniperState {
  public final String itemId;
  public final int lastPrice;
  public final int lastBid;

  public SniperState(String itemId, int lastPrice, int lastBid) {
    this.itemId = itemId;
    this.lastPrice = lastPrice;
    this.lastBid = lastBid;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof SniperState) {
      final SniperState other = (SniperState) obj;
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
}