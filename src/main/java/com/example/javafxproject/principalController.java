package com.example.javafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.sql.*;


//      ATRIBUTOS
public class principalController {
    @FXML
    private ImageView imagemcarro;
    @FXML
    private ComboBox<String> caixamodelo;
    @FXML
    private ComboBox<String> caixamarca;
    @FXML
    private Label VOLTDRIVEtext;
    @FXML
    private Label marcatext;
    @FXML
    private Label modelotext;
    @FXML
    private Label text;

    //      METODOS
    private String url = "jdbc:mysql://localhost:3308/aplicacao";
    private String user = "root";
    private String psw = "";

    @FXML
    public void initialize() {
        loadComboBoxes();

        caixamodelo.setOnAction(event -> updateCarInfo());
        caixamarca.setOnAction(event -> updateCarInfo());
    }

    private void loadComboBoxes() {
        loadCaixamodelo();
        loadCaixamacra();
    }

    private void loadCaixamacra() {
        String query = "SELECT DISTINCT marca FROM carros ORDER BY marca";

        try (Connection connection = DriverManager.getConnection(url, user, psw);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String value = resultSet.getString("marca");
                caixamarca.getItems().add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCaixamodelo() {
        String query = "SELECT DISTINCT modelo FROM carros ORDER BY modelo";

        try (Connection connection = DriverManager.getConnection(url, user, psw);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String value = resultSet.getString("modelo");
                caixamodelo.getItems().add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCarInfo() {
        String selectedMarca = caixamarca.getValue();
        String selectedModelo = caixamodelo.getValue();

        if (selectedMarca != null && selectedModelo != null) {
            String query = String.format("SELECT imagem, modelo, descricao FROM carros WHERE marca='%s' AND modelo='%s'",
                    selectedMarca, selectedModelo);

            try (Connection connection = DriverManager.getConnection(url, user, psw);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    String imagemPath = resultSet.getString("imagem");
                    String descricao = resultSet.getString("descricao");

                    imagemcarro.setImage(new Image("file:" + imagemPath));
                    System.out.println(imagemPath);
                    text.setText("descricao: " + descricao);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}