import org.example.FileReader;
import org.example.model.*;

import java.sql.SQLException;
import java.util.List;

public class FromJsonMain {
    private static List<Class> classes = List.of(AutoWorkshop.class, Equipment.class, Location.class, Commission.class, Worker.class);
    private static ConnectionDB connectionDB = new ConnectionDBImpl();
    private static QuaeryHelper quaeryHelper = new QuaeryHelperImpl(classes);
    private static Quaery quaery = new QuaeryGenerator(quaeryHelper);

    private static void dropAll() throws InstantiationException, IllegalAccessException, SQLException, ClassNotFoundException {
        for (Class c:classes) {
            Object o = Class.forName("org.example.model.%s".formatted(c.getSimpleName())).newInstance();
            CRUDENUM.DROP.execute(connectionDB, quaery, o);
        }
    }

    private static void createAll() throws InstantiationException, IllegalAccessException, SQLException, ClassNotFoundException {
        for (Class c:classes) {
            Object o = Class.forName("org.example.model.%s".formatted(c.getSimpleName())).newInstance();
            CRUDENUM.CREATE.execute(connectionDB, quaery, o);
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        dropAll();
        createAll();

        for (AutoWorkshop aw:new FileReader().readFile("workshops.json")) {
            CRUDENUM.INSERT.execute(connectionDB, quaery, aw);

            for (Worker w:aw.getWorkers()) {
                CRUDENUM.INSERT.execute(connectionDB, quaery, w);
            }
            for (Commission c:aw.getCommissions()) {
                CRUDENUM.INSERT.execute(connectionDB, quaery, c);
            }
            for (Equipment e:aw.getEquipments()) {
                CRUDENUM.INSERT.execute(connectionDB, quaery, e);
            }
            CRUDENUM.INSERT.execute(connectionDB, quaery, aw.getLocation());
        }
    }
}
