import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ParseTweets {
	public static void main(String[] args) throws ParseException {
		String curDir = System.getProperty("user.dir");
		BufferedReader in = null;
		List<String> l = null;
		// Mapping of String->Integer (word -> frequency)
		final TreeMap<String, Integer> frequencyMap = new TreeMap<String, Integer>();

		Path dir = FileSystems.getDefault().getPath(curDir + "/data/js/tweets");
		DirectoryStream<Path> stream = null;
		try {
			stream = Files.newDirectoryStream(dir);
			for (Path path : stream) {
				final File textFile = new File(curDir + "/data/js/tweets/"
						+ path.getFileName());
				// Iterate through each line of the file
				String currentLine;
				StringBuilder stringBuilder = new StringBuilder();
				String ls = System.getProperty("line.separator");
				// skip first line for easy json parsing
				int firstline = 0;
				try {
					in = new BufferedReader(new FileReader(textFile));

					while ((currentLine = in.readLine()) != null) {
						if (firstline != 0) {
							stringBuilder.append(currentLine);
							stringBuilder.append(ls);
						}
						firstline++;
					}
					currentLine = stringBuilder.toString();
					JSONParser parser1 = new JSONParser();
					Object obj = parser1.parse(currentLine);
					JSONArray array = (JSONArray) obj;
					for (int c = 0; c < array.size(); c++) {

						JSONObject obj2 = (JSONObject) array.get(c);
						JSONObject obj3 = (JSONObject) obj2.get("entities");
						JSONArray obj4 = (JSONArray) obj3.get("hashtags");
						if (!obj4.isEmpty()) {
							for (int s = 0; s < obj4.size(); s++) {
								obj2 = (JSONObject) obj4.get(s);
								final String currentWord = obj2.get("text")
										.toString().toLowerCase();
								// Iterate through each word of the current line
								// Delimit words based on whitespace,
								// punctuation, and
								// quotes
								// final StringTokenizer parser = new
								// StringTokenizer(
								// currentLine, " \t\n\r\f.,;:!?'\" ");
								// while (parser.hasMoreTokens()) {
								// final String currentWord =
								// parser.nextToken();

								// if (currentWord.charAt(0) == '#') {
								Integer frequency = frequencyMap
										.get(currentWord);

								// Add the word if it doesn't already exist,
								// otherwise
								// increment the
								// frequency counter.
								if (frequency == null) {
									frequency = 0;
								}
								frequencyMap.put(currentWord, frequency + 1);
							}
						}
					}
					// }

					// }

					// }
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

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		// Display our nice little Map
		frequencyMap.descendingMap();
		l = getWordInDescendingFreqOrder(frequencyMap);
		System.out.println(l);
	}

	static List<String> getWordInDescendingFreqOrder(
			Map<String, Integer> wordCount) {

		// Convert map to list of <String,Integer> entries
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
				wordCount.entrySet());

		// Sort list by integer values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				// compare o2 to o1, instead of o1 to o2, to get descending
				// freq. order
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