#include <iostream>
using namespace std;
  string wordsList[] = {"hello", "panorama", "Maximum", "Shivam", "favorite"};
int gamestatus = 0;
int stage = 0;
int inList = 0;
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
      gamestatus = -1;
  }
}
  void drawWord(char a){
    if(a = '?')
      for(int i = 0; i < hidden.length();i++){
	cout <<"_ ";
      }

	  
  }

int indexChar(char a, pos p){
  return hidden.find(a, p);
}

char queryLetter(){
  cout << "Enter a letter";
  char let;
  cin >> let;
  return let;
}


int main(){
  cout << "Welcome to hangman" << endl;
  while(gamestatus != -1){
    drawMan(stage);
    drawWord('?');
   
  }
  
}
