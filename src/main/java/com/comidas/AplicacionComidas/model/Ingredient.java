package com.comidas.AplicacionComidas.model;

public class Ingredient {
    private String ingredient;
    private String measure;

    // Constructor
    public Ingredient(String ingredient, String measure) {
        this.ingredient = ingredient;
        this.measure = measure;
    }

    // Getters y setters
    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}