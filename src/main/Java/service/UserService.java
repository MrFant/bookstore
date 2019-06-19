package service;

import dao.UserDao;
import domain.User;
import exception.LoginException;
import exception.RegisterException;
import utils.MailUtils;

//import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class UserService {
    private UserDao userDao=new UserDao();
    public void register(User user) throws RegisterException {
        try {
            userDao.addUser(user);
            String emailMsg = "感谢您注册网上书城，点击"
                    + "<a href='http://localhost:8080/bookstore/activeUser?activeCode="
                    + user.getActiveCode() + "'>&nbsp;激活&nbsp;</a>后使用。"
                    + "<br />为保障您的账户安全，请在24小时内完成激活操作";
            MailUtils.sendMail(user.getEmail(), emailMsg);
        }catch (Exception e){
            e.printStackTrace();
            throw new RegisterException("注册失败");
        }
    }

    public User login(String username,String password) throws LoginException {
        try {
            User user=userDao.findUserByUsernameAndPassword(username,password);
            if (user!=null){
                if (user.getState() == 1) {

                    return user;
                } else {
                    throw new LoginException("用户未激活");
                }

            }
            throw new LoginException("用户名或密码错误");

        }catch (SQLException e){
            e.printStackTrace();
            throw new LoginException("登陆失败");
        }

    }

    public static void main(String[] args) {
        try {
            User user=
            new UserService().login("admin","123456");
            System.out.println(user.getEmail());
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }
}
