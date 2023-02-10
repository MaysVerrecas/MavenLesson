package me.receipes.receipesapp.controllers;

import me.receipes.receipesapp.model.Ingredient;
import me.receipes.receipesapp.service.IngredientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping()
    public void addIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
    }

    @GetMapping("/{id}")
    public Ingredient showIngredient(@PathVariable Long id) {
        return ingredientService.getInredient(id);
    }
}
