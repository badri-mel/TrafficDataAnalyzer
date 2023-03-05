package com.aips.service;

import com.aips.model.TrafficDataRow;
import com.aips.model.TrafficDataRowGroup;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TrafficDataProcessor {


    public Integer getTotalCount(List<TrafficDataRow> rows) {
        return rows.stream().mapToInt(TrafficDataRow::getCount).sum();
    }

    /**
     * Transform the list of TrafficDataRow to LocalDate and sum of car count on that date
     * @param rows
     * @return Map<LocalDate,Integer>A Map of Total number of cars on that Date</LocalDate,Integer>
     */
    public Map<LocalDate, Integer> getTotalCarsByDate(List<TrafficDataRow> rows) {
        return rows.stream().collect(Collectors.toMap(TrafficDataRow::getDate, TrafficDataRow::getCount, Integer::sum, LinkedHashMap::new));
    }

    /**
     * Transform the list of TrafficDataRow to LocalDateTime and sum of car count on that datetime
     * @param rows
     * @return Map<LocalDateTime,Integer>A Map of Total number of cars on that DateTime</LocalDateTime,Integer>
     */

    public Map<LocalDateTime, Integer> getTotalCarsByDateTime(List<TrafficDataRow> rows) {
        return rows.stream().collect(Collectors.toMap(TrafficDataRow::getDateTime, TrafficDataRow::getCount));
    }

    /**
     * return the top list of TrafficDataRow with most cars limited by size
     * @param rows
     * @param sizeofGroup
     * @return
     */
    public List<TrafficDataRow> getTopIntervalWithMostCars(List<TrafficDataRow> rows, Integer sizeofGroup) {
        return rows.stream().sorted(Comparator.comparing(TrafficDataRow::getCount, Comparator.reverseOrder())).limit(sizeofGroup).toList();
    }


    /**
     * return the top list of contiguous TrafficDataRow with least cars limited by size
     * @param rows
     * @param sizeofGroup
     * @return
     */

    public List<TrafficDataRow> getContigousIntervalWithLeastCars(List<TrafficDataRow> rows, Integer sizeofGroup,Integer interval) {
        Map<LocalDateTime, Integer> totalCarsByDateTime = getTotalCarsByDateTime(rows);
        List<TrafficDataRowGroup> groups = new LinkedList<>();

        rows.stream().forEach(trafficDataRow -> {
            LinkedList<TrafficDataRow> t = new LinkedList<>();
            LocalDateTime firstRowDateTime = trafficDataRow.getDateTime();
            t.add(TrafficDataRow.builder()
                    .count(trafficDataRow.getCount())
                    .date(trafficDataRow.getDate())
                    .dateTime(trafficDataRow.getDateTime())
                    .build());
            for (int i = 1; i <=(sizeofGroup-1); i++) {

                if(totalCarsByDateTime.get(firstRowDateTime.plusMinutes(interval*i))==null){
                    return;
                }
                t.add(TrafficDataRow.builder()
                        .count(totalCarsByDateTime.get(firstRowDateTime.plusMinutes(interval*i)))
                        .date(firstRowDateTime.plusMinutes(interval*i).toLocalDate())
                        .dateTime(firstRowDateTime.plusMinutes(interval*i))
                        .build());
            }
            groups.add(TrafficDataRowGroup.builder()
                    .entryList(t)
                    .sumCount(t.stream().mapToInt(TrafficDataRow::getCount).sum())
                    .build());

        });


        groups.sort(Comparator.comparingInt(TrafficDataRowGroup::getSumCount));

        return groups.get(0).getEntryList();
    }
}
