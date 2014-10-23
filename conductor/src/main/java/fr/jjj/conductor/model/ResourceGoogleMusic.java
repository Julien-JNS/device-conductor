package fr.jjj.conductor.model;

import com.google.gson.Gson;
import sun.net.www.content.text.PlainTextInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

/**
 * Created by Jaunais on 02/09/2014.
 */
public class ResourceGoogleMusic extends Resource{

    private String email="majulou.jaunais@gmail.com";
    private String password="L29M17!M18J15";
    private String authToken;
    List<String> cookies;

    private static int ALBUMS=0,PLAYLISTS=1;

    private List<MediaItem> rootItems=new ArrayList<MediaItem>();

    private List<MediaItem> currentPath=new ArrayList<MediaItem>();

    private boolean connected=false;

    private Map<String,MediaItem> playlistMap;

    public ResourceGoogleMusic(String label) {
        super(label);

    }

    private void openConnection() {
        URL url = null;
        try {
            url = new URL("https://www.google.com/accounts/ClientLogin");

            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("Passwd", password);
            params.put("Email", email);
            params.put("accountType", "HOSTED_OR_GOOGLE");
            params.put("logincaptcha", "None");
            params.put("logintoken", "None");
            params.put("service", "sj");

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);


            System.out.println("header fields=" + conn.getHeaderFields());
            System.out.println("content type=" + conn.getContentType());
            System.out.println("content=" + conn.getContent());
            int contentLength = conn.getHeaderFieldInt("Content-Length", -1);
            System.out.println("contentLength=" + contentLength);
            if (contentLength > 0) {
                byte[] bytes = new byte[contentLength];
                ((PlainTextInputStream) conn.getContent()).read(bytes);
                String rawCookie = new String(bytes);
                System.out.println("rawCookie=" + rawCookie);
                final int startIndex = rawCookie.indexOf("Auth=") + "Auth=".length();
                int endIndex = rawCookie.indexOf("\n", startIndex);
                if (endIndex == -1) {
                    endIndex = rawCookie.length() - 1;
                }
                authToken = rawCookie.substring(startIndex, endIndex);
                System.out.println("authToken=" + authToken);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //CONFIRMATION???
        //{'url': 'https://play.google.com/music/listen', 'headers': {'Authorization': u'GoogleLogin auth=DQAAANAAAACyYAgJGveZfp7UdD6tEh8hYynZ2sug4SGH5jMeP11-zRdCVDUhBw1bUNYtb7D3RhVghlSK4lzgmQX_ROqQ8WJZ9hIHW_zz6wKQpn6esmM0d0DhsQftMnn5UbP-o7mdIjElcnER9xQd-55GOxnEVjpZf8YtUw-wQ_C41XJHswtM9qa8I4L-8BYyhM07OhVz1Hp_UMGYkRiKOjvcBtvAav3xN3PxIMPOQyDIL8pdKUrsNOWkWwxxbI_m-VjkFBHN8L6TICbG4wCjrtOmxi1JB926'}, 'method': 'HEAD'}
        URL url1 = null;
        try {
            url1 = new URL("https://play.google.com/music/listen");

            HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
            conn1.setRequestMethod("HEAD");
            conn1.setRequestProperty("Authorization", "GoogleLogin auth=" + authToken);
            System.out.println("headers1=" + conn1.getHeaderFields());

            String xt = null;
            String sjsaid = null;

            cookies = conn1.getHeaderFields().get("Set-Cookie");

            Iterator<String> itCookie = conn1.getHeaderFields().get("Set-Cookie").iterator();
            while (itCookie.hasNext()) {
                String rawCookie = itCookie.next();
                System.out.println("rawCookie:" + rawCookie);
                if (rawCookie.contains("xt=")) {
                    final int startIndex = rawCookie.indexOf("xt=") + "xt=".length();
                    final int endIndex = rawCookie.indexOf(";", startIndex);
                    xt = rawCookie.substring(startIndex, endIndex);
                    System.out.println("xt:" + xt);
                } else if (rawCookie.contains("sjsaid=")) {
                    final int startIndex2 = rawCookie.indexOf("sjsaid=") + "sjsaid=".length();
                    final int endIndex2 = rawCookie.indexOf(";", startIndex2);
                    sjsaid = rawCookie.substring(startIndex2, endIndex2);
                    System.out.println("sjsaid:" + sjsaid);
                }

            }
            connected=true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkConnection()
    {
        if(!connected)
        {
            openConnection();
        }
    }

    @Override
    public List<MediaItem> getMediaItems(MediaItem reference) {
        checkConnection();

        List<MediaItem> list=null;
        if(reference==null)
        {
            if(rootItems.isEmpty()) {
                rootItems.add(new MediaItem(new MediaItemDesc(String.valueOf(ALBUMS), "Albums"), this, null));
                rootItems.add(new MediaItem(new MediaItemDesc(String.valueOf(PLAYLISTS), "PlayLists"), this, null));
            }
            list=rootItems;
        }
        else if(currentPath.size()==0)
        {
            if(reference.getDescription().getId().equals(String.valueOf(PLAYLISTS)))
            {
                list=getPlaylists();
            }
            currentPath.add(reference);
        }
        else
        {
            if(currentPath.get(0).getDescription().getId().equals(String.valueOf(PLAYLISTS)))
            {
                list=getPlaylistItems(reference);
            }
            currentPath.add(reference);
        }
        return list;
    }


    private List<MediaItem> getPlaylists()
    {
        List<MediaItem> list=new ArrayList();
        URL url2 = null;
        try {
            url2 = new URL("https://www.googleapis.com/sj/v1.1/playlistfeed");


        Map<String, Object> params2 = new LinkedHashMap<String, Object>();
        params2.put("max-results", "20000");
        params2.put("\'alt\'", "'json'");
        params2.put("'updated-min'", "0");
        params2.put("'include-tracks'", "'true'");

        //'params': {'alt': 'json', 'updated-min': 0, 'include-tracks': 'true'}}
        StringBuilder postData2 = new StringBuilder();
        postData2.append('{');
        for (Map.Entry<String, Object> param : params2.entrySet()) {
            if (postData2.length() >1) postData2.append(',');
            postData2.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData2.append(':');
            postData2.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        postData2.append('}');
        System.out.println("postdata2="+postData2);
        byte[] postDataBytes2 = postData2.toString().getBytes("UTF-8");
        postDataBytes2="{'alt': 'json', 'updated-min': 0, 'include-tracks': 'true'}".getBytes("UTF-8");
        System.out.println("postDataBytes2="+new String(postDataBytes2));
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setRequestMethod("POST");
        conn2.setRequestProperty("Authorization", "GoogleLogin auth=" + authToken);
        conn2.setRequestProperty("Content-Type", "application/json");
        conn2.setRequestProperty("max-results", "2000");
        conn2.setRequestProperty("Content-Length", String.valueOf(postDataBytes2.length));


        conn2.setDoOutput(true);
        conn2.getOutputStream().write(postDataBytes2);

        System.out.println("headers2=" + conn2.getHeaderFields());
        System.out.println("content=" + conn2.getContent());

        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn2.getInputStream()));

          /*  while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }*/

        PlaylistList pll= new Gson().fromJson(reader, PlaylistList.class);

            System.out.println("kind="+pll.getKind());
        Iterator<Playlist> it=pll.getData().getItems().iterator();
        while(it.hasNext())
        {
            Playlist pl=it.next();
            System.out.println(pl.getName()+"("+pl.getId()+")");

            MediaItem subItem = new MediaItem(new MediaItemDesc(pl.getId(), pl.getName()), this, "Playlists");
            playlistMap.put(pl.getId(),subItem);
            list.add(subItem);
            rootItems.get(PLAYLISTS).addSubItem(subItem);
        }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<MediaItem> getPlaylistItems(MediaItem item)
    {
        List<MediaItem> list=null;
        //GET PLAYLIST CONTENTS:
        //{'url': 'https://www.googleapis.com/sj/v1.1/plentryfeed', 'headers': {'Content-Type': 'application/json', 'Authorization': '<omitted>'}, 'data': '{"max-results": "20000"}', 'method': 'POST', 'params': {'alt': 'json', 'updated-min': 0, 'include-tracks': 'true'}}
        URL url3 = null;
        try {
            url3 = new URL("https://www.googleapis.com/sj/v1.1/plentryfeed");


        //'params': {'alt': 'json', 'updated-min': 0, 'include-tracks': 'true'}}
        byte[] postDataBytes3="{'alt': 'json', 'updated-min': 0, 'include-tracks': 'true'}".getBytes("UTF-8");
        System.out.println("postDataBytes3="+new String(postDataBytes3));
        HttpURLConnection conn3 = (HttpURLConnection) url3.openConnection();
        conn3.setRequestMethod("POST");
        conn3.setRequestProperty("Authorization", "GoogleLogin auth=" + authToken);
        conn3.setRequestProperty("Content-Type", "application/json");
        conn3.setRequestProperty("max-results", "2000");
        conn3.setRequestProperty("Content-Length", String.valueOf(postDataBytes3.length));


        conn3.setDoOutput(true);
        conn3.getOutputStream().write(postDataBytes3);

        System.out.println("headers3=" + conn3.getHeaderFields());
        System.out.println("content=" + conn3.getContent());

        String line3;
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(conn3.getInputStream()));

//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }

        PlaylistEntryList plel= new Gson().fromJson(reader3, PlaylistEntryList.class);

        System.out.println("kind="+plel.getKind());
        Iterator<PlaylistEntry> it3=plel.getData().getItems(). iterator();
        while(it3.hasNext())
        {
            PlaylistEntry ple=it3.next();
            MediaItem pl=playlistMap.get(ple.getPlaylistId());
            System.out.println(pl.getDescription().getTitle()+"("+ple.getId()+")");

            MediaItem playlistItem=new MediaItem(new MediaItemDesc(ple.getTrackId(),ple.getTrack().getTitle()),this,getLocation());
            pl.addSubItem(playlistItem);
        }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String getLocation()
    {
        StringBuilder sb=new StringBuilder();
        for(MediaItem item:currentPath)
        {
            sb.append(item.getDescription().getTitle());
            sb.append("|");
        }
        return sb.toString();
    }

    class PlaylistList
    {
        private String kind;

        public PlaylistList()
        {

        }

        public String getKind()
        {
            return kind;
        }

        private PlaylistListData data;

        public PlaylistListData getData(){return data;}
    }

    class PlaylistListData
    {
        private Collection<Playlist> items;

        public Collection<Playlist> getItems()
        {
            return items;
        }

        private Collection<Playlist> data;

        public Collection<Playlist> getData(){return data;}
    }

    class Playlist
    {
        private String kind,id,name;


        public Playlist()
        {
            entries=new ArrayList<PlaylistEntry>();
        }

        public String getKind()
        {
            return kind;
        }
        public String getId()
        {
            return id;
        }
        public String getName()
        {
            return name;
        }

        public List<PlaylistEntry> getEntries() {
            return entries;
        }

        List<PlaylistEntry> entries;

        public void addEntry(PlaylistEntry ple)
        {
            if(entries==null)
            {
                entries=new ArrayList<PlaylistEntry>();
            }
            entries.add(ple);
        }

    }

    class PlaylistEntryList
    {
        private String kind;

        public String getKind()
        {
            return kind;
        }

        private PlaylistListEntryData data;

        public PlaylistListEntryData getData(){return data;}
    }

    class PlaylistListEntryData
    {
        private Collection<PlaylistEntry> items;

        public Collection<PlaylistEntry> getItems()
        {
            return items;
        }

    }

    class PlaylistEntry
    {
        private String kind;
        private String id;
        private String playlistId;

        public String getTrackId() {
            return trackId;
        }

        private String trackId;

        public Track getTrack() {
            return track;
        }

        private Track track;


        public String getKind()
        {
            return kind;
        }
        public String getId()
        {
            return id;
        }

        public String getPlaylistId() {
            return playlistId;
        }
    }

    class Track
    {
        public String getTitle() {
            return title;
        }

        private String title;
    }

    class URLs
    {
        public Collection<String> urls;

        public  Collection<String> getUrls(){
            return urls;
        }

        private String title;
    }

    static private String extractValueFromString(String str,String prefix, String suffix)
    {
        String value=null;
        int start=str.indexOf(prefix)+prefix.length();
        int end=str.indexOf(suffix,start);
        value=str.substring(start,end);
        return value;
    }
}
