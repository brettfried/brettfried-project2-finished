// Author: Brett E. Fried
// Created: Sept. 2023
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Dlewor {

    // constants to allow colored text and backgrounds
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    //this method is the linear search
    public static int linearSearch(String userGuess, ArrayList<String> fiveletterentries, int start, int end) {
        if (start == end) {
            return -1;
        }
        if (fiveletterentries.get(start).equals(userGuess)){
            return 1;
        }
        return linearSearch(userGuess, fiveletterentries, start+1,end);
    }
    //this method is the binary search
    public static int binarySearch(String userGuess, ArrayList<String> fiveletterentries, int start, int end) {
        if (start <= end) {
            int middle = (start+end) / 2;
            int wordComparison = userGuess.compareTo(fiveletterentries.get(middle));
            if (wordComparison == 0) {
                return 1;
            }
            else if (wordComparison < 0){
                return binarySearch(userGuess, fiveletterentries, start, middle-1);
            }
            else {
                return binarySearch(userGuess, fiveletterentries, start, middle-1);
            }
        }
        return -1;
    }
    //finding whether or not the list is sorted
    public static boolean isSorted (String args) {
        if (args.equals("vocab.nytimes.random.txt")){
            return false;
        }
        else {
            return true;
        }
    }
    //assign corresponding colors
    public static int [] matchDlewor (String answer, String userGuess){
            char letter;
            int[] correct = new int [userGuess.length()];
            for (int i = 0; i < userGuess.length(); i++){
                for (int j = 0 ; j < userGuess.length(); j++) {
                    if (userGuess.charAt(i) == answer.charAt(j)){
                        if (i == j){
                            correct[i] = 2;
                            break;
                        }
                        correct[i] = 1;
                        break;
                    }
                    correct[i] = 0;
                }
            }

            // if it is correct return 2 if its kinda correct return 1
return correct;
    }

    //called if answer is correct
    public static boolean foundMatch(String userGuess, String answer) {
        if (userGuess.equals(answer)) {
            return true;
        }
        else {
            return false;
        }
    }

    //printing method
    public static void printDlewor(String userGuess, int[] matcharray){
        for (int i = 0; i < userGuess.length(); i++){
            if (matcharray[i] == 2) {
                System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + userGuess.charAt(i));
            }
            if (matcharray[i] == 1) {
                System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + userGuess.charAt(i));
            }
            if (matcharray[i] == 0) {
                System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + userGuess.charAt(i));
            }
            System.out.print(ANSI_RESET);

        }
        }

    public static void main(String[] args) {
        // print welcome message
        System.out.println("Welcome to Dlewor(TM)");
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;
        String word;
        Scanner scnr = new Scanner(System.in);
       //used if invalid file
        try {
            inputFileNameStream = new FileInputStream(args[0]);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find exception" + args[0]);
            System.exit(1);
        }

        //creating array list for five character words
        ArrayList<String> fiveletterentries = new ArrayList<>();
        inputFileNameScanner = new Scanner(inputFileNameStream);

        //get words
        while (inputFileNameScanner.hasNextLine()){
            word = inputFileNameScanner.nextLine();
            if (word.length()==5){
                fiveletterentries.add(word);
            }
        }

        //random number generator for word
        Random random = new Random();
        int index = random.nextInt(fiveletterentries.size());
        String answer = fiveletterentries.get(index);
        System.out.println(answer);

        //initializing variables
        int start = 0;
        int end = fiveletterentries.size();
        int guesscounter = 1;
        boolean correct = false;
        String userGuess;
        while (guesscounter <= 6 && !correct) {
            System.out.print("\nEnter word (" + guesscounter + "): ");
            userGuess = scnr.next();;
            int[] colors = matchDlewor(answer, userGuess);
            guesscounter = guesscounter + 1;
            correct = foundMatch(userGuess, answer);
            //use binarysearch
            if (isSorted(args[0])) {
                if (binarySearch(userGuess, fiveletterentries, start, end) == -1) {
                    printDlewor(userGuess, colors);
                }
                if (binarySearch(userGuess, fiveletterentries, start, end) == 1) {
                    System.out.println("Invalid word.");
                    guesscounter = guesscounter - 1;
                    continue;
                }
            }
            //use linearsearch
            if (!isSorted(args[0])) {
                if (linearSearch(userGuess, fiveletterentries, start, end) == 1) {
                    printDlewor(userGuess, colors);
                }
                if (linearSearch(userGuess, fiveletterentries, start, end) == -1) {
                    System.out.println("Invalid word.");
                    guesscounter = guesscounter - 1;
                    continue;
                }
            }
            //reward the user for successful guess
            if (foundMatch(userGuess, answer)) {
                System.out.print("\nYesss!");
            }

        }
            //prompts user to try again if failure
            if (guesscounter == 6) {
                System.out.print("\nUnlucky! Try Again!");
            }

        }
    }

