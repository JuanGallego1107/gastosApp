package org.example;

// Clase para representar un gasto con categoría y monto
public class Gasto {
    private String categoria;
    private double monto;

    // Constructor
    public Gasto(String categoria, double monto) {
        this.categoria = categoria;
        this.monto = monto;
    }

    // Obtener la categoría del gasto
    public String getCategoria() {
        return categoria;
    }

    // Obtener el monto del gasto
    public double getMonto() {
        return monto;
    }

    // Representación del gasto como una cadena para mostrar en la lista
    @Override
    public String toString() {
        return categoria + ": $" + monto;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}