/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.BetweennessCentrality;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import java.io.IOException;

/**
 *
 * @author taufikalkaff
 */
public class GraphExplore {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new GraphExplore();

    }

    public void bacaFile() throws IOException {
        String filePath = "graph.txt";
        Graph g = new DefaultGraph("g");
        FileSource fs = FileSourceFactory.sourceFor(filePath);

        fs.addSink(g);

        try {
            fs.begin(filePath);

            while (fs.nextEvents()) {
                // Optionally some code here ...
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fs.end();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fs.removeSink(g);
        }
        g.display();

    }

    public GraphExplore() {
        Graph graph = new SingleGraph("tutorial 1");

        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.display();


                Node A = graph.addNode("A");
                Node B = graph.addNode("B");
                Node E = graph.addNode("E");
                Node C = graph.addNode("C");
                Node D = graph.addNode("D");


        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        graph.addEdge("AD", "A", "D");
        graph.addEdge("DE", "D", "E");
        graph.addEdge("DF", "D", "F");
        graph.addEdge("EF", "E", "F");



        BetweennessCentrality bcb = new BetweennessCentrality();
        bcb.setWeightAttributeName("weight");
        bcb.setWeight(A, B, 1);
                bcb.setWeight(B, E, 6);
                bcb.setWeight(B, C, 5);
                bcb.setWeight(E, D, 2);
                bcb.setWeight(C, D, 3);
                bcb.setWeight(A, E, 4);
        bcb.init(graph);
        bcb.compute();

        System.out.println("A="+ graph.getNode("A").getAttribute("Cb"));
                System.out.println("B="+ graph.getNode("B").getAttribute("Cb"));
                System.out.println("C="+ graph.getNode("C").getAttribute("Cb"));
                System.out.println("D="+ graph.getNode("D").getAttribute("Cb"));
                System.out.println("E="+ graph.getNode("E").getAttribute("Cb"));



        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());

        }


        explore(graph.getNode("A"));
        try {
            bacaFile();
        } catch (IOException ex) {
            Logger.getLogger(GraphExplore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void explore(Node source) {
        Iterator<? extends Node> k = source.getNeighborNodeIterator();

        while (k.hasNext()) {
            Node next = k.next();
            next.setAttribute("ui.class", "marked");
            sleep();

        }
    }

    protected void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }
    protected String styleSheet =
            "node {"
            + "   fill-color: black;"
            + "}"
            + "node.marked {"
            + "   fill-color: red;"
            + "}";
}
