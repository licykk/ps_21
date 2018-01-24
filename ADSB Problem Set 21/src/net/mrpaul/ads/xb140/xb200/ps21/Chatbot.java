package net.mrpaul.ads.xb140.xb200.ps21;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import net.mrpaul.XB140.ps19.*;

public class Chatbot {

	String text = "";
	File f;
	static Map<String, List<String>> ngram;
	static boolean signalFlag = true;
	static Scanner reader = new Scanner(System.in);

	public Chatbot(String filename) throws FileNotFoundException {
		f = new File(filename);}

	public void say() throws IOException, InterruptedException {
		String command = "say -v Daniel " + text;

		Process proc = Runtime.getRuntime().exec(command);
		//Read the output
		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		String line = "";
		while((line = reader.readLine()) != null) {
			System.out.print(line + "\n");
		}
		proc.waitFor();
	}    
	public String generateNext(String userinput) throws FileNotFoundException {
		String response = "";
		if(userinput.equalsIgnoreCase("bye"))
			signalFlag = false;
		String userinput2 = userinput;
		while (userinput2.length() != 0){
			String[] words = userinput2.split(" ");	
			ngram = Markov.buildNgramSuffixDictionary(f, words.length);
			if (ngram.get(userinput2) != null){
				for (int i = 0; i < words.length; i++)
					response += words[i] + " ";
				System.out.println(response);
				response += Markov.babbleNMinLength(f, words.length, 5, userinput2);
				response = response.substring(0, 1).toUpperCase() + response.substring(1); 
				return response;
			}
			else {
				System.out.println(Arrays.toString(words));
				userinput2 = userinput2.replace(words[0], "").trim();
			}
			System.out.println(userinput2);
		}
		Random rand = new Random();
		ArrayList<String> possibleResponses = new ArrayList<String>();
		Scanner r = new Scanner(new File("common.txt"));
		while(r.hasNext()) {
			possibleResponses.add(r.nextLine().trim());
		}
		int n = rand.nextInt(possibleResponses.size());
		return possibleResponses.get(n);
	}
	public static void main(String[] args) throws FileNotFoundException, InterruptedException{
		Chatbot c = new Chatbot("text.txt");
		String userinput = "", response = "";
		while (signalFlag){
			System.out.print("You: ");
			userinput = reader.nextLine();
			if (signalFlag)
				System.out.println("Computer: "+ c.generateNext(userinput));
		}
	}
}