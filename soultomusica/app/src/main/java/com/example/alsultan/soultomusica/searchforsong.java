package com.example.alsultan.soultomusica;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Al Sultan on 4/29/2016.
 */
public class searchforsong extends Activity {

    private GoogleApiClient client;

    String link_to_api="http://developer.echonest.com/api/v4/song/search?api_key=ZSLN415AFXNHTTRAS&format=json&results=1&artist=radiohead&title=karma%20police";
    TextView resp;
    String TextLyricToAnalyze="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach_for_song);

        //initilizing FIREBASE obecting to achive cloud sync with web

        /* search example
            http://developer.echonest.com/api/v4/song/search?api_key=ZSLN415AFXNHTTRAS&format=json&results=1&artist=radiohead&title=karma%20police

            {
  "response": {
    "status": {
      "code": 0,
      "message": "Success",
      "version": "4.2"
    },
    "songs": [
      {
        "artist_id": "ARH6W4X1187B99274F",
        "id": "SOCZZBT12A6310F251",
        "artist_name": "Radiohead",
        "title": "Karma Police"
      }
    ]
  }
}
         */
        //retreive lyric from  chartlyric {xml}
        final EditText ed1=(EditText) findViewById(R.id.songartist);
        final EditText ed2=(EditText) findViewById(R.id.songname);

        ImageButton sendbtn=(ImageButton) findViewById(R.id.sendbtn);

        final TextView txtresults=(TextView) findViewById(R.id.textanalysis);
        resp=(TextView) findViewById(R.id.apiresponse) ;


        final TextView audioresults1=(TextView) findViewById(R.id.audio_analyezed);

        //String chartlyric_link="http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect?artist="+ ed1.getText() +"&song="+ed2.getText();

        sendbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (isConnected()) {
                    //ge the lyrics
                    String getlyric = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect?artist=" + ed1.getText() + "&song=" + ed2.getText();
                    getlyric = getlyric.replaceAll("\\s+","%20");
                    String receivedText = "No Lyric";


                    try {
                        //sending get request to char lyrics
                        receivedText = new SendGetRequestToGetLyrics().execute(getlyric).get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        receivedText = "No Lyric";
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        receivedText = "No Lyric";
                    }
                    /*
                    HandleXML mylyricfromUrl = new HandleXML(getlyric);
                    mylyricfromUrl.fetchXML();
                    while (mylyricfromUrl.parsingComplete) ;
                    receivedText=mylyricfromUrl.getLyrics();
                    */

                    resp.setText(receivedText);
                    TextLyricToAnalyze = receivedText;





                    String receivedResults = "No Results";
                    //SEND RECIEVED TEXT TO ANALYZE:
                    try {
                        receivedResults = new SendPost_LyricToAnalyze().execute("http://sentiment.vivekn.com/docs/api/").get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    //output results
                    txtresults.setText(receivedResults);


                    //Analyzing Audio
                    String audio_analyzed="No Results";

                    String request_from_eco="http://developer.echonest.com/api/v4/song/search?api_key=ZSLN415AFXNHTTRAS&format=json&results=1&" +
                            "artist=" + ed1.getText() +"&title=" + ed2.getText() + "&bucket=audio_summary";

                    request_from_eco=request_from_eco.replaceAll("\\s+","%20");
                    try {
                        audio_analyzed = new SendGetRequestToEchoNest().execute(request_from_eco).get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                    //use mapper i tree<-------------------------------------

                    //try parsing into Json object for comfort use.
                    JSONObject resultsInJson=null,responsInJson=null,trackInJson=null;
                    JSONArray jsonArray = new JSONArray(),audio_summary=new JSONArray();
                    Iterator x=null,itr=null;
                    String toprintJson = "NO PRINT";
                    try {
                        resultsInJson=new JSONObject(audio_analyzed);//hall respons
                        responsInJson=resultsInJson.getJSONObject("response"); //taking specific response

                        //parsing to array we have 2 objectsJASON
                        x = responsInJson.keys();

                        while (x.hasNext()){
                            String key = (String) x.next();
                            jsonArray.put(responsInJson.get(key));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //reach audio_summary object

                    try {
                        toprintJson=jsonArray.get(1).toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    audioresults1.setText(toprintJson);



                    //save data to firebase
                    UserUsage sample1=new UserUsage("user1_testing",ed1.getText()+ " " + ed2.getText());

                    Firebase ref = new Firebase("https://docs-examples.firebaseio.com/android/saving-data/fireblog");
                    Firebase in_users1 = ref.child("UserUsage");
                    in_users1.child("user_" + ed1.getText()).setValue(sample1);



                }

                return false;
            }
        });





        //analse using http://sentiment.vivekn.com/docs/api/ {post request}







        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }







    // convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    // check network connection
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "searchforsong Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alsultan.soultomusica/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "searchforsong Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alsultan.soultomusica/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


//GET requesting Lyrics from XML Response as string
    public static String GET(String url) {
        String xmlString = null;
        String retString;
        retString = null;

        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            // xml = EntityUtils.toString(httpEntity);


            xmlString = EntityUtils.toString(httpEntity);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlString));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("Lyric");
            retString = nodes.item(0).getTextContent();


        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return retString    ;
    }


//function to send text lyric to analyze
    public String postData(String lyric) {
        // Create a new HttpClient and Post Header
        String analysisResult="No Result";


        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = "http://sentiment.vivekn.com/api/text/";
            HttpPost post = new HttpPost(postURL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("txt", lyric));


            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();
            if (resEntity != null) {

              analysisResult =  EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            analysisResult="No Result";
        }


        return analysisResult;
    }

    private class SendPost_LyricToAnalyze extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return postData(TextLyricToAnalyze);
        }
        // onPostExecute displays the results of the AsyncTask.

        /*
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();

            //display the requested vales
            resp.setText(result);
        }
        */
    }


    private class SendGetRequestToGetLyrics extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.

        /*
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();

            //display the requested vales
            resp.setText(result);
        }
        */
    }



//-----------------*****************************------------------------------------
//GET return String
public static String GET_AS_String(String url) {
    String jsonString = null;


        // defaultHttpClient
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpPost = new HttpGet(url);

    HttpResponse httpResponse = null;
    try {
        httpResponse = httpClient.execute(httpPost);
    } catch (IOException e) {
        e.printStackTrace();
    }

    HttpEntity httpEntity = httpResponse.getEntity();
        // xml = EntityUtils.toString(httpEntity);


    try {
        jsonString = EntityUtils.toString(httpEntity);
    } catch (IOException e) {
        e.printStackTrace();
    }


    return jsonString;
}

//GET requesting Lyrics FROM Echo as JSON
private class SendGetRequestToEchoNest extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {


        return GET_AS_String(urls[0]);
    }
    // onPostExecute displays the results of the AsyncTask.

        /*
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();

            //display the requested vales
            resp.setText(result);
        }
        */
}



}




