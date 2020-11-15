package com.example.usercollageapplication.ebook;

public class ModelData {

    private String name,pdfUrI;

    public ModelData() {
    }

    public ModelData(String name, String pdfUrI) {
        this.name = name;
        this.pdfUrI = pdfUrI;
    }

    public String getName() {
        return name;
    }

    public String getPdfUrI() {
        return pdfUrI;
    }
}
