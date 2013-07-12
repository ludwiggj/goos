package test.auctionsniper;

import auctionsniper.AuctionEventListener;
import auctionsniper.AuctionMessageTranslator;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static auctionsniper.AuctionEventListener.PriceSource;
import static auctionsniper.AuctionSniper.SNIPER_ID;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {
  private final Mockery context = new Mockery();
  private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
  private final AuctionMessageTranslator translator = new AuctionMessageTranslator(SNIPER_ID, listener);
  public static final Chat UNUSED_CHAT = null;

  @Test
  public void notifiesAuctionClosedWhenClosedMessageReceived() {
    context.checking(new Expectations() {{
      oneOf(listener).auctionClosed();
    }});

    Message message = new Message();
    message.setBody("SOLVersion: 1.1; Event: CLOSE;");

    translator.processMessage(UNUSED_CHAT, message);
  }

  @Test
  public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
    context.checking(new Expectations() {{
      exactly(1).of(listener).currentPrice(192, 7, PriceSource.FromOtherBidder);
    }});

    Message message = new Message();
    message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");

    translator.processMessage(UNUSED_CHAT, message);
  }

  @Test
  public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
    context.checking(new Expectations() {{
      exactly(1).of(listener).currentPrice(234, 5, PriceSource.FromSniper);
    }});

    Message message = new Message();
    message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: " + SNIPER_ID + ";");

    translator.processMessage(UNUSED_CHAT, message);
  }
}