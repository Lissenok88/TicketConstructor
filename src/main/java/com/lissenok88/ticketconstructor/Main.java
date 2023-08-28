package com.lissenok88.ticketconstructor;

import com.lissenok88.ticketconstructor.service.DataService;
import com.lissenok88.ticketconstructor.to.TicketTo;

import java.util.List;
import java.util.Map;

public class Main {
    private static final String ORIGIN_NAME = "Владивосток";
    private static final String DESTINATION_NAME = "Тель-Авив";

    public static void main(String[] args) {
        DataService dataService = new DataService();
        List<TicketTo> convertedData = dataService.getConvertedData();

        System.out.println("Минимальное время полета между городами Владивосток и Тель-Авив для каждого авиаперевозчика:");
        Map<String, Integer> map = dataService.minFlightTime(ORIGIN_NAME, DESTINATION_NAME, convertedData);
        for (Map.Entry<String, Integer> m : map.entrySet()) {
            System.out.println(m.getKey() + " = " + m.getValue() / 60 + ":" + m.getValue() % 60);
        }

        System.out.println("Разница между средней ценой и медианой для полета между городами  Владивосток и Тель-Авив:");
        System.out.println(Math.abs(dataService.averagePrice(ORIGIN_NAME, DESTINATION_NAME, convertedData) -  dataService.medianPrice(ORIGIN_NAME, DESTINATION_NAME, convertedData)));
    }
}
