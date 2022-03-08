package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class AvatarUploader extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        String userPhone = getUserPhone(req);
        String avatarsDir = (String) req.getServletContext().getAttribute("avatarsDir");
        Path userAvatarsDir = Paths.get(avatarsDir, userPhone);
        Files.createDirectories(userAvatarsDir);

        String strUrl = req.getParameter("url");
        URL url = new URL(strUrl);
        try (InputStream is = url.openStream()) {
            Path avatarPath = userAvatarsDir.resolve(url.getFile() + "_" + UUID.randomUUID().toString());
            Files.copy(is, avatarPath);
        }
    }

    private String getUserPhone(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return Optional.ofNullable(session)
                .map(s -> (User) s.getAttribute("user"))
                .map(User::getPhoneNumber).orElseThrow(() -> new IllegalStateException("Failed to get user phone when saving user avatar."));
    }
}
