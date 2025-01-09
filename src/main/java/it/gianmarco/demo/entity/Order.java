package it.gianmarco.demo.entity;

import ch.qos.logback.core.status.Status;
import it.gianmarco.demo.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDate creation_date, update_date;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "orderProducts", // Nome esplicito per la tabella di associazione
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "productId")
    )
    private List<Product> products;

    private Double total_price;;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum order_status =  OrderStatusEnum.IN_PROGRESS;







}
