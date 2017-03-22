import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLHelper {

    public static void main(String[] args) {
        HashMap<String, String> values = new HashMap<String, String>();
        String xmlString = "<ROOT><name>RNA-Seq Unified Mapper</name><developer>Grant, Gregory R., Ph.D.</developer><developer>DeLaurentis, Michael</developer><developer>Pizarro, Angel</developer><eagle-i_ID>http://eagle-i.itmat.upenn.edu/i/0000013a-3178-df7b-01af-beb880000000</eagle-i_ID><used_by>University of Pennsylvania</used_by><URL>https://github.com/PGFI/rum</URL><URL>http://www.cbil.upenn.edu/RUM/userguide.php</URL></ROOT>";
        Document xml = convertStringToDocument(xmlString);
        Node user = xml.getFirstChild();
        NodeList childs = user.getChildNodes();
        Node child;
        for (int i = 0; i < childs.getLength(); i++) {
            child = childs.item(i);
            System.out.println(child.getNodeName());
            System.out.println(child.getTextContent());
            values.put(child.getNodeName(), child.getTextContent());
        }

    }

    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(
                    xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}