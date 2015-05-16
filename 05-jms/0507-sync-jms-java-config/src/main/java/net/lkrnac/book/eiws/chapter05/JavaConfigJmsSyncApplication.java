package net.lkrnac.book.eiws.chapter05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class JavaConfigJmsSyncApplication {
  private static final String SIMPLE_MESSAGE = "simple message";
  private static final Logger LOG = LoggerFactory
      .getLogger(JavaConfigJmsSyncApplication.class);

  @Autowired
  private SimpleMessageSender messageSender;

  public static void main(String[] args) throws InterruptedException {
    SpringApplication.run(JavaConfigJmsSyncApplication.class, args);
  }

  @Bean
  public SimpleMessageHandler simpleMessageHandler() {
    return new SimpleMessageHandler() {
      @Override
      public void handleMessage(String message) {
        LOG.info("Message Received: {}", message);
      }
    };
  }

  @Scheduled(fixedRate = 1000L)
  public void sendMessage() throws InterruptedException {
    LOG.info("Sending message: {}", SIMPLE_MESSAGE);
    messageSender.send(SIMPLE_MESSAGE);
  }
}
