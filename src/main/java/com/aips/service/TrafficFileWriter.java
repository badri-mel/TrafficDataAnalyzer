package com.aips.service;

import com.aips.model.TrafficDataRow;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TrafficFileWriter {

    public void writeDtoToFile(String filename, Integer count, Map<LocalDate,Integer> totalByDate,List<TrafficDataRow> intervalsByMostCars,List<TrafficDataRow> intervalsByLeastCars){
        try {
            FileWriter fileWriter= new FileWriter(filename);
            fileWriter.write(count+"\n");
            totalByDate.entrySet().stream().forEach(localDateIntegerEntry -> {
                try {
                    fileWriter.write(localDateIntegerEntry.getKey()+" "+localDateIntegerEntry.getValue()+"\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            intervalsByMostCars.forEach(
                    row-> {
                        try {
                            fileWriter.write(row.getDateTime()+" "+row.getCount()+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

            intervalsByLeastCars.forEach(
                    row-> {
                        try {
                            fileWriter.write(row.getDateTime()+" "+row.getCount()+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );


            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
