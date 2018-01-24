package net.mrpaul.ads.xb140.xb200.ps21;

import java.io.IOException;
import java.util.Scanner;

public class Speaker {
	public static void main(String[] args) throws IOException, InterruptedException {
		Chatbot c = new Chatbot("text.txt");
		Scanner r = new Scanner(System.in);
		String uinput = "";
		
		c.say("Hello, I am Potato, your personal pre modern-era chatbot. Please type 'exit' to exit.");
		while(!uinput.toLowerCase().equals("exit")) {
			c.say();
			uinput = r.nextLine();
			c.generateNext(uinput);
		}
	}
}
