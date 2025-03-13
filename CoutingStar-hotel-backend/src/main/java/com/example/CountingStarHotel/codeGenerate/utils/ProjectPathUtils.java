package com.example.CountingStarHotel.codeGenerate.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ProjectPathUtils {
    private static final Path SRC_MAIN_JAVA = Paths.get("src", "main", "java");

    public static Path getOrCreateDirectory(String folderName) throws IOException {
        Path basePath = Paths.get("src", "main", "java", "com", "example");
        Path entityDir = findEntityFolder(basePath, folderName);

        if (entityDir == null) {
            entityDir = basePath.resolve(folderName);
            Files.createDirectories(entityDir);
        }
        System.out.println(folderName + " directory path: " + entityDir.toAbsolutePath());
        return entityDir;
    }

    public static Path findEntityFolder(Path dir, String folderName) {
        try (Stream<Path> paths = Files.walk(dir)) {
            return paths.filter(Files::isDirectory)
                    .filter(p -> p.getFileName().toString().equalsIgnoreCase(folderName))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            System.err.println("Error finding entity folder: " + e.getMessage());
            return null;
        }
    }

    public static String findPackage(String packageName) {
        try (Stream<Path> paths = Files.walk(SRC_MAIN_JAVA)) {
            return paths.filter(Files::isDirectory)
                    .filter(p -> p.getFileName().toString().equalsIgnoreCase(packageName))
                    .map(p -> getPackageFromPath(p))
                    .findFirst()
                    .orElse("");
        } catch (IOException e) {
            System.err.println("Error finding package: " + e.getMessage());
            return "";
        }
    }

    public static String getPackageFromPath(Path path) {
        Path base = Paths.get("src", "main", "java");
        Path relativePath = base.relativize(path);
        return relativePath.toString().replace(FileSystems.getDefault().getSeparator(), ".");
    }
}
