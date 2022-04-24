package br.com.rodpk.productapi.modules.product.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodpk.productapi.modules.product.dto.ProductStockDTO;
import br.com.rodpk.productapi.modules.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class ProductStockListener {


    @Autowired
    private ProductService service;


    // ouve diretamente a fila
    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDTO product) throws JsonProcessingException {
        log.info("Recebendo mensagem de estoque de produto: {}", new ObjectMapper().writeValueAsString(product));
        service.updateProductStock(product);
    }
    
}
