package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.model.AutoWorkshop;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWritener {
    public FileWritener() {
    }

    public void writeToJsonFile(String fileName, List<AutoWorkshop> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(fileName), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
