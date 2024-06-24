package Functions;

import Objects.ClassObject;
import utils.ConnectionPool;
import utils.ConnectionPoolImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassFunctionImpl implements ClassFunction{
    private Connection connection;
    private final ConnectionPool connectionPool;
    public ClassFunctionImpl(ConnectionPool connectionPool){
        if(connectionPool == null){
            this.connectionPool = new ConnectionPoolImpl();
        }else {
            this.connectionPool =connectionPool;
        }
        try {
            this.connection = connectionPool.getConnection("Class");
            //stop automatic mode
            if (this.connection.getAutoCommit()) this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean execute(PreparedStatement preparedStatement){
        if (preparedStatement == null) return false;
        //preparedStatement được biên dịch và truyền đầy đủ tham số
        try {
            int result = preparedStatement.executeUpdate();
            if (result==0) {
                this.connection.rollback();
                return false;
            }
            //xác nhận thực thi sau cùng
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            preparedStatement = null;
        }
        return false;
    }
    private boolean isExist(ClassObject item){
        String sql = "select class_id from class where class_name=?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getClass_name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet!=null && resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return false;
    }
    private boolean isEmpty(ClassObject item){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM class WHERE dateDelete IS NULL");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql.toString());
            preparedStatement.setInt(1,item.getClass_id());
            preparedStatement.setString(2, item.getClass_name());
            preparedStatement.setString(3,item.getDepartment());
            preparedStatement.setString(4,item.getInstructorName());
            preparedStatement.setDate(5,item.getDateAdd());
            preparedStatement.setDate(6,item.getDateUpdate());
            preparedStatement.setDate(7,item.getDateDelete());
            preparedStatement.setString(8,item.getStatus());
            preparedStatement.setInt(9,item.getSemester());
            boolean result = preparedStatement.execute();

            do {
                if (result){
                    ResultSet resultSet = preparedStatement.getResultSet();
                    if (resultSet != null && resultSet.next()){
                        return false;
                    }
                    result=preparedStatement.getMoreResults(preparedStatement.KEEP_CURRENT_RESULT);
                }
            } while (result);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    @Override
    public boolean addClass(ClassObject item) {
        if (this.isExist(item)) return false;
        StringBuilder sql = new StringBuilder();
        sql.append("insert into class(");
        sql.append("class_name, department, instructorName,");
        sql.append("dateAdd,status,semester");
        sql.append(") ");
        sql.append("VALUES(?,?,?,?,?,?)");
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        //biên dịch
        try {
            PreparedStatement preparedStatement =this.connection.prepareStatement(sql.toString());
            preparedStatement.setString(1, item.getClass_name());
            preparedStatement.setString(2,item.getDepartment());
            preparedStatement.setString(3,item.getInstructorName());
            preparedStatement.setString(4,String.valueOf(sqlDate));
            preparedStatement.setString(5,item.getStatus());
            preparedStatement.setInt(6,item.getSemester());
            return this.execute(preparedStatement);
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        return false;
    }

    @Override
    public boolean editClass(ClassObject item) {
        StringBuilder sql = new StringBuilder();
        sql.append("update class ");
        sql.append("set class_name=?,department=?, instructorName=?, dateUpdate=?, status=?,semester=? ");
        sql.append("where class_id=?");
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql.toString());
            preparedStatement.setString(1, item.getClass_name());
            preparedStatement.setString(2, item.getDepartment());
            preparedStatement.setString(3, item.getInstructorName());
            preparedStatement.setString(4,String.valueOf(sqlDate));
            preparedStatement.setString(5, item.getStatus());
            preparedStatement.setInt(6, item.getSemester());
            preparedStatement.setInt(7,item.getClass_id());
            return this.execute(preparedStatement);
        } catch (SQLException e) {
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return false;
    }

    @Override
    public boolean deleteClass(ClassObject item) {
        StringBuilder sql = new StringBuilder();
        sql.append("update class ");
        sql.append("set dateDelete=? ");
        sql.append("where class_id=?");
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(String.valueOf(sql));
            preparedStatement.setString(1,String.valueOf(sqlDate));
            preparedStatement.setInt(2,item.getClass_id());
            return this.execute(preparedStatement);
        } catch (SQLException e) {
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
        return false;
    }

    @Override
    public ClassObject getClass(int id) {
        String sql = "select * from class where class_id=?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet != null && resultSet.next()){
                ClassObject item = new ClassObject();
                item.setClass_id(resultSet.getInt("class_id"));
                item.setClass_name(resultSet.getString("class_name"));
                item.setDepartment(resultSet.getString("department"));
                item.setInstructorName(resultSet.getString("instructorName"));
                item.setDateAdd(resultSet.getDate("dateAdd"));
                item.setStatus(resultSet.getString("status"));
                item.setSemester(resultSet.getInt("semester"));
                return item;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<ClassObject> getClasses(ClassObject var1, int date) {
        ArrayList<ClassObject> result = new ArrayList<>();
        ClassObject item = null;
        //sql statement
        String sql = "SELECT * FROM class WHERE dateDelete IS NULL AND YEAR(dateAdd) = ?";
        //sql += " ORDER BY class_id DESC";
        //sql += " LIMIT "+at+" TOTAL";
        //compile sql statement
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            //
            if (resultSet!=null){
                while (resultSet.next()){
                    item = new ClassObject();
                    item.setClass_id(resultSet.getInt("class_id"));
                    item.setClass_name(resultSet.getString("class_name"));
                    item.setDepartment(resultSet.getString("department"));
                    item.setInstructorName(resultSet.getString("instructorName"));
                    item.setDateAdd(resultSet.getDate("dateAdd"));
                    item.setDateUpdate(resultSet.getDate("dateUpdate"));
                    item.setDateDelete(resultSet.getDate("dateDelete"));
                    item.setStatus(resultSet.getString("status"));
                    item.setSemester(resultSet.getInt("semester"));
                    result.add(item);
                }
                resultSet.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //what if we fail?
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ArrayList<ClassObject> getClasses(ClassObject var1, String var2) {
        ArrayList<ClassObject> result = new ArrayList<>();
        ClassObject item = null;
        //sql statement
        String sql = "SELECT * FROM class";
        //sql += " ORDER BY class_id DESC";
        //sql += " LIMIT "+at+" TOTAL";
        //compile sql statement
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            //
            if (resultSet!=null){
                while (resultSet.next()){
                    item = new ClassObject();
                    item.setClass_id(resultSet.getInt("class_id"));
                    item.setClass_name(resultSet.getString("class_name"));
                    item.setDepartment(resultSet.getString("department"));
                    item.setInstructorName(resultSet.getString("instructorName"));
                    item.setDateAdd(resultSet.getDate("dateAdd"));
                    item.setDateUpdate(resultSet.getDate("dateUpdate"));
                    item.setDateDelete(resultSet.getDate("dateDelete"));
                    item.setStatus(resultSet.getString("status"));
                    item.setSemester(resultSet.getInt("semester"));
                    result.add(item);
                }
                resultSet.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //what if we fail?
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    @Override
    public ArrayList<ClassObject> getClasses(ClassObject classObject, int at, byte total) {
        ArrayList<ClassObject> result = new ArrayList<>();
        ClassObject item = null;
        //sql statement
        String sql = "SELECT * FROM class where dateDelete is NULL";
        //sql += " ORDER BY class_id DESC";
        //sql += " LIMIT "+at+" TOTAL";
        //compile sql statement
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            //
            if (resultSet!=null){
                while (resultSet.next()){
                    item = new ClassObject();
                    item.setClass_id(resultSet.getInt("class_id"));
                    item.setClass_name(resultSet.getString("class_name"));
                    item.setDepartment(resultSet.getString("department"));
                    item.setInstructorName(resultSet.getString("instructorName"));
                    item.setDateAdd(resultSet.getDate("dateAdd"));
                    item.setDateUpdate(resultSet.getDate("dateUpdate"));
                    item.setDateDelete(resultSet.getDate("dateDelete"));
                    item.setStatus(resultSet.getString("status"));
                    item.setSemester(resultSet.getInt("semester"));
                    result.add(item);
                }
                resultSet.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //what if we fail?
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

/*    public static void main(String[] args){
        ClassFunction classFunction = new ClassFunctionImpl((ConnectionPool) null);
        ClassObject classObject = new ClassObject();
        ArrayList<ClassObject> list = classFunction.getClasses(null,0,(byte) 10);
        list.forEach(System.out::println);

    }*/
}


