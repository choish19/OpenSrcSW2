import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class MidTerm {
	String path;
	String command;
	
	public MidTerm(String command, String query,String path) throws ParserConfigurationException, SAXException, IOException {
		this.path = path;
		this.command = command;
		if(!(this.command.equals("-q"))) {
			return;
		}
		this.showSnippet();
		
		
	}
	
	public void showSnippet() throws ParserConfigurationException, SAXException, IOException {

		File file = new File(this.path);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document docs = docBuilder.parse(file);
		NodeList docList = docs.getElementsByTagName("doc");
		
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(this.query, true);
		String[] keys; 
		for(int k = 0; k < kl.size(); k++) {
			Keyword kwrd = kl.get(k);
			keys[k] = kwrd.getString();
			
		}
		
		for(int i = 0; i < docList.getLength(); i++) {
			NodeList doc = docList.item(i).getChildNodes(); //i번째 doc태그의 자식 노드들 불러오기
			for(int j = 0; j < doc.getLength(); j++) {
				String title = "";
				if(doc.item(j).getNodeName().equals("title")) title = doc.item(j).getTextContent();
				if(doc.item(j).getNodeName().equals("body")){ //자식 노드 중 body태그의 노드 찾기
					Node bodyNode = doc.item(j);
					String bodyText = bodyNode.getTextContent();
					int idx = 0;
					String snippet = "";
					int max_score = 0;
					while(bodyText.charAt(i+30) != '\0') { //30번째 자리가 text의 끝이라면
						int score = 0;
						String tmp = bodyText.substring(idx, i+29);
						for(int k = 0; k < keys.length; k++) {
							if(tmp.contains(keys[k])) {
								score++;
							}
						}
						if(max_score < score) {
							snippet = tmp;
						}
						idx++;
					}
					if(max_score == 0) {
						break;
					}
					System.out.println(title + "," + snippet + "," + max_score);
					break;
				}
			}
		}
	}
}


