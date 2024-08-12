package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);

    @Query("SELECT o FROM Order o WHERE YEAR(o.dateCreated) = :currentYear AND o.status = :status")
    List<Order> findCompletedOrdersByCurrentYear(@Param("currentYear") int currentYear, @Param("status") String status);
}
