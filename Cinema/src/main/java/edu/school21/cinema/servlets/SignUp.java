package edu.school21.cinema.servlets;
import edu.school21.cinema.config.ServletContextAttributes;
import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;

public class SignUp extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext)servletContext.getAttribute(ServletContextAttributes.SPRING_CONTEXT);
        userService = applicationContext.getBean(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher rd = req.getRequestDispatcher("/html/signUp.html");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = extractUser(req);
        try {
            userService.save(user);
        } catch (Exception e) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(e.getMessage());
            return;
        }
        resp.sendRedirect("signIn");
    }

    private User extractUser(HttpServletRequest req) {
        String name = req.getParameter("name");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phoneNumber");
        String passWord = req.getParameter("passWord");
        return new User(null, name, lastName, phoneNumber, passWord);
    }
}
