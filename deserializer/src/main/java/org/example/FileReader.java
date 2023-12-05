package org.example;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.AutoWorkshop;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class FileReader {

    public FileReader() {
    }

    public List<AutoWorkshop> readFile(String fileName){
        try {

            return new ObjectMapper().readValue(new FileInputStream(fileName),new TypeReference<List<AutoWorkshop>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
