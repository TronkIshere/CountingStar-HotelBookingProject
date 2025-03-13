package com.example.CountingStarHotel.codeGenerate.generator;

import com.example.CountingStarHotel.codeGenerate.writter.RepoFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class RepositoryCodeGenerator {
    public static void createFile(Path repoDirectoryPath, String selectedEntity) throws IOException {
        String repoName = selectedEntity + "Repository";
        Path filePath = repoDirectoryPath.resolve(repoName + ".java");

        StringBuilder code = new StringBuilder();
        code.append(RepoFileWriter.writeFile(selectedEntity));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info("Repository created at " + filePath);
    }
}
