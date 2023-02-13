package me.receipes.receipesapp.service;

public interface FilesIngredientService {

    boolean saveToFile(String json);

    String readFromFile();
}
