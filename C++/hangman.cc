#include <iostream>
#include <algorithm>
using namespace std;

string wordsList[] = {"hello", "panorama", "maximum", "shivam", "favorite", "programming"};//list of words the player guesses
char guessed[26];//array that stores all the characters that the player guessed
int stage = 0;//counts how many times the player enters a wrong character
int inList = 0;//where the player is in wordsList
int inG= 0;//where the player is in guessed
string hidden = wordsList[inList];//the word that the player has to guess
int numblanks; //numbers of unknown letters left

//draws the hangman depending on global variable stage
void drawMan(){
  if (stage==0){
    cout << "   ___ " <<'\n';
    cout << "  |   |" <<'\n';
    cout << "  |    " << '\n';
    cout << "  |    " <<'\n';
    cout << "  |    " << '\n';
    cout << "  |    " <<'\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
  else if (stage == 1){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |    " << '\n';
    cout << "  |    " << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
      }
  if (stage == 2){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |   |" << '\n';
    cout << "  |    " << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
  if (stage == 3){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |  /|" <<'\n'; 
    cout << "  |    " << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
  if (stage == 4){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |  /|\\" << '\n';
    cout << "  |    " << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
  if (stage == 5){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |  /|\\" << '\n';
    cout << "  |    \\" << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
  if (stage == 6){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |  /|\\" << '\n';
    cout << "  |  / \\ " << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
}

//if there is a character in guessed that equals a given char 
int findc(char in){  
    for(int i = 0; i < 26;i++)
    if(guessed[i]==in)
      return i;
  return -1;
}
	  
//returns false if the latest guess from the player is not in word hidden
bool indexChar(char a){
  numblanks=0;
  bool correct= false;
  guessed[inG]= a;//puts player input into guessed array
  inG++;
  for (int i = 0; i < hidden.length();i++){//prints spaces/letters depending if chars in guessed array= chars in hidden
    if (findc(hidden.at(i))!= -1){
      cout <<guessed[findc(hidden.at(i))] <<" ";
      if(guessed[inG-1]==hidden.at(i))//if the latest input is in hidden, function returns true
	correct= true;
    }
    else
      {
	cout <<"_ ";
	numblanks++;
      }
  }
  cout << "\n";
  return correct;
}


//prints letters guessed by the player
void printGuessed(){
  cout << "Guessed letters: ";
  for (int i = 0; i < 26;i++)
    if(guessed[i] != '!')
    cout << guessed[i]<< ' ';
  cout << '\n';
}


//gets a char from the player
char queryLetter(){
  printGuessed();
  char let;
  while(true){
  cout << "Enter a letter or multiple letters for multiple guesses: ";
  cin >>let;
  bool repeated = false;
    for(int i = 0; i < 26;i++)
      if(guessed[i]==let)
	repeated = true;
    if(isalpha(let) && !cin.fail() && repeated == false)
      break;   
     cout<< "invalid input: not a char or letter is already guessed"<< '\n';
  }
  cout << "\n";
  let=tolower(let);
  return let;
}

//Introduces player to game, prints hangman and blank spaces
//game is in a while loop that stop once the player gets 6 guesses wrong or gets the word correct
int main(){
  cout << "Welcome to hangman" << endl;
  drawMan();
  cout <<"Would you like to use your own word? ";
  string ownWord;
  cin >> ownWord;
  transform(ownWord.begin(), ownWord.end(), ownWord.begin(), ::tolower);
  
  if(ownWord.find("y")!= -1 )
    while(true){
      inList--;
    cout <<"Enter your word "<< '\n';
    cin>> hidden;
    cin.clear();
    cin.ignore(10000, '\n');
    if (string::npos != hidden.find_first_of("0123456789")){
      cout << "invalid input"<< '\n';
    }
    else { 
	transform(hidden.begin(), hidden.end(), hidden.begin(), ::tolower);
      break;
     }
    }
  indexChar('?');
  fill(guessed, guessed+26, '!');
  
  while(stage != 6){
    bool inhidden =indexChar(queryLetter()); 
    if (inhidden == false){
      stage++;
      drawMan();
    }
    else{
      drawMan();
      
    }
    if(numblanks == 0){
      cout << "You got the word correct!"<< '\n';
      stage =6;
    }
    else if(stage == 6 && numblanks > 0)
      cout << "Sorry, you lost, the word was " << hidden<< '\n';
    
    if(stage == 6){//after the game ends, player has the option to restart with a different word or stop playing
      while(true){
	cout << "would you like to play again for a given word, enter yes or no: "<< '\n';
	string again;
      cin >> again;
      
       if (again == "yes" && inList != 5){
	inList++;
	hidden=wordsList[inList];
	inG = 0;
	fill(guessed, guessed+26, '!');
	stage = 0;
	drawMan();
	indexChar('?');
	fill(guessed, guessed+26, '!');
	break;
        }
       else if (again == "yes" && inList == 5){
	cout <<"Sorry, no more words. That's all folks!" << '\n';
	break;
          }
      else if (again == "no"){
	cout << "Thanks for playing!"<<'\n';
	break;
      }
      else cout << "incorrect input" << '\n';
      }
     }
  
  }
}

