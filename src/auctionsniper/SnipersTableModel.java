package auctionsniper;

import auctionsniper.ui.Column;

import javax.swing.table.AbstractTableModel;

import static auctionsniper.ui.MainWindow.STATUS_JOINING;

public class SnipersTableModel extends AbstractTableModel {
  public static final String STARTING_UP_ITEM_ID = "";
  public static final int STARTING_UP_LAST_PRICE = 0;
  public static final int STARTING_UP_LAST_BID = 0;

  private final static SniperState STARTING_UP = new SniperState(STARTING_UP_ITEM_ID, STARTING_UP_LAST_PRICE, STARTING_UP_LAST_BID);
  private String statusText = STATUS_JOINING;
  private SniperState sniperState = STARTING_UP;

  private final static int FIRST_ROW = 0;
  private static final int LAST_ROW = 0;

  public int getRowCount() {
    return 1;
  }

  public int getColumnCount() {
    return Column.values().length;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    switch (Column.at(columnIndex)) {
      case ITEM_IDENTIFIER:
        return sniperState.itemId;
      case LAST_PRICE:
        return sniperState.lastPrice;
      case LAST_BID:
        return sniperState.lastBid;
      case SNIPER_STATUS:
        return statusText;
      default:
        throw new IllegalArgumentException("No column at " + columnIndex);
    }
  }

  public void setStatusText(String newStatusText) {
    statusText = newStatusText;
    fireTableRowsUpdated(FIRST_ROW, LAST_ROW);
  }

  public void sniperStatusChanged(SniperState newSniperState, String newStatusText) {
    sniperState = newSniperState;
    statusText = newStatusText;
    fireTableRowsUpdated(FIRST_ROW, LAST_ROW);
  }
}