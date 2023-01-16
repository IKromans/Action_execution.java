package com.fintelum;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class CsvDataLoaderAndParser {

    private final Logger LOGGER = Logger.getLogger(ScheduledAction.class.getName());
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final Resource csvFileResource = new ClassPathResource("scheduler.csv");
    private final List<ScheduledAction> scheduledActions = new ArrayList<>();

    @PostConstruct
    public void loadAndParseDataFromCsv() {
        try (InputStream csvData = csvFileResource.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(csvData))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("time")) {
                    continue;
                }
                String[] data = line.split(",");
                LocalTime time = LocalTime.parse(data[0], TIME_FORMATTER);
                int bitmask = Integer.parseInt(data[1]);
                scheduledActions.add(new ScheduledAction(time, bitmask));
            }
        } catch (IOException e) {
            LOGGER.info("Error loading \"scheduler.csv\" file.");
        }
    }

    public List<ScheduledAction> getScheduledActions() {
        return scheduledActions;
    }
}