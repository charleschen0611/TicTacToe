import java.util.*;
import java.io.*;

public class TicTacToe 
{

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Scanner scan1 = new Scanner(System.in);
		int numOfPlayers = 0;
		int size = 0;
		int winningSequence = 0;
        int firstTimePlaying = 1;
        int l1 = 0;         //counter for the player to be moved
		String [] symbols;
		String [] moves;
		//prompt and load game if answer is yes, resume game from file
		System.out.print("If you would like to resume a game, please enter \"Resume Game\"; If you would like to start a new game, please enter \"New Game\", or \"Quit\" to quit the game: ");
		String choice = scan1.nextLine();
		
		if(choice.charAt(0)=='Q'||choice.charAt(0)=='q')
		{
			System.out.println("See you next time!");
			System.exit(0);
		}
		else if(choice.charAt(0)=='N'||choice.charAt(0)=='n'||choice.charAt(0)=='R'||choice.charAt(0)=='r')
		{
			if(choice.charAt(0)=='R'||choice.charAt(0)=='r')
			{
				firstTimePlaying++;
				System.out.print("Please enter the text file name you wish to resume from: ");
				String fileName = scan1.next();
                File ifile = new File(fileName);
                InputStreamReader read = new InputStreamReader(new FileInputStream(ifile));
				BufferedReader reader = new BufferedReader(read);
				String temp = null;
				
				//read first three lines for numOfPlayers, size and winning Sequence
				numOfPlayers = Integer.parseInt(reader.readLine());
				size = Integer.parseInt(reader.readLine());
				winningSequence = Integer.parseInt(reader.readLine());
                l1 = Integer.parseInt(reader.readLine());
                moves = new String[size*size];
                
                for(int i = 0; i < moves.length; i++)
                {
                    
                    moves[i] = reader.readLine();
                    //System.out.println(moves[i]+"!!!!!");
                }

				//Assign symbols to players
				symbols = new String[numOfPlayers];
				symbols[0] = "X";
				symbols[1] = "O";
				
				//Assign capital letters to players other than the first two, if existed
				if(numOfPlayers > 2)
				{
					String temp1="";
					//Convert and assign the letters
					for(int i = 2; i < numOfPlayers; i++)
					{
						
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
				
				
				
				while((temp = reader.readLine()) != null)
				{
					printBoard(size, symbols);
				}
			}
			else		//New Game
			{
				//Ask how many players
				
				boolean whileCondition1 = false;
				while(!whileCondition1)
				{
					System.out.print("Please enter the number of players that are playing: ");
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
					System.out.print("Please enter the size of the borad: ");
					size = /*Integer.parseInt(*/scan1.nextInt()/*)*/;
					//Make sure size is between 0 and 999
					if(size > 0 && size <= 999)
					{
						whileCondition2 = true;
					}
					else
					{
						System.out.println("Invalid board size! Please try again.");
					}
				}
			
			
		//prompt the user for the winning sequence
		boolean whileCondition3 = false;
		
		while(!whileCondition3)
		{
			System.out.print("Please enter the winning sequence of the game:");
			winningSequence = scan1.nextInt();
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
		//initialize moves[]
		moves = new String[size*size];
			//initialize the moves[]
			for(int g = 0; g < size*size;g++)
			{
				moves[g] = " ";
			}
//print the board
		/* if(firstTimePlaying==1)
		{ */
			printBoard(size, symbols);
		
			
		//}	
		
		}
	
//Ask for moves from both players
		Scanner scan2 = new Scanner(System.in);
		
		int x = 0;
		int y = 0;
		boolean endWhile = false;
		int allTaken = 0;
		
		int totalSize = size*size;		//counter for the game
		while(!endWhile )
		{
			
			for(int l=l1; l < numOfPlayers; l++)
			{
				if(totalSize>0)
				{
				System.out.print("Player "+(l+1) + ", please enter the row and column numbers for your next move, seperate by a space, or enter \"Quit\" to save and quit the game: ");
				String temp = scan2.nextLine();
				//
				if(temp.charAt(0)=='Q'||temp.charAt(0)=='q')
				{
					Byte [] b = new Byte[2048];
					System.out.print("Please enter the file name you wish to store the game in: ");
					String fileName2 = scan1.next();
					FileOutputStream fos = new FileOutputStream(fileName2,false);
					//PrintWriter pw = new PrintWriter(fos);
					fos.write(String.valueOf(numOfPlayers).getBytes());
					//System.out.println("Writing numOfPlayers!! "+numOfPlayers);
                    fos.write('\n');
                    fos.write(String.valueOf(size).getBytes());
                    //System.out.println("Writing size "+size);
                    fos.write('\n');
					fos.write(String.valueOf(winningSequence).getBytes());
                    //System.out.println("Writing winningSequence "+winningSequence);
                    fos.write('\n');
                    fos.write(String.valueOf(l).getBytes());
                    //System.out.println("Writing player for next move "+l);
                    fos.write('\n');
					for(int f = 0; f < moves.length; f++)
					{
						//fos.write('\n');
                        fos.write(moves[f].getBytes());
                        fos.write('\n');
						//System.out.println("Writing moves["+f+"]: "+moves[f]);
                    }
                     
					fos.close();
					totalSize--;
					System.exit(0);
				}
				
				x = (int)temp.charAt(0)-48;
				y = (int)temp.charAt(2)-48;
				
				//System.out.println(x+" !!! "+y);
				
				boolean whileCondition4 = false;
				
				while(!whileCondition4)
				{
					if(!moves[size*(x-1)+(y-1)].equals(" "))
					{
						System.out.print("That space has been taken. Please try again.\nEnter the row and column numbers for your next move, seperate by a space: ");
						temp = scan2.nextLine();
						x = (int)temp.charAt(0)-48;
						y = (int)temp.charAt(2)-48;
					}
					else
					{
						moves[size*(x-1)+(y-1)] = symbols[l]; 
						whileCondition4 = true;
					}
				}
				//System.out.println("Test0: "+(4*(y-1)+(x-1))+"!!!!!");
				/*
				//Test1
				for(int g = 0; g < size*size;g++)
				{
					System.out.println(g+moves[g]+"!");
				}
				*/
				
				//update the board
				updateBoard(size, moves);
				
				
				
				//check if the game becomes unwinnable
				//System.out.println(symbols.length+"!!");
				
				if(!checkWinnable(moves, winningSequence,size,symbols[l]))
				{
					System.out.println("Check "+(l+1));
					System.out.print("Sorry. A tie occurred. The game becomes unwinnable. Press \"N\" to start a new game, or \"Q\" to quit the game: ");
					String temp1 = scan1.nextLine();
					if(temp1.charAt(0)=='N'||temp1.charAt(0)=='n')
					{
						args = null;
						main(args);
					}
					else if(temp1.charAt(0)=='Q'||temp1.charAt(0)=='q')
					{
						System.out.println("See you next time!");
						System.exit(0);
					}
					
				}
				
				//check if the current player wins
				if(checkWinning(moves, winningSequence,size,symbols[l]))
				{
					System.out.print("Player "+(l+1)+" wins!!"+" \nPress \"N\" to start over, or \"Q\" to quit the game: ");
					String temp1 = scan1.next();
					if(temp1.charAt(0)=='N'||temp1.charAt(0)=='n')
					{
						args = null;
						main(args);
					}
					else if(temp1.charAt(0)=='N'||temp1.charAt(0)=='n')
					{
						System.out.println("See you next time!");
						System.exit(0);
					}
					endWhile = true;
					l = numOfPlayers;
				}
				totalSize--;
				//System.out.println("Total size reduced to "+totalSize);
				}
				else
				{
					System.out.println("All spaces are taken. Game ties.");
					System.out.println("Program restarting...");
					endWhile = true;
					main(args);
				}
			}
		}
		}//End of else if
		else
		{
			System.out.println("Wrong input! Please try again.");
			//String [] args0 = new String[1];
			main(args);
		}
		//System.out.println("End Game~~");
		
	}

	
	public static boolean checkWinnable(String [] moves, int winningSequence, int size,String symbol)	
	{
		//System.out.println("Checking if the game is still winnable...");
		int num0 = 0;	//left diagonal
		int num0_1 = 0;	//empty spaces on left diagonal 
		int num1 = 0;	//right diagonal
		int num1_1 = 0;	//empty spaces on right diagonal 
		
		int [] num2_1 = new int[size];	//empty spaces on left
		int num2_1_1 = 0;	//total of the spaces on left
		int [] num2_2 = new int[size];	//empty spaces on right
		int num2_2_2 = 0;	//total of the spaces on right
		
		int [] num3_1 = new int[size];	//columns
		int num3_1_1 = 0;	//total of the spaces on left
		int [] num3_2 = new int[size];	//empty spaces on columns 
		int num3_2_2 = 0;	//total of the spaces on right
		//int num3 = 0;
		
		//convert moves[] to two dimension array
		String [][] updated2DMoves = new String[size][size];
		int w = 0;		//column number
		int r = 0;		//row number
		int y = 0;		
		
		//convert single dimension array to 2-D array
		while(w<size)
		{
			while(r<size)
			{
				updated2DMoves[w][r] = moves[y];
				y++;
				r++;
			}
			w++;
			r=0;
		}
		/*
		//Test
		for(int l = 0; l< size; l++)
		{
			for(int x=0; x<size; x++)
			{	
				System.out.println(updated2DMoves[l][x]);
			}
		}
		*/
		for(int i = 0; i< size; i++)		//column
		{
			for(int j = 0; j< size; j++)	//row
			{
				
					//left diagonal
					if(i==j)
					{
						
						if(updated2DMoves[i][j]==" ")		//space empty
						{
							num0_1++;
							//System.out.println("one more space on left diagnoal "+num0_1);
						}
						if(updated2DMoves[i][j]!=" " && updated2DMoves[i][j].equals(symbol))	//moves exists on that space
						{
							num0++;
							//System.out.println("num0(left diagonal) ++, "+num0);
						}
					}
					//right diagonal
					if(i==size-j-1)
					{
						if(updated2DMoves[i][size-1-j]==" ")	
						{
							num1_1++;
							//System.out.println("one more space on right diagnoal "+num1_1);
						}
						if(updated2DMoves[i][size-1-j]!=" " && updated2DMoves[i][size-1-i].equals(symbol))
						{
							num1++;
							//System.out.println("num1(right diagonal) ++, "+num1);
						}
					}
					//rows
					
					
					/*
					 * 
					 * 写到这了！！！num_1_1什么的还要再改！
					 * 
					 * */
					
					
					
					
					if(updated2DMoves[i][j]!=" " && updated2DMoves[i][j].equals(symbol))
					{
						//left of the move
						
						for(int d = 0; d < j; d++)
						{
							//System.out.println(j+" Test1 "+d);
							if(updated2DMoves[i][d]==" " || updated2DMoves[i][d].equals(symbol))
							{
								num2_1[i]++;
								//System.out.println("one more space of row on left "+num2_1[i]);
							}
						}
						
						//right of the move
						
						boolean whileCondition2 = false;
						int d = j+1;
						while(d<size && !whileCondition2)
						{
							if(updated2DMoves[i][d]==" ")
							{
								num2_2[i]++;
								//System.out.println("one more space of row on right "+num2_2[i]);
							}
							else
							{
								whileCondition2 = true;
							}
							d++;
						}
						
						
						
						
						//System.out.println("num2["+i+"] ++, "+num2[i]);
					}
					//columns
					if(updated2DMoves[j][i]!=null && updated2DMoves[j][i].equals(symbol))
					{
						num3_1[j]++;
						//System.out.println("num3["+i+"] ++, "+num3[i]);
					}
					
					
				
				
			}
		}
		
		//check if winnable
			//left diagonal
			if(num0 + num0_1 >= winningSequence)
			{
				num0 = 0;	//left diagonal
				num0_1 = 0;	//empty spaces on left diagonal 
				num1 = 0;	//right diagonal
				num1_1 = 0;	//empty spaces on right diagonal 
				
				num2_1 = new int[size];	//empty spaces on left
				num2_1_1 = 0;	//total of the spaces on left
				num2_2 = new int[size];	//empty spaces on right
				num2_2_2 = 0;	//total of the spaces on right
				
				num3_1 = new int[size];	//empty spaces on top
				num3_1_1 = 0; //total of the spaces on top
				num3_2 = new int[size];//empty space on bottom
				num3_2_2 = 0;//total of the space on bottom
				return true;
			}
			//right diagonal
			else if(num1 + num1_1 >= winningSequence)
			{
				num0 = 0;	//left diagonal
				num0_1 = 0;	//empty spaces on left diagonal 
				num1 = 0;	//right diagonal
				num1_1 = 0;	//empty spaces on right diagonal 
				
				num2_1 = new int[size];	//empty spaces on left
				num2_1_1 = 0;	//total of the spaces on left
				num2_2 = new int[size];	//empty spaces on right
				num2_2_2 = 0;	//total of the spaces on right
				
				num3_1 = new int[size];	//columns
				num3_1_1 = 0;
				num3_2 = new int[size]; 
				num3_2_2 = 0;
				
				return true;
			}
			//rows
			for(int h = 0; h < num2_1.length; h++)
			{
				num2_1_1++;
			}
			for(int h = 0; h < num2_2.length; h++)
			{
				num2_2_2++;
			}
			if((num2_1_1+num2_2_2) >= winningSequence)
			{
				return true;
			}
			//columns
			for(int h = 0; h < num3_1.length; h++)
			{
				num3_1_1++;
			}
			for(int h = 0; h < num3_2.length; h++)
			{
				num3_2_2++;
			}
			if((num3_1_1+num3_2_2) >= winningSequence)
			{
				return true;
			}			
			else
			{
				return false;
			}
	}
	
	
	
	
	public static boolean checkWinning(String [] moves, int winningSequence, int size,String symbol)	//再改symbol判断
	{
		int num0 = 0;	//left diagonal
		int num1 = 0;	//right diagonal
		int [] num2 = new int[size];	//rows
		//int num2 = 0;
		int [] num3 = new int[size];	//columns
		//int num3 = 0;
		
		//convert moves[] to two dimension array
		String [][] updated2DMoves = new String[size][size];
		int w = 0;		//column number
		int r = 0;		//row number
		int y = 0;		
		
		//convert single dimension array to 2-D array
		while(w<size)
		{
			while(r<size)
			{
				updated2DMoves[w][r] = moves[y];
				y++;
				r++;
			}
			w++;
			r=0;
		}
		/*
		//Test
		for(int l = 0; l< size; l++)
		{
			for(int x=0; x<size; x++)
			{	
				System.out.println(updated2DMoves[l][x]);
			}
		}
		*/
		for(int i = 0; i< size; i++)		//column
		{
			for(int j = 0; j< size; j++)	//row
			{
				
					//left diagonal
					if(i==j)
					{
						if(updated2DMoves[i][j]!=null && updated2DMoves[i][j].equals(symbol))
						{
							num0++;
							//System.out.println("num0(left diagonal) ++, "+num0);
						}
					}
					//right diagonal
					if(i==size-j-1)
					{
						if(updated2DMoves[i][size-1-i]!=null && updated2DMoves[i][size-1-i].equals(symbol))
						{
							num1++;
							//System.out.println("num1(right diagonal) ++, "+num1);
						}
					}
					//rows
					if(updated2DMoves[i][j]!=null && updated2DMoves[i][j].equals(symbol))
					{
						num2[i]++;
						//System.out.println("num2["+i+"] ++, "+num2[i]);
					}
					//columns
					if(updated2DMoves[j][i]!=null && updated2DMoves[j][i].equals(symbol))
					{
						num3[i]++;
						//System.out.println("num3["+i+"] ++, "+num3[i]);
					}
				
				
			}
		}
		
		if(num0>=winningSequence||num1>=winningSequence)
		{
			return true;
		}
		else
		{
			for(int q = 0; q < size; q++)
			{
				if(num2[q]>=winningSequence)
				{
					return true;
				}
			}
			for(int u= 0; u < size; u++)
			{
				if(num3[u]>=winningSequence)
				{
					return true;
				}
			}
			return false;
		}
		/*
		for(int c = 0; c < size; c++)		//different rows
		{
			//System.out.println("Test0: "+ c +"!");
			//check left diagonals
			if(moves[(size+1)*c].equals(symbol))
			{
				num0++;
				System.out.println("Test1: "+ c +"!!");
			}
			//check right diagonal
			if(moves[((size-1)*(c+1))].equals(symbol))
			{
				num1++;
				System.out.println("Test2: "+ c +"!!!");
			}
			
			//check rows
			
			if((moves[size*c].equals(symbol)) && (moves[size*c+1].equals(symbol)) && (moves[size*c+2].equals(symbol)))
			{
				num2[c]++;
				//System.out.println("Test0: "+ c +"!!!!");
			}
			
			int d = 0;
			for(int e = 0; e < size; e++)
			{
				if(moves[size*c+e].equals(symbol))
				{
					num2[e]++;
					System.out.println("Test3: "+ c +"!!!");
				}
			}
			
			//check columns
			
			if((moves[c].equals(symbol)) && (moves[c+3].equals(symbol)) && (moves[c+6].equals(symbol)))
			{
				num3[c]++;
				//System.out.println("Test0: "+ c +"!!!!!");
			}
			
			for(int f = 0; f < size; f++)
			{
				if(moves[f+f*size].equals(symbol))
				{
					System.out.println("Test4: "+ c +"!!!");
					num3[f]++;
				}
			}
		}*/
		
		/*
		//update condition for rows
		for(int i = 0; i< num2.length;i++)
		{
			if(num2[i] >= winningSequence)
			{
				
				return true;
			}
		}
		//update condition for columns
				for(int i = 0; i< num3.length;i++)
				{
					if(num3[i] >= winningSequence)
					{
						return true;
					}
				}
		if(num0 >= winningSequence || num1 >= winningSequence)
		{
			return true;
		}
		*/
		
		//return false;
	}
	
	//initialize the board
	public static void printBoard(int size, String[] symbols)		//回头把symbols去掉
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
	
	public static void updateBoard(int size, String [] moves)
	{
		System.out.println("Updated board: ");
		//create Strings of places to store moves
				String [] symbolPlaces =new String[size*size]; 
				//initialize the places
				for(int i = 0;i < size*size; i++)
				{
					symbolPlaces[i]=moves[i];
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
				for(int i = 0; i < size; i++)
				{
					System.out.print((i+1)+"  ");
					for(int k =0; k< size-1; k++)
					{
						System.out.print(" "+symbolPlaces[k_total]+" |");
						k_total++;
					}
					System.out.print(" "+symbolPlaces[k_total]);
					k_total++;
					System.out.println("");	
					if(i!=(size-1))
					{
						System.out.println("   "+"---"+underscore);
					}
				}
	}
}
