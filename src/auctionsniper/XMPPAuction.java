package auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import static auctionsniper.Main.*;

public class XMPPAuction implements Auction {
    private Chat chat;

    public XMPPAuction(Chat chat) {
      this.chat = chat;
    }

    public void bid(int amount) {
      sendMessage(String.format(BID_COMMAND_FORMAT, amount));
    }

    public void join() {
      sendMessage(JOIN_COMMAND_FORMAT);
    }

    private void sendMessage(String message) {
      try {
        chat.sendMessage(message);
      } catch (XMPPException e) {
        e.printStackTrace();  //TODO: Auto-generated
      }
    }
  }