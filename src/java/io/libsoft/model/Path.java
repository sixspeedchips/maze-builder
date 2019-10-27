package io.libsoft.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Path {

  private List<Node> path = new ArrayList<>();

  public Path(List<Node> nodes) {
    path.addAll(nodes);
  }

  Path nextNode(Node nextNode) {
    path.add(nextNode);
    return new Path( path);
  }
  public Node last(){
    return path.get(path.size()-1);
  }

  public boolean contains(Object o) {
    return path.contains(o);
  }

  public List<Node> getPath() {
    return path;
  }
}
