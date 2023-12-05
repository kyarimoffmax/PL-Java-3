import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public enum CRUDENUM {


    FIND(){
        @Override
        public ResultSet execute(ConnectionDB cdb, Quaery quaery, Object... objects) {
            Object o = objects[0];

            String q = null;
            try {
                q = quaery.find(o);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            ResultSet rs = null;
            try {
                rs = cdb.executeQuaery(q);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return rs;
        }
    },
    INSERT(){
        @Override
        public ResultSet execute(ConnectionDB cdb, Quaery quaery, Object... objects) {
            Object o = objects[0];

            String q = null;
            try {
                q = quaery.insert(o);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                cdb.execute(q);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    },
    DELETE(){
        @Override
        public ResultSet execute(ConnectionDB cdb, Quaery quaery, Object... objects) {
            Object o = objects[0];

            String q = null;
            try {
                q = quaery.delete(o);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                cdb.execute(q);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    },
    UPDATE(){
        @Override
        public ResultSet execute(ConnectionDB cdb, Quaery quaery, Object... objects) {
            Object o1 = objects[0];
            Object o2 = objects[1];

            String q = null;
            try {
                q = quaery.update(o1, o2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                cdb.execute(q);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    },
    CREATE(){
        @Override
        public ResultSet execute(ConnectionDB cdb, Quaery quaery, Object... objects)  {
            Class c = objects[0].getClass();

            String q = quaery.create(c);
            try {
                cdb.execute(q);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    },
    DROP(){
        @Override
        public ResultSet execute(ConnectionDB cdb, Quaery quaery, Object... objects) {
            Class c = objects[0].getClass();

            String q = quaery.drop(c);
            try {
                cdb.execute(q);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    },
    FIND_ALL(){
        @Override
        public ResultSet execute(ConnectionDB cdb, Quaery quaery, Object... objects) {
            Object o = objects[0];
            String q = quaery.findAll(o.getClass());
            ResultSet rs = null;
            try {
                rs = cdb.executeQuaery(q);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return rs;
        }
    }
    ;

    CRUDENUM() {
    }

    public abstract ResultSet execute(ConnectionDB cdb, Quaery quaery, Object... objects);



}
