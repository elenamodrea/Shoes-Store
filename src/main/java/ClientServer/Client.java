package ClientServer;

import java.io.*;
import java.net.*;
import java.util.*;

// ClientServer.Client class
public class Client {

   private final Socket socket;
   private String[] response;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.response=new String[10];
    }


    // driver code
    public void ClientReq(String req) throws IOException {
        // establish a connection by providing host and port
        // number
            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            //while (req!=null) {
                // sending the user input to server
                out.println(req);
                out.flush();

                // displaying server reply

        String line=in.readLine();
                System.out.println("ClientServer.Server replied "
                        + line);
            //}

            // closing the scanner object


        //while ((line = in.readLine()) != null && line.length() > 0){
           if(line!=null)
            response = line.split(",");
        System.out.println(Arrays.toString(response));
       // }

        }
    public String[] getResponse() {
        return response;
    }

    public Socket getSocket() {
        return socket;
    }

}