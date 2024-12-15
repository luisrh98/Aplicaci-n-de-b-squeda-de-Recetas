package com.comidas.AplicacionComidas.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.comidas.AplicacionComidas.model.Meal;
import com.comidas.AplicacionComidas.model.MealResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonFetcherService {

	private final String API_URL = "https://www.themealdb.com/api/json/v1/1/";
	private final RestTemplate restTemplate;

	public JsonFetcherService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String fetchRandomMeal() {
		String endpoint = "random.php";
		String jsonResponse = restTemplate.getForObject(API_URL + endpoint, String.class);
		System.out.println("JSON recibido en el servicio: " + jsonResponse); // Aquí también deberías ver el JSON
		return jsonResponse;
	}

	public String fetchMainIngredientMeal(String comida) {
		String endpoint = "filter.php?i=" + comida;
		String jsonResponse = restTemplate.getForObject(API_URL + endpoint, String.class);
		System.out.println("JSON recibido en el servicio: " + jsonResponse); // Aquí también deberías ver el JSON
		return jsonResponse;
	}

	public Meal fetchMealByName(String nombre) {
		String endpoint = "search.php?s=" + nombre;
		String jsonResponse = restTemplate.getForObject(API_URL + endpoint, String.class);

		ObjectMapper objectMapper = new ObjectMapper();

		System.out.println("JSON recibido en el servicio: " + jsonResponse); // Aquí también deberías ver el JSON

		// Parsear el JSON a un objeto MealResponse
		try {
			MealResponse mealResponse = objectMapper.readValue(jsonResponse, MealResponse.class);
			if (mealResponse != null && !mealResponse.getMeals().isEmpty()) {
				return mealResponse.getMeals().get(0); // Devuelve la primera receta encontrada
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // En caso de error o no encontrar resultados
	}

	public String fetchMealByFirstLetter(String letter) {
		String endpoint = "search.php?f=" + letter;
		String jsonResponse = restTemplate.getForObject(API_URL + endpoint, String.class);
		System.out.println("JSON recibido en el servicio: " + jsonResponse); // Aquí también deberías ver el JSON
		return jsonResponse;
	}

}