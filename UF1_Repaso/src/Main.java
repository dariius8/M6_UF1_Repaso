import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) {
		crearFichero();
		leerFichero_crearXML();
		leerXML();
	}

	public static void crearFichero() {
		FileOutputStream fos = null;
		ObjectOutputStream salida = null;
		Persona p;
		try {
			// creamos el fichero
			fos = new FileOutputStream("..\\UF1_Repaso\\src\\FichPersona.dat");
			salida = new ObjectOutputStream(fos);
			// creamos los objetos Persona y los guardamos en el fichero
			p = new Persona("Leo Messi", 32);
			salida.writeObject(p);
			p = new Persona("Luis Suarez", 32);
			salida.writeObject(p);
			p = new Persona("Antoine Griezmann", 28);
			salida.writeObject(p);
			System.out.println("---Insertamos los objetos Persona en el fichero dat---\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (salida != null)
					salida.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void leerFichero_crearXML() {
		FileInputStream fis = null;
		ObjectInputStream entrada = null;
		Persona p;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			// definimos el elemento raíz del documento
			Element raiz = doc.createElement("personas");
			doc.appendChild(raiz);
			try {
				// lectura del fichero dat y guardamos en el xml
				fis = new FileInputStream("..\\UF1_Repaso\\src\\FichPersona.dat");
				entrada = new ObjectInputStream(fis);
				System.out.println("---Leemos los objetos Persona del fichero dat---\n");
				while (true) {
					// definimos que cada objeto que lee sea de tipo persona
					p = (Persona) entrada.readObject();
					// mostramos cada objeto persona (toString)
					System.out.println(p);
					// elemento persona
					Element persona = doc.createElement("persona");
					raiz.appendChild(persona);
					// elemento nombre
					Element nombre = doc.createElement("nombre");
					nombre.appendChild(doc.createTextNode(p.getNombre()));
					persona.appendChild(nombre);
					// elemento edad (lo pasamos a string)
					String valor = String.valueOf(p.getEdad());
					Element edad = doc.createElement("edad");
					edad.appendChild(doc.createTextNode(valor));
					persona.appendChild(edad);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			// codigo necesario para finalizar la creación del XML
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("..\\UF1_Repaso\\src\\FichPersona.xml"));
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public static void leerXML() {
		File file = new File("..\\UF1_Repaso\\src\\FichPersona.xml");
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			// almacenamos los nodos para luego mostrar la
			// cantidad de ellos con el método getLength()
			NodeList nList = doc.getElementsByTagName("persona");
			System.out.println("\n---Leemos el contenido del fichero xml---\n");
			System.out.println("Numero de personas: " + nList.getLength() + "\n");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Nombre: " + eElement.getElementsByTagName("nombre").item(0).getTextContent());
					System.out
							.println("Edad: " + eElement.getElementsByTagName("edad").item(0).getTextContent() + "\n");
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
