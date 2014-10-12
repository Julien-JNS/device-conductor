package fr.jjj.conductor.model;

/**
 * Created with IntelliJ IDEA.
 * User: jjaunais
 * Date: 07/10/14
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public class TestHandler extends PlayerHandler {

    public TestHandler() {
//        super("timeout", "10",Resource.ItemArgFormat.NONE);
        super("ping", "127.0.0.1 -n 10",Resource.ItemArgFormat.NONE);
    }


}