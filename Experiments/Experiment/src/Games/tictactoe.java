package Games;


//three things, make board functions
//set win conditions
//make player funtions

public class tictactoe {
		
		private char[][] board;
		private char currentPlayerMark;
		
		public void TicTacToe(){
			 board = new char[3][3];
			 currentPlayerMark = 'x';
			 initializeBoard();
		}
		
		//resets board to empty values
		public void initializeBoard(){
			
			for (int i = 0; i < 3; i++) {
				
				for (int j = 0; j < 3; j++) {
					board[i][j] = '-';
				}
			}

		}
		
		public void printBoard(){
			System.out.println();
		}
		
		 
		
}
