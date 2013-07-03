package test.endtoend.auctionsniper;

import auctionsniper.AuctionSniper;
import auctionsniper.Main;

import static auctionsniper.ui.MainWindow.*;
import static test.endtoend.auctionsniper.FakeAuctionServer.XMPP_HOSTNAME;

public class ApplicationRunner {
  public static final String SNIPER_XMPP_ID = AuctionSniper.SNIPER_ID + "@" + XMPP_HOSTNAME + "/Auction";

  private AuctionSniperDriver driver;
  private String itemId;

  public void startBiddingIn(final FakeAuctionServer auction) {
    itemId = auction.getItemId();
    Thread thread = new Thread("Test Application") {
      @Override
      public void run() {
        try {
          Main.main(XMPP_HOSTNAME, AuctionSniper.SNIPER_ID, AuctionSniper.SNIPER_PASSWORD, auction.getItemId());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    thread.setDaemon(true);
    thread.start();
    driver = new AuctionSniperDriver(1000);
    driver.showsSniperStatus(itemId, -1, -1, STATUS_JOINING);
  }

  public void showsSniperHasLostAuction() {
    driver.showsSniperStatus(itemId, -1, -1, STATUS_LOST);
  }

  public void stop() {
    if (driver != null) {
      driver.dispose();
    }
  }

  public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
    driver.showsSniperStatus(itemId, lastPrice, lastBid, STATUS_BIDDING);
  }

  public void hasShownSniperIsWinning(int winningBid) {
    driver.showsSniperStatus(itemId, winningBid, winningBid, STATUS_WINNING);
  }

  public void showsSniperHasWonAuction(int lastPrice) {
    driver.showsSniperStatus(itemId, lastPrice, lastPrice, STATUS_WON);
  }
}