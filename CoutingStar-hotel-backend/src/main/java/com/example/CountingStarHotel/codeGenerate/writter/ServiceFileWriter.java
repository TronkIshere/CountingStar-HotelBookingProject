package com.example.CountingStarHotel.codeGenerate.writter;

import com.example.CountingStarHotel.codeGenerate.utils.ProjectPathUtils;
import com.example.CountingStarHotel.codeGenerate.utils.StringUtils;

import java.util.List;

public class ServiceFileWriter {
    public static StringBuilder writeFile(String selectedEntity, List<String> entityProperties) {
        String propertiesString = String.join(", ", entityProperties);

        StringBuilder code = new StringBuilder();
        //add import
        code.append("package ").append(ProjectPathUtils.findPackage("service")).append(".impl;\n\n");
        code.append("import ").append(ProjectPathUtils.findPackage("entity")).append(".").append(selectedEntity).append(";\n");
        code.append("import ").append(ProjectPathUtils.findPackage("repository")).append(".").append(selectedEntity).append("Repository;\n");
        code.append("import ").append(ProjectPathUtils.findPackage("service")).append(".").append(selectedEntity).append("Service;\n");
        code.append("import lombok.AccessLevel;").append("\n");
        code.append("import lombok.RequiredArgsConstructor;").append("\n");
        code.append("import lombok.experimental.FieldDefaults;").append("\n");
        code.append("import org.springframework.http.ResponseEntity;\n");
        code.append("import org.springframework.stereotype.Service;").append("\n");
        code.append(RequiredImports.getRequiredImports(propertiesString));

        //add class
        code.append("@Service\n")
                .append("@RequiredArgsConstructor\n")
                .append("@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)\n")
                .append("public class ").append(selectedEntity).append("ServiceImpl implements ").append(selectedEntity).append("Service {\n\n");

        code.append("\t").append(selectedEntity).append("Repository ").append(StringUtils.lowerFirst(selectedEntity)).append("Repository;\n\n");

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
            code.append("\t\tentity.set").append(StringUtils.upperFirst(fieldName)).append("(").append(fieldName).append(");\n");
        }

        code.append("\t\t").append(selectedEntity).append(" savedEntity = ").append(StringUtils.lowerFirst(selectedEntity))
                .append("Repository.save(entity);\n")
                .append("\t\treturn ResponseEntity.ok(savedEntity);\n")
                .append("\t}\n\n");

        code.append("\t@Override\n")
                .append("\tpublic ResponseEntity<").append(selectedEntity).append("> get").append(selectedEntity)
                .append("ById(Long id) {\n")
                .append("\t\treturn ").append(StringUtils.lowerFirst(selectedEntity))
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
                .append("\t\treturn ").append(StringUtils.lowerFirst(selectedEntity)).append("Repository.findById(id)\n")
                .append("\t\t\t.map(existingEntity -> {\n");

        for (String property : entityProperties) {
            String fieldName = property.split(" ")[1];
            code.append("\t\t\t\texistingEntity.set").append(StringUtils.upperFirst(fieldName)).append("(").append(fieldName).append(");\n");
        }

        code.append("\t\t\t\t").append(selectedEntity).append(" updatedEntity = ")
                .append(StringUtils.lowerFirst(selectedEntity)).append("Repository.save(existingEntity);\n")
                .append("\t\t\t\treturn ResponseEntity.ok(updatedEntity);\n")
                .append("\t\t\t})\n")
                .append("\t\t\t.orElse(ResponseEntity.notFound().build());\n")
                .append("\t}\n\n");

        code.append("\t@Override\n")
                .append("\tpublic ResponseEntity<Void> delete").append(selectedEntity).append("ById(Long id) {\n")
                .append("\t\tif (").append(StringUtils.lowerFirst(selectedEntity)).append("Repository.existsById(id)) {\n")
                .append("\t\t\t").append(StringUtils.lowerFirst(selectedEntity)).append("Repository.deleteById(id);\n")
                .append("\t\t\treturn ResponseEntity.noContent().build();\n")
                .append("\t\t} else {\n")
                .append("\t\t\treturn ResponseEntity.notFound().build();\n")
                .append("\t\t}\n")
                .append("\t}\n");

        code.append("}\n");
        return code;
    }
}
