package it.gianmarco.demo.entity.dto;

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
public class OrderDto {
    private LocalDate creationDate;
    private LocalDate updateDate;
    private Long userId;
    private List<Long> productIds;
    private Double totalPrice;
}
