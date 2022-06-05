package edu.school21.cinema.servlets;

import edu.school21.cinema.config.Consts;
import edu.school21.cinema.models.domain.User;
import edu.school21.cinema.services.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class AvatarSelectionServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute(Consts.ServletContextAttributes.SPRING_CONTEXT);
        userService = applicationContext.getBean(UserService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String avatarFileName = req.getParameter("avatarChoice");

        User user = (User) req.getSession(false).getAttribute("user");

        if (avatarFileName != null) {
            user.setAvatarFileName(avatarFileName);
            userService.update(user);
        }
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}
