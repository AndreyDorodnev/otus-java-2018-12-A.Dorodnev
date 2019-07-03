package front.servlets;

import front.helpers.CookiesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import front.template.TemplateProcessor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UserServlet extends HttpServlet {

    @Autowired
    private TemplateProcessor templateProcessor;

    public UserServlet() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        templateProcessor = new TemplateProcessor();
        Cookie[] cookies = request.getCookies();
        String name = CookiesHelper.getName(cookies);
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("user",name);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(templateProcessor.getPage("useredit.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
