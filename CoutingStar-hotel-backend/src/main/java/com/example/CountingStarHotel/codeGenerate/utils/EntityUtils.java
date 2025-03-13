package com.example.CountingStarHotel.codeGenerate.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class EntityUtils {
    public static List<String> getEntityProperties(String selectedEntity) throws ClassNotFoundException, IOException {
        List<String> properties = new ArrayList<>();

        Path basePackage = ProjectPathUtils.getOrCreateDirectory("entity");
        String packageName = ProjectPathUtils.getPackageFromPath(basePackage);
        String className = packageName + "." + selectedEntity;

        Class<?> entityClass = Class.forName(className);

        for (Field field : entityClass.getDeclaredFields()) {
            properties.add(field.getType().getSimpleName() + " " + field.getName());
        }

        return properties;
    }

    public static String selectEntityTable(List<String> entityClasses) {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        String selectedEntity = null;

        while (flag) {
            System.out.println("Entity list:");
            for (int i = 0; i < entityClasses.size(); i++) {
                System.out.println((i + 1) + ". " + entityClasses.get(i));
            }
            System.out.println("0. :Exit");

            String input = scanner.nextLine();
            if(input.equals("0")) {
                System.out.println("Exit from generateService");
            } else {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= entityClasses.size()) {
                    selectedEntity = entityClasses.get(choice - 1);
                    System.out.println("You choose: " + selectedEntity);
                    flag = false;
                } else {
                    System.out.println("Please select again!");
                }
            }
        }
        return selectedEntity;
    }

    public static List<String> getEntityClasses(Path path) {
        List<String> entityClasses = new ArrayList<>();

        if (!Files.exists(path) || !Files.isDirectory(path)) return entityClasses;

        try (Stream<Path> files = Files.list(path)) {
            files.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".java"))
                    .map(p -> p.getFileName().toString().replace(".java", ""))
                    .forEach(entityClasses::add);
        } catch (IOException e) {
            System.err.println("Error reading entity files: " + e.getMessage());
        }
        return entityClasses;
    }
}
