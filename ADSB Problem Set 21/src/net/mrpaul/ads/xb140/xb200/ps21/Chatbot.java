package net.mrpaul.ads.xb140.xb200.ps21;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import net.mrpaul.XB140.ps19.*;

public class Chatbot {
	
	String text = "";
	File f;
	Map<String, List<String>> ngram = Markov.buildNgramSuffixDictionary(f, 3);

    public Chatbot(String filename) {
    		f = new File(filename);
    }
    
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
    }
} 