package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;

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

    public static LocalDateTime localToUTCConversion(LocalDateTime localDT) {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime localZDT = ZonedDateTime.of(localDT, localZoneId);
        ZonedDateTime utcZDT = ZonedDateTime.ofInstant(localZDT.toInstant(), utcZoneId);
        return  utcZDT.toLocalDateTime();
    }

    public static LocalDateTime utcToLocalConversion(LocalDateTime utcDT) {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.of(utcDT, utcZoneId);
        ZonedDateTime localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), localZoneId);
        return localZDT.toLocalDateTime();
    }


}
