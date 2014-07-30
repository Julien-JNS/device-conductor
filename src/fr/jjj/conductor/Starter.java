package fr.jjj.conductor;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class Starter {



    public static void main(String[] args) {

        Conductor sprite=new ConductorFactory().getSprite();


        System.out.println("test start getAM:"+sprite.getActivityMedia());

        System.out.println("test start getAM.getMS:"+sprite.getActivityMedia().getMediaSources());

        System.out.println("test start getAM.getD:"+sprite.getActivityMedia().getMediaDevices());
    }



}
