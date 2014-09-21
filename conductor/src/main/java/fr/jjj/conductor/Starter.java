package fr.jjj.conductor;

/**
 * Created by Jaunais on 26/06/2014.
 */
public class Starter {



    public static void main(String[] args) {

        ConductorImpl conductor=ConductorFactory.getInstance().getConductor();


//        System.out.println("test start getAM:"+conductor.getActivityMedia());
//
//        System.out.println("test start getAM.getMS:"+conductor.getActivityMedia().getResources());
//
//        System.out.println("test start getAM.getD:"+conductor.getActivityMedia().getMediaDevices());
    }



}
