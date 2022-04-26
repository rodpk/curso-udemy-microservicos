package br.com.rodpk.productapi.modules.sales.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.rodpk.productapi.modules.sales.dto.SalesConfirmationDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SalesConfirmationSender {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    public void sendSalesConfirmationMessage(SalesConfirmationDTO message) {
        try {
            log.info("sending message: {}", new ObjectMapper().writeValueAsString(message));
            rabbitTemplate.convertAndSend(productTopicExchange, salesConfirmationKey, message);
            log.info("message was sent successfully!");
        } catch(Exception ex) {
            log.error("error while trying to send sales confirmation message.", ex);
        }
    }
}
