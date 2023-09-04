package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.data.ReceitaDao;
import com.example.model.Receita;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    TextField txtTitulo;
    @FXML
    TextArea txtIngredientes;
    @FXML
    TextArea txtModoPreparo;
    @FXML
    RadioButton rdSalgado;
    @FXML
    RadioButton rdDoce;
    @FXML
    ListView<Receita> lista;

    ArrayList<Receita> receitas = new ArrayList<>();
    private ReceitaDao receitaDao = new ReceitaDao();

    @FXML
    private void adicionar() {
        String titulo = txtTitulo.getText();
        String ingrediente = txtIngredientes.getText();
        String modoPreparo = txtModoPreparo.getText();
        var receita = new Receita(titulo, ingrediente, modoPreparo);
        try {
            receitaDao.inserir(receita);
            mostrarMensagem(AlertType.INFORMATION, "Sucesso", "Receita inserido com sucesso");
        } catch (Exception erro) {
            mostrarMensagem(AlertType.ERROR, "Erro", erro.getLocalizedMessage());
        }
        receitas.add(receita);
        mostrarReceitas();
    }

    public void mostrarReceitas() {
        try {
            receitaDao.listarTodos().forEach(receita -> lista.getItems().add(receita));
        } catch (SQLException e) {
            mostrarMensagem(AlertType.ERROR, "Erro", "Erro ao buscar receita." + e.getMessage());
        }
    }

    public void apagar() {
        var receita = lista.getSelectionModel().getSelectedItem();
        receitas.remove(receita);
        mostrarReceitas();
        mostrarMensagem(AlertType.INFORMATION, "Sucesso", "Receita apagada com sucesso");
    }

    private void mostrarMensagem(AlertType tipo, String titulo, String conteudo) {
        Alert alertaErro = new Alert(tipo);
        alertaErro.setTitle(titulo);
        alertaErro.setContentText(conteudo);
        alertaErro.show();
    }
}
