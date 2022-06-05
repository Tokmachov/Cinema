package edu.school21.cinema.services;

import edu.school21.cinema.models.domain.FileMeta;
import edu.school21.cinema.models.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AvatarService {
    private final Path appAvatarsPath;

    public AvatarService(String appAvatarsPath) {
        Path path = Paths.get(appAvatarsPath);
        this.appAvatarsPath = path;
    }

    public void saveOnDisc(User user, InputStream file, String fileName) throws IOException {
        Path userAvatarsStoragePath = createDirForUserAvatars(appAvatarsPath, user);
        Path avatarPath = userAvatarsStoragePath.resolve(fileName);

        if (Files.exists(avatarPath) && !Files.isDirectory(avatarPath)) {
            String prefix = UUID.randomUUID().toString().substring(0,3);
            fileName = prefix + "__" + fileName;
            avatarPath = userAvatarsStoragePath.resolve(fileName);
        }
        Files.copy(file, avatarPath);
    }

    public FileMeta[] getFileMetas(User user) throws IOException {
        Path userAvatarsPath = resolveUserAvatarsDir(appAvatarsPath, user);

        if (!Files.exists(userAvatarsPath)) {
            return new FileMeta[0];
        }

        try (Stream<Path> pathsOfUserAvatars = Files.list(userAvatarsPath)) {
            return pathsOfUserAvatars
                    .filter(Files::isRegularFile)
                    .map(path -> {
                        String name = path.getFileName().toString();
                        long sizeKb = path.toFile().length() / 1000;
                        String mime = getMime(path);
                        return new FileMeta(name, sizeKb, mime);
                    })
                    .collect(Collectors.toList())
                    .toArray(new FileMeta[0]);
        }
    }

    private Path createDirForUserAvatars(Path appAvatarsPath, User user) throws IOException {
        Path userAvatarsPath = resolveUserAvatarsDir(appAvatarsPath, user);
        return Files.createDirectories(userAvatarsPath);
    }

    private String getMime(Path path) {
        String fileName = path.getFileName().toString();
        int extStart = fileName.lastIndexOf(".");
        if (extStart == -1 || extStart == fileName.length() - 1) {
            return "unknown";
        }
        String ext = fileName.substring(extStart + 1);
        String mimePrefix = isImage(ext)
                ? "image"
                : "file";

        return mimePrefix + "/" + fileName.substring(extStart + 1);
    }

    private boolean isImage(String ext) {
        List<String> imageExts = Arrays.asList("JPEG", "JPG", "PNG", "GIF", "TIFF", "PSD", "PDF", "EPS", "AI", "INDD", "RAW");
        for (String imageExt : imageExts) {
            if (ext.toLowerCase().contains(imageExt.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private Path resolveUserAvatarsDir(Path appAvatarsPath, User user) {
        return appAvatarsPath.resolve(user.getPhoneNumber());
    }

    public Optional<Path> resolveAvatarPath(String fileName, User user) {
        Path avatarPath = appAvatarsPath.resolve(user.getPhoneNumber());
        avatarPath = avatarPath.resolve(fileName);

        return Files.exists(avatarPath)
                ? Optional.of(avatarPath)
                : Optional.empty();
    }
}
