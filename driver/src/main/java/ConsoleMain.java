import org.example.model.*;

import java.sql.SQLException;
import java.util.List;


public class ConsoleMain {
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


    public static void main(String[] args) throws Exception {
        dropAll();
        createAll();

        ConsoleHandler consoleHandler = new ConsoleHandler(
                classes,
                quaery,
                connectionDB
        );

        consoleHandler.start();
    }
}
