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

	  

bool indexChar(char a){
  bool correct;
  for (int i = 0; i < hidden.length();i++){
    if(hidden.at(i) == a){
      cout <<a <<" ";
      correct = true;
    }
    else
      {
	cout <<"_ ";
	if(correct != true)
	  correct = false;
      }
  }
  cout << "\n";
  
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
    if (stage>0)
      drawMan(stage);
    if (indexChar(queryLetter())== false)
      stage++;   
  }
  
}
