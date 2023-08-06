package com.maman14q2.maman14q2;

import java.util.ArrayList;
import java.util.List;

public enum Months {
    January("January", 31),
    February("February", 28),
    March("March", 31),
    April("April", 30),
    May("May", 31),
    June("June", 30),
    July("July", 31),
    August("August", 31),
    September("September", 30),
    October("October", 31),
    November("November", 30),
    December("December", 31);

    private final String monthName;
    private final List<Integer> days;

    Months(String monthName, int maxDays) {
        this.monthName = monthName;
        this.days = new ArrayList<>();
        for (int i = 1; i <= maxDays; i++) {
            this.days.add(i);
        }
    }

    public String getMonthName() {
        return monthName;
    }

    public List<Integer> getDays() {
        return days;
    }
}
