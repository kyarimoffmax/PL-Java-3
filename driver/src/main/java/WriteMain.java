

import org.example.FileWritener;
import org.example.Generator;
import org.example.model.AutoWorkshop;

import java.util.List;

public class WriteMain {
    public static void main(String[] args) {
        Generator g = new Generator(10);

        List<AutoWorkshop> workshops = g.generate();

        new FileWritener().writeToJsonFile("workshops.json", workshops);

    }

}