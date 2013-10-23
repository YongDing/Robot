package File;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

}
