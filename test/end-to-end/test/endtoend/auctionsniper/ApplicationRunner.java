package test.endtoend.auctionsniper;

import auctionsniper.AuctionSniper;
import auctionsniper.Main;
import auctionsniper.ui.MainWindow;

import static auctionsniper.SniperSnapshot.INITIAL_LAST_BID;
import static auctionsniper.SniperSnapshot.INITIAL_LAST_PRICE;
import static auctionsniper.SniperState.*;
import static auctionsniper.SnipersTableModel.INITIAL_ITEM_ID;
import static auctionsniper.SnipersTableModel.textFor;
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
          Main.main(XMPP_HOSTNAME, AuctionSniper.SNIPER_ID, AuctionSniper.SNIPER_PASSWORD, itemId);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    thread.setDaemon(true);
    thread.start();
    driver = new AuctionSniperDriver(1000);

    driver.hasTitle(MainWindow.APPLICATION_TITLE);
    driver.hasColumnTitles();

    //NOTE: Item-id is not shown by UI at this point
    driver.showsSniperStatus(INITIAL_ITEM_ID, INITIAL_LAST_PRICE,
        INITIAL_LAST_BID, textFor(JOINING));
  }

  public void showsSniperHasLostAuction(int lastPrice, int lastBid) {
    driver.showsSniperStatus(itemId, lastPrice, lastBid, textFor(LOST));
  }

  public void stop() {
    if (driver != null) {
      driver.dispose();
    }
  }

  public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
    driver.showsSniperStatus(itemId, lastPrice, lastBid, textFor(BIDDING));
  }

  public void hasShownSniperIsWinning(int winningBid) {
    driver.showsSniperStatus(itemId, winningBid, winningBid, textFor(WINNING));
  }

  public void showsSniperHasWonAuction(int lastPrice) {
    driver.showsSniperStatus(itemId, lastPrice, lastPrice, textFor(WON));
  }
}