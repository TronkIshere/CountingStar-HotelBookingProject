package com.example.CountingStarHotel.codeGenerate.writter;

import com.example.CountingStarHotel.codeGenerate.utils.ProjectPathUtils;
import com.example.CountingStarHotel.codeGenerate.utils.RequiredImports;

public class RequestFileWriter {
    public static StringBuilder writeFile(String fields, String requestNameString, String selectedEntity){
        StringBuilder code = new StringBuilder();
        //add imports
        code.append("package ").append(ProjectPathUtils.findPackage("request")).append(".").append(selectedEntity.toLowerCase()).append(";\n\n");
        code.append("import lombok.Getter;\n");
        code.append("import lombok.AccessLevel;\n");
        code.append("import java.io.Serializable;\n");
        code.append("import lombok.experimental.FieldDefaults;\n");
        code.append(RequiredImports.getRequiredImports(fields));

        //add class
        code.append("@Getter\n").append("@FieldDefaults(level = AccessLevel.PRIVATE)\n");
        code.append("public class ").append(requestNameString).append(" implements Serializable {\n");

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
