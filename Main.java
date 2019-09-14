import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.List;

public class Main {
	
	// Compress data from input FileReader and write compressed data to output
	// FileWriter
	public static void encode(FileReader fr, FileWriter fw) {
		// Store all characters from input file in an array
		char[] chars = TextCountReader.readCharacters(fr);
		
		// create list of single huffman nodes for character count
		List<HuffmanNode> nodes = TextCountReader.readTextCount(chars);
		
		// display count frequencies to console screen 
		System.out.println("Counting frequency");
		for(HuffmanNode n : nodes) {
			System.out.println(n.text + " " + n.count);
		}
		// add single nodes to priority queue
		PriorityQueue pq = new PriorityQueue();
		for (HuffmanNode singleNode : nodes) {
			pq.insert(singleNode);
		}
		//System.out.println("\n===Removing and combining===\n");

		while (pq.getSize() > 1) {
			HuffmanNode left = pq.removeMin();
			HuffmanNode right = pq.removeMin();
			// the other constructor takes care of the combination
			HuffmanNode combinedNode = new HuffmanNode(left, right);
			pq.insert(combinedNode);
		}
		System.out.println("\n===Printing Huffman Code===\n");		
		HuffmanNode huffTree = pq.removeMin();
		huffTree.generateCode();
		huffTree.print();
		
		// Generate the bits string
		System.out.println("\n===Generated bit string===");
		String bitString = "";
		for(int i=0; i < chars.length; i++) {
			char c = chars[i];
			for(HuffmanNode n : nodes) {
				if(n.text.equals(c+"")) {
					bitString += n.code;
					break;
				}
			}
		}
		System.out.println(bitString);
		
		// Generate base64 encoding of the bitstring
		BigInteger bi = new BigInteger(bitString, 2);
		byte[] bytes = bi.toByteArray();
		Encoder encoder = Base64.getEncoder();
		String base64String = encoder.encodeToString(bytes);
		
		// write the compressed base64 data along with code header to output file
		try {
			for (HuffmanNode n : nodes) {
				if(n.text.equals(",")){
					fw.write("\"\"" + n.code + ",");
				}
				else
					fw.write(n.text + n.code + ",");
			}
			fw.write(bitString.length()+",#"+base64String);
		} catch (IOException e) {
			System.out.println("File I/O Error: " + e.getMessage());
			System.exit(1);
		}
	}
	
	// Decompress data from input FileReader and write compressed data to output
	// FileWriter
	public static void decode(FileReader fr, FileWriter fw) {
		// Read entire compressed file as a whole string
		String compressedString = "";
		int b;
		char c;
		try {
			while((b = fr.read()) != -1) {
				c = (char)b;
				compressedString += c;
			}
			fr.close();
		} catch (IOException e) {
			System.out.println("File I/O Error: " + e.getMessage());
			System.exit(1);
		}
		// Extract the code header
		String codeHeader = compressedString.substring(0, compressedString.indexOf('#'));
		// Split the header into huffman node data
		String[] data = codeHeader.split(",");
		// save the char and huffman code in a list of huffman node
		List<HuffmanNode> huffmanNodes = new ArrayList<HuffmanNode>();
		System.out.println("===== Huffman Coding =====");
		for(int i=0; i < data.length-1; i++) {
			String dataStr = data[i];
			String code=null;
			if(dataStr.startsWith("\"\"")) {
				c = ',';
				code = dataStr.substring(2);
			}
			else {
				c = dataStr.charAt(0);
				code = dataStr.substring(1);
			}
			HuffmanNode n = new HuffmanNode(c+"", 0);
			n.code = code;
			System.out.println(n.text+" " + n.code);
			huffmanNodes.add(n);
		}
		// save the number of bits in the encoded bits string
		int bitStringLength = Integer.parseInt(data[data.length-1]);
		//System.out.println(bitStringLength);
		
		// Extract the base64 encoded data
		System.out.println("\n===== Encoded Base64 Data =====");
		String base64Encoded = compressedString.substring(compressedString.indexOf('#')+1);
		System.out.println(base64Encoded);
		
		// Decode the base64 string to bit string
		System.out.println("\n===== Decoded bit string =====");
		Decoder decoder = Base64.getDecoder();
		byte[] decoded = decoder.decode(base64Encoded);
		BigInteger bi2 = new BigInteger(decoded);
		String bitString = bi2.toString(2);
		
		// pad with necessary 0's at the beginning
		int diffLen = bitStringLength - bitString.length();
		for(int j=0; j < diffLen; j++) {
			bitString = "0"+bitString;
		}
		System.out.println(bitString);
		
		// decode the bit string to actual characters and write to output file 
		while(bitString.length() > 0) {
			for(HuffmanNode n : huffmanNodes) {
				String code = n.code;
				String text = n.text;				
				if(bitString.startsWith(code)) {
					try {
						fw.write(text);
						bitString = bitString.substring(code.length());
					} catch (IOException e) {
						System.out.println("File I/O Error: " + e.getMessage());
						System.exit(1);
					}
					break;
				}
			}
		}
		
	}
	
	public static void showUsageAndExit() {
		System.out.println("Usage: java Main OPTION INPUT-FILE OUTPUT-FILE");
		System.out.println("where arguments are as below:");
		System.out.println("OPTION  -c(compress) or -d(decompress)");
		System.out.println("INPUT-FILE  path of input file to compress/decompress");
		System.out.println("OUTPUT-FILE path of output file to compress/decompress");
		System.exit(1);
	}
	
	public static void main(String[] args) {
		// check input and output file are passed as command line arguments
		if(args.length != 3) {
			showUsageAndExit();
		}
		// Save the option
		String option = args[0];
		
		// save the input and output file names
		String inputFilename = args[1];
		String outputFilename = args[2];
		
		// validate the option
		if(!option.equals("-c") && !option.equals("-d")) {
			showUsageAndExit();
		}						
		
		// create FileReader from input file
		FileReader fr = null;
		try {
			fr = new FileReader(new File(inputFilename));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: could not find file " + inputFilename);
			System.exit(1);
		}
		
		// create FileWriter for output file
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(outputFilename));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: could not find file " + inputFilename);
			System.exit(1);
		} catch (IOException e) {
			System.out.println("File I/O error: " + e.getMessage());
			System.exit(1);
		}
				
		// compress/decompress as per option
		if (option.equals("-c")) {
			encode(fr, fw);
		} else {
			decode(fr, fw);
		}
				
		// close the input/output file stream
		try {
			fr.close();
			fw.close();
		} catch (IOException e) {			
		}
		
		/*
		// create list of single huffman nodes for character count
		List<HuffmanNode> nodes = TextCountReader.readTextCount(fr);
		
		
		// Your program should generate this count table by some other methods
		String[] charList = { " ", "s", "t", "a", "c", "e", "r", "i", "n", "u", "m", "o", "p", "d", "l", "v", "y" };
		int[] countList = { 9, 6, 6, 5, 5, 5, 5, 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 };
		
		// initilize PriorityQueue
		PriorityQueue q = new PriorityQueue();
		
		// inserting all the single nodes
		System.out.println("===Adding single nodes===\n");
		
		for (int i = 0; i < charList.length; i++) {
			HuffmanNode singleNode = new HuffmanNode(charList[i], countList[i]);
			System.out.println("Adding \"" + charList[i] + "\"");
			q.insert(singleNode);
			q.printHeap();
		}
		
		System.out.println("\n===Removing and combining===\n");
		
		while (q.getSize() > 1) {
			HuffmanNode left = q.removeMin();
			System.out.println("Removed " + left + " from the heap");
			q.printHeap();
			HuffmanNode right = q.removeMin();
			System.out.println("Removed " + right + " from the heap");
			q.printHeap();
			// the other constructor takes care of the combination
			HuffmanNode combinedNode = new HuffmanNode(left, right);
			q.insert(combinedNode);
			System.out.println("Inserted " + combinedNode + " into the heap");
			q.printHeap();
			System.out.println();
		}
		System.out.println("\n===Printing Huffman Code===\n");
		HuffmanNode huffTree = q.removeMin();
		huffTree.print();
		*/
	}
}