package test.util;

import java.io.ByteArrayInputStream;
 
import javax.xml.parsers.DocumentBuilderFactory;
 
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
 
public class XMLFormatter {
   Node document;
   boolean keepDeclaration;
   
   public XMLFormatter(String xml) {
      try {
         this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes())).getDocumentElement();
         this.keepDeclaration = xml.startsWith("<?xml");
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public String getXML() {
      try {
         DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
         DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
         LSSerializer writer = impl.createLSSerializer();
         
         writer.getDomConfig().setParameter("format-pretty-print", true); // Set this to true if the output needs to be beautified.
         writer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.
         
         return writer.writeToString(document);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }
   
}