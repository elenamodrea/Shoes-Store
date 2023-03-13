package Controller;

import Ajutor.WriteReadFile;
import Business.ConfirmMailBLL;
import DAO.ClientDAO;
import DAO.UserDAO;
import GUI.Client;
import GUI.ConfirmMail;
import Tables.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerConfirmMail {

    private ConfirmMailBLL confirmMailBLL;

    public ControllerConfirmMail() {
       confirmMailBLL=new ConfirmMailBLL();
    }

    public void reqHandler(String[] code){
        confirmMailBLL.confirmMail(code);
    }


}
