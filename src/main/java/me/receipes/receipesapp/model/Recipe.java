package me.receipes.receipesapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.receipes.receipesapp.service.impl.RecipeServiceImpl;

import java.util.List;

@Data
@NoArgsConstructor
public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private List<Step> cookingSteps;

    public Recipe(String name, int cookingTime, List<Ingredient> ingredientList, List<Step> cookingSteps) {
        this.name = name;
        if (cookingTime > 0) {
            this.cookingTime = cookingTime;
        } else {
            this.cookingTime = Math.abs(cookingTime);
        }
        this.ingredients = ingredientList;
        this.cookingSteps = cookingSteps;
    }
    @Data
    @NoArgsConstructor
    public class Step {

        private String name;
        private String action;
    }

}
