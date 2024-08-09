package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
