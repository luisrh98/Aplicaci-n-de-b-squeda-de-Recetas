package com.comidas.AplicacionComidas.model;

import java.util.List;

public class MealResponse {
    private List<Meal> meals;

    // Getter y setter
    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}