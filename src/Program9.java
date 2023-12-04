/*
Name: Connor Byers
Prof: Blanche Cohen
Class: CS2050
Dectription: Program 9 (not actually needed for Program11,
but it proves that it uses the same RedBlackTree to sort)
 */


import java.io.*;
import java.io.IOException;


public class Program9 {
    private String inputFilePath;
    private String outputFilePath;

    public Program9(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    public String readWordsFromFile() {
        StringBuilder allWordsAsString = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    if (!word.isEmpty()) {
                        allWordsAsString.append(word).append(" ");
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return allWordsAsString.toString();
    }

    public void writeSortedStringToFile(String sortedString) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(sortedString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\Winter\\IdeaProjects\\Project11\\DraculaInput.txt"; // Replace with the path to your input file
        String outputFilePath = "draculaOutput.txt"; // Replace with the path to your output file

        Program9 program = new Program9(inputFilePath, outputFilePath);

        // Step 1: Read the file and filter/format the words
        String allWordsAsString = program.readWordsFromFile();

        // Step 2: Insert the words into a search tree
        RedBlackTree rbt = program.getRedBlackTree(allWordsAsString);

        // Step 3: Perform post-order traversal to generate a sorted string
        String sortedString = rbt.inorder();

        // Step 4: Write the sorted string to the output file
        //program.writeSortedStringToFile(sortedString);

        // Perform operations on the existing binary search tree
        System.out.println("Number of Nodes: " + rbt.getNodeCount());
        System.out.println("Tree height: " + rbt.getHeight());
        System.out.println("Max number of Nodes with the same tree height: " + rbt.maxNodes()); //show number of nodes possible with a tree of the same height if that tree was balanced

        // Predefined word to search
        String wordToSearch = "dacians"; // Testing search. Replace with the word you want to search (doesn't say where word is yet, only if it IS there)
        RBTNode result = rbt.searchTree(wordToSearch);

        if (result != null) {
            System.out.println("Found: " + result.getData()); //if search word is found
            System.out.println("[" + result.getData() + "]" + " exists " + rbt.getWordCount(wordToSearch) + " time(s) ");
        } else {
            System.out.println("Key not found."); //if search word is not found
        }
        System.out.println();
        System.out.println("|-----Printing Layers-----|");
        System.out.println();
        rbt.prettyPrintLimited(3);




    }

    private RedBlackTree getRedBlackTree(String words) {
        RedBlackTree rbt = new RedBlackTree();
        String[] wordArray = words.split("\\s+");
        for (String word : wordArray) {
            rbt.insert(word);
        }
        return rbt;
    }





}