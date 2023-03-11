package me.receipes.receipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.receipes.receipesapp.service.FilesIngredientService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/ingredients-file")
public class FilesIngredientController {
    private final FilesIngredientService filesIngredientService;

    public FilesIngredientController(FilesIngredientService filesIngredientService) {
        this.filesIngredientService = filesIngredientService;
    }

    @GetMapping("/download")
    @Operation(
            summary = "Скачать файл с ингредиентами",
            description = "позволяет сохранить файл на компьютер"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл сохранен"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Нет файла на сервере"
            )})
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File file = filesIngredientService.getDataFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"IngredientsLog.json\"")
                    .body(resource);
        }else {
            return ResponseEntity.noContent().build();
        }
    }
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузить файл с ингредиентами",
            description = "позволяет загрузить файл на сервер"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл загружен"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка со стороны сервера"
            )
    })
    public ResponseEntity<Void> uploadRecipeFile(@RequestParam MultipartFile multipartFile){
        filesIngredientService.cleanRecipeDataFile();
        File dataFile = filesIngredientService.getDataFile();

        return getVoidResponseEntity(multipartFile, dataFile);
    }


    private ResponseEntity<Void> getVoidResponseEntity(MultipartFile multipartFile, File dataFile) {
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(multipartFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
