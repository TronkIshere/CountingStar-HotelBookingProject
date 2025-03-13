package com.example.CountingStarHotel.codeGenerate.writter;

public class RequiredImports {
    public static String getRequiredImports(String fields) {
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
}
