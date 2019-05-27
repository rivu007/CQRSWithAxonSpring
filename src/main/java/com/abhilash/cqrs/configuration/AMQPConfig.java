package com.abhilash.cqrs.configuration;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AMQPConfig {
  @Value("${axon.amqp.exchange}")
  private String taskExchangeName;

  @Bean
  TopicExchange variantExchange() {
    return new TopicExchange(taskExchangeName);
  }

  @Bean
  Queue variantQueue() {
    return new Queue("task-queue", true);
  }

  @Bean
  Binding productVariantBinding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("#");
  }

  //Need to use Spring boot 2.0.5-Release. See: https://groups.google.com/forum/#!topic/axonframework/COljLStu7x0
  @Bean
  public SpringAMQPMessageSource myQueueMessageSource(Serializer serializer) {
    return new SpringAMQPMessageSource(serializer) {

      @RabbitListener(queues = "task-queue")
      @Override
      public void onMessage(Message message, Channel channel) {
        log.debug("Event Received: {}");

        log.debug("Event Received: {}", message);
        super.onMessage(message, channel);
      }
    };
  }
}
