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
	String server_ip;
	String serever_port;
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
			setServer_ip(eElement.getElementsByTagName("server_ip").item(0).getTextContent());
			setSerever_port(eElement.getElementsByTagName("server_port").item(0).getTextContent());
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



	public String getServer_ip() {
		return server_ip;
	}



	public void setServer_ip(String server_ip) {
		this.server_ip = server_ip;
	}



	public String getSerever_port() {
		return serever_port;
	}



	public void setSerever_port(String string) {
		this.serever_port = string;
	}

}

