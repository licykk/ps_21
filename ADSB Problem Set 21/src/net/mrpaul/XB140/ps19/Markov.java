package net.mrpaul.XB140.ps19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Markov {
	
	String START_WORD = "START :D";
	
	public static String babbleNeg1(String fileName, int length) throws FileNotFoundException {
		File f = new File(fileName);
		Scanner r = new Scanner(f);
		Set<String> s = new HashSet<String>();
		List<String> line = new ArrayList<String>();
		
		//add words to set
		while(r.hasNext()) {
			line = Arrays.asList(r.nextLine().split(" "));
			s.addAll(line);
		}
		
		String[] array = (String [])line.toArray();
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array)); 
		
		String end = "";
		Random ran = new Random();
		//generates babble
		for(int i = 0; i < length; i++) {
			end += " ";
			end += arrayList.get(ran.nextInt(arrayList.size()));
		}
		System.out.println(end);
		return end;
	}
	
	
	public static String babble0(String fileName, int length) throws FileNotFoundException {
		File f = new File(fileName);
		Scanner r = new Scanner(f);
		List<String> line = new ArrayList<String>();
		ArrayList<String> a = new ArrayList<String>();
		
		//add words to set
		while(r.hasNext()) {
			line = Arrays.asList(r.nextLine().split(" "));
			a.addAll(line);
		}
		
		//generates babble
		String end = "";
		Random ran = new Random();
		//generates babble
		for(int i = 0; i < length; i++) {
			end += " ";
			end += a.get(ran.nextInt(a.size()));
		}
		System.out.println(end);
		return end;
	}
	
	//MAPS
	public static Map<String, List<String>> buildBigramSuffixDictionary(File fileName) throws FileNotFoundException{
		HashMap<String, List<String>> m = new HashMap<String, List<String>>();
		Scanner r = new Scanner(fileName);
		List<String> line = new ArrayList<String>();
		
		//list of keys
		List<String> mk = new ArrayList<String>();
 		
		ArrayList<String> a = new ArrayList<String>();
		
		//add words to a
		while(r.hasNext()) {
			line = Arrays.asList(r.nextLine().split(" "));
			a.addAll(line);
		}
		
		//format and add words
		for(int i = 0; i < a.size() - 1; i ++) {
			if(m.containsKey(a.get(i))) {
				m.get(a.get(i)).add(a.get(i+1));
			}else {
				mk = new ArrayList<String>();
				mk.add(a.get(i+1));
				m.put(a.get(i), mk);
			}
		}
		System.out.println(m);

		
		mk.clear();
		mk.add("Nov");
		mk.add("Dec");
		m.put("START :D", mk);
		return m;
	}
	
	
	public static String babble1(String fileName, int length) throws FileNotFoundException{
		File f = new File(fileName);
		Scanner r = new Scanner(f);
		Map<String, List<String>> m = buildBigramSuffixDictionary(f);
		
		String end = "";
		Random ran = new Random();
		String prev = "START :D";
		
		List<String> l;
		Object[] allKeys = m.keySet().toArray();
		for(int i = 0; i < length; i ++) {
			if(!m.containsKey(prev)) {
				prev = (String)allKeys[ran.nextInt(allKeys.length)];
			}else {
				l = m.get(prev);
				prev = l.get(ran.nextInt(l.size()));
			}
			end += prev;
			end += " ";
		}
		System.out.println(end);
		return end;
	}
	
	
	//OTHER
	
	
	public static Map<String, List<String>> buildNgramSuffixDictionary(File fileName, int n) throws FileNotFoundException{
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Scanner reader = new Scanner((fileName));
		List<String> all = new ArrayList<String>(), values;
		String key;
		while (reader.hasNext()){
			Scanner line = new Scanner(reader.nextLine());
			while (line.hasNext())
				all.add(line.next());}

		for(int i = 0; i < n; i++){
			all.add(0, "NON_WORD");
			all.add(all.size(), "NON_WORD");}
		for (int i = 0; i+n < all.size(); i++){
			key = "";
			for (int k = i; k < i+n; k++){
				key += all.get(k) + " ";}
			key = key.trim();
			if (map.containsKey(key)){
				map.get(key).add(all.get(i+n).trim());}
			else{
				values = new ArrayList<String>();
				values.add(all.get(i+n).trim());
				map.put(key, values);}}
		return map;}

	/**
	 * Babble with the nth order.
	 * 
	 * Set the beginning prefix as n "NON_WORDS."
	 * Go through length times and select a random word from the definition of the prefixes.
	 * Make the previous n words the prefix.
	 * Repeat.
	 * Return the babble.
	 * 
	 * @author Suveena Sreenilayan
	 * @throws FileNotFoundException
	 */
	public static String babbleN(File fileName, int n, int length) throws FileNotFoundException{
		Map<String, List<String>> ngram = buildNgramSuffixDictionary(fileName, n);
		String prefix  = "", babble = ""; int i = 0, suffix;
		List<String> suffixes = new ArrayList<>(), result = new ArrayList<>();
		Random rand = new Random();
		for (int k = 0; k < n; k++) prefix += "NON_WORD ";
		prefix = prefix.trim();
		while (i < length){
			suffixes = ngram.get(prefix); prefix = "";
			suffix = rand.nextInt(suffixes.size());
			if (suffixes.get(suffix).equals("NON_WORD")) i--;
			else	result.add(suffixes.get(suffix));
			if (i >= n)
				for (int k = n; k > 0; k--)
					prefix += " " + result.get(result.size() - k);
			else{
				for (int k = i; k < n - 1; k++) prefix += " " + "NON_WORD";
				for (int k = 0; k < result.size(); k++) prefix += " " + result.get(k);}
			prefix = prefix.trim();
			i++;}
		for (int k = 0; k < result.size(); k++)
			babble += result.get(k) + " ";
		return babble.substring(42);}
	
	
	public static String babbleNMinLength(File fileName, int n, int minLength) throws FileNotFoundException{
		Map<String, List<String>> ngram = buildNgramSuffixDictionary(fileName, n);
		String prefix  = "", babble = ""; int i = 0, suffix;
		List<String> suffixes = new ArrayList<>(), result = new ArrayList<>();
		Random rand = new Random();
		for (int k = 0; k < n; k++) prefix += "NON_WORD ";
		prefix = prefix.trim();
		while (i < minLength || result.get(result.size() - 1).charAt(result.get(result.size() - 1).length() - 1) != 46){
			suffixes = ngram.get(prefix); prefix = "";
			suffix = rand.nextInt(suffixes.size());
			if (suffixes.get(suffix).equals("NON_WORD")) i--;
			else	result.add(suffixes.get(suffix));
			if (i >= n)
				for (int k = n; k > 0; k--)
					prefix += " " + result.get(result.size() - k);
			else{
				for (int k = i; k < n - 1; k++) prefix += " " + "NON_WORD";
				for (int k = 0; k < result.size(); k++) prefix += " " + result.get(k);}
			prefix = prefix.trim();
			i++;}
		for (int k = 0; k < result.size(); k++){
			babble += result.get(k) + " ";
			if (babble.charAt(babble.length() - 2) == 46)
				babble += "\n";
		}
		return babble.substring(43);
	}
	
	
	public static String babbleNMinLength(File fileName, int n, int minLength, String userinput) throws FileNotFoundException{
		Map<String, List<String>> ngram = buildNgramSuffixDictionary(fileName, n);
		String prefix  = "", babble = ""; int i = 0, suffix;
		List<String> suffixes = new ArrayList<>(), result = new ArrayList<>();
		Random rand = new Random();
		prefix = userinput;
		String[] userinput2 = userinput.split(" ");
		prefix = prefix.trim();
		while (i < minLength || result.get(result.size() - 1).charAt(result.get(result.size() - 1).length() - 1) != 46){
			suffixes = ngram.get(prefix); prefix = "";
			suffix = rand.nextInt(suffixes.size());
			if (suffixes.get(suffix).equals("NON_WORD")) i--;
			else	result.add(suffixes.get(suffix));
			if (i >= n)
				for (int k = n; k > 0; k--)
					prefix += " " + result.get(result.size() - k);
			else{
				for (int k = i; k < n - 1; k++) prefix += " " + userinput2[k + 1];
				for (int k = 0; k < result.size(); k++) prefix += " " + result.get(k);}
			prefix = prefix.trim();
			i++;}
		for (int k = 0; k < result.size(); k++)
			babble += result.get(k) + " ";
		return babble;
	}
	
	//public static Map
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(babbleNMinLength(new File("text.txt"), 3, 20));
	}
}



