package org.example;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Main {

    public static Map<Character, Float> sortedCharacterProbabilityMap = null;
    public static ArrayList<Map.Entry<Character, Float>> characterMappingArrayList = null;

    public static Map<Character, String> characterCode = null;

    public static void main(String[] args) throws IOException,FileNotFoundException {
        System.out.println("Hello world!");

        // Creating the input stream object
        FileReader obj = new FileReader("src/main/java/org/example/sample.txt");

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
            System.out.println(data);
        }
        scanner.close();

        //Sorting the character-frequency mapping in descending order using Java Streams API
        Map<Character, Float> sortedCharacterFrequencyMap = characterFrequency.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

//        for (Map.Entry<Character, Float> e: sortedCharacterFrequencyMap.entrySet()) {
//            System.out.println(e);
//        }



        //Creating the character-probability mapping
        sortedCharacterProbabilityMap = new HashMap<>();
        for (Map.Entry<Character, Float> e:sortedCharacterFrequencyMap.entrySet()) {
            sortedCharacterProbabilityMap.put(e.getKey(), e.getValue()/numberOfCharacters);
        }

        sortedCharacterProbabilityMap = sortedCharacterProbabilityMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));


        characterMappingArrayList = new ArrayList<>(sortedCharacterProbabilityMap.entrySet());

        for (Map.Entry<Character, Float> e: characterMappingArrayList) {
            System.out.println(e);
        }

        generateCodes(characterMappingArrayList);



        String encodedString = "";

        scanner = new Scanner(obj);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            for (char c: data.toCharArray()) {
                encodedString+=characterCode.get(c);
            }
        }
        scanner.close();

        FileWriter fileWriter = new FileWriter("src/main/java/org/example/sample_output.txt");

        for (char c: encodedString.toCharArray()) {
            fileWriter.append(c);
        }
    }

    public static void generateCodes(ArrayList<Map.Entry<Character, Float>> list){
        /*
         * This function creates coding tree recursively and then generate codes according to
         * each character's possibility.
         */

        if(list.size () > 1)
        {
            ArrayList <Map.Entry<Character, Float>> left = new ArrayList<>();
            ArrayList <Map.Entry<Character, Float>> right = new ArrayList<>();
            float totalProbability = 0F;

            for (int i = 0; i<list.size(); i++) {
                totalProbability += list.get(i).getValue();
            }


            float totalProbabilityLeftSide = 0F;
            float difference = Float.MAX_VALUE;
            float minDifference = Float.MAX_VALUE;
            int stop = 0;

            for (int i = 0; i < list.size(); i++){
                totalProbabilityLeftSide += sortedCharacterProbabilityMap.get(list.get(i).getKey());
                difference = Math.abs(totalProbability/2 - totalProbabilityLeftSide);
                minDifference = Math.min(difference,minDifference);
                if(difference > minDifference){
                    stop = i;
                    break;
                }
            }

            for(int i = 0; i < stop; i++){
                characterCode.put(list.get(i).getKey(), characterCode.getOrDefault(list.get(i),"") + '0');
                left.add(list.get(i)) ;
            }

            for(int i = stop; i < list.size(); i++){
                characterCode.put(list.get(i).getKey(), characterCode.getOrDefault(list.get(i),"") + '1');
                right.add(list.get(i)) ;
            }

            generateCodes(left) ;
            generateCodes(right) ;
        }
    }
}


