"""
Routes and views for the flask application.
"""
import sys
from datetime import datetime
from flask import render_template,request,json
from FlaskWebClient import app

#from librosa import *


@app.route('/')
@app.route('/home')
def home():
    """Renders the home page."""
    
    return render_template('StartHere.html',title='Home Page',year=datetime.now().year)

@app.route('/SignUp.html')
def StartPage2():
    return render_template('SignUp.html',title='Sign Up',year=datetime.now().year)

@app.route('/signingUp',methods=['POST'])
def signUp():
    # create user code will be here !!
    _name = request.form['inputName']
    _email = request.form['inputEmail']
    _password = request.form['inputPassword']

    # validate the received values
    if _name and _email and _password:
        return json.dumps({'status':'OK'})
    else:
        return json.dumps({'html':'<span> you ass mother fucker</span>'})

@app.route('/logIn.html')
def logIn():
    #go to login 
    return render_template('logIn.html',title='Log In',year=datetime.now().year)


@app.route('/contact')
def contact():
    """Renders the contact page."""
    return render_template(
        'contact.html',
        title='Contact',
        year=datetime.now().year,
        message='Your contact page.'
    )

@app.route('/about')
def about():
    """Renders the about page."""
    return render_template(
        'about.html',
        title='About',
        year=datetime.now().year,
        message='Your application description page.'
    )

@app.route('/ClientMainPage.html')
def ClientMainPage():
    return render_template('ClientMainPage.html',title='Client First Page',year=datetime.now().year)
    #send song through jsong or POST to AnalyzeUserSong()




@app.route('/analyzeSong',methods=['POST'])
def AnalyzeUserSong():
 
    #make object song or file from sended song
    #call SongAnalysis
    #send SongAnalysis.results to user
    pass




# For a given file, return whether it's an allowed type or not
def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1] in app.config['ALLOWED_EXTENSIONS']


#Uploading a File - audio
#*********************************************
@app.route('/Users/Public/Music/uploads')
def uploaded_file(filename):
    return send_from_directory(app.config['UPLOAD_FOLDER'],
                               filename)

# Route that will process the file upload
@app.route('/upload', methods=['POST'])
def upload():
    # Get the name of the uploaded file
    file = request.files['file']
    # Check if the file is one of the allowed types/extensions
    if file and allowed_file(file.filename):
        # Make the filename safe, remove unsupported chars
        filename = secure_filename(file.filename)
        # Move the file form the temporal folder to
        # the upload folder we setup
        file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
        # Redirect the user to the uploaded_file route, which
        # will basicaly show on the browser the uploaded file
        return redirect(url_for('uploaded_file',
                                filename=filename))

    '''
@app.route('/uploads/')
def uploaded_file(filename):
    return send_from_directory(app.config['UPLOAD_FOLDER'],
                               filename) '''