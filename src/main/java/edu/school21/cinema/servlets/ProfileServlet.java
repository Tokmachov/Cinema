package edu.school21.cinema.servlets;
import edu.school21.cinema.config.Consts;
import edu.school21.cinema.models.domain.FileMeta;
import edu.school21.cinema.models.domain.User;
import edu.school21.cinema.models.domain.UserAuthentication;
import edu.school21.cinema.models.dto.AuthenticationDto;
import edu.school21.cinema.services.AvatarService;
import edu.school21.cinema.services.UserAuthenticationMapper;
import edu.school21.cinema.services.UserAuthenticationService;
import edu.school21.cinema.services.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class ProfileServlet extends HttpServlet {

    private AvatarService avatarService;
    private UserService userService;
    private UserAuthenticationService userAuthenticationService;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute(Consts.ServletContextAttributes.SPRING_CONTEXT);
        avatarService = applicationContext.getBean(AvatarService.class);
        userService = applicationContext.getBean(UserService.class);
        userAuthenticationService = applicationContext.getBean(UserAuthenticationService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        User user = (User) req.getSession(false).getAttribute("user");

        FileMeta[] avatars = avatarService.getFileMetas(user);
        List<UserAuthentication> userAuthentications = userAuthenticationService.findByUserId(user.getId());
        List<AuthenticationDto> authenticationDtos = userAuthentications
                .stream()
                .map(UserAuthenticationMapper::toDto)
                .collect(Collectors.toList());

        req.setAttribute("avatarMetas", avatars);
        req.setAttribute("authentications", authenticationDtos);

        RequestDispatcher rd = req.getRequestDispatcher("/jsp/profile.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession(false).getAttribute("user");
        Part filePart = req.getPart("userAvatar");

        if (filePart != null) {
            String fileName = filePart.getSubmittedFileName();
            try (InputStream is = filePart.getInputStream()) {
                avatarService.saveOnDisc(user, is, fileName);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}
