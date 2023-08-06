package com.maman14q2.maman14q2;

import java.io.Serializable;
import java.util.HashMap;

public class DataBase implements Serializable {

    private HashMap<MyDate, String> map;

    public DataBase() {
        this.map = new HashMap<>();
    }

    public void add(MyDate date, String reminder) {
        this.map.put(date, reminder);
    }

    public String getReminder(MyDate date) {
        return this.map.get(date);
    }

    public String delete(MyDate date) {
        return this.map.remove(date);
    }
}
