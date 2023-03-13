package Test;
import ClientServer.*;
import Controller.ControllerLogIn;
import GUI.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket= new Socket("localhost", 1234);
        new Login(socket);

    }
}