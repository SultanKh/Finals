

//********************************************************************************************************************************
//********************************************************************************************************************************
//                                          PLAYING MUSIN/SONGS AND OVERALL INFO TAGS



function search_this() {    //eminem - rap god
    var searchinput = document.getElementById("searchit");
    if (searchinput.value == "") {
        alert("Fuck OFF");
        return;
    }
   
    $.getJSON("http://musicovery.com/api/track.php?fct=getinfo&id=2127&format=json", function (data) {
        
        var schelm = document.createElement("p");
        var to_desply = traverse(data);
        
        $("<p> <b>" + to_desply + "</b></p>").insertAfter(".box1");
    });
    //function help
    
                        
}
function traverse(jsonObj) {
    var st = "";
    var type = typeof jsonObj
    if (type == "object") {
        for (var key in jsonObj) {
            st += key + ": ";
            var type2 = typeof jsonObj[key];
            if (type2 == "object") {
                st += "\n  \t";
            }
            st += traverse(jsonObj[key]) + "\n";
        }
    } else {
        st += jsonObj + "\n";
    }
    return st;
}
//function help ENDS


//=================================================================================================================================
//=================================================================================================================================
//                                          SOUND CLOUD BITCH..


    window.onload = function () {
             SC.initialize({
                 client_id: '119b5993b27288547d01c6800b5609e2'
             });

             var songbygenre = document.getElementById('genre');
             songbygenre.onclick = function(e) {
                 e.preventDefault();
                 playanySCmusic('rock');
             };
            
         };
function playanySCmusic(genre) {
    var soso= document.getElementById("soundcloudplayer");
    SC.get('/tracks', {
        genres: genre,
        bpm: {
            from: 100
        }
    }).then(function (tracks) {
        var random = Math.floor(Math.random() * 49);
        SC.oEmbed(tracks[random].uri, { auto_play: true },soso);
    });
}

           
            
//==============================================================================================================================================
//________________________________________SOUND CLOUD ENDS HERE BITCH___________________________________________________________________________




function dothis() {
    var a = document.getElementById("box1");
                
    var a1 = document.createElement('h1');
    a1.textContent = "Motivating";
    var a2 = document.createElement('h1');
    a2.textContent = "Melancholy";
    a.appendChild(a1);
    a.appendChild(a2);
}

//********************************************************************************************************
//********************************************************************************************************
//                                      handle drag and drop files

function handleDragOver(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    evt.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
}

// Setup the dnd listeners.
$(document).ready(function () {
   

});

//***************************showing the progress bar**************
//*****************************************************************
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



//***************************************MUSIC PLAYER FOR ME

    function getStreamUrl(aId) {
        return 'https://api.soundcloud.com/tracks/' + aId + '/stream?client_id=' + CLIENT_ID;
    }


//***************************************MUSIC PLAYER ENDS HERE