package Controller;

import Business.LoginBLL;
import DAO.UserDAO;
import GUI.*;
import Tables.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerLogIn {
        private  Login login;
        private UserDAO userDAO;
        private LoginBLL loginBLL;

    public ControllerLogIn() {

        userDAO=new UserDAO();
        loginBLL=new LoginBLL(new UserDAO());

    }

    public String handleReq(String[] req){
        String handreq=loginBLL.LoginBss(req[1],req[2]);
        StringBuilder stringBuilder=new StringBuilder("login,");
        if(handreq.equals("client")){
            stringBuilder.append("client");
        }else if(handreq.equals("angajat")){
            stringBuilder.append("angajat");
        }else if(handreq.equals("admin")){
            stringBuilder.append("admin");
        }else {
            stringBuilder.append("parola gresita");
        }
        return stringBuilder.toString();
    }

}
