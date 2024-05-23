package com.example.bookingapp.specification;

import com.example.bookingapp.dto.baseDto.BaseSearchDto;
import com.example.bookingapp.entity.BaseEntity_;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
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

    public static <T> Specification<T> greaterThanOrEqualTo(SingularAttribute<T, Integer> field, Integer value) {
        return checkForNull(value, () ->
                (root, query, criteriaBuilder) -> {
                    query.distinct(true);

                    return criteriaBuilder.greaterThanOrEqualTo(root.get(field), value);

                });
    }


    public static <T> Specification<T> greaterThanOrEqualTo(SingularAttribute<T, Double> field, Double value) {
        return checkForNull(value, () ->
                (root, query, criteriaBuilder) -> {
                    query.distinct(true);

                    return criteriaBuilder.greaterThanOrEqualTo(root.get(field), value);

                });
    }

    public static <T> Specification<T> between(SingularAttribute<T, BigDecimal> field, BigDecimal minValue, BigDecimal maxValue) {
        return checkForNull(minValue, maxValue, () ->
                (root, query, criteriaBuilder) -> {
                    query.distinct(true);

                    return criteriaBuilder.between(root.get(field), minValue, maxValue);

                });
    }


    public static <T> Specification<T> greaterThanOrEqualTo(SingularAttribute<T, BigDecimal> field, BigDecimal value) {
        return checkForNull(value, () ->
                (root, query, criteriaBuilder) -> {
                    query.distinct(true);

                    return criteriaBuilder.greaterThanOrEqualTo(root.get(field), value);

                });
    }

    private static <T, V> Specification<T> checkForNull(V value, Supplier<Specification<T>> supplier) {
        return value == null ? (((root, query, criteriaBuilder) -> null)) : supplier.get();
    }


    private static <T, V, R> Specification<T> checkForNull(V value, R value2, Supplier<Specification<T>> supplier) {
        return value == null && value2 == null ? (((root, query, criteriaBuilder) -> null)) : supplier.get();
    }
}
