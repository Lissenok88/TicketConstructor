package com.lissenok88.ticketconstructor.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ticket {

    String origin;

    @JsonSetter("origin_name")
    String originName;

    String destination;

    @JsonSetter("destination_name")
    String destinationName;

    @JsonSetter("departure_date")
    String departureDate;

    @JsonSetter("departure_time")
    String departureTime;

    @JsonSetter("arrival_date")
    String arrivalDate;

    @JsonSetter("arrival_time")
    String arrivalTime;

    String carrier;

    int stops;

    int price;
}
