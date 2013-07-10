package auctionsniper;

import auctionsniper.ui.Column;

import javax.swing.table.AbstractTableModel;

import static auctionsniper.ui.MainWindow.*;

public class SnipersTableModel extends AbstractTableModel {
  public static final String STARTING_UP_ITEM_ID = "";
  public static final int STARTING_UP_LAST_PRICE = 0;
  public static final int STARTING_UP_LAST_BID = 0;

  private static String[] STATUS_TEXT = {STATUS_JOINING, STATUS_BIDDING, STATUS_WINNING, STATUS_LOST, STATUS_WON};

  private SniperSnapshot snapshot =
      new SniperSnapshot(STARTING_UP_ITEM_ID, STARTING_UP_LAST_PRICE, STARTING_UP_LAST_BID, SniperState.JOINING);
  private final static int FIRST_ROW = 0;
  private static final int LAST_ROW = 0;
  private String state = STATUS_JOINING;

  public int getRowCount() {
    return 1;
  }

  public int getColumnCount() {
    return Column.values().length;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    switch (Column.at(columnIndex)) {
      case ITEM_IDENTIFIER:
        return snapshot.itemId;
      case LAST_PRICE:
        return snapshot.lastPrice;
      case LAST_BID:
        return snapshot.lastBid;
      case SNIPER_STATE:
        return state;
      default:
        throw new IllegalArgumentException("No column at " + columnIndex);
    }
  }

  public void setStatusText(String newStatusText) {
    // Kludge follows...
    SniperSnapshot newSnapshot = new SniperSnapshot(this.snapshot.itemId, this.snapshot.lastPrice,
        this.snapshot.lastBid, SniperState.valueOf(newStatusText.toUpperCase()));
    sniperStatusChanged(newSnapshot);
  }

  public void sniperStatusChanged(SniperSnapshot newSnapshot) {
    this.snapshot = newSnapshot;
    this.state = STATUS_TEXT[newSnapshot.state.ordinal()];
    fireTableRowsUpdated(FIRST_ROW, LAST_ROW);
  }
}