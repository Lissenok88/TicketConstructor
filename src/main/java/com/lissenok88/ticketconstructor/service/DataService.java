package com.lissenok88.ticketconstructor.service;

import com.lissenok88.ticketconstructor.model.Schedule;
import com.lissenok88.ticketconstructor.model.Ticket;
import com.lissenok88.ticketconstructor.repository.DataRepository;
import com.lissenok88.ticketconstructor.to.TicketTo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DataService {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");
    private static final String ZONE_ID_VLADIVOSTOK = "Asia/Vladivostok";
    private static final String ZONE_ID_GMT_3 = "Etc/GMT-3";
    private final DataRepository dataRepository = new DataRepository();

    public List<TicketTo> getConvertedData() {
        Schedule data = dataRepository.findData();
        return data.getTickets().stream().map(this::ticketTo).toList();
    }

    public Map<String, Integer> minFlightTime(String originName, String destinationName, List<TicketTo> tickets) {
        Map<String, Integer> minTimeOfCarrier = new HashMap<>();
        for (TicketTo ticket : tickets) {
            if (ticket.getOriginName().equals(originName) && ticket.getDestinationName().equals(destinationName)) {
                ZonedDateTime startDataTime = toZonedDateTime(ticket.getArrivalDateTime(), ZONE_ID_VLADIVOSTOK);
                ZonedDateTime finishDataTime = toZonedDateTime(ticket.getDepartureDateTime(), ZONE_ID_GMT_3);
                int minTime = (int) ChronoUnit.MINUTES.between(startDataTime, finishDataTime);
                if (minTimeOfCarrier.containsKey(ticket.getCarrier()) && minTimeOfCarrier.get(ticket.getCarrier()) < minTime) {
                    continue;
                }
                minTimeOfCarrier.put(ticket.getCarrier(), minTime);
            }
        }
        return minTimeOfCarrier;
    }

    public double averagePrice(String originName, String destinationName, List<TicketTo> tickets) {
        return tickets.stream()
                .filter(ticket -> ticket.getOriginName().equals(originName) && ticket.getDestinationName().equals(destinationName))
                .mapToDouble(TicketTo::getPrice)
                .average().orElse(0);
    }

    public int medianPrice(String originName, String destinationName, List<TicketTo> tickets) {
        List<Integer> prices = tickets.stream()
                .filter(ticket -> ticket.getOriginName().equals(originName) && ticket.getDestinationName().equals(destinationName))
                .sorted(Comparator.comparing(TicketTo::getPrice))
                .map(TicketTo::getPrice).toList();
        int middleList = prices.size() / 2;
        return (prices.size() % 2 != 0) ? prices.get(middleList + 1) : (prices.get(middleList + 1) + prices.get(middleList)) / 2;
    }

    private TicketTo ticketTo(Ticket ticket) {
        return TicketTo.builder()
                .originName(ticket.getOriginName())
                .destinationName(ticket.getDestinationName())
                .arrivalDateTime(ticket.getDepartureDate() + " " + ticket.getDepartureTime())
                .departureDateTime(ticket.getArrivalDate() + " " + ticket.getArrivalTime())
                .carrier(ticket.getCarrier())
                .price(ticket.getPrice())
                .build();
    }

    private ZonedDateTime toZonedDateTime(String dateTime, String zoneId) {
        return LocalDateTime.parse(dateTime, FORMAT).atZone(ZoneId.of(zoneId));
    }
}
