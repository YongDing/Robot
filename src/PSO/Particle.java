package PSO;

import java.io.FileWriter;
import java.io.IOException;

import ANN.Network;

public class Particle {
	
	int input_number = Network.input_number;
	int hidden_number = Network.hidden_number;
	double[] position=new double[(input_number+1)*hidden_number+hidden_number+1];
	
	public Particle(double[] src) {
		System.arraycopy(src, 0, position, 0,src.length);
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] src) {
		
		System.arraycopy(src, 0, position, 0,src.length);
	}

	public String generateText() {
		String text = "";
		for(int i=0;i<hidden_number;i++){
			text+="hidden_"+i+"_weights\n";
			for(int j=0;j<(input_number+1);j++){
				text+=position[i*j+j];
				text+="\n";
			}
		}
		text+="output_weights\n";
		for(int i=0;i<(hidden_number+1);i++){
			text+=position[(input_number+1)*hidden_number+i];
			text+="\n";
		}
		return text;
	}
	
	public void print(){
		for(int i=0;i<position.length;i++){
			System.out.println(position[i]);
		}
	}

	public void writeFile(String filePath, String content) throws IOException {
		FileWriter fw = new FileWriter(filePath);
		fw.write(content);
		fw.close();
	}
}
