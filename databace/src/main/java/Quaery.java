public interface Quaery {
    String create(Class c);
    String insert(Object o) throws Exception;
    String delete(Object o) throws Exception;
    String update(Object o1, Object o2) throws Exception;
    String find(Object o) throws Exception;

    QuaeryHelper getQuaeryHelper();


    default String findAll(Class c){
        return "SELECT * FROM %s;".formatted(c.getSimpleName());
    }
    default String drop(Class c){
        return "DROP TABLE IF EXISTS %s;".formatted(c.getSimpleName());
    }
}
