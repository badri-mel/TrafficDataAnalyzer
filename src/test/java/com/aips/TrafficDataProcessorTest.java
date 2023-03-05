package com.aips;

import com.aips.model.TrafficDataRow;
import com.aips.service.TrafficDataProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TrafficDataProcessorTest {

    @Test
    public void getGetTotalCarsByDate() {
        TrafficDataProcessor trafficDataProcessor = new TrafficDataProcessor();
        Map<LocalDate, Integer> actual = trafficDataProcessor.getTotalCarsByDate(List.of(TrafficDataRow.builder()
                        .date(LocalDate.parse("2021-12-01"))
                        .dateTime(LocalDateTime.parse("2021-12-01T05:30:00"))
                        .count(Integer.parseInt("5"))
                        .build(),
                TrafficDataRow.builder()
                        .date(LocalDate.parse("2021-12-01"))
                        .dateTime(LocalDateTime.parse("2021-12-01T05:30:00"))
                        .count(Integer.parseInt("12"))
                        .build())
        );

        actual.forEach((key, value) -> {
            Assertions.assertEquals(LocalDate.parse("2021-12-01"), key);
            Assertions.assertEquals(17, value);
        });
    }

    @Test
    public void getGetTotalCount(){
        TrafficDataProcessor trafficDataProcessor = new TrafficDataProcessor();

        Integer count = trafficDataProcessor.getTotalCount(List.of(TrafficDataRow.builder()
                        .date(LocalDate.parse("2021-12-01"))
                        .dateTime(LocalDateTime.parse("2021-12-01T05:00:00"))
                        .count(Integer.parseInt("5"))
                        .build(),
                TrafficDataRow.builder()
                        .date(LocalDate.parse("2021-12-01"))
                        .dateTime(LocalDateTime.parse("2021-12-01T05:30:00"))
                        .count(Integer.parseInt("12"))
                        .build()));

        Assertions.assertEquals(17,count);

    }


    @Test
    public void testGetTopIntervalWithMostCars(){
        TrafficDataProcessor trafficDataProcessor = new TrafficDataProcessor();

        List<TrafficDataRow> result = trafficDataProcessor.getTopIntervalWithMostCars(List.of(TrafficDataRow.builder()
                        .date(LocalDate.parse("2021-12-01"))
                        .dateTime(LocalDateTime.parse("2021-12-01T05:00:00"))
                        .count(Integer.parseInt("5"))
                        .build(),
                TrafficDataRow.builder()
                        .date(LocalDate.parse("2021-12-01"))
                        .dateTime(LocalDateTime.parse("2021-12-01T05:30:00"))
                        .count(Integer.parseInt("12"))
                        .build()), 1);

        result.stream().forEach(trafficDataRow -> {
            Assertions.assertEquals(LocalDateTime.parse("2021-12-01T05:30:00"), trafficDataRow.getDateTime());
            Assertions.assertEquals(12, Integer.parseInt("12"));
        });

    }


    @Test
    public void getGetContigousIntervalWithLeastCars(){
        TrafficDataProcessor trafficDataProcessor = new TrafficDataProcessor();

        List<TrafficDataRow> result = trafficDataProcessor.getContigousIntervalWithLeastCars(List.of(TrafficDataRow.builder()
                                .date(LocalDate.parse("2021-12-01"))
                                .dateTime(LocalDateTime.parse("2021-12-01T05:00:00"))
                                .count(Integer.parseInt("5"))
                                .build(),
                        TrafficDataRow.builder()
                                .date(LocalDate.parse("2021-12-01"))
                                .dateTime(LocalDateTime.parse("2021-12-01T05:30:00"))
                                .count(Integer.parseInt("12"))
                                .build(),
                        TrafficDataRow.builder()
                                .date(LocalDate.parse("2021-12-01"))
                                .dateTime(LocalDateTime.parse("2021-12-01T06:00:00"))
                                .count(Integer.parseInt("14"))
                                .build(),
                        //2021-12-01T06:30:00 15
                        TrafficDataRow.builder()
                                .date(LocalDate.parse("2021-12-01"))
                                .dateTime(LocalDateTime.parse("2021-12-01T06:30:00"))
                                .count(Integer.parseInt("15"))
                                .build(),
                        // 2021-12-01T07:00:00 25
                        TrafficDataRow.builder()
                                .date(LocalDate.parse("2021-12-01"))
                                .dateTime(LocalDateTime.parse("2021-12-01T07:00:00"))
                                .count(Integer.parseInt("25"))
                                .build(),
                        // 2021-12-01T07:30:00 46
                        TrafficDataRow.builder()
                                .date(LocalDate.parse("2021-12-01"))
                                .dateTime(LocalDateTime.parse("2021-12-01T07:30:00"))
                                .count(Integer.parseInt("46"))
                                .build()
                ),
                3, 30);

        Assertions.assertEquals(5,result.get(0).getCount());
        Assertions.assertEquals(LocalDateTime.parse("2021-12-01T05:00:00"),result.get(0).getDateTime());

        Assertions.assertEquals(12,result.get(1).getCount());
        Assertions.assertEquals(LocalDateTime.parse("2021-12-01T05:30:00"),result.get(1).getDateTime());

        Assertions.assertEquals(14,result.get(2).getCount());
        Assertions.assertEquals(LocalDateTime.parse("2021-12-01T06:00:00"),result.get(2).getDateTime());
    }



}
