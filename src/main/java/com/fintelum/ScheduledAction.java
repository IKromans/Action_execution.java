package com.fintelum;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduledAction(LocalTime time, int bitmask) {

    public boolean isBitmaskMatch(DayOfWeek currentDay) {
        int dayOfWeek = currentDay.getValue();
        return (bitmask & (1 << dayOfWeek - 1)) != 0;
    }
}