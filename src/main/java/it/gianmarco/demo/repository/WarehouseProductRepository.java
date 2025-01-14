package it.gianmarco.demo.repository;

import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.entity.WarehouseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Long> {
    Optional<WarehouseProduct> findByWarehouseAndProduct(Warehouse warehouse, Product product);

}
