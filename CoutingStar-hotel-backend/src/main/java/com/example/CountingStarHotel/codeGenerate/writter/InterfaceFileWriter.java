package com.example.CountingStarHotel.codeGenerate.writter;

import com.example.CountingStarHotel.codeGenerate.utils.ProjectPathUtils;
import com.example.CountingStarHotel.codeGenerate.utils.RequiredImports;

import java.util.List;

public class InterfaceFileWriter {
    public static StringBuilder writeFile(String selectedEntity, List<String> entityProperties) {
        String propertiesString = String.join(", ", entityProperties);

        StringBuilder code = new StringBuilder();
        //add imports
        code.append("package ").append(ProjectPathUtils.findPackage("service")).append(";\n\n");
        code.append("import ").append(ProjectPathUtils.findPackage("entity")).append(".").append(selectedEntity + ";\n");
        code.append("import org.springframework.http.ResponseEntity;\n");
        code.append(RequiredImports.getRequiredImports(propertiesString));

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
}
