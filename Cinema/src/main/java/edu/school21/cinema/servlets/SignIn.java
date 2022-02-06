package edu.school21.cinema.servlets;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UserRepositoryImpl;
import edu.school21.cinema.services.UserValidatorService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class SignIn extends HttpServlet {
    UserRepositoryImpl userRepository = new UserRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/html/signIn.html");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String phoneNumber = req.getParameter("phoneNumber");
        final String passWord = req.getParameter("passWord");
        Optional<User> user = userRepository.findByPhone(phoneNumber);
        if (user.isPresent()) {
            PrintWriter pw = resp.getWriter();
            pw.write("User is found");
        } else {
            PrintWriter pw = resp.getWriter();
            pw.write("User is not found");
        }
    }
}
