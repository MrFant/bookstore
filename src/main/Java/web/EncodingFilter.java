package web;

import javax.activation.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        HttpServletRequest httpServletRequest=(HttpServletRequest)req;
//        httpServletRequest.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("filter................................");
        System.out.println("fuck");
        Context context= null;
        try {
            context = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Context envContext= null;
        try {
            envContext = (Context)context.lookup("java:/com/env/jndi/h2");
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

}
