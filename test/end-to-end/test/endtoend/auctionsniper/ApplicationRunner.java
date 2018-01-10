package test.endtoend.auctionsniper;

import auctionsniper.AuctionSniper;
import auctionsniper.Main;

import static auctionsniper.ui.MainWindow.*;
import static test.endtoend.auctionsniper.FakeAuctionServer.XMPP_HOSTNAME;

public class ApplicationRunner {
  public static final String SNIPER_XMPP_ID = AuctionSniper.SNIPER_ID + "@" + XMPP_HOSTNAME + "/Auction";

  private AuctionSniperDriver driver;

  public void startBiddingIn(final FakeAuctionServer auction) {
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
    driver.showsSniperStatus(STATUS_JOINING);
  }

  public void showsSniperHasLostAuction() {
    driver.showsSniperStatus(STATUS_LOST);
  }

  public void stop() {
    if (driver != null) {
      driver.dispose();
    }
  }

  public void hasShownSniperIsBidding() {
    driver.showsSniperStatus(STATUS_BIDDING);
  }

  public void hasShownSniperIsWinning() {
    driver.showsSniperStatus(STATUS_WINNING);
  }

  public void showsSniperHasWonAuction() {
    driver.showsSniperStatus(STATUS_WON);
  }
}