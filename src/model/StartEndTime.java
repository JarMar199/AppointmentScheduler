package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;

/**
 * Class for populating and converting time
 */
public class StartEndTime {
   private static LocalTime startTime = LocalTime.of(0,0);
   private static LocalTime endTime = LocalTime.of(0,0);


    /**
     * @return List of hours
     */
    public static ObservableList<LocalTime> getStartTimes() {
        ObservableList<LocalTime> startTimes= FXCollections.observableArrayList();
        startTimes.add(startTime);
        startTime =startTime.plusMinutes(30);
        while(startTime.isAfter(endTime)){
            startTimes.add(startTime);
            startTime =startTime.plusMinutes(30);
        }
        return startTimes;
    }

    /**
     * @return list of hours
     */
    public static ObservableList<LocalTime> getEndTimes() {
        ObservableList<LocalTime> endTimes= FXCollections.observableArrayList();

        startTime =startTime.plusMinutes(30);
        while(startTime.isAfter(endTime)){
            endTimes.add(startTime);
            startTime =startTime.plusMinutes(30);
        }
        endTimes.add(startTime);
        return endTimes;
    }

    /**
     * @param localDT Converts local system date and time to UTC
     * @return the date and time in UTC
     */
    public static LocalDateTime localToUTCConversion(LocalDateTime localDT) {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime localZDT = ZonedDateTime.of(localDT, localZoneId);
        ZonedDateTime utcZDT = ZonedDateTime.ofInstant(localZDT.toInstant(), utcZoneId);
        return  utcZDT.toLocalDateTime();
    }

    /**
     * @param utcDT Converts UTC to local system date and time
     * @return the local system date and time
     */
    public static LocalDateTime utcToLocalConversion(LocalDateTime utcDT) {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.of(utcDT, utcZoneId);
        ZonedDateTime localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), localZoneId);
        return localZDT.toLocalDateTime();
    }

    /**
     * @param localDT Converts local system date and time to EST
     * @return the date and time in EST
     */
    public static LocalDateTime localToEST(LocalDateTime localDT) {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZonedDateTime localZDT = ZonedDateTime.of(localDT, localZoneId);
        ZonedDateTime estZDT = ZonedDateTime.ofInstant(localZDT.toInstant(), estZoneId);
        return  estZDT.toLocalDateTime();
    }

}
