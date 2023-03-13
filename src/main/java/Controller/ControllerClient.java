package Controller;

import Business.ClientBLL;

import java.io.IOException;

public class ControllerClient {
    private ClientBLL clientBLL;

    public ControllerClient() {
        this.clientBLL = new ClientBLL();
    }

    public String decodeReq(String[] req)  {
        if(req[1].equals("profil"))
           return clientBLL.getProfile(req[2]);
        else if(req[1].equals("updateProfil")){
            clientBLL.updateProfileAndCard(req[2],req);
            return "Update succesfull!";
        }
        else if(req[1].equals("cautare"))
            return clientBLL.productsList(req);
        else if(req[1].equals("newsletter"))
            return clientBLL.getEmail(req);
        else if(req[1].equals("listacomenzi"))
            return clientBLL.getComenzi(req);
        else if(req[1].equals("produseComanda"))
            return clientBLL.getProduseComanda(req);
        else if(req[1].equals("retur")){
            try {
                clientBLL.trimiteRetur(req);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "retur";
        }
        else if(req[1].equals("produs"))
           return clientBLL.getInformatiiProdus(req);
        else if(req[1].equals("reviewNou")){
              return clientBLL.adaugaReview(req);

        }
        else if(req[1].equals("listaReview")){
              return clientBLL.getReviews(req);
        }
        else if(req[1].equals("produseDinCos")){
              return clientBLL.getProdusePentruComanda(req);
        }
        else if(req[1].equals("plasareComanda")){
            try {
                clientBLL.trimiteComanda(req);
                return "comanda trimisa";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(req[1].equals("favorit")){
            try {
               return clientBLL.trimiteFavorit(req);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return "nu merge";
    }
}
