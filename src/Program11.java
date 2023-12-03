
import java.io.*;
import java.io.IOException;

import java.util.Set;

public class Program11 {

    private String inputFilePath;
    private String outputFilePath;
    private HashTable hashTable;

    public Program11(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.hashTable = new HashTable(5);

    }

    public String readAndProcessFile() { //option 3 includes String[]
        StringBuilder processedContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    if (!word.isEmpty()) {
                        processedContent.append(word).append(" ");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processedContent.toString().trim(); //option3 includes .split("\\s+")
    }


    public void writeProcessedContentToFile(String sortedString) { //Option 1 : writeProcessedContentToFile //Option 3: writeSortedStringToFile
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(sortedString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\Winter\\IdeaProjects\\Project11\\DraculaInput.txt";
        String outputFilePath = "PlainDraculaOutput.txt"; //only needed for option 1

        Program11 textToPlainFormat = new Program11(inputFilePath, outputFilePath);

        // Step 1: Read the file and process its content

        String processedContent = textToPlainFormat.readAndProcessFile(); //for option 1 and 2

        //String[] wordsArray = textToPlainFormat.readAndProcessFile(); //Actual option 3

        // Step 2: Insert the words into a RB search tree
        RedBlackTree rbt = textToPlainFormat.getRedBlackTree(processedContent);

        // Optional Step 2 or step 4: Write the processed content to the output

        //textToPlainFormat.writeProcessedContentToFile(processedContent); //Actual option 1

        //System.out.println(processedContent); //Actual option 2

        // Step 3: Perform post-order traversal to generate a sorted string
        String sortedString = rbt.inorder();

        //Step 4:
        //System.out.println(sortedString);
        //textToPlainFormat.writeProcessedContentToFile(sortedString);

        String words = ""; // Example words can be manually added to test (for option 3)


        /*
        for (String word : wordsArray) { //for option 3
            words = words + " " + word;
        }
        */

        HashTable hashTable = getHashTable(sortedString);

        Set<Integer> keys = hashTable.keySet();

        // Insert values into the hash table...

        System.out.println("Key\t\tValue\t\t\tLocation");
        for (int key : keys) {

            if ((hashTable.get(key)).length() <= 5 && key<800 ) {
                System.out.println(key + "\t\t" + hashTable.get(key) + "\t\t\t\t" + hashTable.hashFunction(key));
            }

            else if ((hashTable.get(key)).length() <= 10 && key<1000) {
                System.out.println(key + "\t\t" + hashTable.get(key) + "\t\t\t" + hashTable.hashFunction(key));
            }

            else  {System.out.println(key + "\t" + hashTable.get(key) + "\t\t" + hashTable.hashFunction(key));}
        }



        // Retrieve a Node by index
        int indexToRetrieve = 211; //index will be searched for and how many times it appears using RedBlackTree method
        int positionInList = 0; //If there is a collision, setting this to 1 and up will find the next node on the linked list chain (test when λ is close to 1)
        Node retrievedNode = hashTable.getNodeAtIndex(indexToRetrieve, positionInList);

        // Predefined word to search
        String wordToSearch = retrievedNode.getValue(); // Testing search. Replace with the word you want to search
        RBTNode result = rbt.searchTree(wordToSearch);

        if (result != null) {
            System.out.println("Found: " + result.getData()); //if search word is found
            System.out.println("[" + result.getData() + "]" + " exists " + rbt.getWordCount(wordToSearch) + " time(s) ");
        } else {
            System.out.println("Key not found."); //if search word is not found
        }

        if (retrievedNode != null) {
            System.out.println("Node at index " + indexToRetrieve + ", position " + positionInList +
                    ": Key = " + retrievedNode.getKey() + ", Value = " + retrievedNode.getValue());
        } else {
            System.out.println("Invalid index or no Node at index " + indexToRetrieve);
        }

        //Retrieve a Key by value





    }

    private static HashTable getHashTable(String words) {
        HashTable ht = new HashTable(52127);
        String[] wordArray = words.split("\\s+");

        for (String word : wordArray) {
            int key = calculateKey(word);
            ht.put(key, word);
        }

        return ht;
    }

    private RedBlackTree getRedBlackTree(String words) {
        RedBlackTree rbt = new RedBlackTree();
        String[] wordArray = words.split("\\s+");
        for (String word : wordArray) {
            rbt.insert(word);
        }
        return rbt;
    }

    private static int calculateKey(String word) {
        int key = 0;
        for (char c : word.toCharArray()) {
            key = (key * 2) + (int) c; // Using a prime number for better distribution (it has errors with certain integers)
        }
        return key;
    }




}
