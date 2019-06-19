package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceUtils {
    private static DataSource dataSource=null;
    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();
    static {
        ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
        /*
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/user");
        try {
            comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("");
        comboPooledDataSource.setInitialPoolSize(5);
        comboPooledDataSource.setMaxPoolSize(15);
        */
        dataSource=comboPooledDataSource;
    }


    public static DataSource getDataSource(){
        return dataSource;
    }
    /**
     * 获得一个连接，在手动处理事务过程中
     *
     * @return
     * @throws java.sql.SQLException
     */
    public static Connection getConnection() throws SQLException{
        Connection connection=threadLocal.get();
        if (connection==null){
            connection=dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;

    }

    /**
     * start Ttransaction
     * @throws SQLException
     */
    public static void startTransaction() throws SQLException{
        Connection connection=getConnection();
        if (connection!=null){
            connection.setAutoCommit(false);
        }

    }

    public static void releaseAndCloseConnection() throws SQLException{
        Connection connection=getConnection();
        if (connection!=null){
            connection.commit();
            threadLocal.remove();
            connection.close();
        }
    }

    public static void rollback() throws SQLException{
        Connection connection=getConnection();
        if (connection!=null){
            connection.rollback();
        }
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(

                DataSourceUtils.getDataSource().getConnection()
        );

    }
}
