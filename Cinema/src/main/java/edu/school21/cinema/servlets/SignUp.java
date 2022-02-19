package edu.school21.cinema.servlets;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UserRepositoryImpl;
import edu.school21.cinema.services.UserValidatorService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class SignUp extends HttpServlet {
    UserRepositoryImpl userRepository = new UserRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/html/signUp.html");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext cntx = getServletContext();
        String p = cntx.getInitParameter("myParam");
        final String name = req.getParameter("name");
        final String lastName = req.getParameter("lastName");
        final String phoneNumber = req.getParameter("phoneNumber");
        final String passWord = req.getParameter("passWord");
        final User user = new User(name, lastName, phoneNumber, passWord);
        try {
            UserValidatorService.throwIfNotValid(user);
        } catch (IllegalArgumentException e) {
            PrintWriter pw = resp.getWriter();
            pw.write(e.getMessage() + p);
            return;
        }
        userRepository.save(user);
        resp.sendRedirect("signIn");
    }
}
