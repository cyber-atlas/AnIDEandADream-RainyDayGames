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
		
		private boolean checkRowsforWin(){
			for (int i = 0; i < 3 i++){
				if(checkRowCol(board[i][0], board [i][1], board[i][2]) == true){
					return true;
			
				}
			}
			return false;
		}
		
		private boolean checkColumnforWin(){
			for (int i = 0; i < 3; i++){
				if(checkRowCol(board[0][i], board[1][i], board[2][1]) == true){
					return true;
				}
			}
			return false;
		}
		
		private boolean checkDiagonalforWin(){
			return ((checkRowCol(board[0][0], board[1][1], board[2][2]) ||
					checkRowCol(board[0][2], board[1][1], board[2][0]));
		}
		 
		private boolean checkRowCol(char c1, char c2, char c3){
			return ((c1 != '-') && (c1 == c2) && (c1 == c3));	
		}
		
		public void changePlayer(){
			if(currentPlayerMark == 'x'){
				currentPlayerMark = 'o';
			}
			else{
				currentPlayerMark = 'x';
			}
		}
		
		public void placeMark(int row, int col){
			if(row >= 0 && row < 3){
				if(col >= 0 && col < 3){
					if (board[row][col] == '-'){
						board[row][col] = currentPlayerMark;
						
					}
				}
				
			}
		}
		
		
}
