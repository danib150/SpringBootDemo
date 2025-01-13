package it.gianmarco.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinTable(
            name = "warehouseProducts", // Nome esplicito per la tabella di associazione
            joinColumns = @JoinColumn(name = "wharehouseId"),
            inverseJoinColumns = @JoinColumn(name = "productId")
    )
    private Product product;

    @Min(value = 0)
    private int quantity;
}
