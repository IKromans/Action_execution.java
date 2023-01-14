package com.fintelum;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvDataLoaderAndParser {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final Path csvFilePath = Paths.get("src/main/resources/scheduler.csv");
    private final List<ScheduledAction> scheduledActions = new ArrayList<>();

    @PostConstruct
    public void loadAndParseDataFromCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath.toFile()))) {
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
        } catch (Exception e) {
            System.out.println("Error loading \"scheduler.csv\" file.");
        }
    }

    public List<ScheduledAction> getScheduledActions() {
        return scheduledActions;
    }
}