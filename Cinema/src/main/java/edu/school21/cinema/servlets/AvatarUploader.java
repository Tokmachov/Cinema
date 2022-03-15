package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class AvatarUploader extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/html/uploadAvatar.html");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path userDir = createDirForUserAvatars(req);
        String strUrl = req.getParameter("url");
        String fileName = req.getParameter("fileName");
        downloadAndSave(strUrl, fileName, userDir);
    }

    private Path createDirForUserAvatars(HttpServletRequest req) throws IOException {
        String userPhone = getUserPhone(req);
        ServletContext servletContext = req.getServletContext();
        String avatarsDir = servletContext.getInitParameter("avatarsDir");
        Path userAvatarsDir = Paths.get(avatarsDir, userPhone);
        return Files.createDirectories(userAvatarsDir);
    }

    private String getUserPhone(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return Optional.ofNullable(session)
                .map(s -> (User) s.getAttribute("user"))
                .map(User::getPhoneNumber).orElseThrow(() -> new IllegalStateException("Failed to get user phone when saving user avatar."));
    }

    private void downloadAndSave(String strUrl, String fileName, Path avatarsDir) throws IOException {
        URL url = new URL(strUrl);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "NING/1.0");

        try (InputStream is = urlConnection.getInputStream()) {
            Path avatarPath = avatarsDir.resolve(fileName + "_" + UUID.randomUUID().toString().substring(0,4));
            Files.copy(is, avatarPath);
        }
    }
}
