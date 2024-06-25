package com.example.javafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class loginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField senha;
    @FXML
    private Button concluir;
    @FXML
    private Hyperlink cadastrar;
    @FXML
    private Label texto;
    @FXML
    private CheckBox loginadm;

    @FXML
    private void buttonconcluir() throws IOException {
        String emaill = email.getText();
        String pass = senha.getText();
        boolean isAdminCheckboxChecked = loginadm.isSelected();

        if (isAdminCheckboxChecked) {
            if (validarLogin(emaill, pass, true)) {
                telacadastro();
            } else {
                texto.setText("Apenas os administradores podem acessar!");
            }
        } else {
            if (validarLogin(emaill, pass, false)) {
                telaprincipal();
            } else {
                texto.setText("O Email e/ou Senha estão incorretos!");
            }
        }
    }

    @FXML
    private void telacadastro() throws IOException {
        Stage stage = (Stage) email.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "carros-view.fxml");
    }

    @FXML
    private void telaprincipal() throws IOException {
        Stage stage = (Stage) email.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "principal-view.fxml");
    }

    @FXML
    private void buttonCadastrar() throws IOException {
        Stage stage = (Stage) cadastrar.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "hello-view.fxml");
    }

    private boolean validarLogin(String emaill, String pass, boolean isAdmin) {
        String url = "jdbc:mysql://localhost:3308/aplicacao";
        String user = "root";
        String pwd = "";

        String query = "SELECT * FROM usuarios WHERE email = ? AND senha = ? AND administrador = ?";

        try (Connection connection = DriverManager.getConnection(url, user, pwd);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, emaill);
            preparedStatement.setString(2, pass);
            preparedStatement.setBoolean(3, isAdmin);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
