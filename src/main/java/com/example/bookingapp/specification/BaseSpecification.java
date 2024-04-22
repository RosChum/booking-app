package com.example.bookingapp.specification;

import com.example.bookingapp.dto.baseDto.BaseSearchDto;
import com.example.bookingapp.entity.BaseEntity_;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Supplier;

public class BaseSpecification {

    public static Specification getBaseSpecification(BaseSearchDto searchDto) {
        return equal(BaseEntity_.id, searchDto.getId()).and(equal(BaseEntity_.isDeleted, searchDto.getIsDeleted()));
    }

    public static <T, V> Specification<T> equal(SingularAttribute<T, V> field, V value) {
        return checkForNull(value, () ->
                (root, query, criteriaBuilder) -> {
                    return criteriaBuilder.equal(root.get(field), value);
                });
    }

    private static <T, V> Specification<T> checkForNull(V value, Supplier<Specification<T>> supplier) {
        return value == null ? (((root, query, criteriaBuilder) -> null)) : supplier.get();
    }
}
