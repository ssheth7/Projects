import pymongo, hashlib, re, tldextract, string, random
from pymongo import MongoClient
global client, db, currentuser

class User():
    def setPassword(self, password):
        self.password = password
    def setUsername(self, username):
        self.username = username
    def __init__(self):
        self.username = "default"
        self.password = "default"
    def searchExisting(self, domain):
        col = db[self.username]
        for x in col.find({},{ "domain": 1, "username": 1, "password": 1 })[1:]:
            if(x["domain"] == domain): 
                print("Your credentials for this website already exists!")
                print("Your username for " + domain + " is " + x["username"]) 
                print("Your password for " + domain + " is " + x["password"])
                return True
        return False
    def encrypt(self, domainuser, length):
        encrypted = ""
        invalidpass = True
        
        while(invalidpass):
            randomized = ''.join(random.choice(string.ascii_uppercase + string.digits + string.ascii_lowercase + string.punctuation) for _ in range(length))
            personalized = ''.join(random.choice(self.username) + random.choice(domainuser) for _ in range(length))
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
        print(len(encrypted))
        return encrypted
def Intro():
    invaliduser = True
    while(True):
        username = raw_input("Welcome to your password manager, enter your username. It must be at least 5 characters: \n")
        if(len(username) >= 5):
            if(hash(username) in db.list_collection_names()):
                login =raw_input("This username already exists, Is this yours?")
                if(login.__contains__("y")):
                    Login(hash(username))
                    return 
            else: pass
        else: print("Username not long enough!")
    invalidpass = True
    print("Here are the requirements for the master password:\n")
    print("1. A length between 9 and 15 characters, inclusive\n")
    print("2. Atleast one special character\n")
    print("3. Needs to be alphanumeric\n")
    print("4. Password cannot match your username\n")
    while(invalidpass):
        flags = False
        Accountpass = raw_input("Enter your password: ")
        if(9 > len(Accountpass)):
            flags = True
            print("Password is too short\n")
        if(len(Accountpass) > 15): 
            flags = True
            print("Password is too long\n")
        if(bool(re.match("^[a-zA-Z0-9_]*$", Accountpass)) == True):
            flags = True
            print("Password does not have any special characters\n")
        if(bool(re.match("^[a-zA-Z_]*$", Accountpass) == True)):
            flags = True
            print("Password does not have a number\n")
        if(bool(re.match("^[0-9_]*$", Accountpass) == True)):
            flags = True
            print("Password does not have a letter\n")
        if(Accountpass.__contains__(username)):
            flags = True
            print("Password cannot contain your username\n")
        if(flags == False): 
            invalidpass = False
            Accountpass = hash(Accountpass)
            username = hash(username)
            
            currentuser.setPassword(Accountpass)
            currentuser.setUsername(username)

            print("Connected to database successfully")
            collection = db[currentuser.username]
            passwordfile = {"password" : Accountpass}
            print(currentuser.username + " " + currentuser.password)
            raw_input()
            collection.insert_one(passwordfile)                            
            invalidpass = True
    


def hash(str):
    hash_object = hashlib.sha1(str)
    return hash_object.hexdigest()

def Login(create):
    validuser = False
    if(create != None):
        username = create
        validuser = True
    else:
        username = raw_input("What is your username? ")
        while(validuser == False):
            username = hash(username)
            if (username not in db.list_collection_names()):
                username = raw_input("This username does not exist, type 'create' to create an account or enter a valid username: ")
                if("create" in username):
                    Intro()
                    return
            else: validuser = True
    password = raw_input("Enter the password associated with this account: ")
    while(validuser):
        password = hash(password)
        myquery = { "password": password }
        col = db[username]
        if(col.find_one()["password"] == password):
            currentuser.setPassword(password)
            currentuser.setUsername(username)
            return
        else:
            password = raw_input("Wrong password, try again or type 'create' to create an account")
            if("create" in password):
                Intro()
                return

        
def getDomain(url):
    if('http' and '.' not in url):
        return url
    info = tldextract.extract(url)
    return info.domain

if __name__ == '__main__':    
    currentuser = User()
    client = pymongo.MongoClient("mongodb+srv://username:<password>@cluster0-w47ob.azure.mongodb.net/test?retryWrites=true&w=majority")
    db = client.test
    new = raw_input("Are you a returning user?")
    if("y" not in new):Intro()
    else: Login(None)
    print("Logged in successfully!")
    while(True):
        print("Here you can query or create a website credential")
        print("Type exit to quit")
        domain = getDomain(raw_input("What website would you like to create credentials for?\n"))
        if(domain == "exit"):
            exit()
        print(domain)
        if(currentuser.searchExisting(domain) == False):
            domainuser = raw_input("What is your username for this website? ")
            plength = input("What length would you like your password? ")
            domainpass = currentuser.encrypt(domainuser, plength)
            print("Your username for " + domain + " is " + domainuser) 
            print("Your password for " + domain + " is " + domainpass)
            webfile = {"domain" : domain, "username" : domainuser, "password": domainpass}
            col = db[currentuser.username]
            col.insert_one(webfile)
        
                  
        