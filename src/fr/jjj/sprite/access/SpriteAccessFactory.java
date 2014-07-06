package fr.jjj.sprite.access;

import fr.jjj.sprite.Sprite;
import fr.jjj.sprite.access.rmi.SpriteAccessRMI;
import fr.jjj.sprite.config.NetworkConfig;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class SpriteAccessFactory {

    private NetworkConfig config;

    public SpriteAccessFactory(NetworkConfig networkConfig)
    {
        this.config=networkConfig;
    }

    public SpriteAccess getSpriteAccess(Sprite sprite)
    {
        return new SpriteAccessRMI(sprite, config.getHost(),config.getPort());
    }
}
