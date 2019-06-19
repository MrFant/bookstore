package web;

import domain.User;
import exception.LoginException;
import service.UserService;

//import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserService userService = new UserService();

        try {
            User user = userService.login(username, password);
            // 登陆失败时抛出LoginException
            request.getSession().setAttribute("user", user);
            String role = user.getRole();

            if ("Administrator".equals(role)) {
                response.sendRedirect(request.getContextPath() + "WebContent/admin/login/home.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "WebContent/client/myAccount.jsp");
            }
        } catch (LoginException e) {
            e.printStackTrace();
            request.setAttribute("login_message", e.getMessage());
            request.getRequestDispatcher("/WebContent/client/login.jsp").forward(request, response);
            // 把错误信息存储在request的属性中，所以必须使用request的dispatcher方法转发request请求
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }
}
