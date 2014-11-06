package convertData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConvertData {
	/**
	 * only want fields 1 = userID,2 = movieID , 3 = rating, 4 = timestamp and
	 * replace tabs with ,
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(
				"data/u.data"));
		BufferedWriter bw = new BufferedWriter(new FileWriter(
				"data/nonMoversFormatted.csv"));

		String line;

		while ((line = br.readLine()) != null) {
			String[] values = line.split("\\t", -1);
			bw.write(values[0] + "," + values[1] + "\n");

		}

		br.close();
		bw.close();

	}

}
