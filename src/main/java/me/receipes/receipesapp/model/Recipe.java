package me.receipes.receipesapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private List<String> cookingSteps;

}
