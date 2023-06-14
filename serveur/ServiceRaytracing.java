package serveur;

import serviceCalcul.InterfaceServiceCalcul;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServiceRaytracing implements InterfaceServiceRaytracing, Serializable {
    ArrayList<InterfaceServiceCalcul>noeudsCalcul = new ArrayList<>();
    int numNoeud = 0;

    @Override
    public void supprimerNoeudCalcul(InterfaceServiceCalcul noeud) throws RemoteException {
        noeudsCalcul.remove(noeud);
    }

    @Override
    public void enregistrerNoeudCalcul(InterfaceServiceCalcul noeud) throws RemoteException {
        noeudsCalcul.add(noeud);
    }

    @Override
    public InterfaceServiceCalcul getNoeudCalcul() throws RemoteException {
        numNoeud += 1;
        if(numNoeud >= noeudsCalcul.size()){
            numNoeud = 0;
        }
        return this.noeudsCalcul.get(numNoeud);
    }
}
