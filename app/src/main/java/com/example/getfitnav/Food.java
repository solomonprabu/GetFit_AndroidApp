package com.example.getfitnav;

public class Food {


    public String Carbhohydrates;
    public String Fat;
    public String Protein;
    public String fName;
    public String time;


    String carbs, fatVal, proteinVal, nameVal, timestampVal;

    public Food(){}


    public Food(String carbs, String fatVal, String proteinVal, String nameVal, String timestampVal) {
        this.carbs = carbs;
        this.fatVal = fatVal;
        this.proteinVal = proteinVal;
        this.nameVal = nameVal;
        this.timestampVal = timestampVal;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getFatVal() {
        return fatVal;
    }

    public void setFatVal(String fatVal) {
        this.fatVal = fatVal;
    }

    public String getProteinVal() {
        return proteinVal;
    }

    public void setProteinVal(String proteinVal) {
        this.proteinVal = proteinVal;
    }

    public String getNameVal() {
        return nameVal;
    }

    public void setNameVal(String nameVal) {
        this.nameVal = nameVal;
    }

    public String getTimestampVal() {
        return timestampVal;
    }

    public void setTimestampVal(String timestampVal) {
        this.timestampVal = timestampVal;
    }
}

