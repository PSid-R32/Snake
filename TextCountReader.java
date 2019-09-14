import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextCountReader {

	// Read all characters of a file in an array
	public static char[] readCharacters(FileReader fr) {
		List<Character> charList = new ArrayList<Character>();
		try {
			int b;
			char c;
			while((b = fr.read()) != -1) {
				c = (char)b;
				charList.add(c);
			}			
		} catch (IOException e) {
			System.out.println("File I/O Error: " + e.getMessage());
			return null;
		}
		char[] chars = new char[charList.size()];
		for(int i=0; i < chars.length; i++) {
			chars[i] = charList.get(i);
		}
		return chars;
	}
	
	// Compute count of all characters as a list of
	// single huffman tree nodes
	public static List<HuffmanNode> readTextCount(char[] chars){
		char c;
		List<HuffmanNode> nodes = new ArrayList<HuffmanNode>();
		for(int i=0; i < chars.length; i++) {
			c = chars[i];
			boolean found = false;
			for(HuffmanNode n : nodes) {
				if(n.text.equals(c+"")) {
					n.count += 1;
					found = true;
					break;
				}
			}
			if(!found) {
				HuffmanNode n = new HuffmanNode(c+"", 1);
				nodes.add(n);
			}
		}			
		return nodes;
	}
	
	
}
