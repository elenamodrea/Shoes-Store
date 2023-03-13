package Business;
import DAO.UserDAO;
import Tables.User;

public class LoginBLL {
private UserDAO userDAO;
   public LoginBLL(){
       this.userDAO=new UserDAO();
   }
    public LoginBLL(UserDAO userDAO) {
        this.userDAO=userDAO;
    }

    public String LoginBss(String username, String password){
        User user=userDAO.getUserName(username);

        if(user != null) {
            if (password.equals(user.getParola())) {
                if (user.getTipUser().equals("client")) {
                    return "client";
                } else if (user.getTipUser().equals("angajat")) {
                    return "angajat";
                } else {
                    return "admin";
                }
            } else {
                return "parola gresita";
            }
        }
        else return "Userul nu a fost gasit";
    }

    public String interfaceGen(String[] strings){
        return strings[1];
    }
}
