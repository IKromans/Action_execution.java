package com.fintelum;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class SchedulerService {

    private final CsvDataLoaderAndParser csvDataLoaderAndParser;
    private LocalDateTime lastExecutionTime = LocalDateTime.MIN;
    public SchedulerService(CsvDataLoaderAndParser csvDataLoaderAndParser) {
        this.csvDataLoaderAndParser = csvDataLoaderAndParser;
    }

    @Scheduled(cron = "${scheduler.cron}")
    public void schedule() {
        csvDataLoaderAndParser.loadAndParseDataFromCsv();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Africa/Lagos"));
        DayOfWeek currentDay = now.getDayOfWeek();
        List<ScheduledAction> actions = csvDataLoaderAndParser.getScheduledActions();
        for (ScheduledAction action : actions) {
            if (now.toLocalTime().isAfter(action.time()) || now.toLocalTime().equals(action.time())) {
                if (now.toLocalTime().isBefore(action.time().plusMinutes(1)) && action.isBitmaskMatch(currentDay)) {
                    if (now.isAfter(lastExecutionTime.plusMinutes(1))){
                        System.out.println("Executing action.");
                        lastExecutionTime = now;
                    }
                }
            }
        }
    }
}