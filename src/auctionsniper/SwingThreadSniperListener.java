package auctionsniper;

import javax.swing.*;

public class SwingThreadSniperListener implements SniperListener {
  private final SniperListener delegate;

  public SwingThreadSniperListener(SniperListener delegate) {
    this.delegate = delegate;
  }

  public void sniperStateChanged(final SniperSnapshot snapshot) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        delegate.sniperStateChanged(snapshot);
      }
    });
  }
}