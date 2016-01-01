
import webapp2
from pyechonest import config
from pyechonest import *
config.ECHO_NEST_API_KEY="ZSLN415AFXNHTTRAS"
class MainPage(webapp2.RequestHandler):
	def get(self):
		#read get request
		self.response.headers['Content-Type'] = 'text/plain'
		self.response.write('Hello motherfucker')

class findArtist(webapp2.RequestHandler):
	def get(self):
		self.response.headers['Content-Type'] = 'text/plain'
		self.response.write('finding artist')
		his_name=self.request.get('artist_name')
		weezer_results = artist.search(his_name)
		weezer = weezer_results[0]
		weezer_blogs = weezer.blogs
		self.response.write('Blogs about this motherfucker:' + str([blog.get('url') for blog in weezer_blogs]))
		


class hot_top_artist(webapp2.RequestHandler):
	def get(self):
		hottest=self.request.get('hotartist')
		bk = artist.Artist(hottest)
		print "Artists similar to: %s:" % (bk.name,)
		for similar_artist in bk.similar: 
			self.response.write("\t%s" % (similar_artist.name,))
		pass

class search_forSong(webapp2.RequestHandler):
	def get(self):
		#read paramter artist name and title
		nameofartist=self.request.get('artist')
		titlesong=self.request.get('title')

		rkp_results = song.search(nameofartist, titlesong)
		karma_police = rkp_results[0]
		self.response.write(karma_police.artist_location)
		self.response.write('tempo:'+ karma_police.audio_summary['tempo'] + 'duration:' + karma_police.audio_summary['duration'])

		pass

class analyzeThisSong(webapp2.RequestHandler):
	def get(self):
		
		ss_results = song.search(artist='the national', title='slow show', buckets=['id:7digital-US', 'tracks'], limit=True)
		slow_show = ss_results[0]
		ss_tracks = slow_show.get_tracks('7digital-US')
		self.response.write(ss_tracks[0].get('preview_url'))
		pass


app = webapp2.WSGIApplication([
	('/', MainPage),
	('/findArtist',findArtist),


], debug=True)
