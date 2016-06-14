package com.example.alsultan.soultomusica;

/**    FOR THE SAKE OF USING_YOUTBE

 * Created by Al Sultan on 6/2/2016.
 */
public class Entry {

    private final int id;
    private String name;
    private String url;
    private boolean isYoutube;

    public Entry(int id, String name, String url, boolean youtube) {
        this.id = id;
        this.name = name;
        this.url = url;
        isYoutube = youtube;
    }

    public Entry(String name, String url, boolean youtube) {
        this(-1, name, url, youtube);
    }

    public Entry(int id, String name, String url, int isYoutubeInt) {
        this(id, name, url, false);
        if (isYoutubeInt != 0) {
            setYoutube(true);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isYoutube() {
        return isYoutube;
    }

    public void setYoutube(boolean youtube) {
        isYoutube = youtube;
    }

    public void setYoutube(int youtube) {
        if (youtube == 0) {
            isYoutube = false;
        } else {
            isYoutube = true;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
