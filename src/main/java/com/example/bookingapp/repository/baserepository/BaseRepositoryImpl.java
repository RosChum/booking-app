package com.example.bookingapp.repository.baserepository;

import com.example.bookingapp.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class BaseRepositoryImpl<Entity extends BaseEntity> extends SimpleJpaRepository<Entity, Long> implements BaseRepository<Entity> {

    public EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<Entity, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public void deleteById(Long aLong) {
        super.findById(aLong).ifPresent(entity ->
        {
            entity.setIsDeleted(true);
            super.save(entity);
        });

    }

    @Override
    public void deleteAll(Iterable<? extends Entity> entities) {
        entities.forEach(entity -> {
            entity.setIsDeleted(true);
            super.save(entity);
        });
        super.deleteAll(entities);
    }

    @Override
    public void delete(Entity entity) {
        entity.setIsDeleted(true);
        super.save(entity);
    }

    public void hardDeleteById(Long id){
        super.deleteById(id);
    }
}
