package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello world!");

        // Creating the input stream object
        InputStream obj = new FileInputStream("sample.txt");

        //Initializing the character-frequency mapping
        HashMap<Character, Float> characterFrequency = new HashMap<>();

        //Reading the text file and updating the character-frequency mapping
        int numberOfCharacters = 0;
        Scanner scanner = new Scanner(obj);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            for (char c: data.toCharArray()) {
                characterFrequency.put(c, characterFrequency.getOrDefault(c,0.0F)+ 1.0F);
                numberOfCharacters++;
            }
        }
        scanner.close();

        //Sorting the character-frequency mapping in descending order using TreeMap and custom comparator
        Comparator comparator = new MyComparator(characterFrequency);

        Map<Character, Float> sortedCharacterFrequencyMap = new TreeMap(comparator);
        sortedCharacterFrequencyMap.putAll(characterFrequency);

        for (float f: sortedCharacterFrequencyMap.values()) {
            f /= numberOfCharacters;
        }

        //hashmap.entrySet().stream()
        //        .sorted(Map.Entry.<String, Integer> comparingByValue().reversed())






    }
}



//Created own comparator class used for sorting the character-frequency mapping in descending order
class MyComparator implements Comparator {
    Map map;

    public MyComparator(Map map) {
        this.map = map;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return (o2.toString()).compareTo(o1.toString());
    }
}