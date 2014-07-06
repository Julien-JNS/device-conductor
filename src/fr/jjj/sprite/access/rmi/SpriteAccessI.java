package fr.jjj.sprite.access.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Jaunais on 26/06/2014.
 */
public interface SpriteAccessI extends Remote{



    MediaActivityAccessI getActivityMedia()throws RemoteException;
}
