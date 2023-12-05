import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

    private ConnectionDB connectionDB;
    private Quaery quaery;
    private Class aClass;
    public Servlet(ConnectionDB connectionDB, Quaery quaery, Class aClass) {
        this.connectionDB = connectionDB;
        this.quaery = quaery;
        this.aClass = aClass;
    }


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Object object = toObjectfromRequest(request, "");
        ResultSet resultSet;

        if(object == null)
            resultSet = connectionDB.executeQuaery(quaery.findAll(object.getClass()));
        else
            resultSet = connectionDB.executeQuaery(quaery.find(object));

        fillResponse(response, resultSet);
    }
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object = toObjectfromRequest(request, "");
        connectionDB.execute(quaery.insert(object));
    }
    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object = toObjectfromRequest(request, "");
        connectionDB.execute(quaery.delete(object));
    }
    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object set = toObjectfromRequest(request, "");
        Object where = toObjectfromRequest(request, "");

        connectionDB.execute(quaery.update(set, where));
    }

    private Object toObjectfromRequest(HttpServletRequest request, String prefix) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        boolean isEmpty = true;
        Object object = aClass.newInstance();
        for (Field f:quaery.getQuaeryHelper().getFields(aClass)) {
            String value = request.getParameter(prefix+f.getName());
            if(value.isEmpty())
                continue;

            isEmpty = false;
            setValue(object, f.getName(), convertValue(value, f.getType()));
        }

        return isEmpty ? null : object;
    }
    private Object convertValue(String val, Class type){
        if (type.equals(Integer.class) || type.equals(int.class))
            return Integer.parseInt(val);
        if (type.equals(Long.class) || type.equals(long.class))
            return Long.parseLong(val);
        return val;
    }

    private void setValue(Object o, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = aClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(o, value);
    }

    private void fillResponse(HttpServletResponse response, ResultSet resultSet) throws SQLException, IOException {
        int count = 0;
        while (resultSet.next()){
            response.getWriter().println("element: %d".formatted(count));
            for (Field f: quaery.getQuaeryHelper().getFields(aClass)) {
                response.getWriter().println("%s = %s\n".formatted(f.getName(), resultSet.getObject(f.getName())));
            }
        }
    }
}
