package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private ArrayList<Client> clients;
    DAO dao;

    // Buscador layout
    @FXML
    private Button buttonSearcher;
    @FXML
    private TextField numberTextSearch;
    @FXML
    private ListView lvClients;
    @FXML
    private TextField textApellido;

    public void initialize() {
        dao = new DAO();
    }

    public void imageButtonClicked(MouseEvent mouseEvent) {

        try {

            Stage stage = (Stage) buttonSearcher.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("addClient.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buttonSearcherClicked(ActionEvent actionEvent) {

        try {
            lvClients.getItems().clear();

            clients = dao.getClienteByNum(numberTextSearch.getText());

            for (int i = 0; i < clients.size(); i++) {
                lvClients.getItems().add(clients.get(i).getNombre() + ", " + clients.get(i).getApellido() + ", " + clients.get(i).getTelefono());
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void onListViewClicked(MouseEvent mouseEvent) {

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {

                String clientInfo = (String) lvClients.getSelectionModel().getSelectedItem();

                if (clientInfo != null && !clientInfo.isEmpty()) {
                    try {

                        FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
                        Parent root = fmxlLoader.load();

                        ControllerMain controller = fmxlLoader.getController();
                        controller.setClient(clientInfo);
                        Scene scene = new Scene(root);

                        Stage stage = (Stage) buttonSearcher.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void buttonApellidoClicked(ActionEvent actionEvent) {

        try {
            lvClients.getItems().clear();

            clients = dao.getClienteByName(textApellido.getText());

            for (int i = 0; i < clients.size(); i++) {
                lvClients.getItems().add(clients.get(i).getNombre() + ", " + clients.get(i).getApellido() + ", " + clients.get(i).getTelefono());
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
}
