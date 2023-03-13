package Controller;

import Ajutor.CodeGenerator;
import Ajutor.ConfirmEmail;
import Ajutor.WriteReadFile;
import Business.RegisterBLL;
import GUI.ConfirmMail;
import GUI.Login;
import GUI.Register;
import Tables.RandomID;
import Tables.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControllerRegister {

    private RegisterBLL registerBLL;
    public ControllerRegister() {
      registerBLL=new RegisterBLL();

    }

    public String regRequest(String[] strings){
        return registerBLL.HandlerRegister(strings);
    }







}
