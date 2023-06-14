import serveur.InterfaceServiceRaytracing;
import serviceCalcul.InterfaceServiceCalcul;
import serviceCalcul.ServiceCalcul;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

public class LancerNoeudCalcul {
    public static void main(String[] args) throws Exception{
        //récupérer l'annuaire du SC
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        //récupérer la classe du SC
        InterfaceServiceRaytracing sR =(InterfaceServiceRaytracing)registry.lookup("ServiceRaytracing");
        //créer un service caclul
        ServiceCalcul serviceCalcul = new ServiceCalcul();
        //caster le service
        InterfaceServiceCalcul noeudCalcul = (InterfaceServiceCalcul) UnicastRemoteObject.exportObject(serviceCalcul, 0);
        //enregistrer le noeud
        sR.enregistrerNoeudCalcul(noeudCalcul);
        System.out.println("noeud enregistrer");
    }
}
