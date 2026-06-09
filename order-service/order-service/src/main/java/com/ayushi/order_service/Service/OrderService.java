package com.ayushi.order_service.Service;


import com.ayushi.order_service.dto.OrderCreatedEvent;
import com.ayushi.order_service.dto.ProductResponse;
import com.ayushi.order_service.Entity.Order;
import com.ayushi.order_service.Repository.OrderRepository;
import com.ayushi.order_service.kafka.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final OrderProducer orderProducer;


    public Order createOrder(Order order) {


        ProductResponse product =
                restTemplate.getForObject(
                        "http://PRODUCT-SERVICE/products/" + order.getProductId(),
                        ProductResponse.class
                );

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Boolean inStock =
                restTemplate.getForObject(
                        "http://INVENTORY-SERVICE/inventory/"
                                + order.getProductId()
                                + "/"
                                + order.getQuantity(),
                        Boolean.class
                );

        if (inStock == null || !inStock) {
            throw new RuntimeException("Insufficient stock");
        }

        restTemplate.put(
                "http://INVENTORY-SERVICE/inventory/"
                        + order.getProductId()
                        + "/"
                        + order.getQuantity(),
                null
        );

//        order.setTotalPrice(
//                product.getPrice() * order.getQuantity()
//        );
//
//        return orderRepository.save(order);

        order.setTotalPrice(
                product.getPrice() * order.getQuantity()
        );

        Order savedOrder = orderRepository.save(order);

        orderProducer.publish(
                new OrderCreatedEvent(
                        savedOrder.getId(),
                        savedOrder.getProductId(),
                        savedOrder.getQuantity()
                )
        );

        return savedOrder;

    }



    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
