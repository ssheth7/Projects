import pymongo, hashlib, re, tldextract, string, random
from pymongo import MongoClient
from bson.json_util import dumps, loads
def credtest(user,pwd, db):
    user=hash(user)
    pwd = hash(pwd)
    if (user not in db.list_collection_names()): return False
    myquery = { "password": pwd }
    col = db[user]
    if(col.find_one()["password"] == pwd): return True
    return False
def createAccount(client, Auser, Apass):
    client = pymongo.MongoClient(client)
    db=client.test
    if(hash(Auser) in db.list_collection_names()):
        return False
    if(9 > len(Apass)):
        return False
    elif(bool(re.match("^[a-zA-Z0-9_]*$", Apass)) == True):
        return False
    elif(bool(re.match("^[a-zA-Z_]*$", Apass) == True)):
        return False
    elif(bool(re.match("^[0-9_]*$", Apass) == True)):
        return False
    elif(Apass.__contains__(Auser)):
        return False
    else: 
        Apass = hash(Apass)
        Auser = hash(Auser)
        collection = db[Auser]
        passwordfile = {"password" : Apass}
        collection.insert_one(passwordfile) 
        return True
def hash(str):
    str = str.encode('utf 8')
    hash_object = hashlib.sha1(str)
    return hash_object.hexdigest()
def getDomain(url):
    if('http' and '.' not in url):
        return url
    info = tldextract.extract(url)
    return info.domain
def Getcredentials(domain, user, client):
    client = pymongo.MongoClient(client)
    db=client.test
    col = db[hash(user)]
    domain = getDomain(domain)
    for x in col.find({},{ "domain": 1, "username": 1, "password": 1 })[1:]:
            if(x["domain"] == domain): 
                print("Your credentials for this website already exists!")
                return x
    return False
def createWebCredentials(client, domain, domainuser, user, length):
    client = pymongo.MongoClient(client)
    db=client.test
    col = db[hash(user)]
    encrypted = ""
    domain = getDomain(domain)  
    invalidpass = True
    length = int(length)
    for x in col.find({},{ "domain": 1, "username": 1, "password": 1 })[1:]:
        if(x["domain"] == domain): 
            return x["password"]
    while(invalidpass):
        randomized = ''.join(random.choice(string.ascii_uppercase + string.digits + string.ascii_lowercase + string.punctuation) for _ in range(length))
        personalized = ''.join(random.choice(user) + random.choice(domainuser) for _ in range(length))
        encrypted += randomized[0:length/2] + personalized[length/2:length] 
        if(bool(re.match("^[a-zA-Z0-9_]*$", encrypted)) == False):
            print("Password does not have any special characters\n")
            pass
        if(bool(re.match("^[a-zA-Z_]*$", encrypted) == False)):
            print("Password does not have a number\n")
            pass
        if(bool(re.match("^[0-9_]*$", encrypted) == False)):
            print("Password does not have a letter\n")
            pass
        invalidpass = False
    webfile = {"domain" : domain, "username" : domainuser, "password": encrypted}
    col.insert_one(webfile)
    return encrypted