import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuaeryHelperImpl implements QuaeryHelper{
    private Map<Class, List<Field>> fields;
    private Map<Class, String> types;
    public QuaeryHelperImpl(List<Class> classes) {
        types = initTypes();
        fields = initFields(classes);
    }
    public Map<Class, List<Field>> initFields(List<Class> classes){
        Map<Class, List<Field>> fields = new HashMap<>();
        for (Class c:classes) {
            fields.put(c, getSimpleFields(c));
        }
        return fields;
    }
    private Map<Class, String> initTypes(){
        return Map.of(
                Long.class, "BIGINT",
                long.class, "BIGINT",
                Integer.class, "INT",
                int.class, "INT",
                String.class, "VARCHAR(255)"
        );
    }


    @Override
    public boolean isSimple(Class c){
        return types.containsKey(c);
    }
    @Override
    public List<Field> getFields(Class c){
        return fields.get(c);
    }
    @Override
    public String convertType(Class c){
        return types.get(c);
    }
    @Override
    public Map<String, Object> getValues(Object o) throws Exception {
        List<Field> fields = getFields(o.getClass());

        Map<String, Object> values = new HashMap<>();
        for (Field f:fields) {
            Object v = getValue(o, f.getName());
            if(v != null){
                values.put(f.getName(), v);
            }
        }
        return values;
    }
}
