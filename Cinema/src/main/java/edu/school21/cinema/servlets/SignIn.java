package edu.school21.cinema.servlets;
import edu.school21.cinema.config.ServletContextAttributes;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class SignIn extends HttpServlet {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext)servletContext.getAttribute(ServletContextAttributes.SPRING_CONTEXT);
        userRepository = applicationContext.getBean(UserRepository.class);
        bCryptPasswordEncoder = applicationContext.getBean(BCryptPasswordEncoder.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher rd = req.getRequestDispatcher("/html/signIn.html");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String phoneNumber = req.getParameter("phoneNumber");
        final String passWord = req.getParameter("passWord");
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

        if (user.isPresent() && bCryptPasswordEncoder.matches(passWord, user.get().getPassword())) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/html/profile.html");
        } else {
            resp.sendRedirect(req.getContextPath() + "/signUp");
        }
    }
}
