package caveExplorer;

public class TwoDArrayPractice {

	
	
	public static void main(String[] args) {
		String[][] pic = new String[6][10];
		//Loops through the array and gives each item a default value
		for(int row = 0; row < pic.length; row++) {
			for(int col = 0; col < pic[0].length; col++) {
				pic[row][col] = " ";
			}
		}
		/*
		drawHorizontalLine(pic, 0);
		drawHorizontalLine(pic, 5);
		drawVerticalLine(pic, 0);
		drawVerticalLine(pic, 9);
		drawSlot(pic, 4, 5);
	    */
		drawBox(pic, 4, 5);
		print(pic);
	}

	/**
	 * A box looks like this:
	 * 		___
	 * 		|*|	
	 * 		|_|
	 * CATCH: No ArrayIndexOutOfBoundsExeception
	 * Draw any portion that does fit on the canvas
	 * @param pic - The picture 2D array that you want the box in
	 * @param i - The "y"/row coordinate of the space in the 2D array
	 * @param j - The "x"/column coordinate of the space in the 2D array
	 */
	private static void drawBox(String[][] pic, int i, int j) {
		drawSlot(pic, i, j);
		drawSlot(pic, i+1, j);
		drawIfInBounds("_", pic, i+1, j);
		/*
		if(i + 1 < pic.length && i >= 0 && j >= 0 && j < pic[i].length) {
			pic[i+1][j] = "_";
		}
		*/
		for(int col = j - 1; col <= j + 1; col++) {
			drawIfInBounds("_", pic, i-1, col);
		}
	}
	 
	/**
	 * Draws the specified object into the array if the coordinates exist 
	 * @param string - The value you want to put into the array at the given coordinate
	 * @param pic - The picture 2D array that you want the string in
	 * @param i - The "y"/row coordinate of the space in the 2D array
	 * @param j - The "x"/column coordinate of the space in the 2D array
	 */
	private static void drawIfInBounds(String string, String[][] pic, int i, int j) {
		//SPECIAL NOTE: Always check row before column
		if(i >= 0 && i < pic.length && j >= 0 && j < pic[i].length) {
			pic[i][j] = string;
		}
	}

	/**
	 * A slot looks like this: 
	 * 		| |
	 * It is two vertical lines with a space between them
	 * The coordinates, i, j are the coordinates of the space
	 * In other words, a slot is a vertical line in front of i,j and after i,j
	 * CATCH: No ArrayIndexOutOfBoundExceptions
	 * @param pic - The picture 2D array that you want to create a slot in
	 * @param i - The "y"/row coordinate of the space in the 2D array
	 * @param j - The "x"/column coordinate of the space in the 2D array
	 */
	private static void drawSlot(String[][] pic, int i, int j) {
		if(i >= 0 && i < pic.length) {
			if(j > 0) {
				pic[i][j - 1] = "|";
			}
			if(j + 1 < pic[i].length) {
				pic[i][j + 1] = "|";
			}	
		}
	}

	/**
	 * A method that creates a vertical line at a given column of the given 2D array
	 * @param pic - The picture 2D array that you want to create a vertical line in
	 * @param col - The column number that you want to have a vertical line in
	 */
	private static void drawVerticalLine(String[][] pic, int col) {
		for(int row = 0; row < pic.length; row++) {
			pic[row][col] = "|";
		}
	}

	/**
	 * A method that creates a horizontal line in a given column of a 2D array
	 * @param pic - The picture 2D array that you want to create a horizontal line in
	 * @param row - The row number that you want to be a horizontal line
	 */
	private static void drawHorizontalLine(String[][] pic, int row) {
		for(int col = 0; col < pic[row].length; col++) {
			pic[row][col] = "-";
		}
	}

	/**
	 * A method takes a 2D array and prints the array without any brackets
	 * or commas, only the values. 
	 * @param pic - A 2D array with values that creates a picture when printed  
	 */
	public static void print(String[][] pic) {
		for(int row = 0; row < pic.length; row++) {
			for(int col = 0; col < pic[0].length; col++) {
				System.out.print(pic[row][col]);
			}
			System.out.println("");
		}
		/*
		 * Possible Classmate's Solution -
		 * Declare and Define - String line = "";
		 * Define one line of the row and at the end of each row add a "\n" to the String
		 * Just print out the line at the end of the for loops 
		 */
	}
}
