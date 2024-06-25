package com.example.javafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//     ATRIBUTOS
public class HelloController {
    @FXML
    private Label texto;
    @FXML
    private TextField nome;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private CheckBox termosuso;
    @FXML
    private Button confirmcadastro;
    @FXML
    private Hyperlink esquecisenha;

//        Set = setar - Get = receber

    //      METODOS
    @FXML
    private void bottoncadastrar() {

        if (termosuso.isSelected()) {
            String nomecompleto = nome.getText();
            String emaill = email.getText();
            String pass = password.getText();
            String confpass = confirmpassword.getText();

            if (confirmPassword(pass, confpass)) {
                salvarnobanco(nomecompleto, emaill, pass);

                texto.setText("Cadastro realizado com sucesso!");
            } else {
                texto.setText("As senhas não coincidem");
            }
        } else {
            texto.setText("Termos de uso Obrigatorio!");
        }
    }

        private boolean confirmPassword (String pass, String confpass){
            if (pass.equals(confpass)) {
                return true;
            } else {
                return false;
            }
        }

        @FXML
        private void telaLogin() throws IOException {
            Stage stage = (Stage) email.getScene().getWindow();
            SceneSwitcher.switchScene(stage, "login-view.fxml");
        }

        private void salvarnobanco(String nomecompleto, String emaill, String pass) {
            String url = "jdbc:mysql://localhost:3308/aplicacao";
            String user = "root";
            String pwd = "";

            String query = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(url, user, pwd);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, nomecompleto);
                preparedStatement.setString(2, emaill);
                preparedStatement.setString(3, pass);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Usuário salvo com sucesso!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }