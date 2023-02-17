package me.receipes.receipesapp.service;

import java.io.File;
import java.nio.file.Path;

public interface FilesRecipeService {
    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    boolean cleanRecipeDataFile();

    Path returnPath();

    File getRecipeDataFile();
}
