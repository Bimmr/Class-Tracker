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
    private String room;


    public ClassSchedule(int id, String dayOfWeek, Time startTime, Time endTime, String room){
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
