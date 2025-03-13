package com.example.CountingStarHotel.codeGenerate.writter;

import com.example.CountingStarHotel.codeGenerate.utils.ProjectPathUtils;
import com.example.CountingStarHotel.codeGenerate.utils.RequiredImports;
import com.example.CountingStarHotel.codeGenerate.utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerFileWriter {
    public static StringBuilder writeFile(String selectedEntity, List<String> entityProperties) {
        String propertiesString = String.join(", ", entityProperties);

        StringBuilder code = new StringBuilder();
        //add imports
        code.append("package ").append(ProjectPathUtils.findPackage("controller")).append(";\n\n");
        code.append("import ").append(ProjectPathUtils.findPackage("entity")).append(".").append(selectedEntity).append(";\n");
        code.append("import ").append(ProjectPathUtils.findPackage("service")).append(".").append(selectedEntity).append("Service;\n");
        code.append("import org.springframework.http.ResponseEntity;\n");
        code.append("import lombok.RequiredArgsConstructor;\n");
        code.append("import org.springframework.web.bind.annotation.*;\n");
        code.append(RequiredImports.getRequiredImports(propertiesString));

        //add class
        code.append("@CrossOrigin(\"http://localhost:5173\")\n");
        code.append("@RequiredArgsConstructor\n");
        code.append("@RestController\n");
        code.append("@RequestMapping(\"/").append(selectedEntity.toLowerCase()).append("s\")\n");
        code.append("public class ").append(selectedEntity).append("Controller {\n");
        code.append("\tprivate final ").append(selectedEntity).append("Service ").append(StringUtils.lowerFirst(selectedEntity)).append("Service;\n\n");

        // Create Method
        code.append("\t@PostMapping\n");
        code.append("\tpublic ResponseEntity<").append(selectedEntity).append("> create").append(selectedEntity).append("(");
        code.append(String.join(", ", entityProperties));
        code.append(") {\n");
        code.append("\t\treturn ").append(StringUtils.lowerFirst(selectedEntity)).append("Service.save").append(selectedEntity).append("(");
        code.append(entityProperties.stream().map(prop -> prop.split(" ")[1]).collect(Collectors.joining(", ")));
        code.append(");\n");
        code.append("\t}\n\n");

        // Get By ID Method
        code.append("\t@GetMapping(\"/{id}\")\n");
        code.append("\tpublic ResponseEntity<").append(selectedEntity).append("> get").append(selectedEntity).append("ById(@PathVariable Long id) {\n");
        code.append("\t\treturn ").append(StringUtils.lowerFirst(selectedEntity)).append("Service.get").append(selectedEntity).append("ById(id);\n");
        code.append("\t}\n\n");

        // Update Method
        code.append("\t@PutMapping(\"/{id}\")\n");
        code.append("\tpublic ResponseEntity<").append(selectedEntity).append("> update").append(selectedEntity).append("(@PathVariable Long id, ");
        code.append(String.join(", ", entityProperties));
        code.append(") {\n");
        code.append("\t\treturn ").append(StringUtils.lowerFirst(selectedEntity)).append("Service.update").append(selectedEntity).append("(id, ");
        code.append(entityProperties.stream().map(prop -> prop.split(" ")[1]).collect(Collectors.joining(", ")));
        code.append(");\n");
        code.append("\t}\n\n");

        // Delete Method
        code.append("\t@DeleteMapping(\"/{id}\")\n");
        code.append("\tpublic ResponseEntity<Void> delete").append(selectedEntity).append("(@PathVariable Long id) {\n");
        code.append("\t\treturn ").append(StringUtils.lowerFirst(selectedEntity)).append("Service.delete").append(selectedEntity).append("ById(id);\n");
        code.append("\t}\n");

        code.append("}\n");
        return code;
    }
}
