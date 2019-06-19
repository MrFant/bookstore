package dao;

import domain.User;
//import javafx.scene.chart.PieChart;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import javax.sql.DataSource;
import java.sql.SQLException;

public class UserDao {
    public void addUser(User user) throws SQLException{
        String sql="insert into user(username,password,gender,email,telephone,introduce,activeCode)" +
                " values(?,?,?,?,?,?,?)";
        QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
        int row=queryRunner.update(sql,user.getUsername(),user.getPassword(),user.getGender(),user.getEmail(),
                user.getTelephone(),user.getIntroduce(),user.getActiveCode());
        if (row==0)
            throw new RuntimeException();

    }

    public User findUserByActiveCode(String activeCode) throws SQLException{
        String sql="select * from user where activeCode=?";
        QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
        return queryRunner.query(sql,new BeanHandler<User>(User.class),activeCode);


    }

    public User findUserByUsernameAndPassword(String username,String password) throws SQLException{
        String sql="select * from user where username=? and password=?";
        QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
        return queryRunner.query(sql,new BeanHandler<User>(User.class),username,password);
    }


    public static void main(String[] args) {
       UserDao userDao=
       new UserDao();

        try {
            userDao.addUser(new User());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
