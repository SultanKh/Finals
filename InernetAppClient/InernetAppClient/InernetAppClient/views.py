"""
Routes and views for the flask application.
"""

from datetime import datetime
from flask import render_template
from InernetAppClient import app



current_year=datetime.now().year

@app.route('/')
@app.route('/home')
def home():
    """Renders the home page."""
    return render_template(
        '/ClientFinePage.html',
        title='Home Page',
        year=current_year,
    )

@app.route('/contact')
def contact():
    """Renders the contact page."""
    return render_template(
        'contact.html',
        title='Contact',
        year=current_year,
        message='Your contact page.'
    )

@app.route('/about')
def about():
    """Renders the about page."""
    return render_template(
        'about.html',
        title='About',
        year=current_year,
        message='Your application description page.'
    )
@app.route('/SignUp.html')
def StartPage2():
    return render_template('SignUp.html',title='Sign Up',year=current_year)

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
    return render_template('logIn.html',title='Log In',year=current_year)

@app.route('/ClientFinePage.html')
def ClientFinePage():
    return render_template('ClientFinePage.html',title='Client Page',year=current_year)
