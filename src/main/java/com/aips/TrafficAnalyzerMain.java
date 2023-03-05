package com.aips;

import com.aips.model.TrafficDataRow;
import com.aips.service.TrafficDataProcessor;
import com.aips.service.TrafficFileReader;
import com.aips.service.TrafficFileWriter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TrafficAnalyzerMain {

    public static void main(String[] args) {
        TrafficFileReader fileReader = new TrafficFileReader();


        if (args.length < 2) {
            System.out.print("Please enter the inout file followed by output file name");
            return;
        }
        List<TrafficDataRow> result = fileReader.fileToDto(args[0]); //"src/main/resources/TrafficeInputFile.txt"
        TrafficDataProcessor processor = new TrafficDataProcessor();

        List<TrafficDataRow> topCars = processor.getTopIntervalWithMostCars(result, 3);


        List<TrafficDataRow> leastCars = processor.getContigousIntervalWithLeastCars(result, 3, 30);

        TrafficFileWriter writer = new TrafficFileWriter();

        writer.writeDtoToFile(args[1], processor.getTotalCount(result), processor.getTotalCarsByDate(result), topCars, leastCars);
    }
}
