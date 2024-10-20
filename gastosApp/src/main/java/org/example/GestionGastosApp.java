package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.List;

public class GestionGastosApp extends Application {

    private GestorGastos gestorGastos;
    private Label totalGastosLabel;
    private ListView<Gasto> gastosListView;
    private Label presupuestoLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Gastos");

        // Inicializar el gestor de gastos con un presupuesto inicial de 0
        gestorGastos = new GestorGastos(0);

        // Campo para ingresar el presupuesto
        TextField presupuestoField = new TextField();
        presupuestoField.setPromptText("Presupuesto");

        // Botón para establecer el presupuesto
        Button setPresupuestoButton = new Button("Establecer Presupuesto");
        setPresupuestoButton.setOnAction(e -> {
            try {
                double presupuesto = Double.parseDouble(presupuestoField.getText());
                gestorGastos.setPresupuesto(presupuesto);
                presupuestoLabel.setText("Presupuesto: $" + String.format("%.2f", presupuesto));
                presupuestoField.clear();
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "Por favor, ingresa un presupuesto válido.");
            }
        });

        // Campos de texto para ingresar categoría y monto
        TextField categoriaField = new TextField();
        categoriaField.setPromptText("Categoría");

        TextField montoField = new TextField();
        montoField.setPromptText("Monto");

        // Botón para agregar gasto
        Button addButton = new Button("Agregar Gasto");
        addButton.setOnAction(e -> {
            String categoria = categoriaField.getText();
            if (categoria.isEmpty()) {
                mostrarAlerta("Error", "Por favor, ingresa una categoría.");
                return; // Si el campo de categoría está vacío, no continuar
            }

            try {
                double monto = Double.parseDouble(montoField.getText());

                // Validar y agregar el gasto, mostrando alerta si excede el presupuesto
                boolean agregado = gestorGastos.agregarGasto(categoria, monto);
                if (agregado) {
                    categoriaField.clear();
                    montoField.clear();
                    actualizarListaGastos();
                    actualizarTotalGastos();
                } else {
                    mostrarAlerta("Presupuesto excedido", "No se puede agregar el gasto. Excede el presupuesto.");
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "Por favor, ingresa un monto válido.");
            }
        });

        // ListView para mostrar los gastos agregados
        gastosListView = new ListView<>();

        // Botón para eliminar gasto
        Button deleteButton = new Button("Eliminar Gasto");
        deleteButton.setOnAction(e -> {
            Gasto selectedGasto = gastosListView.getSelectionModel().getSelectedItem();
            if (selectedGasto != null) {
                gestorGastos.eliminarGasto(selectedGasto.getCategoria());
                actualizarListaGastos();
                actualizarTotalGastos();
            }
        });

        // Etiqueta para mostrar el total de los gastos
        totalGastosLabel = new Label("Total: $0.00");

        // Etiqueta para mostrar el presupuesto
        presupuestoLabel = new Label("Presupuesto: $0.00");

        // Botón para mostrar gráfico
        Button graficoButton = new Button("Mostrar Gráfico");
        graficoButton.setOnAction(e -> mostrarGraficoGastos());

        // Layout de la interfaz
        VBox layout = new VBox(10, presupuestoField, setPresupuestoButton, presupuestoLabel,
                categoriaField, montoField, addButton, gastosListView, deleteButton, totalGastosLabel, graficoButton);
        Scene scene = new Scene(layout, 400, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para actualizar la lista de gastos
    private void actualizarListaGastos() {
        gastosListView.getItems().clear();
        gastosListView.getItems().addAll(gestorGastos.getGastos());
    }

    // Método para actualizar el total de gastos
    private void actualizarTotalGastos() {
        double total = gestorGastos.calcularTotal();
        totalGastosLabel.setText("Total: $" + String.format("%.2f", total));
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para mostrar gráfico de gastos
    private void mostrarGraficoGastos() {
        if (gestorGastos.getGastos().isEmpty()) {
            mostrarAlerta("Gráfico", "No hay gastos para mostrar en el gráfico.");
            return;
        }

        // Crear un dataset para el gráfico de pastel
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Gasto> gastos = gestorGastos.getGastos();
        for (Gasto gasto : gastos) {
            dataset.setValue(gasto.getCategoria(), gasto.getMonto());
        }

        // Crear el gráfico de pastel
        JFreeChart pieChart = ChartFactory.createPieChart("Distribución de Gastos", dataset, true, true, false);
        ChartPanel chartPanel = new ChartPanel(pieChart); // Panel para mostrar el gráfico

        // Crear una nueva ventana para mostrar el gráfico
        JFrame frame = new JFrame("Gráfico de Gastos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
