package fr.jjj.conductor.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jaunais on 02/09/2014.
 */
public class PlayerHandler {

    private Log log= LogFactory.getLog(this.getClass());

    private Process process;

    private String playerCommand;

    private String defaultOptions;

    private Resource.ItemArgFormat itemArgFormat;
    private OutputStream outputStream;

    public enum Action{
        VOLUP,VOLDOWN,NEXT,PREV,PAUSE,STOP;
    }

    public PlayerHandler(String playerCommand, String defaultOptions, Resource.ItemArgFormat itemArgFormat) {
        this.playerCommand = playerCommand;
        this.defaultOptions = defaultOptions;
        this.itemArgFormat=itemArgFormat;
    }

    public void play(MediaItem item)
    {
        List<String> command=new ArrayList<String>();
        command.add(playerCommand);
        command.addAll(Arrays.asList(defaultOptions.split(" ")));
        command.add(item.getMediaSource().getItemArg(item.getDescription().getTitle(),itemArgFormat));
        StringBuilder builder=new StringBuilder();
        for (String str : command) {
            builder.append(str);
            builder.append(" ");
        }
        log.info("Player command: "+ builder.toString());
        try {
            ProcessBuilder pb = new ProcessBuilder(command.toArray(new String[command.size()]));
            process = pb.start();
            outputStream = process.getOutputStream();
            final InputStream inputStream = process.getInputStream();
            new Thread(new Runnable(){
                @Override
                public void run() {
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    try {
                        while ((line = br.readLine()) != null) {
                            log.info(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            try {
                process.waitFor();
                outputStream.close();
                inputStream.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void action(Action action)
    {
        switch(action)
        {
            case VOLDOWN:

                break;
            case VOLUP:
                break;
            case NEXT:
                break;
            case PREV:
                break;
            case PAUSE:

                break;
            case STOP:
                break;
            default:
                break;

        }
    }

    public void command(DeviceDesc.Command action)
    {
        switch(action)
        {
            case VOLDOWN:
                pressKey("-");
                break;
            case VOLUP:
                pressKey("+");
                break;
            case NEXT:
                break;
            case PREV:
                break;
            case PAUSE:
                pressKey("p");
                break;
            case STOP:
                process.destroy();
                break;
            default:
                break;

        }

    }

    private void pressKey(String key)
    {
        log.info("Process="+process);
        OutputStream stream=process.getOutputStream();
        log.info("Outputstream="+stream);
        try {
            stream.write(key.getBytes());
            stream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
