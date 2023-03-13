package Business;

import Ajutor.WriteReadFile;
import Controller.ControllerConfirmMail;
import DAO.ClientDAO;
import DAO.UserDAO;
import GUI.ConfirmMail;
import Tables.RandomID;
import Tables.User;

import javax.swing.*;

public class ConfirmMailBLL {

    private WriteReadFile writeReadFile;
    private UserDAO userDAO;
    private RandomID randomID;

    public ConfirmMailBLL() {
        this.writeReadFile=new WriteReadFile();
        this.randomID=new RandomID();
        this.userDAO=new UserDAO();
    }

    public void confirmMail(String[] code) {
        User user=new User(code[2],code[3],code[4],code[5],code[6],code[7],code[8],code[9],code[10]);
        System.out.println(user.getMail()+" "+code[1]);
        if (verify(code[1], writeReadFile.readFile(user.getMail(), "credentiale.txt"))) {
            System.out.println("a intrat in if");
            System.out.println("Verified");
            userDAO.createUser(user);
            Tables.Client client = new Tables.Client(randomID.getRandomID(), user);
            ClientDAO clientDAO = new ClientDAO();
            clientDAO.createClient(client);
            System.out.println("Created account");
        }
    }

    public Boolean verify(String code, String cod) {
        if (code.equals(cod))
            return true;
        return false;

    }
}
