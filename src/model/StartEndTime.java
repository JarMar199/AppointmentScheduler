package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

public class StartEndTime {
   private static LocalTime startTime = LocalTime.of(0,0);
   private static LocalTime endTime = LocalTime.of(0,0);

    public static ObservableList<LocalTime> getTimes() {
        ObservableList<LocalTime> startEndTimes= FXCollections.observableArrayList();
        startEndTimes.add(startTime);
        startTime =startTime.plusMinutes(30);
        while(startTime.isAfter(endTime)){
            startEndTimes.add(startTime);
            startTime =startTime.plusMinutes(30);
        }
        return startEndTimes;
    }


}
