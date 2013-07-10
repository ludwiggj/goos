package test.endtoend.auctionsniper;

import auctionsniper.SniperSnapshot;
import org.junit.After;
import org.junit.Test;

import static auctionsniper.SniperSnapshot.INITIAL_LAST_PRICE;

public class AuctionSniperEndToEndTest {
  private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
  private final ApplicationRunner application = new ApplicationRunner();

  @Test
  public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
    auction.startSellingItem();
    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
    auction.announceClosed();

    // This test currently fails because auction item id is not shown
    application.showsSniperHasLostAuction(SniperSnapshot.INITIAL_LAST_BID, INITIAL_LAST_PRICE);
  }

  @Test
  public void sniperMakesAHigherBidButLoses() throws Exception {
    final int increment = 98;
    final int currentPrice = 1000;
    final int raisedBid = currentPrice + increment;

    auction.startSellingItem();
    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
    auction.reportPrice(currentPrice, increment, "other bidder");
    application.hasShownSniperIsBidding(currentPrice, raisedBid);
    auction.hasReceivedBid(raisedBid, ApplicationRunner.SNIPER_XMPP_ID);
    auction.announceClosed();

    // Sniper loses auction because although he bid highest, the auction hasn't reported this as
    // the winning bid via auction.reportPrice() - a mistake?
    application.showsSniperHasLostAuction(currentPrice, raisedBid);
  }

  @Test
  public void sniperWinsAnAuctionByBiddingHigher() throws Exception {
    final int firstIncrement = 98;
    final int secondIncrement = 97;
    final int currentPrice = 1000;
    final int raisedBid = currentPrice + firstIncrement;

    auction.startSellingItem();
    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
    auction.reportPrice(currentPrice, firstIncrement, "other bidder");
    application.hasShownSniperIsBidding(currentPrice, raisedBid);
    auction.hasReceivedBid(raisedBid, ApplicationRunner.SNIPER_XMPP_ID);
    auction.reportPrice(raisedBid, secondIncrement, ApplicationRunner.SNIPER_XMPP_ID);
    application.hasShownSniperIsWinning(raisedBid);
    auction.announceClosed();
    application.showsSniperHasWonAuction(raisedBid);
  }

  @After public void stopAuction() {
    auction.stop();
  }
  @After public void stopApplication() {
    application.stop();
  }
}