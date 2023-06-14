import serveur.InterfaceServiceRaytracing;
import serveur.ServiceRaytracing;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerSC {
    public static void main(String[] args) {
        try{
            ServiceRaytracing service = new ServiceRaytracing();
            InterfaceServiceRaytracing sR = (InterfaceServiceRaytracing) UnicastRemoteObject.exportObject(service, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ServiceRaytracing", sR);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
