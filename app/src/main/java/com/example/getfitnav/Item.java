package com.example.getfitnav;

public class Item {

    private String imageUri, recipeLabel;
    private int calories,fatQuantity,carbsQuantity,ProteinQuantity;

    public Item() {
    }

    public Item(String imageUri, String recipeLabel, int calories, int fatQuantity, int carbsQuantity, int ProteinQuantity){
        this.imageUri = imageUri;
        this.recipeLabel = recipeLabel;
        this.calories = calories;
        this.fatQuantity = fatQuantity;
        this.carbsQuantity = carbsQuantity;
        this.ProteinQuantity = ProteinQuantity;

    }

    public String getImageurl() {
        return imageUri;
    }

    public String getTags() {
        return recipeLabel;
    }

    public int getCalories() {
        return calories;
    }
    public int getFatQuantity() {
        return fatQuantity;
    }
    public int getCarbsQuantity() {
        return carbsQuantity;
    }
    public int getProteinQuantity() {
        return ProteinQuantity;
    }


}

