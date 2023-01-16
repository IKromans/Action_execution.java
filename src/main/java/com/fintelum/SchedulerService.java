package com.fintelum;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SchedulerService {

    private final Logger LOGGER = Logger.getLogger(ScheduledAction.class.getName());
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
            if (isTimeForAction(now, action) && isDayForAction(currentDay, action) && isNotLastExecutionWithinOneMinute(now)) {
                LOGGER.info("Executing action.");
                lastExecutionTime = now;
            }
        }
    }

    private boolean isTimeForAction(LocalDateTime now, ScheduledAction action) {
        return now.toLocalTime().isAfter(action.time()) || now.toLocalTime().equals(action.time());
    }

    private boolean isDayForAction(DayOfWeek currentDay, ScheduledAction action) {
        return action.isBitmaskMatch(currentDay);
    }

    private boolean isNotLastExecutionWithinOneMinute(LocalDateTime now) {
        return now.isAfter(lastExecutionTime.plusMinutes(1));
    }
}