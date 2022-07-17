import java.util.*;
import java.io.*;
/*
 * 
 * Author: Hongyi Chen
 * 
 * */
public class TicTacToe2D 
{
	// Print the welcoming message before main to avoid reappearance of the message
	static 
	{ 
	    System.out.println("Welcome to the game of Tic-Tac-Toe!\n"); 
	} 
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Scanner scan1 = new Scanner(System.in);
		int numOfPlayers = 0;
		int size = 0;
		int winningSequence = 0;
		String [] symbols;
		String [][] moves = new String[9][9];	// Initialized for the quit()
		int nextPlayer = 0;
		
		//prompt and load game if answer is yes, resume game from file
		System.out.print("If you would like to resume a game, please enter \"Resume Game\"; \nIf you would like to start a new game, please enter \"New Game\";\nOr \"Quit\" to quit the game: ");
		String choice = scan1.nextLine();
		
		if(choice.equals("Quit"))
		{
			quit(numOfPlayers, size, winningSequence, moves);
			System.out.println("See you next time!");
			System.exit(0);
		}
		else if(choice.equals("New Game")||choice.equals("Resume Game"))
		{
			if(choice.equals("Resume Game"))
			{
				System.out.print("\nPlease enter the text file name you wish to resume from: ");
				String fileName = scan1.next();
				File ifile = new File(fileName);
				BufferedReader reader = new BufferedReader(new FileReader(ifile));
				String temp = null;
				
				//read first three lines for numOfPlayers, size and winning Sequence
				numOfPlayers = Integer.parseInt(reader.readLine());
				size = Integer.parseInt(reader.readLine());
				winningSequence = Integer.parseInt(reader.readLine());
				
				//Assign symbols to players
				symbols = new String[numOfPlayers];
				symbols[0] = "X";
				symbols[1] = "O";
				int i = 2;
				//Assign capital letters to players other than the first two, if existed
				if(numOfPlayers > 2)
				{
					String temp1="";
					//Convert and assign the letters
					for(i = 2; i < numOfPlayers; i++)
					{
						// Get the next letter from the alphabet
						temp1 += i;
						byte [] b = temp1.getBytes();
						int j = 0;		//to avoid the reselection of 'X' and 'O'
						if((char)(b[0]+15)!='X'||(char)(b[0]+15)!='O')
						{
							symbols[i] = String.valueOf((char)(b[0]+15+j));
						}
						else		//the next number to be assigned is 'X' or 'O'
						{
							j++;
							symbols[i] = String.valueOf((char)(b[0]+15+j));
						}
						temp1 = "";
						//System.out.println(symbols[i]+"!!!");
					}
				}
				i++;
				// Read the moves into moves[][]
				moves = new String[size][size];
				
				int e = 0;
				String line ="";
				while((line = reader.readLine()) != null){

					String[] temp1 = line.split(",");

					for(int f = 0; f < size; f++)
					{
						moves[e][f] = temp1[f];
					}
					System.out.println("\n");
					e++;
					}
				
				// Check who's the next player to make the move:
				int z = 0;
				for(int c = 0; c < moves.length; c++)
				{
					for(int d = 0; d < moves[0].length; d++)
					{
						if(!moves[c][d].equals("0"))
						{
							z++;
						}
					}
				}
				nextPlayer = z % numOfPlayers;
			}
			else		//New Game
			{
				System.out.println();
				//Ask how many players
				boolean whileCondition1 = false;
				while(!whileCondition1)
				{
					System.out.print("Please enter the number of players that are playing: ");
					while(!scan1.hasNextInt())
					{
						System.out.print("Invalid number of players! Please re-enter the number of players: ");
						choice = scan1.next();
					}
					
					numOfPlayers = Integer.parseInt(scan1.next());
			
					//check if the number is greater than 26
					if(numOfPlayers > 26)
					{
						System.out.println("Too many players! Please try again.");
					}
					else if(numOfPlayers < 2)
					{
						System.out.println("Too less players! Please try again.");
					}
					else
					{
						whileCondition1 = true;
					}
				}
				
				//create symbols for players
				symbols = new String[numOfPlayers];
				symbols[0] = "X";
				symbols[1] = "O";
				
				//Assign capital letters to players other than the first two, if existed
				if(numOfPlayers > 2)
				{
					String temp="";
					//Convert and assign the letters
					for(int i = 2; i < numOfPlayers; i++)
					{	
						temp += i;
						byte [] b = temp.getBytes();
						int j = 0;		//to avoid the reselection of 'X' and 'O'
						if((char)(b[0]+15)!='X'||(char)(b[0]+15)!='O')
						{
							symbols[i] = String.valueOf((char)(b[0]+15+j));
						}
						else		//the next number to be assigned is 'X' or 'O'
						{
							j++;
							symbols[i] = String.valueOf((char)(b[0]+15+j));
						}
						temp = "";
						//System.out.println(symbols[i]+"!!!");
					}
				}
				//prompt for the size of the board
				boolean whileCondition2 = false;
		
				while(!whileCondition2)
				{
					System.out.print("Please enter the size of the board: ");
					
					while(!scan1.hasNextInt())
					{
						System.out.print("Invalid size! Please re-enter the size(>2) of the board: ");
						choice = scan1.next();
					}
					size = Integer.parseInt(scan1.next());
					
					//Make sure size is between 0 and 19
					if(size * size < numOfPlayers)
					{
						System.out.println("Board size too small! Please try again.");
					}
					else if(size > 1 && size <= 19)
					{
						whileCondition2 = true;	
					}
					else
					{
						System.out.println("Board size too large! Please try again.");
					}
				}
				//prompt the user for the winning sequence
				boolean whileCondition3 = false;
				
				while(!whileCondition3)
				{
					System.out.print("Please enter the winning sequence of the game: ");

					while(!scan1.hasNextInt())
					{
						System.out.print("Invalid size! Please re-enter the winning sequence of the game: ");
						choice = scan1.next();
					}
					winningSequence = Integer.parseInt(scan1.next());
					
					if(winningSequence > size)
					{
						System.out.println("Winning sequence selected is larger than the size of the board. Please try again.");
					}
					else if(((winningSequence-1)*numOfPlayers+1)>(size*size))	//first check if winning is possible(if too many players are playing)
					{
						System.out.println("Winning not possible! Please enter a smaller winning sequence.");	
					}
					else
					{
						whileCondition3 = true;
					}
				}
				//print the board
				printBoard(size);
				
				moves = new String[size][size];
				//initialize the moves[]
				for(int g = 0; g < size;g++)
				{
					for(int h =0; h < size; h++)
					{
						moves[g][h] = "0";
					}
				}
			}		
	//Ask for moves from both players
			Scanner scan2 = new Scanner(System.in);
			int x = 0;
			int y = 0;
			boolean endWhile = false;
			String temp = "";
			
			int totalSize = size*size;		//counter for the game
			while(!endWhile)
			{
				for(int l = nextPlayer; l < numOfPlayers; l++)
				{
					updateBoard(size, moves);
					if(totalSize>0)
					{
						System.out.print("Player "+(l+1) + ", please enter the row and column numbers for your next move, \nseperate by a space, or enter \"Quit\" to save and quit the game: ");
						// Non-coordinate input
						if(!scan2.hasNextInt())
						{
							temp = scan2.next();
							// Save & Quit the game
							if(temp.equals("Quit"))
							{
								quit(numOfPlayers, size, winningSequence, moves);
							}
							// Invalid input
							else
							{ 
								System.out.println("Invalid input for row number! Please try again.");
								System.out.print("Player "+(l+1) + ", please enter the row and column numbers for your next move, \nseperate by a space, or enter \"Quit\" to save and quit the game: ");
							}
						}
						// first input is valid
						else 
						{
							temp = scan2.next();
							x = Integer.parseInt(temp)-1;
							while(!scan2.hasNextInt())
							{
								System.out.println("Invalid input for column number! Please try again.");
								System.out.print("Player "+(l+1) + ", please enter column numbers for your next move, or enter \"Quit\" to save and quit the game: ");
							}
							temp = scan2.next();
							
							y = Integer.parseInt(temp)-1;
							
							boolean whileCondition5 = false;	//Check if the input row & column number are valid
							// Check if the input is larger than the board size
							while(!whileCondition5)
							{
								if(x >= size)
								{
									System.out.print("Row number too large. Please try again.\nEnter the row and column numbers for your next move, seperate by a space: ");
									temp = scan2.next();
									x = Integer.parseInt(temp) - 1;
									temp = scan2.next();
									y = Integer.parseInt(temp) - 1;
								}
								else if(y >= size)
								{
									System.out.print("Column number too large. Please try again.\nEnter the row and column numbers for your next move, seperate by a space: ");
									temp = scan2.next();
									x = Integer.parseInt(temp) - 1;
									temp = scan2.next();
									y = Integer.parseInt(temp) - 1;
									System.out.println(x+" !!! "+y);
								}
								else 
								{
									whileCondition5 = true;
								}
							}
							// Make sure the called space is not occupyoed by other piece
							boolean whileCondition4 = false;
							
							while(!whileCondition4)
							{
								if(!moves[x][y].equals("0"))
								{
									System.out.print("That space has been taken. Please try again.\nEnter the row and column numbers for your next move, seperate by a space: ");
									temp = scan2.next();
									x = Integer.parseInt(temp) - 1;
									temp = scan2.next();
									y = Integer.parseInt(temp) - 1;
								}
								else
								{
									moves[x][y] = symbols[l]; 
									whileCondition4 = true;
								}
							}
							updateBoard(size, moves);
							
							//check if the current player wins
							if(checkWinning(moves, winningSequence,size,symbols[l]))
							{
								System.out.print("Player "+(l+1)+" wins!!"+" \nPress \"N\" to start the next game, or \"Q\" to quit the game: ");
								String temp1 = scan1.next();
								System.out.println("");
								if(temp1.equals("N"))
								{
									main(args);
								}
								else if(temp1.equals("Q"))
								{
									System.out.println("See you next time!");
									System.exit(0);
								}
								endWhile = true;
								l = numOfPlayers;
							}
						}
					totalSize--;
					}
					else
					{
						if(!checkWinning(moves, winningSequence, totalSize, symbols[l]))
						{
							System.out.println("All spaces are taken. Game ties.");
							System.out.println("Program restarting...\n");
							endWhile = true;
							main(args);
						}
					}
				}
			}
			}//End of else if
			else
			{
				System.out.println("Wrong input! Please try again.\n");
				main(args);
			}
	}	
	public static void quit(int numOfPlayers, int size, int winningSequence, String [][] moves) throws FileNotFoundException, IOException
	{
		Byte [] b = new Byte[2048];
		System.out.print("Please enter the name of the txt file you wish to store the game in, or \"quit\" to quit directly: ");
		Scanner scan1 = new Scanner(System.in);
		boolean whileCondition6 = false;
		String response = scan1.nextLine();
		while(!whileCondition6)
		{
			if(response.equals("Quit") || response.equals("quit"))
			{
				System.out.println("Goodbye!");
				System.exit(0);
			}
			else if(!response.substring(response.length()-4).equals(".txt"))
			{
				System.out.print("Invalid input! Please re-enter the name of the txt file you wish to store the game in, or \"quit\" to quit directly: ");
				response = scan1.nextLine();
			}
			else
			{
				FileWriter writeFile = null;
				try 
				{
					File ifile = new File(response);//文件保存路径
				if(!ifile.exists())
				{
					ifile.createNewFile();
				}
				writeFile = new FileWriter(ifile);

				writeFile.write(numOfPlayers + "\n");
				System.out.println("Writing numOfPlayers " + numOfPlayers);
				writeFile.write(size + "\n");
				System.out.println("Writing size "+size);
				writeFile.write(winningSequence + "\n");
				System.out.println("Writing winningSequence " + winningSequence);
				
				for(int f = 0; f < moves.length; f++)
				{
					for(int g = 0; g < moves[0].length - 1; g++)
					{
						writeFile.write(moves[f][g] + ",");
						System.out.println("Writing moves["+f+"][" +g+"]: "+moves[f][g]);
					}
					
					writeFile.write(moves[f][moves[0].length - 1]);
					writeFile.write("\n");
				}
				writeFile.flush();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally 
			{
				try
				{
					if(writeFile != null)
					{
						writeFile.close();
					}
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
				System.out.println("Game saved. Goodbye!");
				System.exit(0);
			}
		}
	}
	public static boolean checkWinning(String [][] moves, int winningSequence, int size, String symbol)	//再改symbol判断
	{
		boolean won = false;
		int num0 = 0;	//counter for winning in rows 
		int num1 = 0;	//counter for winning in columns
		int num2 = 0;	//counter for winning in left to right diagonals
		int num3 = 0;	//counter for winning in right to left diagonals
		
		// Starting from the top left
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				/*
				 * Check for winning in rows
				*/
				if(j + winningSequence <= size)	// To make sure the starting tile of the sequence is still winnable
				{
					// Begins the winning sequence checking
					if(moves[i][j].equals(symbol))	
					{
						num0 = 1;
						// check if the space [i][j] is the begining of a winning sequence
						for(int m = 1; m < winningSequence; m++)
						{
							if(moves[i][j+m].equals(symbol))
							{
								num0++;
							}
						}
						// Check if the current sequence is long enough
						if(num0 >= winningSequence)
						{
							won = true;
						}
					}
				}
				/*
				 * Check for winning in columns
				*/
				if(i + winningSequence <= size)	
				{
					// Begins the winning sequence checking
					if(moves[i][j].equals(symbol))	
					{
						num1 =1;
						// check if the space [i][j] is the begining of a winning sequence
						for(int m = 1; m < winningSequence; m++)
						{
							if(moves[i+m][j].equals(symbol))
							{
								num1++;
							}
						}
						// Check if the current sequence is long enough
						if(num1 >= winningSequence)
						{
							won = true;
						}
					}
				}
				/*
				 * Check for winning in all left to right diagonals 
				*/
				if(i + winningSequence <= size && j > winningSequence)	
				{
					// Begins the winning sequence checking
					if(moves[i][j].equals(symbol))	
					{
						num2 =1;
						// check if the space [i][j] is the begining of a winning sequence
						for(int m = 1; m < winningSequence; m++)
						{
							if(moves[i+m][j-m].equals(symbol))
							{
								num2++;
							}
						}
						// Check if the current sequence is long enough
						if(num2 >= winningSequence)
						{
							won = true;
						}
					}
				}
				/*
				 * Check for winning in all right to left diagonals 
				*/
				if(i + winningSequence <= size && j + winningSequence <= size)	
				{
					// Begins the winning sequence checking
					if(moves[i][j].equals(symbol))	
					{
						num2 =1;
						// check if the space [i][j] is the begining of a winning sequence
						for(int m = 1; m < winningSequence; m++)
						{
							if(moves[i+m][j+m].equals(symbol))
							{
								num2++;
							}
						}
						// Check if the current sequence is long enough
						if(num2 >= winningSequence)
						{
							won = true;
						}
					}
				}
			}
		}
		//reset the counter
		num0 = 0;
		num1 = 0;
		return won;
	}
	//initialize the board
	public static void printBoard(int size)
	{
		//create Strings of places to store moves
		String [] symbolPlaces =new String[size*size]; 
		//initialize the places
		for(int i = 0;i < size*size; i++)
		{
			symbolPlaces[i]=" ";
		}
		//line 1
		System.out.print("    ");
		for(int i = 1; i <= size; i++)
		{
			System.out.print(i+"   ");
		}
		System.out.println();
		
		//create underscore line
		String underscore = "";
		for(int j = 1; j < size; j++)
		{
			underscore +="+---";
		}
		//rest of the lines
		for(int i = 0; i < size; i++)
		{
			System.out.print((i+1)+"  ");
			for(int k =0; k< size-1; k++)
			{
				System.out.print(" "+symbolPlaces[i]+" |");
			}
			System.out.print(" "+symbolPlaces[size-1]);
			System.out.println("");	
			if(i!=(size-1))
			{
				System.out.println("   "+"---"+underscore);
			}
		}
	}
	public static void updateBoard(int size, String [][] moves)
	{
		System.out.println("Updated board: ");
		//create Strings of places to store moves
				String [][] symbolPlaces =new String[size][size]; 
				//initialize the places
				for(int i = 0;i < size; i++)
				{
					for(int j = 0; j < size; j++)
						symbolPlaces[i][j]=moves[i][j];
					//System.out.println(symbolPlaces[i]);
				}
				
				//line 1
				System.out.print("    ");
				for(int i = 1; i <= size; i++)
				{
					System.out.print(i+"   ");
				}
				System.out.println();
				
				//create underscore line
				String underscore = "";
				for(int j = 1; j < size; j++)
				{
					underscore +="+---";
				}
				
				//rest of the lines
				int k_total = 0;
				String temp = " ";
				for(int i = 0; i < size; i++)
				{
					k_total = 0;
					System.out.print((i+1)+"  ");
					for(int k =0; k< size-1; k++)
					{
						if(symbolPlaces[i][k].equals("0"))
						{
							System.out.print(" "+ temp +" |");
						}
						else
						{
							System.out.print(" "+symbolPlaces[i][k]+" |");
							k_total++;
						}
					}
					
					if(symbolPlaces[i][size - 1].equals("0"))
					{
						System.out.print(" "+ temp);
					}
					else
					{
						System.out.print(" "+symbolPlaces[i][size - 1]);
						k_total++;
					}
					System.out.println("");	
					if(i!=(size-1))
					{
						System.out.println("   "+"---"+underscore);
					}
				}
	}
}