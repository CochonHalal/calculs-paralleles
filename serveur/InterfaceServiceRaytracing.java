package serveur;

import serviceCalcul.InterfaceServiceCalcul;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServiceRaytracing extends Remote {
    void supprimerNoeudCalcul(InterfaceServiceCalcul noeud) throws RemoteException;

    void enregistrerNoeudCalcul(InterfaceServiceCalcul noeud) throws RemoteException;

    InterfaceServiceCalcul getNoeudCalcul() throws RemoteException;
}
