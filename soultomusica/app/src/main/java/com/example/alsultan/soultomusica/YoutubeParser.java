package com.example.alsultan.soultomusica;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**    FOR THE SAKE OF USING_YOUTBE

 * * Parsing search results from Youtube.
 */
public class YoutubeParser {
    private static final String TAG = "XMLYoutubeParser";

    public List<Entry> parse(BufferedReader reader) {
        try {

            XmlPullParser parser = Xml.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            //parser.setInput(in, "UTF-8");


            parser.setInput(reader);
            parser.nextTag();
            return readFeed(parser);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            try {
                Log.i("info","trying to close HERHEH");
                reader.close();
            } catch (Exception e) {
                Log.i("Info", "cant closing..!!!");
                e.printStackTrace();
            }
        }
        Log.i("Info", "return NUUUUUUUUUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLll..!!!");

        return null;
    }

    private List<Entry> readFeed(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        List<Entry> entries = new ArrayList<Entry>();

        parser.require(XmlPullParser.START_TAG, null, "feed");
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("entry")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private Entry readEntry(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "entry");
        String title = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            String rel = parser.getAttributeValue(null, "rel");
            if (name.equalsIgnoreCase("title")) {
                title = readTitle(parser);
            } else if (name.equalsIgnoreCase("link")
                    && rel.equals("alternate")) {

                link = readLink(parser);
            } else {
                skip(parser);
            }
        }
        return new Entry(title, link, true);
    }

    private String readLink(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, null, "link");

        link = parser.getAttributeValue(null, "href");
        parser.nextTag();

        parser.require(XmlPullParser.END_TAG, null, "link");

        return link;
    }

    private String readTitle(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "title");
        return title;
    }

    private String readText(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException,
            IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }

        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
                case XmlPullParser.END_TAG:
                    depth--;
                    break;

            }
        }

    }


}