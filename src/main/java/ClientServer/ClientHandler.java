package ClientServer;

import Controller.ControllerClient;
import Controller.ControllerConfirmMail;
import Controller.ControllerLogIn;
import Controller.ControllerRegister;
import Controller.*;
import Tables.Angajat;
import Tables.Produs;
import Tables.Review;
import Tables.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private ControllerLogIn controllerLogIn;
    private ControllerRegister controllerRegister;
    private ControllerClient controllerClient;
    private User user;
    private ControllerConfirmMail confirmMail;
    private ControllerGenerateCredentials controllerGenerateCredentials;
    private List<Angajat> angajatList;
    private ControllerPaySalary controllerPaySalary;
    private Angajat angajat;
    private ControllerProduct controllerProduct;
    private Produs produs;
    private List<Produs> produsList;
    private ControllerAngajat controllerAngajat;
    private List<Review> reviewList;


    // Constructor
    public ClientHandler(Socket socket)
    {
        this.clientSocket = socket;
        this.controllerLogIn=new ControllerLogIn();
        this.controllerRegister=new ControllerRegister();
        this.controllerAngajat= new ControllerAngajat();
        this.controllerClient=new ControllerClient();
        this.confirmMail=new ControllerConfirmMail();
        this.controllerGenerateCredentials = new ControllerGenerateCredentials();
        this.controllerPaySalary = new ControllerPaySalary();
        this.controllerProduct = new ControllerProduct();
    }

    public void run()
    {
        PrintWriter out = null;
        BufferedReader in = null;
        try {

            // get the outputstream of client
            out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            // get the inputstream of client
            in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            String line,handhand= "";
            StringBuilder string= new StringBuilder();
            while ((line = in.readLine()) != null && line.length() > 0) {

                // writing the received message from
                // client
                System.out.printf(" Sent from the client: %s\n", line);
                String[] req=line.split(",");
                if(req[0].equals("login")){
                   handhand=controllerLogIn.handleReq(req);
                }
                else if(req[0].equals("register")){
                    //String r =
                    handhand =controllerRegister.regRequest(req);

                }else if(req[0].equals("confirm")){
                    confirmMail.reqHandler(req);
                    handhand="";
                }

                else if(req[0].equals("showAngajat")) {
                    angajatList = controllerGenerateCredentials.generateDataTable();
                    handhand = controllerGenerateCredentials.handleReq(angajatList);
                    System.out.println("response clienthandler: " + handhand);
                }
                else if(req[0].equals("showAngajatPay")) {
                    angajatList = controllerPaySalary.generateAngajats();
                    for(Angajat a: angajatList){
                        System.out.println("client handler :" + a.getIdAngajat());
                    }
                    handhand = controllerPaySalary.handleReq(angajatList);
                    System.out.println("response clienthandler: " + handhand);
                }
                else if(req[0].equals("PayEmployee")) {
                    angajat = controllerPaySalary.handleReq(req);
                    controllerPaySalary.sendMail(angajat);
                    System.out.println("client handler :" + angajat.getIdAngajat());
                    handhand = angajat.getIdAngajat();
                    System.out.println("response clienthandler: " + handhand);
                }
                else if(req[0].equals("GenerateCredentials")) {
                    user = controllerGenerateCredentials.convertUser(req);
                    handhand = controllerGenerateCredentials.generateCredentials(user, Integer.parseInt(req[2]), req[3], req[4], req[5]);
                    System.out.println("response clienthandler: " + handhand);
                }
                else if(req[0].equals("SendCredentials")) {
                    String[] strings = new String[5];
                    strings[0] = req[1];
                    strings[1] = req[2];
                    String mail = controllerGenerateCredentials.convertMail(req[3]);
                    controllerGenerateCredentials.sendMail(mail, strings);
                    handhand = "";
                }
                else if(req[0].equals("CreateProduct")) {
                    produs = controllerProduct.createProduct(req);
                    String[] strings = new String[5];
                    strings[0] = produs.getIdProdus();
                    strings[1] = req[1];
                    controllerProduct.handleReq(strings);
                    handhand = "";
                }
                else if(req[0].equals("ShowProducts")) {
                    produsList = controllerProduct.generateProdus();
                    for(Produs p: produsList){
                        System.out.println("client handler :" + p.getIdProdus());
                    }
                    handhand = controllerProduct.handleReq(produsList);
                    System.out.println("response clienthandler: " + handhand);
                }
                else if(req[0].equals("ModifyProducts")) {
                    produs = controllerProduct.modifyProduct(req);
                    handhand = "";
                }
                else if(req[0].equals("DeleteProducts")) {
                    controllerProduct.deleteProduct(req[1]);
                    handhand = "";
                }
                else if(req[0].equals("RestocProducts")) {
                    controllerProduct.restocProduct(req);
                    controllerProduct.restocWrite(req);
                    handhand = "";
                }
                else if(req[0].equals("client")){
                    handhand=controllerClient.decodeReq(req);
                }
                else if(req[0].equals("email")){
                    //System.out.println(req);
                    controllerAngajat.sendRetur(req);
                    handhand="";
                }else if(req[0].equals("review")){
                    handhand=controllerAngajat.getReviews();
                    System.out.println("response clienthandler:" +handhand);
                }else if(req[0].equals("produsReview")){
                    handhand=controllerAngajat.getSpecProd(req);
                } else if(req[0].equals("updateRating")){
                    controllerAngajat.updateRatingProd(req);
                    handhand = "";
                }else if(req[0].equals("checkStoc")){
                    handhand = controllerAngajat.checkProd(req);;
                }
                else if(req[0].equals("ShowNotificationsAdmin")){
                    produsList = controllerProduct.generateProdusNotification();
                    for(Produs p: produsList){
                        System.out.println("client handler :" + p.getIdProdus());
                    }
                    handhand = controllerProduct.handleReq(produsList);
                    System.out.println("response clienthandler: " + handhand);
                }
                else if(req[0].equals("NotificationHandled")){
                    controllerProduct.handleNotifications(req);
                    handhand = "";
                }else if(req[0].equals("check")){
                    handhand=controllerAngajat.checkComand(req);
                }else if(req[0].equals("dataComanda")){
                    handhand=controllerAngajat.checkDataComanda(req);
                }else if(req[0].equals("angajat")){
                    handhand=controllerAngajat.handleAngajatReq(req);
                }


                out.println(handhand);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public User getUser() {
        return user;
    }
}