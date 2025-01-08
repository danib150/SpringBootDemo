package it.gianmarco.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "customer_order")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    private LocalDate creation_date, update_date;

    private enum Status {
        IN_PROGRESS,
        COMPLETED,
        CANCELLED

    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_products", // Nome esplicito per la tabella di associazione
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    private Double total_price;;

    @Enumerated(EnumType.STRING)
    private Status order_status = Status.IN_PROGRESS;

}
