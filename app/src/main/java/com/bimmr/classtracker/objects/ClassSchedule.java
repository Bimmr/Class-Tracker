package com.bimmr.classtracker.objects;

import java.sql.Time;

/**
 * Created by Randy on 2017-10-15.
 */

public class ClassSchedule {

    private int id;

    private String dayOfWeek;
    private Time startTime;
    private Time endTime;


    public ClassSchedule(int id, String dayOfWeek, Time startTime, Time endTime){
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ClassSchedule(int id, String string){
        this.id = id;

        //Day-start:time-end:time
        //Monday 10:00-13:00
        String[] parts = string.split("-");
        String day = parts[0];
        String start = parts[1];
        String end = parts[2];

        this.dayOfWeek = day;
        this.startTime = Time.valueOf(start);
        this.endTime = Time.valueOf(end);
    }

    public String getDayOfWeek() {
        return dayOfWeek;
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
    public void updateToDB(){

    }
}
