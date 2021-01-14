package z3solver.z3Parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Z3Parser {

    int numOfFBs;
    int[][] intensity;

    public static int[][] calculateIntensity(Map<String, List<String>> dataConnectionMap,
                                             Map<String, String> functionMap) {
        int[][] intensity = new int[functionMap.size()][functionMap.size()];

        for (int i = 0; i < functionMap.size(); i++) {
            for (int j = 0; j < functionMap.size(); j++) {
                intensity[i][j] = 0;
            }
        }

        Iterator<Map.Entry<String, List<String>>> it = dataConnectionMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> pair = it.next();

            List<String> list = pair.getValue();
            String temp = functionMap.get(pair.getKey());

            Scanner sc = new Scanner(temp);
            sc.skip("[^0-9]*");
            int row = sc.nextInt();

            for (String s : list) {
                temp = functionMap.get(s);
                Scanner sc1 = new Scanner(temp);
                sc1.skip("[^0-9]*");
                int col = sc1.nextInt();
                intensity[row - 1][col - 1]++;
            }

            it.remove(); // avoids a ConcurrentModificationException
        }

        for (int i = 0; i < functionMap.size(); i++) {
            for (int j = 0; j < functionMap.size(); j++) {
                if (intensity[i][j] != 0) {
                    intensity[j][i] = intensity[i][j];
                }
            }
        }

        return intensity;
    }

    public static Map<String, List<String>> findDC(Element root) {
        NodeList nList = root.getElementsByTagName("Connection");
        String source;
        String destination;
        Map<String, List<String>> dataConnectionMap = new HashMap<>();

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getParentNode().getParentNode().getNodeName().equals("SubAppNetwork")
                    && node.getParentNode().getNodeName().equals("DataConnections")) {

                Element element = (Element) node;

                source = element.getAttribute("Source").split("\\.")[0];
                destination = element.getAttribute("Destination").split("\\.")[0];
                if (dataConnectionMap.containsKey(source)) {
                    dataConnectionMap.get(source).add(destination);
                } else {
                    List<String> destinations = new ArrayList<>();
                    destinations.add(destination);
                    dataConnectionMap.put(source, destinations);
                }

            }
        }

        return dataConnectionMap;
    }

    public static Map<String, String> findFB(Element root) {
        NodeList nList = root.getElementsByTagName("FB");
        Map<String, String> functionBlockMap = new HashMap<>();

        for (int i = 0, j = 1; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getParentNode().getNodeName().equals("SubAppNetwork")) {
                Element element = (Element) node;
                functionBlockMap.put(element.getAttribute("Name"), "x" + j);
                j++;
            }
        }

        System.out.println(functionBlockMap.size());

        for (Map.Entry<String, String> entry : functionBlockMap.entrySet()) {
            System.out.println(entry.getValue() + "---" + entry.getKey());
        }

        return functionBlockMap;
    }

    public void parsing(String filepath)
            throws ParserConfigurationException, SAXException, IOException {

        // Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Build Document
        Document document = builder.parse(new File(filepath));

        // Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        // Here comes the root node
        Element root = document.getDocumentElement();

        setNumOfFBs(findFB(root).size());
        setIntensity(calculateIntensity(findDC(root), findFB(root)));

    }

    public int getNumOfFBs() {
        return numOfFBs;
    }

    public void setNumOfFBs(int numOfFBs) {
        this.numOfFBs = numOfFBs;
    }

    public int[][] getIntensity() {
        return intensity;
    }

    public void setIntensity(int[][] intensity) {
        this.intensity = intensity;
    }

}
