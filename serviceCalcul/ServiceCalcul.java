package serviceCalcul;

import raytracer.Image;
import raytracer.Scene;

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;

public class ServiceCalcul implements InterfaceServiceCalcul, Serializable {

    @Override
    public Image calculerPartieScene(Scene scene, int x, int y, int largeur, int hauteur) throws ServerNotActiveException {
        System.out.println("calcul en cours : " + RemoteServer.getClientHost());
        Image image = scene.compute(x, y, largeur, hauteur);
        System.out.println("calcul effectué : " + RemoteServer.getClientHost());
        return image;
    }
}
