import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class kuir {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		String command = args[0];   
		String path = args[1];
		if(command.equals("-m")) {
			String command2 = args[2];
			String query = args[3];
			MidTerm midTerm = new MidTerm(command2, query, path);
		}
	}

}
