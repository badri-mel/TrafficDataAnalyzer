package com.aips.service;

import com.aips.model.TrafficDataRow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public class TrafficFileReader {



    public List<TrafficDataRow> fileToDto(String fileName){
        try {
            Stream<String> result = Files.lines(Paths.get(fileName));
            return result.map(stringRow -> new TrafficDataRow(LocalDateTime.parse(stringRow.split("\\s+")[0]), Integer.parseInt(stringRow.split("\\s+")[1]))).toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
