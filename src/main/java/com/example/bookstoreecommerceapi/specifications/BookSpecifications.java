package com.example.bookstoreecommerceapi.specifications;

import com.example.bookstoreecommerceapi.models.Book;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookSpecifications {
    public static Specification<Book> hasGenres(List<String> genres) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String genre : genres) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("genre")),
                        genre.toLowerCase()
                ));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Book> hasPublishers(List<String> publishers) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String publisher : publishers) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("publisher")),
                        publisher.toLowerCase()
                ));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Book> hasPriceRanges(List<String> priceRanges) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String range : priceRanges) {
                String[] limits = range.split("-");
                if (limits.length == 2) {
                    predicates.add(criteriaBuilder.between(root.get("price"), Integer.parseInt(limits[0]), Integer.parseInt(limits[1])));
                }else {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Integer.parseInt(limits[0])));
                }
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        });
    }
}
