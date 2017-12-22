/**
 * PROJECT 5
 * Java Keyword Identifier
 * Name: Andrew Talbot
 * Class: CMSC 256 - Sec 901
 * Semester: Fall 2017
 *
 * Modified FrequencyCounter to take a String and add to the map rather than a file input
 */

import java.util.*;

public class FrequencyCounter {
 // private DictionaryInterface<String, Integer> wordTable;
 	private TreeMap<String, Integer> wordTable;
  
  public FrequencyCounter() {
    wordTable = new TreeMap<String, Integer>();
  } 
  
  /** Task: insert a string into the map and increment frequency
   *  @param word  a string */
  public void add(String word) {

        Integer frequency = wordTable.get(word);

        if (frequency == null) { 				// add new word to table
            wordTable.put(word, new Integer(1));
        }
        else { 									// increment count of existing word; replace wordTable entry
            frequency++;
            wordTable.put(word, frequency);
        }
  }
  
  /** Task: Displays words and their frequencies of occurrence. */
  public void display(boolean mode)
  {
    Set<Map.Entry<String, Integer>> allWords = wordTable.entrySet();
    Iterator<Map.Entry<String, Integer>> keyIterator = allWords.iterator();
    
    while (keyIterator.hasNext())   {
        Map.Entry<String, Integer> element =  keyIterator.next();
        if(!mode)
            System.out.println(element.getKey());
        else {
            System.out.println(element.getKey() + " " +  element.getValue());
        }
    }
  } 
} 
