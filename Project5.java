/**
 * PROJECT 5
 * Java Keyword Identifier
 * Name: Andrew Talbot
 * Class: CMSC 256 - Sec 901
 * Semester: Fall 2017
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import TreePackage.AVLTree;

// java.util.Scanner is three identifiers
// _ or $ to start is invalid, so is number at start, and special character midway
// Ignore comments (// or /* */) and string literals and ignore them
// frequencies of valid identifiers

public class Project5 {

    private static Scanner input = new Scanner(System.in);
    private static AVLTree<String> keywords = new AVLTree<>();
    private static FrequencyCounter identifiers = new FrequencyCounter();
    private static FrequencyCounter invalids = new FrequencyCounter();

    // replace HashMap with FrequencyCounter from content area on blackboard
    // need Binary Search Tree

    public static void main(String[] args) {

        printHeading();

        String keywordFile, sourceCodeFile, keywordFileContents, sourceCodeFileContents;

        switch(args.length) {
            case 0:
                keywordFile = promptForFileName(true, true);
                keywordFileContents = readFile(keywordFile, true);
                storeKeywords(keywordFileContents);

                sourceCodeFile = promptForFileName(true, false);
                sourceCodeFileContents = readFile(sourceCodeFile, false);
                parseSourceCode(sourceCodeFileContents);
                break;
            case 1:
                keywordFile = args[0];
                keywordFileContents = readFile(keywordFile, true);
                storeKeywords(keywordFileContents);

                sourceCodeFile = promptForFileName(true, false);
                sourceCodeFileContents = readFile(sourceCodeFile, false);
                parseSourceCode(sourceCodeFileContents);
                break;
            default: // 2 or greater
                keywordFile = args[0];
                keywordFileContents = readFile(keywordFile, true);
                storeKeywords(keywordFileContents);

                sourceCodeFile = args[1];
                sourceCodeFileContents = readFile(sourceCodeFile, false);
                parseSourceCode(sourceCodeFileContents);
                break;
        }

        // Print out keyword data
        System.out.print("The keyword file contains the following unique keywords: ");
        printFive(keywords.getInorderIterator());

        // Print out invalid identifiers
        System.out.println("The following are invalid identifiers in the file: ");
        invalids.display(false);

        // Print out identifier data
        System.out.println("\nThe following tokens are valid identifiers in the file, " +
                sourceCodeFile.substring(sourceCodeFile.lastIndexOf("/") + 1) + ":");
        identifiers.display(true);
    }

    private static String readFile(String path, boolean mode) {
        StringBuilder fileContents = new StringBuilder();
        // create instance of file class and check for file path's existence
        try {
            Scanner input = openFile(path, mode);
            String line;

            // while data remains in the file
            while (input.hasNext()) {
                line = input.nextLine().trim();
                fileContents.append(line).append("\n");
            }

            input.close(); // close scanner
        } catch (FileNotFoundException noFile) {
            System.out.println("The program was unable to load the file. Please restart.");
        }
        return fileContents.toString();
    }

    private static void storeKeywords(String keywordsFileContent) {
        String[] keywordsArr = keywordsFileContent.split("\n");
        for(String keyword : keywordsArr)
            keywords.add(keyword);
    }

    private static void parseSourceCode(String sourceCodeFileContents) {
        String[] cleanedSource = clean(sourceCodeFileContents);
        for(String token : cleanedSource) {
            if(token.length() > 0 && isValid(token))
                identifiers.add(token);
        }
    }

    private static boolean isValid(String token) {
        boolean returnValue = false;
        boolean invalidStart = token.substring(0,1).matches("[0-9]|[$_]");

        if(!invalidStart) {
            if(!isReserved(token))
                returnValue = true;
        } else if(!isNumericLiteral(token))
            invalids.add(token);

        return returnValue;
    }

    private static boolean isNumericLiteral(String token) {
        return token.matches("[0-9]+");
    }

    private static boolean isReserved(String token) {
        return keywords.contains(token);
    }

    private static String[] clean(String code) {
        String clean1 = code.replaceAll("//.+[\n]", ""); // Single line comments
        String clean2 = clean1.replaceAll("\n", " "); // Make everything one line
        String clean3 = clean2.replaceAll("(\'[^']+\')", ""); // Character literals
        String clean4 = clean3.replaceAll("(\"[^\"]+\")", ""); // String literals
        String clean5= clean4.replaceAll("(/[*].*?[*]/)", ""); // Multi line comments
        String[] finalClean = clean5.split("[\\s*+=\\[\\]{}()&|/^%.,;:≤≥<>]|[-]"); // Split by potential delimiters
        return finalClean;
    }

    private static void printFive(Iterator iter) {
        int i = 0;
        while(iter.hasNext()) {
            System.out.print(iter.next() + " ");
            if(i % 5 == 0)
                System.out.print("\n");
            i++;
        }
        System.out.println("\n");
    }

    /**
     * Gets a file path from the keyboard (alternative prompts depending on entry point)
     * @return
     *      String
     */
    private static String promptForFileName(boolean firstPrompt, boolean mode) {
        String alternatePrompt = (firstPrompt) ? ": " : " (\":q\" to quit): ";
        String contents = (mode) ? "the reserved words" : "the source code";
        System.out.println("Enter the full path to the file containing " + contents + alternatePrompt);
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    /**
     * Attempts to open the file, prompts for a different file if none exists.
     * User has the option to quit the program.
     * @return
     *      Scanner
     */
    private static Scanner openFile(String pathName, boolean mode) throws FileNotFoundException {
        File file = new File(pathName);
        while (!file.exists()) {
            System.out.println("You didn't input a valid file name. Try again!");
            file = new File(promptForFileName(false, mode));
            if(file.getName().equals(":q")) System.exit(0);
        }
        // System.out.println("Showing songs from " + file.getName() + "\n");
        return new Scanner(file);
    }

    /**
     * Prints name, project number, course identifier and current semester to console.
     */
    private static void printHeading() {

        String name, semester, projectNumber, courseID;

        name = "Andrew Talbot";
        projectNumber = "Project #5";
        courseID = "CMSC 256, Section 901";
        semester = "Fall 2017";

        String[] data = {"****** HEADING ******", name, projectNumber, courseID, semester, "====================="};

        System.out.println(String.join("\n", data));
    }
}
