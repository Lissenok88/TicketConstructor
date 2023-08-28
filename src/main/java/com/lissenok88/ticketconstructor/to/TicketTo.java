package com.lissenok88.ticketconstructor.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketTo {
    String originName;
    String destinationName;
    String departureDateTime;
    String arrivalDateTime;
    String carrier;
    int price;
}
