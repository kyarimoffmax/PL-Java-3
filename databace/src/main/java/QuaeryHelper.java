import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface QuaeryHelper {
    boolean isSimple(Class c);
    List<Field> getFields(Class c);
    String convertType(Class c);
    Map<String, Object> getValues(Object o) throws Exception;



    default List<Field> getSimpleFields(Class c){
        return Arrays.stream(c.getDeclaredFields())
                .filter(field -> isSimple(field.getType()))
                .collect(Collectors.toList());
    }
    default Object getValue(Object object, String fieldName) throws Exception {
        return object.getClass().getMethod(
                createGetter(fieldName)
        ).invoke(object);
    }
    default String createGetter(String fieldName){
        return "get" + capitalizeFirstLetter(fieldName);
    }
    default String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    default String convertToSqlStr(Object o){
        return o.getClass().equals(String.class) ? "\'%s\'".formatted(o) : o.toString();
    }
}
