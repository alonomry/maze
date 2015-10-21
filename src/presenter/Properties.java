package presenter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class Properties {
	String _interface;
	String MaxTreads;
	String SolvingAlgorithm;
	String GenerateAlgorithm;

	public Properties(String path) {

    try {

	File fXmlFile = new File(path);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
				
	doc.getDocumentElement().normalize();
	
	NodeList nList = doc.getElementsByTagName("staff");
			
		Node nNode = nList.item(0);
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;

			set_interface(eElement.getElementsByTagName("user_interface").item(0).getTextContent());
			setMaxTreads(eElement.getElementsByTagName("max_threads").item(0).getTextContent());
			setSolvingAlgorithm(eElement.getElementsByTagName("solving_algorithm").item(0).getTextContent());
			setGenerateAlgorithm(eElement.getElementsByTagName("generate_algorithm").item(0).getTextContent());
				}
    } catch (Exception e) {
	e.printStackTrace();
    }
  }

	

	public String get_interface() {
		return _interface;
	}

	public String getMaxTreads() {
		return MaxTreads;
	}

	public String getSolvingAlgorithm() {
		return SolvingAlgorithm;
	}

	public void set_interface(String _interface) {
		this._interface = _interface;
	}

	public void setMaxTreads(String maxTreads) {
		MaxTreads = maxTreads;
	}

	public void setSolvingAlgorithm(String solvingAlgorithm) {
		SolvingAlgorithm = solvingAlgorithm;
	}



	public String getGenerateAlgorithm() {
		return GenerateAlgorithm;
	}



	public void setGenerateAlgorithm(String generateAlgorithm) {
		GenerateAlgorithm = generateAlgorithm;
	}

}

