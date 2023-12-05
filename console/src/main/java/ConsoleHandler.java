import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConsoleHandler {

    private Quaery quaery;
    private ConnectionDB connectionDB;
    private Map<String, Class> associateClasses;
    private Set<String> commands;



    public ConsoleHandler(List<Class> classes, Quaery quaery, ConnectionDB connectionDB) {
        this.quaery = quaery;
        this.connectionDB = connectionDB;
        initAssociateClasses(classes);
        initCommands();
    }

    private void initAssociateClasses(List<Class> classes){
        associateClasses = classes.stream()
                .collect(Collectors.toMap(Class::getSimpleName, Function.identity()));
    }
    private void initCommands(){
        commands = Arrays.stream(CRUDENUM.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    public void start() throws Exception {
        boolean flag = true;
        while (flag){
            flag = nextCommand();
        }
    }
    private boolean nextCommand() throws Exception {
        System.out.print("COMMAND -> ");
        String command = new Scanner(System.in).nextLine().trim();
        if (command.equals("EXIT")) {
            return false;
        } else if (command.equals("HELP")) {
            System.out.println("SUPPORTED COMMANDS: " + commands.toString());
            System.out.println("EXISTIND TABLES: " + associateClasses.keySet().toString());
        } else if (!commands.contains(command)) {
            System.out.println("!!!!! COMMAND \'%s\' NOT FOUND !!!!!".formatted(command));
            System.out.println("SUPPORTED COMMANDS: " + commands.toString());
        } else {
            System.out.print("TABLE -> ");
            String table = new Scanner(System.in).nextLine().trim();
            if (!associateClasses.keySet().contains(table)) {
                System.out.println("!!!!! TABLE \'%s\' NOT FOUND !!!!!".formatted(table));
                System.out.println("EXISTIND TABLES: " + associateClasses.keySet().toString());
            } else {
                CRUDENUM c = CRUDENUM.valueOf(command);
                if (c.equals(CRUDENUM.UPDATE)) {
                    System.out.println("READING WHERE");
                    Object o1 = readObject(table);
                    System.out.println(o1);
                    System.out.println("READING SET");
                    Object o2 = readObject(table);
                    System.out.println(o2);
                    c.execute(connectionDB, quaery, o1, o2);

                } else if (c.equals(CRUDENUM.CREATE) || c.equals(CRUDENUM.DELETE)) {
                    System.out.println("NOT NOW");

                } else if (c.equals(CRUDENUM.FIND)) {
                    Object o = readObject(table);
                    printResultSet(c.execute(connectionDB, quaery, o), o);

                } else if (c.equals(CRUDENUM.FIND_ALL)) {
                    Object o = Class.forName("org.example.model.%s".formatted(table)).newInstance();
                    printResultSet(c.execute(connectionDB, quaery, o), o);

                } else {
                    System.out.println("READING");
                    Object o = readObject(table);
                    c.execute(connectionDB, quaery, o);
                }

            }
        }
        return true;
    }



    private void printResultSet(ResultSet rs, Object o1) throws SQLException {
        List<Field> fields = quaery.getQuaeryHelper().getFields(o1.getClass());
        while (rs.next()){
            for (Field f:fields) {
                System.out.println(
                        "%s => %s".formatted(f.getName(), rs.getObject(f.getName()))
                );
            }
            System.out.println();
        }
    }
    private Object readObject(String name) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Object o = Class.forName("org.example.model.%s".formatted(name)).newInstance();
        Scanner scanner = new Scanner(System.in);
        for (Field f:quaery.getQuaeryHelper().getFields(o.getClass())) {
            System.out.print(f.getName()+" -> ");
            String value = scanner.nextLine().trim();
            if(value.isEmpty())
                continue;
            setValue(o, f.getName(), convertValue(value, f.getType()));
        }
        return o;
    }

    private Object convertValue(String val, Class type){
        if (type.equals(Integer.class) || type.equals(int.class))
            return Integer.parseInt(val);
        if (type.equals(Long.class) || type.equals(long.class))
            return Long.parseLong(val);
        return val;
    }

    private void setValue(Object o, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(o, value);
    }

}
