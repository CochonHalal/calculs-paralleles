import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.time.Instant;
import java.time.Duration;

import raytracer.Disp;
import raytracer.Scene;
import raytracer.Image;
import serveur.InterfaceServiceRaytracing;
import serviceCalcul.InterfaceServiceCalcul;

public class LancerRaytracer {

    public static String aide = "Raytracer : synthèse d'image par lancé de rayons (https://en.wikipedia.org/wiki/Ray_tracing_(graphics))\n\nUsage : java LancerRaytracer [fichier-scène] [largeur] [hauteur]\n\tfichier-scène : la description de la scène (par défaut simple.txt)\n\tlargeur : largeur de l'image calculée (par défaut 512)\n\thauteur : hauteur de l'image calculée (par défaut 512)\n";
     
    public static void main(String args[]) throws RemoteException, NotBoundException {

        // Le fichier de description de la scène si pas fournie
        String fichier_description="simple.txt";

        // largeur et hauteur par défaut de l'image à reconstruire
        int largeur = 512, hauteur = 512;
        
        if(args.length > 0){
            fichier_description = args[0];
            if(args.length > 1){
                largeur = Integer.parseInt(args[1]);
                if(args.length > 2)
                    hauteur = Integer.parseInt(args[2]);
            }
        }else{
            System.out.println(aide);
        }
        
   
        // création d'une fenêtre 
        Disp disp = new Disp("Raytracer", largeur, hauteur);
        
        // Initialisation d'une scène depuis le modèle 
        Scene scene = new Scene(fichier_description, largeur, hauteur);

        //récupérer l'annuaire du SC
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        //récupérer la classe du SC
        InterfaceServiceRaytracing sR =(InterfaceServiceRaytracing)registry.lookup("ServiceRaytracing");

        // Calcul de l'image de la scène les paramètres : 
        // - x0 et y0 : correspondant au coin haut à gauche
        // - l et h : hauteur et largeur de l'image calculée
        // Ici on calcule toute l'image (0,0) -> (largeur, hauteur)

        int nbDecoup = Integer.parseInt(args[3]);
        int l = largeur/nbDecoup, h = hauteur/nbDecoup;
        final int lar = largeur, hau = hauteur;

        for(int y = 0; y < hauteur; y = y + h){
            for(int x = 0; x < largeur; x = x + l){
                final int currentX = x;
                final int currentY = y;

                Thread thread = new Thread(() -> {
                    InterfaceServiceCalcul serviceCalcul = null;
                    try {
                        serviceCalcul = sR.getNoeudCalcul();
                    } catch (RemoteException e) {
                        System.out.println("1 plus de place : " + e.getMessage());
                    }
                    try {
                        if (currentX + l <= lar && currentY + h <= hau) {
                            Image image = serviceCalcul.calculerPartieScene(scene, currentX, currentY, l, h);
                            disp.setImage(image, currentX, currentY);
                        }
                    } catch (RemoteException e) {
                        try {
                            sR.supprimerNoeudCalcul(serviceCalcul);
                            System.out.println("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n ahah");
                        } catch (Exception ex) {
                            System.out.println("Erreur supprimer noeud : " + ex.getMessage());
                        }
                    } catch (ServerNotActiveException e) {
                        System.out.println("2 " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("3 " + e.getMessage());
                    }
                });

                thread.start();
            }
        }

        /*// Chronométrage du temps de calcul
        Instant debut = Instant.now();
        System.out.println("Calcul de l'image :\n - Coordonnées : "+x0+","+y0
                           +"\n - Taille "+ largeur + "x" + hauteur);
        Image image = scene.compute(x0, y0, l, h);
        Image image2 = scene.compute(x1, y1, l, h);
        Instant fin = Instant.now();

        long duree = Duration.between(debut, fin).toMillis();

        System.out.println("Image calculée en :"+duree+" ms");

        // Affichage de l'image calculée
        disp.setImage(image, x0, y0);
        disp.setImage(image2, x1, y1);*/
    }	
}
