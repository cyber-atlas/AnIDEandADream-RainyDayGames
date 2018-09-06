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
		
		//printing the board layout
		public void printBoard(){
			System.out.println("-------------");
			
			for (int i = 0; i < 3; i++){
				System.out.println("| ");
				
				for (int j = 0; j < 3; j++){
					System.out.println(board[i][j] + " | ");
				}
				System.out.println();
				System.out.println("-------------");
			}
		}
		
		//checking if the board has any empty squares
		public boolean isBoardFull(){
			boolean isFull = true;
			
			for(int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					if (board[i][j] = '-'){
						isFull = false; 
					}
				}
			}
			return isFull;
			
		}
		
		//check if the player has won
		public boolean checkforWin(){
			return (checkRowsforWin() || checkColumnforWin() || checkDiagonalforWin());
		}
		
		
		
		 
		
}
