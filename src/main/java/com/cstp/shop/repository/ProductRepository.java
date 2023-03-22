package com.cstp.shop.repository;

import java.util.List;
import java.util.Optional;

import com.cstp.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);

    List<Product> findAllByCategory(String category);
}
