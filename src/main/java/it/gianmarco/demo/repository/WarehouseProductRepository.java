package it.gianmarco.demo.repository;

import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.entity.WarehouseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Long> {
    Optional<WarehouseProduct> findByWarehouseAndProduct(Warehouse warehouse, Product product);
    @Query("SELECT p FROM WarehouseProduct wp JOIN Product p ON p.productId = wp.product.productId WHERE wp.warehouse.id = :warehouseId")
    List<Product> findByWarehouseId(@Param("warehouseId") Long warehouseId);

}
