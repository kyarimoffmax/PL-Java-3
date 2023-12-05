package org.example;

import org.example.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    private Integer count;

    public Generator(Integer count) {
        this.count = count;
    }

    public List<AutoWorkshop> generate(){
        List<AutoWorkshop> workshops = new ArrayList<>();
        for (int i = 1; i < count+1; i++) {

            List<Worker> workers = new ArrayList<>();
            for (int j = 1; j < new Random().nextInt(3, 10); j++) {
                workers.add(new Worker(i*(count+1)*10+j, "fio_"+i*(count+1)*10+j, "specialization_"+j, i));
            }

            List<Commission> orders = new ArrayList<>();
            for (int j = 1; j < new Random().nextInt(10)+3; j++) {
                orders.add(new Commission(i*(count+1)*10+j, "brand_"+new Random().nextInt(100), "service_"+new Random().nextInt(100), new Random().nextInt(1000, 300000), i));
            }

            List<Equipment> equipments = new ArrayList<>();
            for (int j = 1; j < new Random().nextInt(3, 10); j++) {
                equipments.add(new Equipment(i*(count+1)*10+j, "equipment"+new Random().nextInt(100), new Random().nextInt(1, 10), "specialization_"+j, i));
            }

            AutoWorkshop ws = new AutoWorkshop(
                    i,
                    "AutoWorkshop_"+i,
                    workers,
                    orders,
                    equipments,
                    new Location(
                            "Country_"+new Random().nextInt(100),
                            "City_"+new Random().nextInt(100),
                            "Street_"+new Random().nextInt(100),
                            "House_"+new Random().nextInt(100),
                            i)
            );
            workshops.add(ws);

        }
        return workshops;
    }

}
