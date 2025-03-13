package com.example.CountingStarHotel.codeGenerate.generator;

import com.example.CountingStarHotel.codeGenerate.writter.EntityFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class EntityCodeGenerator {
    public static void createFile(Path entitiesDirectoryPath, String fields, String className) throws IOException {
        StringBuilder code = new StringBuilder();
        code.append(EntityFileWriter.writeFile(fields, className));

        Files.write(entitiesDirectoryPath, code.toString().getBytes(StandardCharsets.UTF_8));
    }
}
