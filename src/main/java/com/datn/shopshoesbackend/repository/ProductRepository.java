package com.datn.shopshoesbackend.repository;

import com.datn.shopshoesbackend.domain.dto.ResponseProductDto;
import com.datn.shopshoesbackend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new com.datn.shopshoesbackend.domain.dto.ResponseProductDto(p.id, p.name, p.code, p.description, p.view, a.price, i.imageLink, p.brand.name, p.sale.discount, p.isActive) FROM Product p " +
            "INNER JOIN Attribute a ON p.id = a.product.id " +
            "INNER JOIN Image i ON p.id = i.product.id WHERE a.size = ?1 AND i.name = ?2 AND p.isActive = ?3")
    Page<ResponseProductDto> getAllProducts(@Param("size") Integer size,
                                            @Param("name") String name,
                                            @Param("active") Boolean active,
                                            Pageable pageable);

}
