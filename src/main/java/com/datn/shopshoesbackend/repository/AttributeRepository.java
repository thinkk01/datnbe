package com.datn.shopshoesbackend.repository;

import com.datn.shopshoesbackend.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    Attribute findByProduct_IdAndSize(Long productId, Integer size);

}
