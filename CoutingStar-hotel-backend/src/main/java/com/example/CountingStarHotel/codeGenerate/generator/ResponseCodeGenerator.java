package com.example.CountingStarHotel.codeGenerate.generator;

import com.example.CountingStarHotel.codeGenerate.writter.EntityFileWriter;
import com.example.CountingStarHotel.codeGenerate.writter.ResponseFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

@Slf4j
public class ResponseCodeGenerator {
    public static void createFile(Path entitiesDirectoryPath, String fields, String responseNameString, String selectedEntity) throws IOException {
        Path filePath = entitiesDirectoryPath.resolve(selectedEntity.toLowerCase()).resolve(responseNameString + ".java");

        StringBuilder code = new StringBuilder();
        code.append(ResponseFileWriter.writeFile(fields, responseNameString, selectedEntity));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info( responseNameString + " created at " + filePath);
    }
}
