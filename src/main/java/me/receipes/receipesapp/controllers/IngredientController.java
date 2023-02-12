package me.receipes.receipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.receipes.receipesapp.model.Ingredient;
import me.receipes.receipesapp.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "СRUD-операции над ингредиентами")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping()
    @Operation(summary = "Добавление нового ингредиента",
            description = "Принимает тело запроса в JSON и добавляет ингредиент в карту на хранение")
    @ApiResponse(responseCode = "200", description = "Ингридиент создан и добавлен в карту")
    public ResponseEntity<Long> addIngredient(@RequestBody Ingredient ingredient) {
        long id = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск ингридиента по id",
            description = "Принимает id игредиента и ищет его в карте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ингридиет найден",
                    content = {
                    @Content(mediaType = "aplication/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Ингредиент не найден")
    })
    public ResponseEntity<Ingredient> showIngredient(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getInredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование ингредиента",
            description = "Принимает id ингредиента, ищет его в карте и затирает на новые значения по тому же id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ингридиет найден и изменен",
                    content = {
                            @Content(mediaType = "aplication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Ингредиент не найден")
    })
    public ResponseEntity<Ingredient> changeInredient(@PathVariable Long id, @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.changeIngredient(id, newIngredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиента по id",
            description = "Принимает id ингредиента, ищет его в карте и удаляет из неё")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ингридиет найден и удален",
                    content =
                            @Content(mediaType = "aplication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))

            ),
            @ApiResponse(responseCode = "404", description = "Ингредиент не найден")
    })
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.deleteIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }
    @DeleteMapping
    @Operation(summary = "Удаление всех игредиентов", description = "Полностью очищает карту с хранящимися в ней игредиентами")
    public ResponseEntity<Void> deleteAllIngredients() {
        ingredientService.deleteAllIngredients();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Получение всех ингредиентов", description = "Возвращает карту, с хранящимися в ней игредиентами")
    @ApiResponse(responseCode = "200",
            description = "Карта ингредиентов получена",
            content = {
            @Content(mediaType = "aplication/json" ,schema = @Schema(implementation = Ingredient.class))
            }

    )
    public ResponseEntity<Map<Long,Ingredient>> showAllIngredients() {
        return ResponseEntity.ok().body(ingredientService.showAllIngredient());
    }
}
