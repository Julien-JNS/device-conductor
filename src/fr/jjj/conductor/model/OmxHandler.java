package fr.jjj.conductor.model;

/**
 * Created by Jaunais on 08/09/2014.
 */
public class OmxHandler extends PlayerHandler {

    public OmxHandler() {
        super("omxplayer", "-o local",Resource.ItemArgFormat.URL);
    }
}
