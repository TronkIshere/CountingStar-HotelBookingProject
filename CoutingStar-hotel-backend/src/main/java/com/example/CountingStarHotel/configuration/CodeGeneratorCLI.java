package com.example.CountingStarHotel.configuration;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.FileWriter;
import java.io.IOException;

@ShellComponent
public class CodeGeneratorCLI {

    @ShellMethod(key = "addEntity", value = "Generate a new Entity class")
    public String generateEntity(String name, String fields) throws IOException {
        String className = capitalize(name);
        String directoryPath = "./src/main/java/com/example/CountingStarHotel/entities/";
        String filePath = directoryPath + className + ".java";

        StringBuilder code = new StringBuilder();
        code.append(addImport(fields));
        code.append(addClassExtendAbstractClass(className));
        code.append(addProperties(fields));

        FileWriter writer = new FileWriter(filePath);
        writer.write(code.toString());
        writer.close();
        return "Entity created at " + filePath;
    }

    public StringBuilder addImport(String fields) {
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

    private StringBuilder addClassExtendAbstractClass(String className) {
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

    public StringBuilder addProperties(String fields) {
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
}

