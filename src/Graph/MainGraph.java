/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.util.Iterator;
import java.util.Vector;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import com.google.common.collect.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author taufikalkaff
 */
public class MainGraph {

    String weightAttributeName = "weight";

    public static void main(String args[]) {
        MainGraph objGraph = new MainGraph();
        Graph graph_utama = objGraph.createGraph();
        objGraph.mergeNode(graph_utama);
    }

    public Graph createGraph() {
        Graph graph = new SingleGraph("graphUtama");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.display();

        Node A = graph.addNode("A");
        Node B = graph.addNode("B");
        Node C = graph.addNode("C");
        Node D = graph.addNode("D");
        Node E = graph.addNode("E");
        Node F = graph.addNode("F");

        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("DE", "D", "E");
        graph.addEdge("DF", "D", "F");
        graph.addEdge("EF", "E", "F");

        setWeight(A, B, 1);
        setWeight(B, C, 5);
        setWeight(C, A, 6);
        setWeight(C, D, 2);
        setWeight(D, E, 3);
        setWeight(D, F, 4);
        setWeight(E, F, 1);

        return graph;
    }

    public Vector createCluster(Graph graph) {
        Vector clusters = new Vector();

        for (Node n : graph) {

            Iterator<? extends Node> nodes = n.getNeighborNodeIterator();
            System.out.println(n.getId());
            while (nodes.hasNext()) {
                Node n_tetangga = nodes.next();
                double modularity = getModularity(n, n_tetangga);
                System.out.println("tetangga=" + n_tetangga.getId());
            }
        }

        return clusters;
    }

    public void mergeNode(Graph graph) {
        Node a = graph.getNode("A");
        Node b = graph.getNode("B");
        Map<String, Object> alledge = new HashMap<String, Object>();

        Iterator<? extends Node> nodes_A = a.getNeighborNodeIterator();
        Iterator<? extends Node> nodes_B = b.getNeighborNodeIterator();

        HashSet<Object> set = new HashSet<Object>();

        // Merge
        while (nodes_A.hasNext()) {
            Node to = nodes_A.next();

            System.out.println("edgeA: " + a.getId() + "-" + to.getId() + " : " + weight(a, to) + " : " + a.getEdgeBetween(to).getId());
            //set.add(to);
            alledge.put(a.getEdgeBetween(to).getId(), weight(a, to));
        }
        while (nodes_B.hasNext()) {
            Node to = nodes_B.next();

            System.out.println("edgeB: " + b.getId() + "-" + to.getId() + " : " + weight(b, to) + " : " + b.getEdgeBetween(to).getId());
            //set.add(to);
            if (alledge.containsKey(b.getEdgeBetween(to).getId())) {
                Double weightbefore = (Double) alledge.get(b.getEdgeBetween(to).getId());
                //alledge.remove(b.getEdgeBetween(to).getId());
                alledge.put(b.getEdgeBetween(to).getId(), weight(b, to) + weightbefore);
            } else {
                alledge.put(b.getEdgeBetween(to).getId(), weight(b, to) );
            }
        }

        // New iterator of unique objects
        Iterator<Object> nodes_all = set.iterator();


        while (nodes_all.hasNext()) {
            Node n = (Node) nodes_all.next();
            System.out.println("tetangga=" + n.getId());
        }

        for (Map.Entry<String, Object> entry : alledge.entrySet()) {
            System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
        }


    }

    public void clusterGraph(Graph graph) {
        Graph graphCluster = new SingleGraph("graphUtama");
        graphCluster.setAutoCreate(true);
        graphCluster.setStrict(false);
        graphCluster.display();

        Cluster clusterNode = new Cluster(null);

        //sebelum ini cari dulu siapa aja group clusternya pake modularity

    }

    private double getModularity(Node n, Node n_tetangga) {
        int m = 3;

        return 0.0;
    }

    public void setWeight(Node from, Node to, double weight) {
        if (from.hasEdgeBetween(to.getId())) {
            from.getEdgeBetween(to.getId()).setAttribute(weightAttributeName,
                    weight);

        }
    }

    public double weight(Node from, Node to) {
        Edge edge = from.getEdgeBetween(to.getId());

        if (edge != null) {
            if (edge.hasAttribute(weightAttributeName)) {
                return edge.getNumber(weightAttributeName);

            } else {
                return 1.0;

            }
        } else {
            return 0.0;
        }
    }
}
