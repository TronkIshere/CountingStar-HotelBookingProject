package com.example.CountingStarHotel.codeGenerate.generator;

import com.example.CountingStarHotel.codeGenerate.writter.RequestFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class RequestCodeGenerator {
    public static void createFile(Path entitiesDirectoryPath, String fields, String requestNameString, String selectedEntity) throws IOException {
        Path filePath = entitiesDirectoryPath.resolve(selectedEntity.toLowerCase()).resolve(requestNameString + ".java");

        StringBuilder code = new StringBuilder();
        code.append(RequestFileWriter.writeFile(fields, requestNameString, selectedEntity));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info( requestNameString + " created at " + filePath);
    }
}
