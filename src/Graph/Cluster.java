/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import org.graphstream.graph.*;

/**
 *
 * @author taufikalkaff
 */
public class Cluster {

    Map<Node, Double> neighbour = new HashMap();
    Map<Node, Double> child = new HashMap();
    Node parent;
    Double Weight;
    Double selfLoop;
    String weightAttributeName = "weight";

    public Cluster(Node parent) {
        this.parent = parent;
    }

    public Cluster() {
        this.parent = null;
    }

    public void addNeighbour(Node nodeID, Double weight) {
        neighbour.put(nodeID, weight);
    }

    public void addChild(Node nodeID, Double weight) {
        child.put(nodeID, weight);
    }

    public void setWeight(Node from, Node to, double weight) {
        if (from.hasEdgeBetween(to.getId())) {
            from.getEdgeBetween(to.getId()).setAttribute(weightAttributeName,
                    weight);
        }
    }

    public Map<Node, Double> getNeighbour() {
        //dari node anggota, dapet node tetangga
        //dari node tetangga, hitung summary edge dan tetangganya
        //return cluster atau return node aja?
        Map<Node, Double> neighbour = new HashMap();
        for (Map.Entry<Node, Double> entry : child.entrySet()) {
            
            Node k = entry.getKey();

            Iterator<? extends Node> t = k.getNeighborNodeIterator();
            while (t.hasNext()) {
                Node to = t.next();
                if (neighbour.containsKey(to)){
                    Double weightbefore = neighbour.get(to);
                    neighbour.put(to, weight(to, k)+weightbefore);
                }
            }
            System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
        }

        return neighbour;
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
