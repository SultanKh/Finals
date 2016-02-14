#!/usr/bin/python

# This script takes an audio file and performs an echoprint lookup on it.
# Requirements: pyechonest >= 4.2.15 http://code.google.com/p/pyechonest/
#               The echoprint-codegen binary (run make from ../src)
#               an Echo Nest API key

import sys
import os
import inspect

sys.path.append( '../pyechonest' )
cmd_folder = os.path.realpath(os.path.abspath(os.path.split(inspect.getfile( inspect.currentframe() ))[0]))
if cmd_folder not in sys.path:
    sys.path.insert(0, cmd_folder)

import pyechonest.config as config
import pyechonest.song as song

config.CODEGEN_BINARY_OVERRIDE = os.path.abspath("../echoprint-codegen")

# Put your API key in a shell variable ECHO_NEST_API_KEY, or put it here
config.ECHO_NEST_API_KEY='ZSLN415AFXNHTTRAS'

def lookup(file):
    # Note that song.identify reads just the first 30 seconds of the file
    fp = song.util.codegen(file)
    if len(fp) and "code" in fp[0]:
        # The version parameter to song/identify indicates the use of echoprint
        result = song.identify(query_obj=fp, version="4.11")
        print "Got result:", result
        
        if len(result):
            dicSong={}
            print "Artist: %s (%s)" % (result[0].artist_name, result[0].artist_id)
            dicSong['Artist']=result[0].artist_name
            dicSong['ArtistId']=result[0].artist_id
            print "Song: %s (%s)" % (result[0].title, result[0].id)
            dicSong['Song']=result[0].title
            dicSong['SongId']=result[0].id
            return dicSong
        else:
            dic={}
            dic['NoMatch']="No match. This track may not be in the database yet."
            return dic
    else:
        dic={}
        dic['Cannot']="Could not decode"
        print dic
            

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print >>sys.stderr, "Usage: %s <audio file>" % sys.argv[0]
        sys.exit(1)
    lookup(sys.argv[1])