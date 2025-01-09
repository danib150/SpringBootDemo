package it.gianmarco.demo.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String product_name;
    private String product_description;

    private double price;

}
