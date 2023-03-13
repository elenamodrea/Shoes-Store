package Business;

import Ajutor.CodeGenerator;
import Ajutor.ConfirmEmail;
import Ajutor.WriteReadFile;
import Controller.ControllerConfirmMail;
import DAO.UserDAO;
import GUI.ConfirmMail;
import Tables.RandomID;
import Tables.User;

import java.io.IOException;
import java.util.List;

public class RegisterBLL {
    private RandomID randomID;
    private CodeGenerator codeGenerator;
    private ConfirmEmail confirmEmail;
    private UserDAO userDAO;

    public RegisterBLL() {
        this.randomID=new RandomID();
        this.codeGenerator=new CodeGenerator();
        this.confirmEmail=new ConfirmEmail();
        this.userDAO = new UserDAO();
    }
    public RegisterBLL(UserDAO userDAO) {
        this.randomID=new RandomID();
        this.codeGenerator=new CodeGenerator();
        this.confirmEmail=new ConfirmEmail();
        this.userDAO = userDAO;
    }

    public String HandlerRegister(String[] strings){
        List<User> users = userDAO.findAllUser();
        User user = new User(randomID.getRandomID(), strings[1], strings[2], strings[3],strings[4],strings[5],"client",strings[6],strings[7]);
        WriteReadFile writeReadFile;
        String cod = codeGenerator.generateCodes();
        confirmEmail.sendMail(user.getMail(), user.getMail(), cod);
        System.out.println(user.getMail());
        writeReadFile = new WriteReadFile();
        StringBuilder stringBuilder1 = new StringBuilder(user.getMail() + " " + cod);

        for(User u: users){
            if(u.getMail().equals(user.getMail())){
                return "Mailul a fost deja folosit.";
            }
        }
        for(User u: users){
            if(u.getUsername().equals(user.getUsername())){
                return "Username-ul a fost deja folosit.";
            }
        }
        try {
            writeReadFile.writeFile(String.valueOf(stringBuilder1), "credentiale.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return user.getIdUser();
    }
}
