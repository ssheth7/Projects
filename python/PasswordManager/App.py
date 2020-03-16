import PMBackend
from flask import Flask, render_template, request, redirect, url_for, session
import json, os
import pymongo, hashlib, re, tldextract, string, random
from pymongo import MongoClient

app = Flask(__name__)
app.secret_key = "any random string"
@app.route('/users/<name>')
def users(name):     
   return render_template('success.html', name = session['username']) 

@app.route('/create')
def Create():
   session['newUser'] = True  
   return render_template('accountForm.html')

@app.route('/users/<name>/credentials', methods = ['POST', 'GET'])
def showCredentials(name):
   if request.method == 'POST':
      domain = request.form['Domain']
      rdoc = PMBackend.Getcredentials(domain, session["username"], open('env.txt', 'r').readline())
      if(rdoc is False): return render_template('newCred.html', name = session['username'], domain = request.form['Domain'])
      else: return render_template('result.html', domain = rdoc['domain'], user = rdoc['username'], pwd = rdoc['password'] )

@app.route('/users/<name>/create<domain>Credentials', methods = ['POST', 'GET'])
def createCredentials(name, domain):
   duser = request.form['Duser']
   plength = request.form['plength']
   newpass = PMBackend.createWebCredentials(open('env.txt', 'r').readline(),domain, duser, name, plength)
   return render_template('result.html', domain = domain, user = duser, pwd = newpass)

@app.route('/')
def home():
   print ('set')
   session['newUser'] = "False"
   return render_template('home.html')

@app.route('/result')
def result():
      return PMBackend.credtest()
@app.route('/login', methods = ['POST', 'GET'])
def login():
   if session['newUser'] == "False" and request.method == 'POST':
      user = request.form['Username']
      pwd =  request.form['Password']
      client = pymongo.MongoClient(open('env.txt', 'r').readline())
      db = client.test 
      if(PMBackend.credtest(user, pwd, db)):
         session['username'] = user 
         return redirect(url_for('users', name = user)) 
      else:
         return render_template('home.html')
   else:
      session['username'] = request.form['Username']
      aPwd = request.form['Password']
      if(PMBackend.createAccount(open('env.txt', 'r'),session['username'], aPwd)== False):
         return render_template('accountForm.html')
      else: return redirect(url_for('users', name = session['username']))
if __name__ == '__main__':
   #session['newUser'] = 'False'
   app.run(debug = True)