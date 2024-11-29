package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findByName(String name);

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM Book b " +
            "WHERE LOWER(CONVERT(b.name USING utf8mb4)) LIKE LOWER(CONCAT('%', CONVERT(:query USING utf8mb4), '%'))" +
            " OR LOWER(CONVERT(b.author USING utf8mb4)) LIKE LOWER(CONCAT('%', CONVERT(:query USING utf8mb4), '%'))" +
            " OR b.price <= :price",
            countQuery = "SELECT * FROM Book b " +
                    "WHERE LOWER(CONVERT(b.name USING utf8mb4)) LIKE LOWER(CONCAT('%', CONVERT(:query USING utf8mb4), '%'))" +
                    " OR LOWER(CONVERT(b.author USING utf8mb4)) LIKE LOWER(CONCAT('%', CONVERT(:query USING utf8mb4), '%'))" +
                    " OR b.price <= :price",
            nativeQuery = true)
    Page<Book> searchBooks(
            @Param("query") String query,
            @Param("price") long price,
            Pageable pageable
    );
}
