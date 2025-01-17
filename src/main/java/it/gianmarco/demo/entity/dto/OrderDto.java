package it.gianmarco.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private LocalDate creationDate;
    private LocalDate updateDate;
    private Long userId;
    private Map<Long, Integer> productQuantities;  // Modifica: Mappa prodotto -> quantità
    private Double totalPrice;
}
