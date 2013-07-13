package auctionsniper;

import auctionsniper.ui.Column;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel implements SniperListener {
  public static final String INITIAL_ITEM_ID = "";

  private final static String[] STATUS_TEXT = {
      "Joining", "Bidding", "Winning", "Lost", "Won"
  };

  private SniperSnapshot snapshot = SniperSnapshot.joining(INITIAL_ITEM_ID);
  private static final int FIRST_ROW = 0;
  private static final int LAST_ROW = 0;

  @Override
  public int getRowCount() {
    return 1;
  }

  @Override
  public int getColumnCount() {
    return Column.values().length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return Column.at(columnIndex).valueIn(snapshot);
  }

  public static String textFor(SniperState state) {
    return STATUS_TEXT[state.ordinal()];
  }

  @Override
  public String getColumnName(int column) {
    return Column.at(column).name;
  }

  public void sniperStateChanged(SniperSnapshot newSnapshot) {
    this.snapshot = newSnapshot;
    fireTableRowsUpdated(FIRST_ROW, LAST_ROW);
  }
}