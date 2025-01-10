package com.example.musicBox.utilites;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class WeekUtils {


    public static LocalDateTime getStartOfWeek() {
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY));
        return LocalDateTime.of(startOfWeek, LocalTime.MIDNIGHT);
    }

    public static LocalDateTime getEndOfWeek() {
        LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        return LocalDateTime.of(endOfWeek, LocalTime.MAX);
    }
}
