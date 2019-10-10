

#include <iostream>
using namespace std;

string wordsList[] = {"hello", "panorama", "Maximum", "Shivam", "favorite"};//list of words for the player to choose from
char guessed[26];//array that stores all the characters that the player guessed
int stage = 0;//counts how many times the player enters a wrong character
int inList = 0;//where the player is in wordsList
int inG= 0;//where the player is in guessed
string hidden = wordsList[inList];//the word that the player has to guess

//draws the hangman depending on variable stage
//change level to stage?
void drawMan(int level){
  if (level == 0){
    cout << "   ___ " <<'\n';
    cout << "  |   |" <<'\n';
    cout << "  |    " << '\n';
    cout << "  |    " <<'\n';
    cout << "  |    " << '\n';
    cout << "  |    " <<'\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
  else if (level == 1){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |    " << '\n';
    cout << "  |    " << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
      }
  if (level == 2){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |   |" << '\n';
    cout << "  |    " << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
  if (level == 3){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |  /|" <<'\n'; 
    cout << "  |    " << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
  if (level == 4){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |  /|\\" << '\n';
    cout << "  |    " << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
  if (level == 5){
    cout << "   ___ " <<'\n';
    cout << "  |   |" << '\n';
    cout << "  |   o" << '\n';
    cout << "  |  /|\\" << '\n';
    cout << "  |    \\" << '\n';
    cout << "  |    " << '\n';
    cout << " _|___ " << '\n';
    cout << "|_____|" << '\n';
  }
  if (level == 6){
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
  bool correct= false;
  guessed[inG]= a;
  inG++;
  for (int i = 0; i < hidden.length();i++){
    if (findc(hidden.at(i))!= -1){
      cout <<guessed[findc(hidden.at(i))] <<" ";
      if(guessed[inG-1]==hidden.at(i))
	correct= true;

    }
    else
      {
	cout <<"_ ";
      }
  }
  cout << "\n";
  return correct;
}


//gets a char from the player
char queryLetter(){
  cout << "Enter a letter";
  char let;
  cout << " ";
  cin >> let;
  cout << "\n";
  return let;
}

//Introduces player to game, prints hangman and blank spaces
//game is in a while loop that stop once the player gets 6 guesses wrong or gets the word correct
int main(){
  cout << "Welcome to hangman" << endl;
  drawMan(stage);
  indexChar('?');


  
  int ncorrect = 0;
  while(stage != 6){
    bool inhidden =indexChar(queryLetter()); 
    if (inhidden == false){
      stage++;
      drawMan(stage);
    }
    else{
      drawMan(stage);
      ncorrect++;
    }
    if(ncorrect == hidden.length()){
      cout << "You got the word correct!";
      stage =6;
    }
    
    if(stage == 6){//after the game ends, player has the option to restart with a different word or stop playing
      while(true){
	cout << "would you like to play again, enter yes or no: "<< '\n';
	string again;
      cin >> again;
      if (again == "yes"){
	inList++;
	hidden=wordsList[inList];
	ncorrect = 0;
	inG = 0;
	fill(guessed, guessed+16, '!');
	stage = 0;
	drawMan(stage);
	indexChar('?');
	break;
        }
      else if (again == "no"){
	cout << "Thanks for playing!";
	break;
      }
      else cout << "incorrect input" << '\n';
      }
     }
  
  }
}
