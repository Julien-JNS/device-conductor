package fr.jjj.sprite;


import fr.jjj.sprite.access.SpriteAccessFactory;
import fr.jjj.sprite.activity.media.ActivityMedia;
import fr.jjj.sprite.activity.media.MediaSource;
import fr.jjj.sprite.config.MediaActivityConfig;
import fr.jjj.sprite.config.MediaSourceConfig;
import fr.jjj.sprite.config.SpriteConfig;

import java.util.Iterator;

/**
 * Created by Jaunais on 01/07/2014.
 */
public class SpriteFactory {

    private SpriteConfig config;

    private Sprite uniqueSprite;

    public SpriteFactory() {
        config=SpriteConfig.getConfig();

    }

    public Sprite getSprite() {
        if(uniqueSprite==null) {

            uniqueSprite = new Sprite();


            MediaActivityConfig mediaConfig=config.getMediaActivityConfig();
            if(mediaConfig!=null)
            {

                ActivityMedia mediaActivity=new ActivityMedia();
                Iterator<MediaSourceConfig> itSourceConfigs=mediaConfig.getMediaSourceConfigs().iterator();
                while(itSourceConfigs.hasNext())
                {
                    MediaSourceConfig mediaSourceConfig=itSourceConfigs.next();
                    mediaActivity.addMediaSource(new MediaSource(mediaSourceConfig.getType()));
                }
                uniqueSprite.setActivity(ISprite.ActivityType.MEDIA,mediaActivity);
            }

            // ACCESS
            new SpriteAccessFactory(config.getNetworkConfig()).getSpriteAccess(uniqueSprite);
        }
        return uniqueSprite;
    }
}
