package com.example.demo;

import com.example.config.RedisConfig;
import com.example.queue.MessagePublisher;
import com.example.queue.RedisMessageSubscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.UUID;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class RedisMessageListenerIntegrationTest {
 private MessagePublisher redisMessagePublisher;
 @Autowired 
  public void setMessagePublisher(MessagePublisher redisMessagePublisher) { 
    this.redisMessagePublisher = redisMessagePublisher;
  } 
  //  @Autowired  //  private MessagePublisherImpl redisMessagePublisher;
    @Test  
  public void testOnMessage() throws Exception {   
    String message = "Message test" + UUID.randomUUID();    
    redisMessagePublisher.publish(message);     
    redisMessagePublisher.publishTopic("test",message); 
    Thread.sleep(100);          
    assertTrue(RedisMessageSubscriber.messageList.get(0).contains(message));
  }}
