package test.endtoend.auctionsniper;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import static auctionsniper.ui.MainWindow.MAIN_WINDOW_NAME;
import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import static java.lang.String.valueOf;

@SuppressWarnings("unchecked")
public class AuctionSniperDriver extends JFrameDriver {
  public AuctionSniperDriver(int timeoutMillis) {
    super(new GesturePerformer(),
          JFrameDriver.topLevelFrame(
            named(MAIN_WINDOW_NAME),
            showingOnScreen()),
            new AWTEventQueueProber(timeoutMillis, 100));
  }

  public void showsSniperStatus(String itemId, int lastPrice, int lastBid, String statusText) {
    new JTableDriver(this).hasRow(
        matching(withLabelText(itemId), withLabelText(valueOf(lastPrice)),
            withLabelText(valueOf(lastBid)), withLabelText(statusText)));
  }

  public void showsSniperStatus(String itemId, String statusText) {
      new JTableDriver(this).hasRow(
          matching(withLabelText(itemId), withLabelText(statusText)));
    }
}