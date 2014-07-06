package fr.jjj.sprite;

import fr.jjj.sprite.config.SpriteConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class Starter {



    public static void main(String[] args) {

        Sprite sprite=new SpriteFactory().getSprite();


    }



}
