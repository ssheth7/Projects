#include <string>
#include <iostream>
using namespace std;
  string wordsList[] = {"hello", "panorama", "Maximum", "Shivam", "favorite"};
char guessed[26];
int stage = 0;
int inList = 0;
int inG= 0;
string hidden = wordsList[inList];


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
//returns guessed index=> print from guessed array
int findc(char in){
  for(int i = 0; i < 26;i++)
    if(guessed[i]==in)
      return i;
  return -1;
}
	  

bool indexChar(char a){
  bool correct= false;
  guessed[inG]= a;
  inG++;
  for (int i = 0; i < hidden.length();i++){
    if (findc(hidden.at(i))!= -1){
      cout <<guessed[findc(hidden.at(i))] <<" ";
      correct = true;
	}
    else
      {
	cout <<"_ ";
      }
  }
  cout << "\n";
  return correct;
}



char queryLetter(){
  cout << "Enter a letter";
  char let;
  cout << " ";
  cin >> let;
  cout << "\n";
  return let;
}


int main(){
  cout << "Welcome to hangman" << endl;
  drawMan(stage);
  indexChar('?');
  while(stage != 6){
    bool inhidden =indexChar(queryLetter()); 
    if (inhidden == false){
      stage++;
      drawMan(stage);
    }
    else drawMan(stage);
    if(stage == 6){
      while(true){
	cout << "would you like to play again, enter yes or no: ";
	string again;
      cin >> again;
      if (again == "yes"){
	inList++;
	stage = 0;
        }
      else if (again == "no")
	cout << "Thanks for playing!";
      break;
      }
    }
  }
  
}
