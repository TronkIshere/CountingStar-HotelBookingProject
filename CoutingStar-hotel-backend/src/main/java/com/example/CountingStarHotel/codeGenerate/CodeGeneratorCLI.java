package com.example.CountingStarHotel.codeGenerate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@ShellComponent
@Slf4j
public class CodeGeneratorCLI {
    @ShellMethod(key = "addEntity", value = "Generate a new Entity class")
    public String generateEntity(String name, String fields) throws IOException {
        String className = capitalize(name);
        String directoryPath = "./src/main/java/com/example/CountingStarHotel/entities/";
        String filePath = directoryPath + className + ".java";

        StringBuilder code = new StringBuilder();
        code.append(addImportForEntity(fields));
        code.append(addClassExtendAbstractClassForEntity(className));
        code.append(addPropertiesForEntity(fields));

        FileWriter writer = new FileWriter(filePath);
        writer.write(code.toString());
        writer.close();
        return "Entity created at " + filePath;
    }

    public StringBuilder addImportForEntity(String fields) {
        StringBuilder code = new StringBuilder();
        code.append("package com.example.CountingStarHotel.entities;\n\n");
        code.append("import com.example.CountingStarHotel.entities.common.AbstractEntity;\n");
        code.append("import jakarta.persistence.*;\n");
        code.append("import lombok.*;\n");
        code.append("import lombok.experimental.FieldDefaults;\n\n");
        code.append(getRequiredImports(fields));
        return code;
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

    private StringBuilder addClassExtendAbstractClassForEntity(String className) {
        StringBuilder code = new StringBuilder();

        //add annotation
        code.append("@Entity\n");
        code.append("@Getter\n");
        code.append("@Setter\n");
        code.append("@AllArgsConstructor\n");
        code.append("@NoArgsConstructor\n");
        code.append("@FieldDefaults(level = AccessLevel.PRIVATE)\n");

        //add class
        code.append("public class ").append(className).append(" extends AbstractEntity<Long> {\n");
        return code;
    }

    public StringBuilder addPropertiesForEntity(String fields) {
        StringBuilder code = new StringBuilder();
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
        String entitiesDirectoryPath = "./src/main/java/com/example/CountingStarHotel/entities/";
        String repoDirectoryPath = "./src/main/java/com/example/CountingStarHotel/repositories/";
        String interfaceDirectoryPath = "./src/main/java/com/example/CountingStarHotel/services/";
        String serviceDirectoryPath = "./src/main/java/com/example/CountingStarHotel/services/impl/";
        String controllerDirectoryPath = "./src/main/java/com/example/CountingStarHotel/controller/";

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

    private List<String> getEntityProperties(String selectedEntity) throws ClassNotFoundException {
        List<String> properties = new ArrayList<>();

        String basePackage = "com.example.CountingStarHotel.entities";
        String className = basePackage + "." + selectedEntity;

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

    private List<String> getEntityClasses(String path) {
        File folder = new File(path);
        List<String> entityClasses = new ArrayList<>();

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".java"));
            if (files != null) {
                for (File file : files) {
                    String className = file.getName().replace(".java", "");
                    entityClasses.add(className);
                }
            }
        }

        return entityClasses;
    }

    private void createRepositoryFile(String repoDirectoryPath, String selectedEntity) throws IOException {
        String repoName = selectedEntity + "Repository";
        String filePath = repoDirectoryPath + repoName + ".java";
        StringBuilder code = new StringBuilder();

        code.append(addImportForRepo(selectedEntity));
        code.append(addClassExtendJpa(selectedEntity));

        FileWriter writer = new FileWriter(filePath);
        writer.write(code.toString());
        writer.close();

        log.info("Repository created at " + filePath);
    }

    private StringBuilder addImportForRepo(String selectedEntity) {
        StringBuilder code = new StringBuilder();
        code.append("package com.example.CountingStarHotel.repositories;\n\n");
        code.append("import com.example.CountingStarHotel.entities." + selectedEntity + ";\n");
        code.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
        code.append("\n");
        return code;
    }

    private StringBuilder addClassExtendJpa(String selectedEntity) {
        StringBuilder code = new StringBuilder();
        code.append("public interface ")
                .append(selectedEntity)
                .append("Repository extends")
                .append(" JpaRepository<")
                .append(selectedEntity)
                .append(", Long> {\n}");
        return code;
    }

    private void createInterfaceFile(String interfaceDirectoryPath, String selectedEntity, List<String> entityProperties) throws IOException {
        String interfaceName = selectedEntity + "Service";
        String filePath = interfaceDirectoryPath + interfaceName + ".java";
        StringBuilder code = new StringBuilder();

        code.append(addImportForInterface(selectedEntity, entityProperties));
        code.append(addClassAndMethodForInterface(selectedEntity, entityProperties));

        FileWriter writer = new FileWriter(filePath);
        writer.write(code.toString());
        writer.close();

        log.info("Interface created at " + filePath);
    }

    private StringBuilder addImportForInterface(String selectedEntity, List<String> entityProperties) {
        String propertiesString = String.join(", ", entityProperties);

        StringBuilder code = new StringBuilder();
        code.append("package com.example.CountingStarHotel.services;\n\n");
        code.append("import com.example.CountingStarHotel.entities." + selectedEntity + ";\n");
        code.append("import org.springframework.http.ResponseEntity;\n");
        code.append(getRequiredImports(propertiesString));
        return code;
    }

    private StringBuilder addClassAndMethodForInterface(String selectedEntity, List<String> entityProperties) {
        StringBuilder code = new StringBuilder();

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

    private void createServiceFile(String serviceDirectoryPath, String selectedEntity, List<String> entityProperties) throws IOException {
        String serviceImplName = selectedEntity + "ServiceImpl";
        String filePath = serviceDirectoryPath + serviceImplName + ".java";
        StringBuilder code = new StringBuilder();

        code.append(addImportForServiceImpl(selectedEntity, entityProperties));
        code.append(addClassAndMethodForServiceImpl(selectedEntity, entityProperties));

        FileWriter writer = new FileWriter(filePath);
        writer.write(code.toString());
        writer.close();

        log.info("Interface created at " + filePath);
    }

    private StringBuilder addImportForServiceImpl(String selectedEntity, List<String> entityProperties) {
        String propertiesString = String.join(", ", entityProperties);

        StringBuilder code = new StringBuilder();
        code.append("package com.example.CountingStarHotel.services.impl;\n\n");
        code.append("import com.example.CountingStarHotel.entities.").append(selectedEntity).append(";\n");
        code.append("import com.example.CountingStarHotel.repositories.").append(selectedEntity).append("Repository;\n");
        code.append("import com.example.CountingStarHotel.services.").append(selectedEntity).append("Service;\n");
        code.append("import lombok.AccessLevel;").append("\n");
        code.append("import lombok.RequiredArgsConstructor;").append("\n");
        code.append("import lombok.experimental.FieldDefaults;").append("\n");
        code.append("import org.springframework.http.ResponseEntity;\n");
        code.append("import org.springframework.stereotype.Service;").append("\n");
        code.append(getRequiredImports(propertiesString));
        return code;
    }

    private StringBuilder addClassAndMethodForServiceImpl(String selectedEntity, List<String> entityProperties) {
        StringBuilder code = new StringBuilder();

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

    private void createControllerFile(String controllerDirectoryPath, String selectedEntity, List<String> entityProperties) throws IOException {
        String controllerName = selectedEntity + "Controller";
        String filePath = controllerDirectoryPath + controllerName + ".java";
        StringBuilder code = new StringBuilder();

        code.append(addImportForController(selectedEntity, entityProperties));
        code.append(addClassAndMethodForController(selectedEntity, entityProperties));

        FileWriter writer = new FileWriter(filePath);
        writer.write(code.toString());
        writer.close();

        log.info("Interface created at " + filePath);
    }

    private StringBuilder addImportForController(String selectedEntity, List<String> entityProperties) {
        String propertiesString = String.join(", ", entityProperties);

        StringBuilder code = new StringBuilder();
        code.append("package com.example.CountingStarHotel.controller;\n\n");
        code.append("import com.example.CountingStarHotel.entities.").append(selectedEntity).append(";\n");
        code.append("import com.example.CountingStarHotel.services.").append(selectedEntity).append("Service;\n");
        code.append("import org.springframework.http.ResponseEntity;\n");
        code.append("import lombok.RequiredArgsConstructor;\n");
        code.append("import org.springframework.web.bind.annotation.*;\n");
        code.append(getRequiredImports(propertiesString));
        return code;
    }

    private StringBuilder addClassAndMethodForController(String selectedEntity, List<String> entityProperties) {
        StringBuilder code = new StringBuilder();
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

