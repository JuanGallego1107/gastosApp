package org.example;

import java.util.ArrayList;
import java.util.List;

public class GestorGastos {
    // Lista que contiene los gastos agregados
    private List<Gasto> gastos = new ArrayList<>();

    // Variable para el presupuesto
    private double presupuesto;

    // Constructor para inicializar el presupuesto
    public GestorGastos(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    // Método para agregar un gasto, que valida el monto y el presupuesto
    public boolean agregarGasto(String categoria, double monto) {
        if (monto <= 0) {
            return false;  // No agregar si el monto es menor o igual a 0
        }

        double totalConNuevoGasto = calcularTotal() + monto;
        if (totalConNuevoGasto > presupuesto) {
            return false;  // No agregar si el nuevo total supera el presupuesto
        }

        // Si ya existe un gasto con la misma categoría, sumar los montos
        for (Gasto gasto : gastos) {
            if (gasto.getCategoria().equalsIgnoreCase(categoria)) {
                gasto.setMonto(gasto.getMonto() + monto);
                return true;
            }
        }

        // Si no existe, agrega un nuevo gasto
        gastos.add(new Gasto(categoria, monto));
        return true;
    }

    // Método para eliminar un gasto según la categoría
    public void eliminarGasto(String categoria) {
        gastos.removeIf(gasto -> gasto.getCategoria().equalsIgnoreCase(categoria));
    }

    // Método para calcular el total de los gastos
    public double calcularTotal() {
        return gastos.stream().mapToDouble(Gasto::getMonto).sum();
    }

    // Método para obtener la lista de gastos
    public List<Gasto> getGastos() {
        return gastos;
    }

    // Método para verificar si hay gastos
    public boolean hayGastos() {
        return !gastos.isEmpty();
    }

    // Getter para obtener el presupuesto
    public double getPresupuesto() {
        return presupuesto;
    }

    // Setter para cambiar el presupuesto
    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }
}
