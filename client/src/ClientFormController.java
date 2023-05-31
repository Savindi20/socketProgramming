import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    public TextField txtClient;
    public TextArea txtClientArea;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String message ="";

    public void btnClientSendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtClient.getText().trim());
        dataOutputStream.flush();
        clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(() -> {
            try {
                socket=new Socket("Localhost",8000);
                txtClientArea.appendText("Client Active");

                dataInputStream= new DataInputStream(socket.getInputStream());
                dataOutputStream= new DataOutputStream(socket.getOutputStream());

                while (!message.equals("exit")){
                    message=dataInputStream.readUTF();
                    txtClientArea.appendText("\nServer: "+ message);
                }
            }
            catch (Exception exception){
                System.out.println(exception.getMessage());
            }

        }).start();

    }
    private void clear() {
        txtClient.clear();
    }
}
