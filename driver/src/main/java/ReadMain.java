

import org.example.FileReader;
import org.example.model.AutoWorkshop;

import java.util.List;
import java.util.Random;


public class ReadMain {
    public static void main(String[] args) {
        List<AutoWorkshop> workshops = new FileReader().readFile("workshops.json");

        for (AutoWorkshop w : workshops) {
            System.out.println(w.getId());
            System.out.println(w.getName());
            System.out.println(w.getWorkers());
            System.out.println(w.getCommissions());
            System.out.println(w.getEquipments());
            System.out.println(w.getLocation());
            new Random();
            System.out.println("_____________________________");
        }

    }

}