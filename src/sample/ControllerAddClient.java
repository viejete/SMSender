package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerAddClient {

    private DAO dao;

    // Add Client layout
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellido;
    @FXML
    private TextField textDNI;
    @FXML
    private TextField textTelefono;
    @FXML
    private TextField textDireccion;
    @FXML
    private TextField textEmail;
    @FXML
    private RadioButton femaleButton;
    @FXML
    private RadioButton maleButton;

    public void initialize() {
        dao = new DAO();

        ToggleGroup group = new ToggleGroup();

        femaleButton.setToggleGroup(group);
        maleButton.setToggleGroup(group);
    }

    public void clickAddClient(ActionEvent actionEvent) {

        Client client = new Client();

        client.setNombre(textNombre.getText());
        client.setApellido(textApellido.getText());
        client.setDni(textDNI.getText());
        client.setTelefono(textTelefono.getText());
        client.setDireccion(textDireccion.getText());
        client.setEmail(textEmail.getText());
        if (femaleButton.isSelected()) {
            client.setSexo('F');
        } else if (maleButton.isSelected()) {
            client.setSexo('M');
        }

        boolean isSaved = dao.insertClient(client);

        if (isSaved) {
            Alert success = new Alert(Alert.AlertType.INFORMATION , "¡Cliente guardado correctamente!" , ButtonType.OK);
            success.show();
        } else {
            Alert success = new Alert(Alert.AlertType.ERROR , "¡No se ha podido guardar el cliente en la base de datos!" , ButtonType.OK);
            success.show();
        }
    }

    public void returnClicked(MouseEvent mouseEvent) {

        try {

            FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("searcher.fxml"));
            Parent root = fmxlLoader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) textNombre.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
