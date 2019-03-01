package com.example.slabiak.appointmentscheduler.model;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayPlan {

    private TimePeroid workingHours;
    private List<TimePeroid> breaks;

    public DayPlan(){
    }

    public ArrayList<TimePeroid> getPeroidsWithBreaksExcluded(){
        ArrayList<TimePeroid> breaksExcluded = new ArrayList<>();
        breaksExcluded.add(getWorkingHours());
        List<TimePeroid> breaks = getBreaks();
        if(breaks.size()>0) {
            ArrayList<TimePeroid> toAdd = new ArrayList<TimePeroid>();
            for (TimePeroid break1 : breaks) {
                for (TimePeroid peroid : breaksExcluded) {
                    if (break1.getStart().isBefore(peroid.getStart()) && break1.getEnd().isAfter(peroid.getStart()) && break1.getEnd().isBefore(peroid.getEnd())) {
                        peroid.setStart(break1.getEnd());
                    }
                    if (break1.getStart().isAfter(peroid.getStart()) && break1.getStart().isBefore(peroid.getEnd()) && break1.getEnd().isAfter(peroid.getEnd())) {
                        peroid.setEnd(break1.getStart());
                    }
                    if (break1.getStart().isAfter(peroid.getStart()) && break1.getEnd().isBefore(peroid.getEnd())) {
                        toAdd.add(new TimePeroid(peroid.getStart(), break1.getStart()));
                        peroid.setStart(break1.getEnd());
                    }
                }
            }
            breaksExcluded.addAll(toAdd);
            Collections.sort(breaksExcluded);
        }
        return breaksExcluded;
    }

    public TimePeroid getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(TimePeroid workingHours) {
        this.workingHours = workingHours;
    }

    public List<TimePeroid> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<TimePeroid> breaks) {
        this.breaks = breaks;
    }
}