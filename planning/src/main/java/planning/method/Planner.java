package planning.method;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;

import planning.model.Action;
import planning.model.Element;
import planning.model.System;
import planning.model.SystemVariant;

public class Planner {

	private List<Node> checkedNodes;

	private List<Node> uncheckedNodes;

	private Node initialNode;

	private Node finalNode;

	private List<Edge> edges;

	private System initialSystem;

	private System finalSystem;

	private Element[] elements;

	private DefaultDirectedGraph<Node, Edge> network;

	public Planner(System initialSystem, System finalSystem, Element[] elements) {
		this.initialSystem = initialSystem;
		this.finalSystem = finalSystem;
		this.elements = elements;

		this.checkedNodes = new ArrayList<>();
		this.uncheckedNodes = new ArrayList<>();

		this.edges = new ArrayList<>();

		this.network = new DefaultDirectedGraph<>(Edge.class);
	}

	public void plan() {
		initialNode = new Node(initialSystem);
		uncheckedNodes.add(initialNode);
		network.addVertex(initialNode);

		while (true) {
			iterate();
			if (finalNode != null) {
				break;
			}
			if (uncheckedNodes.isEmpty()) {
				break;
			}
		}
	}

	private void iterate() {
		Node sourceNode = uncheckedNodes.get(0);
		System sourceSystem = sourceNode.getSystem();
		if (sourceSystem.contains(finalSystem)) {
			finalNode = sourceNode;
		}
		for (Element element : elements) {
			SystemVariant systemVariants[] = element.applyTo(sourceSystem);
			for (SystemVariant systemVariant : systemVariants) {
				System targetSystem = systemVariant.getSystem();

				Node targetNode = null;
				for (Node checkedNode : checkedNodes) {
					if (checkedNode.getSystem().equals(targetSystem)) {
						targetNode = checkedNode;
						break;
					}
				}
				if (targetNode == null) {
					targetNode = new Node(targetSystem);
					uncheckedNodes.add(targetNode);
				}

				Edge edge = new Edge(systemVariant.getAction());
				edges.add(edge);

				network.addVertex(targetNode);
				network.addEdge(sourceNode, targetNode, edge);
			}
		}

		uncheckedNodes.remove(0);
		checkedNodes.add(sourceNode);
	}

	public List<Action> getShortestPlan() {
		DijkstraShortestPath<Node, Edge> alg = new DijkstraShortestPath<>(network);
		GraphPath<Node, Edge> path = alg.getPath(initialNode, finalNode);
		List<Action> actions = new ArrayList<>();
		for (Edge edge : path.getEdgeList()) {
			actions.add(edge.getAction());
		}
		return actions;
	}
}
