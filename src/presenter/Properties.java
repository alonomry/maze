package presenter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class Properties {

	public Properties() {

    try {

	File fXmlFile = new File("lib/properties.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
				
	doc.getDocumentElement().normalize();
	
	NodeList nList = doc.getElementsByTagName("staff");
			
	System.out.println("----------------------------");

		Node nNode = nList.item(0);
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;

			System.out.println("user interface : " + eElement.getElementsByTagName("user_interface").item(0).getTextContent());
			System.out.println("max threads : " + eElement.getElementsByTagName("max_threads").item(0).getTextContent());
			System.out.println("solving algorithm : " + eElement.getElementsByTagName("solving_algorithm").item(0).getTextContent());
		}
//	}
    } catch (Exception e) {
	e.printStackTrace();
    }
  }

}

