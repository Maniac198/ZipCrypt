package com.example.zipcrypt.util;

import java.util.*;

public class HuffmanCoding {

    private static Map<Character, String> huffmanCodes;
    private static Node root;

    // Compress the input text using Huffman coding
    public static String compress(String input) {
        // Build frequency map
        Map<Character, Integer> frequencyMap = buildFrequencyMap(input);

        // Build Huffman tree
        PriorityQueue<Node> priorityQueue = buildPriorityQueue(frequencyMap);
        root = buildHuffmanTree(priorityQueue);

        // Generate Huffman codes
        huffmanCodes = generateCodes(root);

        // Compress (encode) the input
        return encode(input, huffmanCodes);
    }

    // Decompress the compressed binary string back to original text
    public static String decompress(String encodedString) {
        return decode(encodedString, root);
    }

    // Helper method: Builds a frequency map of characters in the input string
    private static Map<Character, Integer> buildFrequencyMap(String input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : input.toCharArray()) {
            frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        }
        return frequencyMap;
    }

    // Helper method: Builds a priority queue using the frequency map
    private static PriorityQueue<Node> buildPriorityQueue(Map<Character, Integer> frequencyMap) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.freq));
        for (var entry : frequencyMap.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }
        return priorityQueue;
    }

    // Helper method: Builds the Huffman tree from the priority queue
    private static Node buildHuffmanTree(PriorityQueue<Node> priorityQueue) {
        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            priorityQueue.add(parent);
        }
        return priorityQueue.poll(); // Root node of the Huffman tree
    }

    // Helper method: Generates Huffman codes by traversing the tree
    private static Map<Character, String> generateCodes(Node root) {
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateCodeRecursive(root, "", huffmanCodes);
        return huffmanCodes;
    }

    // Recursive helper method to generate codes
    private static void generateCodeRecursive(Node node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) return;

        // Leaf node contains character
        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.ch, code);
        }

        generateCodeRecursive(node.left, code + "0", huffmanCodes);
        generateCodeRecursive(node.right, code + "1", huffmanCodes);
    }

    // Helper method: Encodes the input string using Huffman codes
    private static String encode(String input, Map<Character, String> huffmanCodes) {
        StringBuilder encodedString = new StringBuilder();
        for (char ch : input.toCharArray()) {
            encodedString.append(huffmanCodes.get(ch));
        }
        return encodedString.toString();
    }

    // Helper method: Decodes the encoded string using the Huffman tree
    private static String decode(String encodedString, Node root) {
        StringBuilder decodedString = new StringBuilder();
        Node current = root;

        for (char bit : encodedString.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;

            // If leaf node, append character to result
            if (current.left == null && current.right == null) {
                decodedString.append(current.ch);
                current = root;
            }
        }

        return decodedString.toString();
    }

    // Node class for building the Huffman tree
    static class Node {
        char ch; // Character
        int freq; // Frequency of character
        Node left, right; // Left and right children

        // Constructor for leaf node
        Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        // Constructor for internal node
        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    }
}
