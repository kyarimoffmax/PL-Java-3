import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuaeryGenerator implements Quaery{


    private QuaeryHelper qh;

    public QuaeryGenerator(QuaeryHelper qh) {
        this.qh = qh;
    }

    @Override
    public String create(Class c) {
        StringBuilder sb = new StringBuilder();
        String nl = "\n";
        sb.append(String.format("CREATE TABLE IF NOT EXISTS %s (", c.getSimpleName())).append(nl);
        List<Field> pf = qh.getFields(c);
        for(int i = 0; i < pf.size(); i++) {
            Field f = pf.get(i);
            sb.append("\t%s %s".formatted(f.getName(), qh.convertType(f.getType())));
            sb.append((i != pf.size()-1) ? " , " : ");").append(nl);
        }
        return sb.toString();
    }

    @Override
    public String insert(Object o) throws Exception {
        List<Field> primitiveFields = qh.getFields(o.getClass());
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("INSERT INTO %s VALUES (", o.getClass().getSimpleName())).append("\n");
        for(int i = 0; i < primitiveFields.size(); i++) {
            Field f = primitiveFields.get(i);
            Object val = qh.getValue(o, f.getName());
            sb
                    .append(qh.convertToSqlStr(val))
                    .append((i != primitiveFields.size()-1) ? " , " : ");")
                    .append("\n");
        }
        return sb.toString();
    }

    @Override
    public String delete(Object o) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM %s WHERE ".formatted(o.getClass().getSimpleName())).append("\n");
        putSecondPart(sb, o, " and ", ";");
        return sb.toString();
    }

    @Override
    public String update(Object o1, Object o2) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("UPDATE %s SET ", o1.getClass().getSimpleName())).append("\n");
        putSecondPart(sb, o2, " , ", " WHERE ");
        putSecondPart(sb, o1, " and ", ";");
        return sb.toString();
    }

    @Override
    public String find(Object o) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("SELECT * FROM %s WHERE ", o.getClass().getSimpleName())).append("\n");
        putSecondPart(sb, o, " and ", ";");
        return sb.toString();
    }

    @Override
    public QuaeryHelper getQuaeryHelper() {
        return qh;
    }

    private void putSecondPart(StringBuilder sb, Object o, String defultCymbol, String lastCymbol) throws Exception {
        Map<String, Object> values = qh.getValues(o);
        List<String> fields = new ArrayList<>(values.keySet());
        for (int i = 0; i < fields.size(); i++) {
            String fn = fields.get(i);
            sb
                    .append("%s = %s".formatted(fn, qh.convertToSqlStr(values.get(fn))))
                    .append((i != fields.size()-1) ? defultCymbol : lastCymbol)
                    .append("\n");
        }
    }
}
