package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Order;
import com.example.bookstoreecommerceapi.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

}
