import java.io.*;
import java.util.*;

public class Main 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		// First we will load the dictionary.txt file to be used for the game
		// Then we will randomly pick a word from that file
		// Finally, we play the game using our picked word
		String[] dictionary = loadInput("dictionary.txt");

		String pickedWord = pickWord(dictionary);

		System.out.println(pickedWord);

		playGame(pickedWord);
	}

	// Class constant which we will use to determine how many tries the player will get
	public final static int maxWrong = 5;
	
	public static String[] loadInput(String fileName) throws FileNotFoundException
	{
		// Use scanner to read in fileName to an array
		// Populate the array with each word in fileName, using newlines as the delimiter
		Scanner readDictionary = new Scanner(new File("dictionary.txt"));

		String[] dictWords = new String[127100];
		
		int i = 0;

		while (readDictionary.hasNextLine()) 
		{			
		  	String line = readDictionary.nextLine();

      		dictWords[i] = line;

			i++;
		}
		
		return dictWords;
	}
	   
	   
	public static String pickWord(String[] words) 
	{
		// Randomly pick a word from the words array
		Random random = new Random();
		
		int ranNum = random.nextInt(words.length);

		String pickedWord = words[ranNum];
		
		return pickedWord;
	}
	
	public static void playGame(String secretWord) 
	{
		// Instantiate a boolean array letterChecker that is the same length as the letters in secretWord
		// Initially populate letterChecker with all false bools to start the game with no letters showing
		// Create a char array named letterArray that uses each letter in secretWord as an indice
		// We will use letterArray in multiple methods to check against user input and update letterChecker
		// Create a scanner object input to receive user input
		// Create a variable wrongAttempts and set to 0 to check against our maxWrong constant
		// Create a boolean gameComplete and set to false to check against when to exit our game
		// We will use a do while loop to run our program once and check to see if our game is complete
		
		boolean[] letterChecker = new boolean[secretWord.length()];

		for(int i = 0; i < letterChecker.length; i++)
		{
			letterChecker[i] = false;
		}

		char[] letterArray = secretWord.toCharArray();

		Scanner input = new Scanner(System.in);

		int wrongAttempts = 0;

		boolean gameComplete = false;

		System.out.println("Let's play a game of Hangman!\n");

		do
		{
			// First, we need to print out blank word for our user
			// Now we will prompt them to guess a letter and turn this letter to lowercase in case an uppercase letter was input
			// We also will only take the first letter in case multiple letters were input by the user
			// We will use checkGuess() to see if the user input is in our word; we will print occurences in the console
			// We will use updateLetters to update our boolean array letterChecker; this array allows us to show correctly guessed letters
			// If the chosen letter is not in our word, we will increment wrongAttempts and let our user know how many attempts they have left
			// Finally, we will use our gameOver() method to see if our user has satisified the condition to end our game
			
			printWord(letterChecker, letterArray);

			System.out.print("\n\nChoose your next letter: ");
			
			char letterGuessed = input.next().charAt(0);

			letterGuessed = Character.toLowerCase(letterGuessed);
			
			int numInWord = checkGuess(letterChecker, letterArray, letterGuessed);

			System.out.println("\nThe letter " + letterGuessed + " is in the word " + numInWord + " times.\n");

			letterChecker = updateLetters(letterChecker, letterArray, letterGuessed);

			if(numInWord == 0)
			{
				wrongAttempts++;

				int remaining = maxWrong - wrongAttempts;

				System.out.println("You have " + remaining + " remaining incorrect guesses.\n");
			}

			gameComplete = gameOver(letterChecker, wrongAttempts, secretWord);
		}
		while(!gameComplete);
	}
	
	public static void printWord(boolean[] letterChecker, char[] letterArray) 
	{
	  	// Loop through our boolean array to see which letters were guessed correctly and which letters we should still hide
		
		for(int i = 0; i < letterChecker.length; i++)
		{
			if(letterChecker[i])
			{
				System.out.print(letterArray[i] + " ");
			}
			else
			{
				System.out.print("_ ");
			}
		}
	}
	
	public static boolean gameOver(boolean[] letters, int wrongAttempts, String secretWord) 
	{
		// Instantiate the boolean gameCompleted to return to our playGame() method to see if our game meets the conditions to exit
		// Instantiate a count variable. We will use this while looping through our letters array to see if all the indices are true
		// We will compare count to the length of letters, if they're the same the user has won the game
		// If wrongAttempts equals our maxWrong constant, we will end the game and alert the user that they lost and what their word was
		
		boolean gameCompleted = false;

		int count = 0;

		for(boolean i : letters)
		{
			if(i)
			{
				count++;
			}
		}

		if(count == letters.length)
		{
			gameCompleted = true;

			System.out.println("Congratulations, you won!\nYour word was: " + secretWord);
		}

		if(wrongAttempts == maxWrong)
		{
			gameCompleted = true;

			System.out.println("You lose.\nYour word was: " + secretWord);
		}
		
		return gameCompleted;
	}
	   
	public static int checkGuess (boolean[] letters, char[] letterArray, char guessLetter) 
	{
		// Instantiate the variable numInWord to see how many occurences the guessed letter have in our word
		
		int numInWord = 0;

		for(char x : letterArray)
		{
			if(guessLetter == x)
			{
				numInWord++;
			}
		}
		
		return numInWord;
	}

	public static boolean[] updateLetters(boolean[] letterChecker, char[] letterArray, char letterGuessed)
	{
		// Create the variable count to iterate through our letterChecker array
		// If the letter guessed is the same as the corresponding index in our letterArray, we will update our letterChecker boolean array indice to true to show the correctly guessed letter
		
		int count = 0;

		for(char x : letterArray)
		{
			if(letterGuessed == x)
			{
				letterChecker[count] = true;
			}

			count++;
		}
		
		return letterChecker;
	}
}
