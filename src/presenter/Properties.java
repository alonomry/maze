package presenter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


/**
 * 
 *<h2>Properties</h2>
 *reading from Xml file and
 *@param _interface - which interface load (GUI/CLI)
 *@param MaxTreads - how many threads will run simultaneously
 *@param SolvingAlgorithm - which solving algorithm will be used(Bfs, Astar-air, Astar-manhattan)
 *@param GenerateAlgorithm - which generating algorithm will be used(Mymaze , Simplemaze)
 *@param server_ip - server ip for connection
 *@param serever_port - server port for connection
 */
public class Properties {
	
	/** The _interface. */
	String _interface;
	
	/** The Max treads. */
	String MaxTreads;
	
	/** The Solving algorithm. */
	String SolvingAlgorithm;
	
	/** The Generate algorithm. */
	String GenerateAlgorithm;
	
	/** The server_ip. */
	String server_ip;
	
	/** The serever_port. */
	String serever_port;
	
	/**
	 * Instantiates a new properties.
	 *
	 * @param path the file path
	 */
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

	

	/**
	 * Gets the _interface.
	 *
	 * @return the _interface
	 */
	public String get_interface() {
		return _interface;
	}

	/**
	 * Gets the max treads.
	 *
	 * @return the max treads
	 */
	public String getMaxTreads() {
		return MaxTreads;
	}

	/**
	 * Gets the solving algorithm.
	 *
	 * @return the solving algorithm
	 */
	public String getSolvingAlgorithm() {
		return SolvingAlgorithm;
	}

	/**
	 * Sets the _interface.
	 *
	 * @param _interface the new _interface
	 */
	public void set_interface(String _interface) {
		this._interface = _interface;
	}

	/**
	 * Sets the max treads.
	 *
	 * @param maxTreads the new max treads
	 */
	public void setMaxTreads(String maxTreads) {
		MaxTreads = maxTreads;
	}

	/**
	 * Sets the solving algorithm.
	 *
	 * @param solvingAlgorithm the new solving algorithm
	 */
	public void setSolvingAlgorithm(String solvingAlgorithm) {
		SolvingAlgorithm = solvingAlgorithm;
	}



	/**
	 * Gets the generate algorithm.
	 *
	 * @return the generate algorithm
	 */
	public String getGenerateAlgorithm() {
		return GenerateAlgorithm;
	}



	/**
	 * Sets the generate algorithm.
	 *
	 * @param generateAlgorithm the new generate algorithm
	 */
	public void setGenerateAlgorithm(String generateAlgorithm) {
		GenerateAlgorithm = generateAlgorithm;
	}



	/**
	 * Gets the server_ip.
	 *
	 * @return the server_ip
	 */
	public String getServer_ip() {
		return server_ip;
	}



	/**
	 * Sets the server_ip.
	 *
	 * @param server_ip the new server_ip
	 */
	public void setServer_ip(String server_ip) {
		this.server_ip = server_ip;
	}



	/**
	 * Gets the serever_port.
	 *
	 * @return the serever_port
	 */
	public String getSerever_port() {
		return serever_port;
	}



	/**
	 * Sets the serever_port.
	 *
	 * @param string the new serever_port
	 */
	public void setSerever_port(String string) {
		this.serever_port = string;
	}

}

