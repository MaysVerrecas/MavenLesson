package me.receipes.receipesapp.controllers;

import me.receipes.receipesapp.model.Recipe;
import me.receipes.receipesapp.service.RecipeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping()
    public void addRecipe(@RequestBody Recipe recipe){
        recipeService.addRecipe(recipe);
    }

    @GetMapping("/{id}")
    public Recipe showRecipe(@PathVariable Long id){
        return recipeService.getRecipe(id);
    }
}
