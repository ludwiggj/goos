package auctionsniper;

import auctionsniper.ui.Column;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
  public static final String INITIAL_ITEM_ID = "";

  private final static String[] STATUS_TEXT = {
      "Joining", "Bidding", "Winning", "Lost", "Won"
  };

  private SniperSnapshot snapshot = SniperSnapshot.joining(INITIAL_ITEM_ID);
  private static final int FIRST_ROW = 0;
  private static final int LAST_ROW = 0;

  public int getRowCount() {
    return 1;
  }

  public int getColumnCount() {
    return Column.values().length;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    return Column.at(columnIndex).valueIn(snapshot);
  }

  public static String textFor(SniperState state) {
    return STATUS_TEXT[state.ordinal()];
  }

  public void sniperStatusChanged(SniperSnapshot newSnapshot) {
    this.snapshot = newSnapshot;
    fireTableRowsUpdated(FIRST_ROW, LAST_ROW);
  }
}