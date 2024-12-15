package com.comidas.AplicacionComidas.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.comidas.AplicacionComidas.model.Meal;
import com.comidas.AplicacionComidas.model.MealResponse;
import com.comidas.AplicacionComidas.services.JsonFetcherService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MealController {

	@Autowired
	private JsonFetcherService jsonFetcherService;

	@GetMapping("/")
	public String showIndex() {
		// Aquí se sirve la página de inicio con el menú
		return "index"; // Thymeleaf buscará index.html en /src/main/resources/templates
	}

	@GetMapping("/random-meal")
    public String fetchRandomMeal(Model model) {
        // Obtener el JSON
        String json = jsonFetcherService.fetchRandomMeal();

        // Deserializar el JSON a MealResponse
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MealResponse mealResponse = objectMapper.readValue(json, MealResponse.class);
            if (mealResponse.getMeals() != null && !mealResponse.getMeals().isEmpty()) {
                Meal meal = mealResponse.getMeals().get(0); // Obtener la primera receta
                model.addAttribute("meal", meal); // Agregar la receta al modelo
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retornar la vista
        return "recetaAleatoria"; // Thymeleaf buscará recetaAleatoria.html
    }
    
	@GetMapping("/mainIngredient-meal")
	public String fetchMealsByIngredient(Model model, @RequestParam String comida) {
	    // Obtener el JSON de la API
	    String json = jsonFetcherService.fetchMainIngredientMeal(comida);

	    // Deserializar el JSON a MealResponse
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        MealResponse mealResponse = objectMapper.readValue(json, MealResponse.class);
	        if (mealResponse.getMeals() != null && !mealResponse.getMeals().isEmpty()) {
	            model.addAttribute("mealResponse", mealResponse);  // Pasa mealResponse al modelo
	            model.addAttribute("comida", comida);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return "buscarPorIngrediente";  // Nombre de la vista
	}
	
	@GetMapping("/meal")
	public String getMealDetails(@RequestParam(required = false) String comida,
	                             Model model) {

	    if (comida == null || comida.isBlank()) {
	        model.addAttribute("error", "No se especificó el nombre de la receta.");
	        return "error";
	    }

	    // Busca la receta por nombre
	    Meal meal = jsonFetcherService.fetchMealByName(comida);

	    if (meal == null) {
	        model.addAttribute("error", "No se encontró la receta con el nombre: " + comida);
	        return "error";
	    }

	    model.addAttribute("meal", meal);
	    return "meal-details";
	}
	
	@GetMapping("/mealByLetter")
	public String fetchMealByFirstLetter(Model model, @RequestParam String letter) {
	    // Obtener el JSON de la API
	    String json = jsonFetcherService.fetchMealByFirstLetter(letter);

	    // Deserializar el JSON a MealResponse
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        MealResponse mealResponse = objectMapper.readValue(json, MealResponse.class);
	        if (mealResponse.getMeals() != null && !mealResponse.getMeals().isEmpty()) {
	            model.addAttribute("mealResponse", mealResponse);  // Pasa mealResponse al modelo
	            model.addAttribute("letter", letter);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return "buscarPorIngrediente";  // Nombre de la vista
	}
	
}