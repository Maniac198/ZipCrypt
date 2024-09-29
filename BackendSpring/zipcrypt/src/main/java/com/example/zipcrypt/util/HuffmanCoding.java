// src/main/java/com/example/fileprocessor/util/HuffmanCoding.java
package com.example.zipcrypt.util;

import java.util.*;

public class HuffmanCoding {

    public static String compress(String input) {
        // Build frequency map
        Map<Character, Integer> frequencyMap = buildFrequencyMap(input);

        // Build Huffman tree
        PriorityQueue<Node> priorityQueue = buildPriorityQueue(frequencyMap);

        Node root = buildHuffmanTree(priorityQueue);

        // Generate Huffman codes
        Map<Character, String> huffmanCodes = generateCodes(root);

        // Compress the input
        return encode(input, huffmanCodes);
    }

    // Additional methods for building tree, encoding, and decoding...

    private static Map<Character, Integer> buildFrequencyMap(String input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : input.toCharArray()) {
            frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        }
        return frequencyMap;
    }

    private static PriorityQueue<Node> buildPriorityQueue(Map<Character, Integer> frequencyMap) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.freq));
        for (var entry : frequencyMap.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }
        return priorityQueue;
    }

    private static Node buildHuffmanTree(PriorityQueue<Node> priorityQueue) {
        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }

    private static Map<Character, String> generateCodes(Node root) {
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateCodeRecursive(root, "", huffmanCodes);
        return huffmanCodes;
    }

    private static void generateCodeRecursive(Node node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) return;

        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.ch, code);
        }

        generateCodeRecursive(node.left, code + "0", huffmanCodes);
        generateCodeRecursive(node.right, code + "1", huffmanCodes);
    }

    private static String encode(String input, Map<Character, String> huffmanCodes) {
        StringBuilder encodedString = new StringBuilder();
        for (char ch : input.toCharArray()) {
            encodedString.append(huffmanCodes.get(ch));
        }
        return encodedString.toString();
    }

    static class Node {
        char ch;
        int freq;
        Node left, right;

        Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    }
}
