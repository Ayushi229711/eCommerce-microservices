package com.ayushi.order_service.Repository;


import com.ayushi.order_service.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
