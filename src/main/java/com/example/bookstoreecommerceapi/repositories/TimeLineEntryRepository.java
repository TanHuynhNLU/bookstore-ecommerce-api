package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.TimeLineEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeLineEntryRepository extends JpaRepository<TimeLineEntry, Long> {
}
