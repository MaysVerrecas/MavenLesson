package me.receipes.receipesapp.service;

public interface FilesRecipeService {
    boolean saveToFile(String json);

    String readFromFile();
}
