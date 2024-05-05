package com.example.bookingapp.specification;

import com.example.bookingapp.dto.baseDto.BaseSearchDto;
import com.example.bookingapp.entity.BaseEntity_;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.function.Supplier;

public class BaseSpecification {

    public static Specification getBaseSpecification(BaseSearchDto searchDto) {
        return equal(BaseEntity_.id, searchDto.getId()).and(equal(BaseEntity_.isDeleted, searchDto.getIsDeleted()));
    }

    public static <T, V> Specification<T> equal(SingularAttribute<T, V> field, V value) {
        return checkForNull(value, () ->
                (root, query, criteriaBuilder) -> {
                    query.distinct(true);
                    return criteriaBuilder.equal(root.get(field), value);
                });
    }

    public static <T> Specification<T> like(SingularAttribute<T, String> field, String value) {
        return checkForNull(value, () ->
                (root, query, criteriaBuilder) -> {
                    query.distinct(true);
                    return criteriaBuilder.like(root.get(field), "%" + value + "%");
                });
    }


    public static <T> Specification<T> between(SingularAttribute<T, ZonedDateTime> field,
                                               ZonedDateTime timeFrom, ZonedDateTime timeTo) {
        if (timeTo == null && timeFrom == null) {
            return (root, query, criteriaBuilder) ->
            {

                return criteriaBuilder.in(root.get(field));
            };
        }
        if (timeFrom == null) {
            return (root, query, criteriaBuilder) -> {
                return criteriaBuilder.lessThanOrEqualTo(root.get(field), timeTo);
            };
        }
        if (timeTo == null) {
            return (root, query, criteriaBuilder) -> {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(field), timeFrom);
            };

        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(field), timeFrom, timeTo);
    }

    private static <T, V> Specification<T> checkForNull(V value, Supplier<Specification<T>> supplier) {
        return value == null ? (((root, query, criteriaBuilder) -> null)) : supplier.get();
    }


    private static <T, V, R> Specification<T> checkForNull(V value, R value2, Supplier<Specification<T>> supplier) {
        return value == null && value2 == null ? (((root, query, criteriaBuilder) -> null)) : supplier.get();
    }
}
