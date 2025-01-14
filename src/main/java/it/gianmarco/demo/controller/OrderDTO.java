package it.gianmarco.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private Long userId; // Solo l'ID dell'utente
    private List<Long> productIds; // Solo gli ID dei prodotti
    private Double totalPrice;
}
