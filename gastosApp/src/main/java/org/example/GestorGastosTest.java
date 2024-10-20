package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GestorGastosTest {

    @Test
    public void CP_01_CrearGasto() {
        GestorGastos gestor = new GestorGastos(200);  // Presupuesto de 200
        gestor.agregarGasto("Alimentación", 50);

        assertEquals(1, gestor.getGastos().size());
        assertEquals(50, gestor.calcularTotal());
    }

    @Test
    public void CP_02_CrearGastoSinMonto() {
        GestorGastos gestor = new GestorGastos(200);

        // Intentar agregar un gasto con monto vacío
        assertFalse(gestor.agregarGasto("Alimentación", 0));  // Debe retornar false
    }

    @Test
    public void CP_03_CrearGastoMontoCero() {
        GestorGastos gestor = new GestorGastos(200);

        // Intentar agregar un gasto con monto 0
        assertFalse(gestor.agregarGasto("Alimentación", 0));  // Debe retornar false
        assertEquals(0, gestor.calcularTotal());
        assertEquals(0, gestor.getGastos().size());
    }

    @Test
    public void CP_04_EliminarGasto() {
        GestorGastos gestor = new GestorGastos(200);
        gestor.agregarGasto("Alimentación", 50);
        gestor.eliminarGasto("Alimentación");

        assertEquals(0, gestor.getGastos().size());
    }

    @Test
    public void CP_05_AgregarGastoMismoNombre() {
        GestorGastos gestor = new GestorGastos(200);
        gestor.agregarGasto("Alimentación", 50);
        gestor.agregarGasto("Alimentación", 30);

        assertEquals(1, gestor.getGastos().size());
        assertEquals(80, gestor.calcularTotal());
    }

    @Test
    public void CP_06_VisualizarGraficoSinGastos() {
        GestorGastos gestor = new GestorGastos(200);

        // Validar que si no hay gastos, se maneja el caso correctamente
        assertFalse(gestor.hayGastos());
    }

    @Test
    public void CP_08_AgregarGastoExcediendoPresupuesto() {
        GestorGastos gestor = new GestorGastos(100);  // Presupuesto de 100
        gestor.agregarGasto("Alimentación", 50);
        boolean resultado = gestor.agregarGasto("Ropa", 60);  // Esto debería exceder el presupuesto

        assertFalse(resultado);  // No debería permitir agregar el gasto
        assertEquals(1, gestor.getGastos().size());  // Solo un gasto debería existir
        assertEquals(50, gestor.calcularTotal());  // Total debería seguir siendo 50
    }
}
