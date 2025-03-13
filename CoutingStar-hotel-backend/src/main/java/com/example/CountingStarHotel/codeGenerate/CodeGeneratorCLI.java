package com.example.CountingStarHotel.codeGenerate;

import com.example.CountingStarHotel.codeGenerate.generator.*;
import com.example.CountingStarHotel.codeGenerate.utils.EntityUtils;
import com.example.CountingStarHotel.codeGenerate.utils.ProjectPathUtils;
import com.example.CountingStarHotel.codeGenerate.utils.StringUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@ShellComponent
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodeGeneratorCLI {

    @ShellMethod(key = "addEntity", value = "Generate a new Entity class")
    public String generateEntity(String name, String fields) throws IOException {
        String className = StringUtils.capitalize(name);
        Path directoryPath = ProjectPathUtils.getOrCreateDirectory("entity");
        Path entitiesDirectoryPath = directoryPath.resolve(className + ".java");

        EntityCodeGenerator.createFile(entitiesDirectoryPath, fields, className);

        return "Entity created at " + entitiesDirectoryPath;
    }

    @ShellMethod(key = "addService", value = "Generate a new service class")
    public String generateService() throws IOException, ClassNotFoundException {
        Path entitiesDirectoryPath = ProjectPathUtils.getOrCreateDirectory("entity");
        Path repoDirectoryPath = ProjectPathUtils.getOrCreateDirectory("repository");
        Path interfaceDirectoryPath = ProjectPathUtils.getOrCreateDirectory("service");
        Path serviceDirectoryPath = ProjectPathUtils.getOrCreateDirectory("impl");
        Path controllerDirectoryPath = ProjectPathUtils.getOrCreateDirectory("controller");

        List<String> entityClasses = EntityUtils.getEntityClasses(entitiesDirectoryPath);
        if (entityClasses.isEmpty()) return "Not Entity has found!";

        String selectedEntity = EntityUtils.selectEntityTable(entityClasses);
        if (selectedEntity == null) return "Not Entity selected";

        List<String> entityPropertiesList = EntityUtils.getEntityProperties(selectedEntity);;

        RepositoryCodeGenerator.createFile(repoDirectoryPath, selectedEntity);
        InterfaceCodeGenerator.createFile(interfaceDirectoryPath, selectedEntity, entityPropertiesList);
        ServiceCodeGenerator.createFile(serviceDirectoryPath, selectedEntity, entityPropertiesList);
        ControllerCodeGenerator.createFile(controllerDirectoryPath, selectedEntity, entityPropertiesList);

        return "Created controller, service, repository is completed";
    }

}

