package me.receipes.receipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.receipes.receipesapp.model.Recipe;
import me.receipes.receipesapp.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "СRUD-операции над рецептами")
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping()
    @Operation(summary = "Добавление нового рецепта",
            description = "Принимает тело запроса в JSON и добавляет рецепт в карту на хранение")
    @ApiResponse(responseCode = "200", description = "Рецепт создан и добавлен в карту")
    public ResponseEntity<Long> addRecipe(@RequestBody Recipe recipe){
        long id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск рецепта по id",
            description = "Принимает id рецепта и ищет его в карте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Рецепт найден",
                    content =
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class))
            ),
            @ApiResponse(responseCode = "404", description = "Рецепт не найден")
    })
    public ResponseEntity<Recipe> showRecipe(@PathVariable Long id){
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
    @GetMapping
    @Operation(summary = "Получение всех рецептов", description = "Возвращает карту, с хранящимися в ней рецептами")
    @ApiResponse(responseCode = "200",
            description = "Карта рецептов получена",
            content = {
                    @Content(mediaType = "application/json" ,
                            array = @ArraySchema(schema = @Schema(implementation = Recipe.class )))
            }

    )
    public ResponseEntity<Map<Long,Recipe>> showAllRecipes() {
        return ResponseEntity.ok(recipeService.showAllRecipes());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование рецепта",
            description = "Принимает id рецепта, ищет его в карте и затирает на новые значения по тому же id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Рецепт найден и изменен",
                    content =
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class))
            ),
            @ApiResponse(responseCode = "404", description = "Рецепт не найден")
    })
    public ResponseEntity changeRecipe(@PathVariable long id, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.changeRecipe(id, newRecipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рецепта по id",
            description = "Принимает id рецепта, ищет его в карте и удаляет из неё")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Рецепт найден и удален",
                    content =
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class))

            ),
            @ApiResponse(responseCode = "404", description = "Рецепт не найден")
    })
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.deleteRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping
    @Operation(summary = "Удаление всех рецептов", description = "Полностью очищает карту с хранивщимися в ней рецептами")
    public ResponseEntity<Void> deleteAllRecipes() {
        recipeService.deleteAllRecipes();
        return ResponseEntity.ok().build();
    }

}
