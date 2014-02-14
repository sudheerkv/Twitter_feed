import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class trend {
	public static void main(String[] args) {
		String curDir = System.getProperty("user.dir");
		final File textFile = new File(curDir +"/src/Feed.txt");
		 BufferedReader in=null;

		// Mapping of String->Integer (word -> frequency)
		final TreeMap<String, Integer> frequencyMap = new TreeMap<String, Integer>();

		// Iterate through each line of the file
		String currentLine;
		try {
			in = new BufferedReader(new FileReader(textFile));

			while ((currentLine = in.readLine()) != null) {

				// Remove this line if you want words to be case sensitive
				currentLine = currentLine.toLowerCase();

				// Iterate through each word of the current line
				// Delimit words based on whitespace, punctuation, and quotes
				final StringTokenizer parser = new StringTokenizer(currentLine,
						" \t\n\r\f.,;:!?' ");
				while (parser.hasMoreTokens()) {
					final String currentWord = parser.nextToken();

					if(!currentWord.contains("@") && currentWord.charAt(0)!= '@' && currentWord.charAt(0) == '#'){
					Integer frequency = frequencyMap.get(currentWord);

					// Add the word if it doesn't already exist, otherwise
					// increment the
					// frequency counter.
					if (frequency == null) {
						frequency = 0;
					}
					frequencyMap.put(currentWord, frequency + 1);}

				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Display our nice little Map
		frequencyMap.descendingMap();
		List<String> l = getWordInDescendingFreqOrder(frequencyMap);
		System.out.println(l);
	}
	static List<String> getWordInDescendingFreqOrder(Map<String, Integer> wordCount) {

	    // Convert map to list of <String,Integer> entries
	    List<Map.Entry<String, Integer>> list = 
	        new ArrayList<Map.Entry<String, Integer>>(wordCount.entrySet());

	    // Sort list by integer values
	    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	            // compare o2 to o1, instead of o1 to o2, to get descending freq. order
	            return (o2.getValue()).compareTo(o1.getValue());
	        }
	    });
	    // Populate the result into a list
	    List<String> result = new ArrayList<String>();
	    for (Map.Entry<String, Integer> entry : list) {
	        result.add(entry.getKey());
	    }
	    return result;
	}
}