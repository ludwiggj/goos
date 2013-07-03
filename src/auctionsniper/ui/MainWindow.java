package auctionsniper.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainWindow extends JFrame {
  public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
  public static final String SNIPER_TABLE_NAME = "Auction Sniper Table";
  public static final String SNIPER_STATUS_NAME = "sniper status";
  public static final String STATUS_JOINING = "Joining";
  public static final String STATUS_LOST = "Lost";
  public static final String STATUS_WON = "Won";
  public static final String STATUS_BIDDING = "Bidding";
  public static final String STATUS_WINNING = "Winning";

  private final JLabel sniperStatus = createLabel(STATUS_JOINING);

  private final SnipersTableModel snipers = new SnipersTableModel();
  public MainWindow() {
    super("Auction Sniper");
    setName(MAIN_WINDOW_NAME);
//    add(sniperStatus);
    fillContentPane(makeSnipersTable());
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  private void fillContentPane(JTable snipersTable) {
    final Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
  }

  private JTable makeSnipersTable() {
    final JTable snipersTable = new JTable(snipers);
    snipersTable.setName(SNIPER_TABLE_NAME);
    return snipersTable;
  }

  private JLabel createLabel(String initialText) {
    JLabel result = new JLabel(initialText);
    result.setName(SNIPER_STATUS_NAME);
    result.setBorder(new LineBorder(Color.BLACK));
    return result;
  }

  public void showStatusText(String statusText) {
//    sniperStatus.setText(status);
    snipers.setStatusText(statusText);
  }
}