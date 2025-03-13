package com.example.CountingStarHotel.codeGenerate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
@Slf4j
public class CodeGeneratorCLI {

    private Path getOrCreateDirectory(String folderName) throws IOException {
        Path basePath = Paths.get("src", "main", "java", "com", "example");
        Path entityDir = findEntityFolder(basePath, folderName);

        if (entityDir == null) {
            entityDir = basePath.resolve(folderName);
            Files.createDirectories(entityDir);
        }
        System.out.println(folderName + " directory path: " + entityDir.toAbsolutePath());
        return entityDir;
    }

    private Path findEntityFolder(Path dir, String folderName) {
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

    private static final Path SRC_MAIN_JAVA = Paths.get("src", "main", "java");

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

    private static String getPackageFromPath(Path path) {
        Path base = Paths.get("src", "main", "java");
        Path relativePath = base.relativize(path);
        return relativePath.toString().replace(FileSystems.getDefault().getSeparator(), ".");
    }

    private String getRequiredImports(String fields) {
        StringBuilder imports = new StringBuilder();

        // Date and time
        if (fields.contains("LocalDateTime")) {
            imports.append("import java.time.LocalDateTime;\n");
        } else if (fields.contains("LocalDate")) {
            imports.append("import java.time.LocalDate;\n");
        }
        if (fields.contains("Instant")) {
            imports.append("import java.time.Instant;\n");
        }
        if (fields.contains("Timestamp")) {
            imports.append("import java.sql.Timestamp;\n");
        }

        // Number
        if (fields.contains("BigDecimal")) {
            imports.append("import java.math.BigDecimal;\n");
        }
        if (fields.contains("Double")) {
            imports.append("import java.lang.Double;\n");
        }
        if (fields.contains("Float")) {
            imports.append("import java.lang.Float;\n");
        }

        // collections
        if (fields.contains("List")) {
            imports.append("import java.util.List;\n");
        }
        if (fields.contains("Set")) {
            imports.append("import java.util.Set;\n");
        }
        if (fields.contains("Map")) {
            imports.append("import java.util.Map;\n");
        }

        // special
        if (fields.contains("UUID")) {
            imports.append("import java.util.UUID;\n");
        }
        if (fields.contains("Boolean")) {
            imports.append("import java.lang.Boolean;\n");
        }

        imports.append("\n");

        return imports.toString();
    }

    @ShellMethod(key = "addEntity", value = "Generate a new Entity class")
    public String generateEntity(String name, String fields) throws IOException {
        String className = capitalize(name);
        Path directoryPath = getOrCreateDirectory("entity");
        Path filePath = directoryPath.resolve(className + ".java");

        StringBuilder code = new StringBuilder();
        code.append(writeEntityFile(fields, className));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        return "Entity created at " + filePath;
    }

    public StringBuilder writeEntityFile(String fields, String className) {
        StringBuilder code = new StringBuilder();
        code.append("package ").append(findPackage("entity")).append(";\n\n");
        code.append("import ").append(findPackage("entity")).append(".common.AbstractEntity;\n");
        code.append("import jakarta.persistence.*;\n");
        code.append("import lombok.*;\n");
        code.append("import lombok.experimental.FieldDefaults;\n\n");
        code.append(getRequiredImports(fields));

        //add annotation
        code.append("@Entity\n");
        code.append("@Getter\n");
        code.append("@Setter\n");
        code.append("@AllArgsConstructor\n");
        code.append("@NoArgsConstructor\n");
        code.append("@FieldDefaults(level = AccessLevel.PRIVATE)\n");

        //add class
        code.append("public class ").append(className).append(" extends AbstractEntity<Long> {\n");

        //add properties
        for (String field : fields.split(",")) {
            String[] parts = field.split(":");
            if (parts.length == 2) {
                code.append("\t").append(parts[0]).append(" ").append(parts[1]).append(";\n");
            }
        }
        code.append("}\n");

        return code;
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    @ShellMethod(key = "addService", value = "Generate a new service class")
    public String generateService() throws IOException, ClassNotFoundException {
        Path entitiesDirectoryPath = getOrCreateDirectory("entity");
        Path repoDirectoryPath = getOrCreateDirectory("repository");
        Path interfaceDirectoryPath = getOrCreateDirectory("service");
        Path serviceDirectoryPath = getOrCreateDirectory("impl");
        Path controllerDirectoryPath = getOrCreateDirectory("controller");

        List<String> entityClasses = getEntityClasses(entitiesDirectoryPath);
        if (entityClasses.isEmpty()) return "Not Entity has found!";

        String selectedEntity = selectEntityTable(entityClasses);
        if (selectedEntity == null) return "Not Entity selected";

        List<String> entityPropertiesList = getEntityProperties(selectedEntity);;

        createRepositoryFile(repoDirectoryPath, selectedEntity);
        createInterfaceFile(interfaceDirectoryPath, selectedEntity, entityPropertiesList);
        createServiceFile(serviceDirectoryPath, selectedEntity, entityPropertiesList);
        createControllerFile(controllerDirectoryPath, selectedEntity, entityPropertiesList);

        return "Created controller, service, repository is completed";
    }

    private List<String> getEntityProperties(String selectedEntity) throws ClassNotFoundException, IOException {
        List<String> properties = new ArrayList<>();

        Path basePackage = getOrCreateDirectory("entity");
        String packageName = getPackageFromPath(basePackage);
        String className = packageName + "." + selectedEntity;

        Class<?> entityClass = Class.forName(className);

        for (Field field : entityClass.getDeclaredFields()) {
            properties.add(field.getType().getSimpleName() + " " + field.getName());
        }

        return properties;
    }

    private String selectEntityTable(List<String> entityClasses) {
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

    private List<String> getEntityClasses(Path path) {
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

    private void createRepositoryFile(Path repoDirectoryPath, String selectedEntity) throws IOException {
        String repoName = selectedEntity + "Repository";
        Path filePath = repoDirectoryPath.resolve(repoName + ".java");

        StringBuilder code = new StringBuilder();
        code.append(writeRepoFile(selectedEntity));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info("Repository created at " + filePath);
    }

    private StringBuilder writeRepoFile(String selectedEntity) {
        StringBuilder code = new StringBuilder();
        //add imports
        code.append("package ").append(findPackage("repository")).append(";\n\n");
        code.append("import ").append(findPackage("entity")).append(".").append(selectedEntity + ";\n");
        code.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
        code.append("\n");

        //add class
        code.append("public interface ")
                .append(selectedEntity)
                .append("Repository extends")
                .append(" JpaRepository<")
                .append(selectedEntity)
                .append(", Long> {\n}");
        return code;
    }

    private void createInterfaceFile(Path interfaceDirectoryPath, String selectedEntity, List<String> entityProperties) throws IOException {
        String interfaceName = selectedEntity + "Service";
        Path filePath = interfaceDirectoryPath.resolve(interfaceName + ".java");

        StringBuilder code = new StringBuilder();
        code.append(writeInterfaceFile(selectedEntity, entityProperties));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info("Interface created at " + filePath);
    }

    private StringBuilder writeInterfaceFile(String selectedEntity, List<String> entityProperties) {
        String propertiesString = String.join(", ", entityProperties);

        StringBuilder code = new StringBuilder();
        //add imports
        code.append("package ").append(findPackage("service")).append(";\n\n");
        code.append("import ").append(findPackage("entity")).append(".").append(selectedEntity + ";\n");
        code.append("import org.springframework.http.ResponseEntity;\n");
        code.append(getRequiredImports(propertiesString));

        //add class
        code.append("public interface ").append(selectedEntity).append("Service {\n\n");

        code.append("\t").append("ResponseEntity<").append(selectedEntity).append("> save").append(selectedEntity).append("(");
        if (!entityProperties.isEmpty()) {
            String params = String.join(", ", entityProperties);
            code.append(params);
        }
        code.append(");\n\n");

        code.append("\t").append("ResponseEntity<").append(selectedEntity).append("> get").append(selectedEntity)
                .append("ById(Long id);\n\n");

        code.append("\t").append("ResponseEntity<").append(selectedEntity).append("> update").append(selectedEntity).append("(Long id, ");
        if (!entityProperties.isEmpty()) {
            String params = String.join(", ", entityProperties);
            code.append(params);
        }
        code.append(");\n\n");

        code.append("\t").append("ResponseEntity<Void> delete").append(selectedEntity).append("ById(Long id);\n");
        code.append("}");
        return code;
    }

    private void createServiceFile(Path serviceDirectoryPath, String selectedEntity, List<String> entityProperties) throws IOException {
        String serviceImplName = selectedEntity + "ServiceImpl";
        Path filePath = serviceDirectoryPath.resolve(serviceImplName + ".java");

        StringBuilder code = new StringBuilder();
        code.append(writeServiceImplFile(selectedEntity, entityProperties));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info("Interface created at " + filePath);
    }

    private StringBuilder writeServiceImplFile(String selectedEntity, List<String> entityProperties) {
        String propertiesString = String.join(", ", entityProperties);

        StringBuilder code = new StringBuilder();
        //add import
        code.append("package ").append(findPackage("service")).append(".impl;\n\n");
        code.append("import ").append(findPackage("entity")).append(".").append(selectedEntity).append(";\n");
        code.append("import ").append(findPackage("repository")).append(".").append(selectedEntity).append("Repository;\n");
        code.append("import ").append(findPackage("service")).append(".").append(selectedEntity).append("Service;\n");
        code.append("import lombok.AccessLevel;").append("\n");
        code.append("import lombok.RequiredArgsConstructor;").append("\n");
        code.append("import lombok.experimental.FieldDefaults;").append("\n");
        code.append("import org.springframework.http.ResponseEntity;\n");
        code.append("import org.springframework.stereotype.Service;").append("\n");
        code.append(getRequiredImports(propertiesString));

        //add class
        code.append("@Service\n")
                .append("@RequiredArgsConstructor\n")
                .append("@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)\n")
                .append("public class ").append(selectedEntity).append("ServiceImpl implements ").append(selectedEntity).append("Service {\n\n");

        code.append("\t").append(selectedEntity).append("Repository ").append(lowerFirst(selectedEntity)).append("Repository;\n\n");

        code.append("\t@Override\n")
                .append("\tpublic ResponseEntity<").append(selectedEntity).append("> save").append(selectedEntity).append("(");
        if (!entityProperties.isEmpty()) {
            String params = String.join(", ", entityProperties);
            code.append(params);
        }
        code.append(") {\n")
                .append("\t\t").append(selectedEntity).append(" entity = new ").append(selectedEntity).append("();\n");

        for (String property : entityProperties) {
            String fieldName = property.split(" ")[1];
            code.append("\t\tentity.set").append(upperFirst(fieldName)).append("(").append(fieldName).append(");\n");
        }

        code.append("\t\t").append(selectedEntity).append(" savedEntity = ").append(lowerFirst(selectedEntity))
                .append("Repository.save(entity);\n")
                .append("\t\treturn ResponseEntity.ok(savedEntity);\n")
                .append("\t}\n\n");

        code.append("\t@Override\n")
                .append("\tpublic ResponseEntity<").append(selectedEntity).append("> get").append(selectedEntity)
                .append("ById(Long id) {\n")
                .append("\t\treturn ").append(lowerFirst(selectedEntity))
                .append("Repository.findById(id)\n")
                .append("\t\t\t.map(ResponseEntity::ok)\n")
                .append("\t\t\t.orElse(ResponseEntity.notFound().build());\n")
                .append("\t}\n\n");

        code.append("\t@Override\n")
                .append("\tpublic ResponseEntity<").append(selectedEntity).append("> update").append(selectedEntity)
                .append("(Long id, ");
        if (!entityProperties.isEmpty()) {
            String params = String.join(", ", entityProperties);
            code.append(params);
        }
        code.append(") {\n")
                .append("\t\treturn ").append(lowerFirst(selectedEntity)).append("Repository.findById(id)\n")
                .append("\t\t\t.map(existingEntity -> {\n");

        for (String property : entityProperties) {
            String fieldName = property.split(" ")[1];
            code.append("\t\t\t\texistingEntity.set").append(upperFirst(fieldName)).append("(").append(fieldName).append(");\n");
        }

        code.append("\t\t\t\t").append(selectedEntity).append(" updatedEntity = ")
                .append(lowerFirst(selectedEntity)).append("Repository.save(existingEntity);\n")
                .append("\t\t\t\treturn ResponseEntity.ok(updatedEntity);\n")
                .append("\t\t\t})\n")
                .append("\t\t\t.orElse(ResponseEntity.notFound().build());\n")
                .append("\t}\n\n");

        code.append("\t@Override\n")
                .append("\tpublic ResponseEntity<Void> delete").append(selectedEntity).append("ById(Long id) {\n")
                .append("\t\tif (").append(lowerFirst(selectedEntity)).append("Repository.existsById(id)) {\n")
                .append("\t\t\t").append(lowerFirst(selectedEntity)).append("Repository.deleteById(id);\n")
                .append("\t\t\treturn ResponseEntity.noContent().build();\n")
                .append("\t\t} else {\n")
                .append("\t\t\treturn ResponseEntity.notFound().build();\n")
                .append("\t\t}\n")
                .append("\t}\n");

        code.append("}\n");
        return code;
    }

    private String upperFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private String lowerFirst(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private void createControllerFile(Path controllerDirectoryPath, String selectedEntity, List<String> entityProperties) throws IOException {
        String controllerName = selectedEntity + "Controller";
        Path filePath = controllerDirectoryPath.resolve(controllerName + ".java");

        StringBuilder code = new StringBuilder();
        code.append(writeControllerFile(selectedEntity, entityProperties));

        Files.write(filePath, code.toString().getBytes(StandardCharsets.UTF_8));
        log.info("Interface created at " + filePath);
    }

    private StringBuilder writeControllerFile(String selectedEntity, List<String> entityProperties) {
        String propertiesString = String.join(", ", entityProperties);

        StringBuilder code = new StringBuilder();
        //add imports
        code.append("package ").append(findPackage("controller")).append(";\n\n");
        code.append("import ").append(findPackage("entity")).append(".").append(selectedEntity).append(";\n");
        code.append("import ").append(findPackage("service")).append(".").append(selectedEntity).append("Service;\n");
        code.append("import org.springframework.http.ResponseEntity;\n");
        code.append("import lombok.RequiredArgsConstructor;\n");
        code.append("import org.springframework.web.bind.annotation.*;\n");
        code.append(getRequiredImports(propertiesString));

        //add class
        code.append("@CrossOrigin(\"http://localhost:5173\")\n");
        code.append("@RequiredArgsConstructor\n");
        code.append("@RestController\n");
        code.append("@RequestMapping(\"/").append(selectedEntity.toLowerCase()).append("s\")\n");
        code.append("public class ").append(selectedEntity).append("Controller {\n");
        code.append("\tprivate final ").append(selectedEntity).append("Service ").append(lowerFirst(selectedEntity)).append("Service;\n\n");

        // Create Method
        code.append("\t@PostMapping\n");
        code.append("\tpublic ResponseEntity<").append(selectedEntity).append("> create").append(selectedEntity).append("(");
        code.append(String.join(", ", entityProperties));
        code.append(") {\n");
        code.append("\t\treturn ").append(lowerFirst(selectedEntity)).append("Service.save").append(selectedEntity).append("(");
        code.append(entityProperties.stream().map(prop -> prop.split(" ")[1]).collect(Collectors.joining(", ")));
        code.append(");\n");
        code.append("\t}\n\n");

        // Get By ID Method
        code.append("\t@GetMapping(\"/{id}\")\n");
        code.append("\tpublic ResponseEntity<").append(selectedEntity).append("> get").append(selectedEntity).append("ById(@PathVariable Long id) {\n");
        code.append("\t\treturn ").append(lowerFirst(selectedEntity)).append("Service.get").append(selectedEntity).append("ById(id);\n");
        code.append("\t}\n\n");

        // Update Method
        code.append("\t@PutMapping(\"/{id}\")\n");
        code.append("\tpublic ResponseEntity<").append(selectedEntity).append("> update").append(selectedEntity).append("(@PathVariable Long id, ");
        code.append(String.join(", ", entityProperties));
        code.append(") {\n");
        code.append("\t\treturn ").append(lowerFirst(selectedEntity)).append("Service.update").append(selectedEntity).append("(id, ");
        code.append(entityProperties.stream().map(prop -> prop.split(" ")[1]).collect(Collectors.joining(", ")));
        code.append(");\n");
        code.append("\t}\n\n");

        // Delete Method
        code.append("\t@DeleteMapping(\"/{id}\")\n");
        code.append("\tpublic ResponseEntity<Void> delete").append(selectedEntity).append("(@PathVariable Long id) {\n");
        code.append("\t\treturn ").append(lowerFirst(selectedEntity)).append("Service.delete").append(selectedEntity).append("ById(id);\n");
        code.append("\t}\n");

        code.append("}\n");
        return code;
    }
}

