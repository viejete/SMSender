package sample;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class ControllerMain {

    private String accountSid;
    private String authToken;

    private String client;
    private DAO dao;

    // Main layout
    @FXML
    private TextField numberTextField;
    @FXML
    private TextArea textAreaSend;
    @FXML
    private ListView lvHistorial;
    @FXML
    private ListView lvPredefinidos;

    public void initialize() {
        dao = new DAO();
        setPredefinidos();

        decryptKeys();
    }

    private void setPredefinidos() {
        ArrayList<String> predefinidos = dao.getPredefinidos();

        for (String s : predefinidos) {
            lvPredefinidos.getItems().add(s);
        }
    }


    public void sendClicked(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Enviar SMS?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            String number = numberTextField.getText();
            String body = textAreaSend.getText();

            Twilio.init(accountSid, authToken);

            Message message = Message.creator(new PhoneNumber(number), new PhoneNumber("+34988057143"), body).create();

            Client cliente = null;
            try {
                ArrayList<Client> clients = dao.getClienteByNum(number);
                cliente = clients.get(0);
            } catch (Exception e) {
                System.err.println("No hay ningun cliente con ese numero!");
            }

            SMS sms = new SMS();

            sms.setClient(cliente);
            sms.setTexto(body);

            boolean isSaved = false;

            if (cliente != null) {
                isSaved = dao.insertMessage(sms);
            }

            if (isSaved) {
                Alert success = new Alert(Alert.AlertType.INFORMATION, "¡Mensaje enviado y guardado!", ButtonType.OK);
                success.show();
            } else {
                Alert success = new Alert(Alert.AlertType.ERROR, "¡Mensaje enviado correctamente, pero no guardado en el servidor!", ButtonType.OK);
                success.show();
            }
        }
    }

    public void setClient(String client) {
        this.client = client;
        String[] clientInfo = client.split(", ");
        numberTextField.setText(clientInfo[2]);
    }

    public void historialClicked(Event event) {

        try {
            lvHistorial.getItems().clear();

            ArrayList<SMS> sms = dao.getHistorialByNum(client.split(", ")[2]);

            for (int i = 0; i < sms.size(); i++) {
                lvHistorial.getItems().add(sms.get(i).getTexto() + " - " + sms.get(i).getFechaEnvio());
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void predefinidosClick(MouseEvent mouseEvent) {

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {

                String sms = (String) lvPredefinidos.getSelectionModel().getSelectedItem();

                if (sms != null && !sms.isEmpty()) {
                    textAreaSend.setText(sms);
                }
            }
        }
    }

    public void returnClicked(MouseEvent mouseEvent) {

        try {

            FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("searcher.fxml"));
            Parent root = fmxlLoader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) textAreaSend.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void decryptKeys() {
        try {

            File file = new File("./src/encrypted/encryptedFile.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String text = bufferedReader.readLine();

            String key = "Egregia2017admin";
            Key aesKey = new SecretKeySpec(key.getBytes() , "AES");

            Base64.Decoder decoder = Base64.getDecoder();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE , aesKey);

            String decrypted = new String(cipher.doFinal(decoder.decode(text)));

            accountSid = decrypted.split(",")[0];
            authToken = decrypted.split(",")[1];

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
}
