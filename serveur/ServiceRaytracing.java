package serveur;

import serviceCalcul.InterfaceServiceCalcul;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServiceRaytracing implements InterfaceServiceRaytracing, Serializable {
    ArrayList<InterfaceServiceCalcul>noeudsCalcul = new ArrayList<>();
    int numNoeud = 0;

    @Override
    public synchronized void supprimerNoeudCalcul(InterfaceServiceCalcul noeud) {
        try{
            noeudsCalcul.remove(noeud);
        } catch(Exception e){
            System.out.println("Erreur supprimer noeud dans ServiceRaytracing : " + e.getMessage());
        }
    }

    @Override
    public void enregistrerNoeudCalcul(InterfaceServiceCalcul noeud) {
        try{
            noeudsCalcul.add(noeud);
        } catch(Exception e){
            System.out.println("Erreur ajouter noeud dans ServiceRaytracing : " + e.getMessage());
        }
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