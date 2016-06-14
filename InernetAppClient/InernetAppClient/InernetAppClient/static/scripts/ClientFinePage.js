


/*=============================BEFORE STARTING - SOME WORK FOR YOUTUBE====================================*/
var apiKey_sucker = 'AIzaSyBokFX4P5W4kZvvx4N4F9WKnMEtoIf8akU';

var client_sucker_api = '728307064424-669057fd79p2jjg8bt7hk9vdi91ib90b.apps.googleusercontent.com';

var ECHONEST_APIKEY = 'ZSLN415AFXNHTTRAS';

var SOUNDCLOUD_APIKEY = '202024184bfc1c0f1831be842aacd1a7';

var googleApiClientReady = function () {
      var loadApi = function () {
        return new Promise(function (resolve, reject) {
            gapi.client.setApiKey(apiKey_sucker);
            gapi.client.load('youtube', 'v3', resolve);
        });
    };
    loadApi().then(function () {
        console.log("GABPIII LOADDED");
        gabi.client.setApiKey(apiKey_sucker);
        gabi.client.load("youtube", "v3", function () {
            //youtube api is ready
            $("search-button").click(function (e) {
                e.preventDefault();
                console.log("IN CLICK LOADDED");

                // prepare the request
                var request = gapi.client.youtube.search.list({
                    part: 'snippet',
                    order: 'viewCount',
                    type: 'video',
                    maxResults: 10,
                    q: encodeURIComponent($("#searchit").val()).replace(/%20/g, "+")

                });
                // execute the request
                request.execute(function (response) {
                    console.log("line FUCK RESPONEsss");
                    var results = response.result;
                    $("#results").html("");
                    $.each(results.items, function (index, item) {
                        $.get("item.html", function (data) {
                            $("#results").append(tplawesome(data, [{ "title": item.snippet.title, "videoid": item.id.videoId }]));
                        });
                    });
                    resetVideoHeight();
                });
            });

            $(window).on("resize", resetVideoHeight);

        });

        console.log("Finish in youtube when do click");

    });
};








/*            SEARCHING      AND EMBIDDING            */

//search songs-> save songs titles->go analyse

function search_this_soundcloud(emotion) {    //eminem - rap god

    var searchinput = document.getElementById("searchit");
    if (searchinput.value == "") {
        alert("Fuck OFF");
        return;
    }
    SC.get('/tracks', {
        q: searchinput.value
    }).then(function (tracks) {

        alert(tracks[1].permalink_url);
        SC.oEmbed(tracks[1].permalink_url, {
            element: document.getElementById('soundcloud')
        });
    });

    /*http://soundcloud.com/forss/sets/soulhack*/


   

}

/*              FINITO          */


/*===================HANDLE UPLOAD FILE=================*/

var reader;
var progress = document.querySelector('.percent');

function updateProgress(evt) {
    // evt is an ProgressEvent.
    if (evt.lengthComputable) {
        var percentLoaded = Math.round((evt.loaded / evt.total) * 100);
        // Increase the progress bar length.
        if (percentLoaded < 100) {
            progress.style.width = percentLoaded + '%';
            progress.textContent = percentLoaded + '%';
        }
    }
}

function errorHandler(evt) {
    switch (evt.target.error.code) {
        case evt.target.error.NOT_FOUND_ERR:
            alert('File Not Found!');
            break;
        case evt.target.error.NOT_READABLE_ERR:
            alert('File is not readable');
            break;
        case evt.target.error.ABORT_ERR:
            break; // noop
        default:
            alert('An error occurred reading this file.');
    };
}

function handleFileSelect(evt) {
    // Reset progress indicator on new file selection.
    progress.style.width = '0%';
    progress.textContent = '0%';

    reader = new FileReader();
    reader.onerror = errorHandler;
    reader.onprogress = updateProgress;
    reader.onabort = function (e) {
        alert('File read cancelled');
    };
    reader.onloadstart = function (e) {
        document.getElementById('progress_bar').className = 'loading';
    };
    reader.onload = function (e) {
        // Ensure that the progress bar displays 100% at the end.
        progress.style.width = '100%';
        progress.textContent = '100%';
        setTimeout("document.getElementById('progress_bar').className='';", 2000);
    }

    // Read in the image file as a binary string.
    reader.readAsBinaryString(evt.target.files[0]);
}
//document.getElementById('audiofile').addEventListener('change', handleFileSelect, false);



/*=====================handle File Uploading=============*/






/*=======================YOUTUVE PLAY BOY PLAYER=========================*/

//YOUTUBE PLAYER------------------------>
// Load the IFrame Player API code asynchronously.


function tplawesome(e, t) {
    res = e; for (var n = 0; n < t.length; n++) {
        res = res.replace(/\{\{(.*?)\}\}/g, function (e, r) {
            return t[n][r]
        })
    }
    return res
}


/*
var authInited = googleApiClientReady().then(function () {
    gapi.auth2.init({
        client_id: '728307064424-669057fd79p2jjg8bt7hk9vdi91ib90b.apps.googleusercontent.com'
    });
})
*/
    
function resetVideoHeight() {
    $(".video").css("height", $("#results").width() * 9 / 16);
}


/*
function SearchFromYouTube() {
    var $results = $('#results');
    if ($query_song == "") {
        alert("Fuck OFF");
        return;
    }


    var $query_song = $('#searchit');
    //use: https://www.youtube.com/embed?listType=search&list=QUERY

    var searchQueryFromYB = "http://gdata.youtube.com/feeds/api/standardfeeds/most_popular?v=2&alt=json"


    $.getJSON(searchQueryFromYB, function (json) {

        var count = 0;

        var resultdisplayed = "";

        $.each(json, function (i, field) {
            resultdisplayed += '<p><a href ="http://youtu.be/' + field.id + '">';
            resultdisplayed += '<img src="http://i.ytimg.com/vi/' + field.id + '/default.jpg">';
            resultdisplayed += '<h2>' + field.title + ' ' + field.duration + '</h2></a></p>';
            $("#results").append(resultdisplayed + " ");
        });


        if (json.data.items) {

            var items = json.data.items;
            var html = "";

            items.forEach(function (item) {



                // Include the YouTube Watch URL youtu.be 
                html += '<p><a href="http://youtu.be/' + item.id + '">';

                // Add the default video thumbnail (default quality)
                html += '<img src="http://i.ytimg.com/vi/' + item.id + '/default.jpg">';

                // Add the video title and the duration
                html += '<h2>' + item.title + ' ' + item.duration + '</h2></a></p>';
                count++;

            });
        }

        // Did YouTube return any search results?
        if (count === 0) {
            $results.html("No videos found");
        } else {
            // Display the YouTube search results
            $results.html(html);
        }

        showMyVideos(json);

    });

    function showMyVideos(data) {
        var feed = data.feed;
        var entries = feed.entry || [];
        var html = ['<ul>'];
        for (var i = 0; i < entries.length; i++) {
            var entry = entries[i];
            var title = entry.title.$t;
            html.push('<li>', title, '</li>');
        }
        html.push('</ul>');
        document.getElementById('results').innerHTML = html.join('');
    }





}


function SearchFromYouTube2() {
    var q666 = $('#searchit').val();

    window.onLoadCallback = function () {
        gapi.auth2.init({
            client_id: '728307064424-669057fd79p2jjg8bt7hk9vdi91ib90b.apps.googleusercontent.com'
        });
        
        var request = gapi.client.youtube.search.list({
            q: q666,
            part: 'snippet',
            order: 'viewCount',
            type: 'video',
            maxResults: 10
        });
        //execute this request
        request.execute(function (response) {
            console.log(response);

            //var str = JSON.stringify(response.result);
            //$('#results').html('<h3>' + str + '</h3>');
        });

    }

   
   
}   


function SearchFromYouTube3() {


    var request = gapi.client.urlshortener.url.get({
        'shortUrl': 'http://goo.gl/fbsS'
    });
    request.then(function (response) {
        appendResults(response.result.longUrl);
    }, function (reason) {
        console.log('Error: ' + reason.result.error.message);
    });
}

//called automatically
function init() {
    gapi.client.setApiKey(apiKey_sucker);
    gapi.client.load('urlshortener', 'v1').then(SearchFromYouTube3);
}



/* Snippest Code - Contact Youtube and display results
function makeRequest(q) {
        var request = gapi.client.youtube.search.list({
          q: 'q',
          part: 'id,snippet',
          maxResults: 3
        });

        request.execute(function(response) {
          $('#results').empty();
          var resultItems = response.result.items;
          $.each(resultItems, function(index, item) {
            vidTitle = item.snippet.title;
            vidThumburl =  item.snippet.thumbnails.default.url;
            vidThumbimg = '<pre><img id="thumb" src="'+vidThumburl+'" alt="No  Image Available." style="width:204px;height:128px"></pre>';
            $('#results').append('<pre>' + vidTitle + vidThumbimg +  '</pre>');
          });
        });
      }

      function init() {
        gapi.client.setApiKey('AIzaSyCWzGO9Vo1eYOW4R4ooPdoFLmNk6zkc0Jw');
        gapi.client.load('youtube', 'v3', function() {
          data = jQuery.parseJSON( '{ "data": [{"name":"orsons"}] }' );
          $.each(data["data"], function(index, value) {
            makeRequest(value["name"]);
          });
        });
      }



*/


/*=======================================================================*/

//==================SEND TO ECHONET-///////////

//spotify https://api.spotify.com/v1/audio-features/{id}  id=song id
//spotify https://api.spotify.com/v1/search?q=abba&type=track&market=US
//echonest http://developer.echonest.com/api/v4/song/search?api_key=ZSLN415AFXNHTTRAS&format=json&results=1&artist=radiohead&title=karma%20police



/*
    search songs->returned responses -> use ID to embeded players
    -> save names to analyse
*/
function SEND_TO_SPOTIFY() {
    var query = document.getElementById("searchit").value;
    query.replace(" ", "%20");
    var get_link = "https://api.spotify.com/v1/search?q=" + query;
    get_link += "&type=track";
    get_link += "&limit=10";

    var results = "";
    $.getJSON(get_link, function (data) {
        //data is the JSON string
        results = data;
        console.log(data);

    });




}