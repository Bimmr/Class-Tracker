package com.bimmr.classtracker.objects;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Randy on 2017-10-15.
 */

public class ClassSchedule {

    private int id;

    private String dayOfWeek;
    private Time startTime;
    private Time endTime;
    private String room;


    public ClassSchedule(int id, String dayOfWeek, Time startTime, Time endTime, String room) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDayOfWeekVal() {
        switch (getDayOfWeek()) {
            case "Sunday":
                return Calendar.SUNDAY;
            case "Monday":
                return Calendar.MONDAY;
            case "Tuesday":
                return Calendar.TUESDAY;
            case "Wednesday":
                return Calendar.WEDNESDAY;
            case "Thursday":
                return Calendar.THURSDAY;
            case "Friday":
                return Calendar.FRIDAY;
            case "Saturday":
                return Calendar.SATURDAY;
        }
        return -1;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void updateToDB() {

    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
