import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerFormController implements Initializable {
    public TextField txtSever;
    public TextArea txtServerArea;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String message ="";

    public void btnSeverSendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtSever.getText().trim());
        dataOutputStream.flush();
        clear();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(() -> {
            try {
            serverSocket= new ServerSocket(8000);
            txtServerArea.appendText("Server Started");
            socket = serverSocket.accept();
            System.out.println("\nclient accepted!");
            dataInputStream= new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while (!message.equals("exit")){
                message=dataInputStream.readUTF();
                txtServerArea.appendText("\nClient: " + message);
            }


        } catch (IOException e){
            e.printStackTrace();
        }


        }).start();
    }
    private void clear() {
        txtSever.clear();
    }
}
