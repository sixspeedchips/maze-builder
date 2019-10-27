package io.libsoft.model;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Node {

  private Position position;
  private ListProperty<Node> neighbors;
  private BooleanProperty visited;
  private ListProperty<Direction> walls;
  private int level;



  public Node(Position position) {
    this.position = position;
    walls = new SimpleListProperty<>(FXCollections.observableArrayList());
    walls.addAll(Direction.values());
    visited = new SimpleBooleanProperty();
    neighbors = new SimpleListProperty<>(FXCollections.observableArrayList());
  }

  public boolean isVisited() {
    return visited.getValue();
  }

  public void setVisited(boolean visited) {
    this.visited.setValue(visited);
  }

  public void removeWall(Direction direction) {
    walls.remove(direction);
  }

  public synchronized List<Direction> getWalls() {
    return walls.getValue();
  }

  public ObservableList<Node> getNeighbors() {
    return neighbors.get();
  }

  public ListProperty<Node> neighborsProperty() {
    return neighbors;
  }

  public BooleanProperty visitedProperty() {
    return visited;
  }

  public ListProperty<Direction> wallsProperty() {
    return walls;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }

  public Position getPosition() {
    return position;
  }

  public void addNeighbor(Node node) {
    neighbors.add(node);
  }

  @Override
  public String toString() {
    return "Node{" +
        "position=" + position +
        ", visited=" + visited +
        ", walls=" + walls +
        ", level=" + level +
        '}';
  }

  public void reset() {
    setLevel(0);
    setVisited(false);
  }
}
