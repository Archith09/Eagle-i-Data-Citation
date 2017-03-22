import java.util.Arrays;
import java.util.regex.Pattern;
import java.io.*;

import javax.annotation.Generated;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.sparql.core.Var;
import org.apache.tomcat.util.file.Matcher;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

import com.fasterxml.jackson.databind.util.JSONPObject;

import testJena.TestJena;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class EagleServlet extends HttpServlet { 
	/**
	 *
	 */
	
	private static final long serialVersionUID = -4502955968124312019L;

	public enum outFormat{JSONFORMAT, STRINGFORMAT};
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException { 

		//get the resource
		String a = "<";
		String b = ">";
		String eagleID = request.getParameter("resource");
		String resource= a.concat(eagleID).concat(b);
		
		//get substring of eagleID (eg: eagleID = http:/eagle-i.itmat.upenn.edu/i/0000013a-3178-df7b-01af-beb880000000, substring = 0000013a-3178-df7b-01af-beb880000000)
		String filename = eagleID.substring(eagleID.lastIndexOf('/') + 1).trim();				
		
		System.out.println("Resource-->"+resource);
		
		//System.out.println("Citation-->" + TestJena.getCitation(resource, TestJena.outFormat.JSONFORMAT));
		
		//build the response/citation
		//String str = "{\"name\":[{\"content\":\"Benchmarker for Evaluating the Effectiveness of RNA-Seq Software\"}],\"version\":[],\"developer\":[{\"content\":\"Grant, Gregory R., Ph.D.\"}],\"manufacturer\":[],\"used_by\":[{\"content\":\"Penn Center for Bioinformatics\"}],\"URL\":[{\"content\":\"http://www.cbil.upenn.edu/BEERS/\"}],\"eagle-i_ID\":\"http://eagle-i.itmat.upenn.edu/i/0000013d-4bda-0e4b-ae62-bb6880000000\"}";
		//String str = "{\"glossary\": {\"title\": \"example glossary\",\"GlossDiv\": {\"title\": \"S\",\"GlossList\": {\"GlossEntry\": {\"ID\": \"SGML\",\"SortAs\": \"SGML\",\"GlossTerm\": \"Standard Generalized Markup Language\",\"Acronym\": \"SGML\",\"Abbrev\": \"ISO 8879:1986\",\"GlossDef\": {\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",	\"GlossSeeAlso\": [\"GML\", \"XML\"]},\"GlossSee\": \"markup\"}} } } }";
		//String my_new_str = str.replaceAll("\"", "'");
		//System.out.println("my_new_str-->" + my_new_str);
		String str1 = TestJena.getCitation(resource, TestJena.outFormat.JSONFORMAT);
		//System.out.println("str-->" + str);
		
		//System.out.println("str1-->" + str1);
		
		//convert JSON to xml
		JSONObject o=new JSONObject();
		try {
			//o = new JSONObject(my_new_str);
			//o = new JSONObject(str);
	        o.put("ROOT", (new JSONObject(str1)));
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		String xml = null;
		try {
			xml = org.json.XML.toString(o);
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		//write xml to a document
		BufferedWriter out = new BufferedWriter(new FileWriter("/Users/archith/Desktop/"+filename+".xml"));
	    try { 
	        out.write(xml);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        out.close();
	    }
		System.out.println(xml);
		
		//convert xml to string
		StringBuffer xmlString = null;
		try { 
			BufferedReader in = new BufferedReader(new FileReader("/Users/archith/Desktop/"+filename+".xml")); 
			StringBuffer output = new StringBuffer(); 
			String st; 
			while ((st=in.readLine()) != null) { 
			output.append(st); 
			}
			xmlString = output;
			//System.out.println("xml string-->"+output.toString()); 
			in.close(); 
		} 
		catch (Exception fx) { 
			System.out.println("Exception " + fx.toString()); 
		}
		
		//convert xmlString to Key-Value Pairs
		HashMap<String, String> values = new HashMap<String, String>();
        String xmlString1 = xmlString.toString();
		Document xml1 = convertStringToDocument(xmlString1);
        Node user = xml1.getFirstChild();
        NodeList childs = user.getChildNodes();
        Node child;
        BufferedWriter outRIS = new BufferedWriter(new FileWriter("/Users/archith/Desktop/" + filename + ".ris"));
        BufferedWriter outBIB = new BufferedWriter(new FileWriter("/Users/archith/Desktop/" + filename + ".bib"));
        outBIB.write("@resource{eagle-id:" + eagleID + "," + '\n');
        
        for (int i = 0; i < childs.getLength(); i++) {
            child = childs.item(i);
            //System.out.println(child.getNodeName());
            //System.out.println(child.getTextContent());
            
            //generate RIS and BibTEX files
    	    try { 
    	    	String s = child.getNodeName();
    	        outRIS.write(s.substring(0, Math.min(s.length(), 2)).toUpperCase());
    	        outRIS.write("  - ");
    	        outRIS.write(child.getTextContent());
    	        outRIS.write('\n');
    	        
    	        outBIB.write(child.getNodeName());
    	        //outBIB.write('\t' + '\t');
    	        outBIB.write("  = {");
    	        outBIB.write(child.getTextContent());
    	        
    	        if( i != (childs.getLength())-1 ){
    	        	outBIB.write("},");
    	        }
    	        else{
    	        	outBIB.write("}");
    	        }
    	        
    	        outBIB.write('\n');
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	    
            values.put(child.getNodeName(), child.getTextContent());
        }
        outRIS.close();
        
        outBIB.write("}");
        outBIB.close();
        
		//extract data without tags from xml to generate normal citation string
		StringBuffer normalCitation = new StringBuffer(); 
		String reg = "<.*?>(.*?)</.*?>";
        Pattern p = Pattern.compile(reg);
        java.util.regex.Matcher m = p.matcher(xmlString);
        while(m.find())
        {
        	String s1 = m.group(1);
            //System.out.println("data-->"+s1);
            normalCitation.append(s1);
            normalCitation.append(";");
        }
		
		//send normal citation back to web page as response
		System.out.println("normal citation-->" + normalCitation);
		response.setContentType("text/html");
		response.getWriter().print(normalCitation);
		
		//create an alert message
//		PrintWriter out1 = response.getWriter();  
//		response.setContentType("text/html");  
//		out1.println("<script type=\"text/javascript\">");  
//		out1.println("alert('Citation is generated. Please click the link if you wish to see in another format.');");  
//		out1.println("</script>");
		//response.getWriter().print(normalCitation);
		
		// Functionality to delete files with extension .xml, .ris, .bib which are older than 5 minutes
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException { 
	}

	public static void main (String args[]) throws IOException{
		TestJena test = new TestJena();
		System.out.println(test);
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