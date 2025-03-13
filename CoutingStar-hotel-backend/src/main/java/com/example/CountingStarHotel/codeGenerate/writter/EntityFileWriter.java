package com.example.CountingStarHotel.codeGenerate.writter;

import com.example.CountingStarHotel.codeGenerate.utils.ProjectPathUtils;
import com.example.CountingStarHotel.codeGenerate.utils.RequiredImports;

public class EntityFileWriter {
    public static StringBuilder writeFile(String fields, String className) {
        StringBuilder code = new StringBuilder();
        code.append("package ").append(ProjectPathUtils.findPackage("entity")).append(";\n\n");
        code.append("import ").append(ProjectPathUtils.findPackage("entity")).append(".common.AbstractEntity;\n");
        code.append("import jakarta.persistence.*;\n");
        code.append("import lombok.*;\n");
        code.append("import lombok.experimental.FieldDefaults;\n\n");
        code.append(RequiredImports.getRequiredImports(fields));

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
}
