package com.example.javafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class carrosController {
    @FXML
    private Button voltar;
    @FXML
    private TextField marca;
    @FXML
    private TextField modelo;
    @FXML
    private TextArea descricao;
    @FXML
    private TextField imagem;
    @FXML
    private Button concluir;
    @FXML
    private Label txtconcluir;

    @FXML
    private void buttonvoltar() {
        try {
            Stage stage = (Stage) voltar.getScene().getWindow();
            SceneSwitcher.switchScene(stage, "login-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buttonconcluir() {
        try {
            String marcaText = marca.getText();
            String modeloText = modelo.getText();
            String descricaoText = descricao.getText();
            String imagemText = imagem.getText();

            if (salvarNoBanco(marcaText, modeloText, descricaoText, imagemText)) {
                txtconcluir.setText("O Produto foi adicionado com sucesso!");
            } else {
                txtconcluir.setText("Erro ao tentar adicionar produto.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            txtconcluir.setText("Erro ao tentar adicionar produto.");
        }
    }

    private boolean salvarNoBanco(String marca, String modelo, String descricao, String imagem) {
        String url = "jdbc:mysql://localhost:3308/aplicacao";
        String user = "root";
        String password = "";

        String query = "INSERT INTO carros (marca, modelo, descricao, imagem) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, marca);
            preparedStatement.setString(2, modelo);
            preparedStatement.setString(3, descricao);
            preparedStatement.setString(4, imagem);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
