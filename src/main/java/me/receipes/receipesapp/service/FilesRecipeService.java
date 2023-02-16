package me.receipes.receipesapp.service;

import java.io.File;

public interface FilesRecipeService {
    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    boolean cleanRecipeDataFile();
}
