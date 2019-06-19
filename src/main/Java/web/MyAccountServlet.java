package web;

import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MyAccountServlet")
public class MyAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 点击前台系统中的【我的账户】，分以下两种情况：
         * 1、用户未登录，进入登录页面
         * 2、用户已登录
         *   a、超级用户，进入到后台系统
         *   b、普通用户，登录到我的账户
         */
        User user=(User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath()+"/WebContent/client/login.jsp");
        }
        else {
            if (user.getRole().equals("超级用户")){
                response.sendRedirect(request.getContextPath()+"/WebContent/admin/login/home.jsp");
            }
            else {
                response.sendRedirect(request.getContextPath()+"/WebContent/client/myAccount.jsp");
            }
        }
    }
}
