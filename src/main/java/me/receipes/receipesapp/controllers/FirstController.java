package me.receipes.receipesapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping("/")
    public String appIsExecutedPage(){
        return "Приложение запущено!";
    }

    @GetMapping("/info")
    public String infoPage(){
        return "Анастасия Тришкина " + "RecipesApp " + "Приложение для сайта рецептов";
    }
}
