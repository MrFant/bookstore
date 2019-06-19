package web;

import domain.User;
import exception.RegisterException;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import utils.ActiveCodeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user=new User();
        try {
            BeanUtils.populate(user,request.getParameterMap());
            user.setActiveCode(ActiveCodeUtils.creatActiveCode());
        }catch (InvocationTargetException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        UserService userService=new UserService();
        try {
            userService.register(user);
        } catch (RegisterException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
            return;
        }
        response.sendRedirect(request.getContextPath()+"/WebContent/client/registersuccess.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);

    }
}
