import sys
import os
import inspect
from emotions import em
from textblob import TextBlob

class LyricAnalyz:
    
    def __init__(self,givenlyric):
        self.lyric=givenlyric
    
    #using text classification for sentiment analysis-Naive Bayes Classifier from TwxtBlob API
    def textblobClassifying(self):
        words=TextBlob(self.lyric)
        testimonial = TextBlob("Textblob is amazingly simple to use. What great fun!")
        testimonial.sentiment
        return testimonial


    #a function that splits the lyrics into k tokens like n-gram
    def giveKwords(self,k):
        if k==0:
            return null
        ret=[]
        num_of_itr=len(self.lyric)/k
        starter=0
        ender=k
        for i in num_of_itr:
            ret.append(self.lyric[starter:ender])
            starter=ender
            ender=ender+k

        return ret

    #evaluting the valanecr and arousal of every word in a sentince @return ths linear value
    def evaluateMonogram(self,lyric):
        allsplits=lyric.split()
        #now computing
        valences=[]
        arousal=[]
        for word in allsplits:
            #evalute a specefic word with respect to ANEW
            valences.append()#values inserted here
            arousal.append()

        #evalute the results in lists
       
        return 0#return the aproximate values
    #Demintional
    def evaluateText(self,lyric):
        valences=[]
        arousal=[]
        words_arousal=[]
        words_valence=[]
        sentences=lyric.split('.')
        for sentence in sentences:
            for word in sentence:
                words_arousal.append(arousal_of(word))
                words_valence.append(valence_of(word))
            valences.append(average(arousal_of))
            arousal.append(average(words_valence))
        return average(valences),average(arousal)
        
       


    #use this function it is A Categorial Method approach, keyword base emotion detector, detects words with emotion meaning. 
    def ingestlyricV1(self):

        #split givenlyrics
        c=self.lyric.split('.')#sentences
        q=[]
        for x in c:
            q.extend(analyse(x))#find if emotion
        r={}
        for x in set(q):
            r[x] = q.count(x)#count appearince
        return r
    
    def analyse(e):
        w=[]
        for x in em:
            if x in e:
                w.append(em[x])
        return w



#This an example looking like this:
"""

sad , 456
embarrassed , 2
belittled , 3
ecstatic , 5
hated , 22
angry , 174
singled out , 30
codependent , 2
attracted , 66
esteemed , 6
surprise , 26
adequate , 22
bored , 5
apathetic , 32
entitled , 11
independent , 30
obsessed , 7
fearless , 48
safe , 11
free , 68
focused , 3
alone , 22
lustful , 7
loved , 27
cheated , 11
derailed , 1
fearful , 74
average , 45
attached , 49
lost , 22
anxious , 32
demoralized , 3
happy , 173
powerless , 62
    """