package edu.school21.cinema.servlets;
import edu.school21.cinema.config.Consts;
import edu.school21.cinema.models.domain.User;
import edu.school21.cinema.models.domain.UserAuthentication;
import edu.school21.cinema.services.UserAuthenticationService;
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
import java.time.LocalDateTime;
import java.util.Optional;

public class SignInServlet extends HttpServlet {
    private UserService userService;
    private UserAuthenticationService userAuthenticationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext)servletContext.getAttribute(Consts.ServletContextAttributes.SPRING_CONTEXT);
        userService = applicationContext.getBean(UserService.class);
        userAuthenticationService = applicationContext.getBean(UserAuthenticationService.class);
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
        Optional<User> user = userService.findByPhoneNumber(phoneNumber);
        if (user.isPresent() && userService.isCorrectPassword(user.get(), passWord)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user.get());

            String address = req.getRemoteAddr();
            address = address.equals("0:0:0:0:0:0:0:1")
                    ? "127.0.0.1"
                    : address;

            userAuthenticationService.save(new UserAuthentication(user.get(), address, LocalDateTime.now()));
            resp.sendRedirect(req.getContextPath() + "/profile");
        } else {
            resp.sendRedirect(req.getContextPath() + "/signIn");
        }
    }
}
