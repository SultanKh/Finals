# Beat tracking example
from __future__ import print_function
from librosa import *
import lookup
import LyricAnalyz
#sys.path.insert(0, os.path.abspath(os.path.dirname(__file__)))


class SongAnalyzing1(object):

    #first we bring the file song_to_Acknowledge(file) ;from client side as binary post
    #acknowledge the song by echo finger print; We will have songe title name Library:echoprint-codegen
    def ingestLyric(lyric):
        songDetail=lookup(theSong) #Music Match
        if songDetail.has_key('Cannot'):
            error
        elif songDetail.has_key('NoMatch'):
            error
        else:
            pass
        #Acknowledge the lyrics from Music match- using SongTitle code from behind
        #track.lyrics.get?track_id=songDetail['songId']
        songInProcess=Track.track(songdetail['SongId'])

        Lyrics=songInProcess.lyrics()

        #MY role: ingest the lyrics
        senti=LyricAnalyz(Lyrics)
        return senti.textblobClassifying()



    



    #ingest the audio using librosa library
    



    """description of class"""

    # 1.  Get the file path to the included audio example
    filename = librosa.util.example_audio_file()

# 2.  Load the audio as a waveform `y`
#    Store the sampling rate as `sr`
    y, sr = librosa.load(filename)

# 3.  Run the default beat tracker
    tempo, beat_frames = librosa.beat.beat_track(y=y, sr=sr)

    print('Estimated tempo: {:.2f} beats per minute'.format(tempo))

# 4.  Convert the frame indices of beat events into timestamps
    beat_times = librosa.frames_to_time(beat_frames, sr=sr)

    print('Saving output to beat_times.csv')
    librosa.output.times_csv('beat_times.csv', beat_times)


