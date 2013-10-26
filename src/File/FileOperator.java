package File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileOperator {

	public FileOperator() {

	}

	public static String readToBuffer(String filePath) throws IOException {
		StringBuffer buffer = new StringBuffer();
		InputStream is = new FileInputStream(filePath);
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine();
		while (line != null) {
			buffer.append(line);
			buffer.append("\n");
			line = reader.readLine();
		}
		reader.close();
		is.close();
		return buffer.toString();
	}
	
	
	public static int[] readCount(String path){
		int[] result=new int[2];
		try {
			BufferedReader reader = null;
			try {
				File file=new File(path);
				// Read file "count.dat" which contains 2 lines, a round count, and a battle count
				reader = new BufferedReader(new FileReader(file));

				// Try to get the counts
				result[0] = Integer.parseInt(reader.readLine());
				result[1] = Integer.parseInt(reader.readLine());
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		} catch (IOException e) {
			// Something went wrong reading the file, reset to 0.
		} catch (NumberFormatException e) {
			// Something went wrong converting to ints, reset to 0
		}
		return result;
	}

	public void writeFile(String filePath, String content) throws IOException {
		FileWriter fw = new FileWriter(filePath);
		fw.write(content);
		fw.close();
	}

	public void writeFitness(String content) {
		String file = "fitness";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
			String text="";
			String str;
			while ((str = br.readLine()) != null) {
				text+=str;
				text+="\n";
			}
			text+=content;
			bw.write(text);
			br.close();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
