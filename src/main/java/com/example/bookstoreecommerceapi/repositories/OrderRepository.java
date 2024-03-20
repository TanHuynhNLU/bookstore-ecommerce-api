package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
