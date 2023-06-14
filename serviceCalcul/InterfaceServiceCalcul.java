package serviceCalcul;

import raytracer.Image;
import raytracer.Scene;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface InterfaceServiceCalcul extends Remote {
    Image calculerPartieScene(Scene scene, int x, int y, int largeur, int hauteur) throws RemoteException, ServerNotActiveException;
}
