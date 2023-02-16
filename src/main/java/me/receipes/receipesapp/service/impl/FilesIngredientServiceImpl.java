package me.receipes.receipesapp.service.impl;

import me.receipes.receipesapp.service.FilesIngredientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesIngredientServiceImpl implements FilesIngredientService {
    @Value("${name.to.data.file}")
    private String nameDataFile;

    @Value("${path.to.data.file}")
    private String pathDataFile;

    @Override
    public boolean saveToFile(String json){
        try {
            cleanDataFile();
            Files.writeString(Path.of(pathDataFile, nameDataFile), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile(){
        try {
            return Files.readString(Path.of(pathDataFile, nameDataFile));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public File getDataFile() {
        return new File(pathDataFile + "/" + nameDataFile);
    }

    @Override
    public boolean cleanRecipeDataFile() {
        try {
            Path path = Path.of(pathDataFile, nameDataFile);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean cleanDataFile(){
        try {
            Path path = Path.of(pathDataFile, nameDataFile);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
