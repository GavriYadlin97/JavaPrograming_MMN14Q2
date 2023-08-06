package com.maman14q2.maman14q2;

import java.io.Serializable;
import java.util.Objects;

public class MyDate implements Serializable {

    private int day;
    private Months month;
    private int year;

    public MyDate(int day, Months month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MyDate date = (MyDate) o;
        return day == date.day && month == date.month && year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }
}
