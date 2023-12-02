
import java.io.*;
import java.io.IOException;

import java.util.Set;
import java.util.HashSet;

public class Program11 {

    private String inputFilePath;
    private String outputFilePath;
    private HashTable hashTable;

    public Program11(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.hashTable = new HashTable(5);

    }

    public String[] readAndProcessFile() { //option 3 includes String[]
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
        return processedContent.toString().trim().split("\\s+"); //option3 includes .split("\\s+")
    }


    public void writeSortedStringToFile(String sortedString) { //Option 1 : writeProcessedContentToFile //Option 3: writeSortedStringToFile
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(sortedString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\Winter\\IdeaProjects\\Project11\\DraculaSample.txt";
        String outputFilePath = "PlainDraculaOutput.txt"; //only needed for option 1

        Program11 textToPlainFormat = new Program11(inputFilePath, outputFilePath);

        // Step 1: Read the file and process its content

        //String processedContent = textToPlainFormat.readAndProcessFile(); //for option 1 and 2

        String[] wordsArray = textToPlainFormat.readAndProcessFile(); //for option 3

        // Step 2: Write the processed content to the output

        //textToPlainFormat.writeProcessedContentToFile(processedContent); //option 1

        //System.out.println(processedContent); //option 2

        String words = ""; // Example words


        for (String word : wordsArray) {
            words = words + " " + word;
        }

        HashTable hashTable = getHashTable(words);

        Set<Integer> keys = hashTable.keySet();

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

        // Insert values into the hash table...

        // Retrieve a Node by index
        int indexToRetrieve = 63;
        int positionInList = 0;
        Node retrievedNode = hashTable.getNodeAtIndex(indexToRetrieve, positionInList);

        if (retrievedNode != null) {
            System.out.println("Node at index " + indexToRetrieve + ", position " + positionInList +
                    ": Key = " + retrievedNode.getKey() + ", Value = " + retrievedNode.getValue());
        } else {
            System.out.println("Invalid index or no Node at index " + indexToRetrieve);
        }



    }

    private static HashTable getHashTable(String words) {
        HashTable ht = new HashTable(127);
        String[] wordArray = words.split("\\s+");

        for (String word : wordArray) {
            int key = calculateKey(word);
            ht.put(key, word);
        }

        return ht;
    }

    private static int calculateKey(String word) {
        int key = 0;
        for (char c : word.toCharArray()) {
            key += (int) c;
        }
        return key;
    }




}
