package client;

import common.IInitiatorPeer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestApp {

    private static IInitiatorPeer initiatorPeer;

    public static void main(String[] args) {
        /* Needed for Mac OS X */
        System.setProperty("java.net.preferIPv4Stack", "true");

        String peerAccessPoint = args[0];
        String operation = args[1];
        String pathName;

        try {
            Registry registry = LocateRegistry.getRegistry("localhost"); //TODO: This is part of peerAcessPoint
            initiatorPeer = (IInitiatorPeer) registry.lookup(peerAccessPoint);
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }

        switch (operation) {
            case "BACKUP":
                pathName = args[2];
                int replicationDegree = Integer.parseInt(args[3]);
                try {
                    initiatorPeer.backup(pathName, replicationDegree);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case "RESTORE":
                pathName = args[2];
                try {
                    initiatorPeer.restore(pathName);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case "DELETE":
                pathName = args[2];
                try {
                    initiatorPeer.delete(pathName);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case "RECLAIM":
                int maximumDiskSpace = Integer.parseInt(args[2]);
                try {
                    initiatorPeer.reclaim(maximumDiskSpace);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                break;
            case "STATE":
                try {
                    System.out.println(initiatorPeer.state());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

        }


    }

}
