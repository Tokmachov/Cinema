package edu.school21.cinema.servlets;
import edu.school21.cinema.config.Consts;
import edu.school21.cinema.models.domain.User;
import edu.school21.cinema.services.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;

public class LogOutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/signIn");
    }
}
