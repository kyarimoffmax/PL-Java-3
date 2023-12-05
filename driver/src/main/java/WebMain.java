import org.example.model.*;

import java.util.List;

public class WebMain {
    private static List<Class> classes = List.of(AutoWorkshop.class, Equipment.class, Location.class, Commission.class, Worker.class);
    private static ConnectionDB connectionDB = new ConnectionDBImpl();
    private static QuaeryHelper quaeryHelper = new QuaeryHelperImpl(classes);
    private static Quaery quaery = new QuaeryGenerator(quaeryHelper);

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.fillServletContainer(
                connectionDB,
                quaery,
                classes
        );

    }
}
