package com.lissenok88.ticketconstructor.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lissenok88.ticketconstructor.model.Schedule;

import java.io.IOException;

public class DataRepository {
    private static final String FILE_NAME = "/tickets.json";

    public Schedule findData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(this.getClass().getResource(FILE_NAME), Schedule.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
