package com.example.CountingStarHotel.codeGenerate.writter;

import com.example.CountingStarHotel.codeGenerate.utils.ProjectPathUtils;

public class RepoFileWriter {
    public static StringBuilder writeFile(String selectedEntity) {
        StringBuilder code = new StringBuilder();
        //add imports
        code.append("package ").append(ProjectPathUtils.findPackage("repository")).append(";\n\n");
        code.append("import ").append(ProjectPathUtils.findPackage("entity")).append(".").append(selectedEntity + ";\n");
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
}
