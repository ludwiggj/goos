package auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

import static auctionsniper.ui.MainWindow.STATUS_JOINING;

public class SnipersTableModel extends AbstractTableModel {
  private String statusText = STATUS_JOINING;

  public int getRowCount() {
    return 1;
  }

  public int getColumnCount() {
    return 1;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    return statusText;
  }

  public void setStatusText(String newStatusText) {
    statusText = newStatusText;
    fireTableRowsUpdated(0, 0);
  }
}
