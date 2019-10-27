package io.libsoft.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Maze {

  private final int rows;
  private final int columns;
  private Node[][] cells;
  private Random random = new Random();
  private BooleanProperty done = new SimpleBooleanProperty();
  private Builder builder = new Builder();
  private Solver solver = new Solver();
  private BooleanProperty solving = new SimpleBooleanProperty(false);

  public Maze(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    init();
  }

  public static Maze newEmptyMaze(int size) {
    return new Maze(size, size);
  }

  private void init() {
    cells = new Node[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        cells[i][j] = new Node(Position.of(i, j));
      }
    }
    done.setValue(false);
  }


  public Node getCell(int i, int j) {
    return cells[i][j];
  }

  public List<Node> flatten() {
    List<Node> temp = new ArrayList<>();
    for (Node[] cells : cells) {
      temp.addAll(Arrays.asList(cells));
    }
    return temp;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Node[] cell : cells) {
      for (Node node : cell) {
        sb.append(node);
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public boolean isDone() {
    return done.get();
  }

  public BooleanProperty doneProperty() {
    return done;
  }

  public boolean isSolving() {
    return solving.get();
  }

  public BooleanProperty solvingProperty() {
    return solving;
  }

  public int size() {
    return rows;
  }

  public void build() {
    Platform.runLater(()->{
      builder.run();
    });
//    new Thread(builder).start();
  }

  public void solve(){
    new Thread(solver).start();
  }

  class Builder implements Runnable {

    @Override
    public void run() {
      this.build(null, 0);
      done.setValue(true);
    }

    public void build(Node node, int level) {
      node = node != null ? node : cells[new Random().nextInt(size())][new Random().nextInt(size())];
      node.setLevel(level);
      node.setVisited(true);
      for (List<Route> reachable = routes(node); reachable.size() > 0; reachable = routes(node)) {
        Route selection = reachable.get(random.nextInt(reachable.size()));
        Node finalNode = node;
//        Platform.runLater(() -> {
          selection.getDestination().removeWall(selection.getDirection().opposite());
          finalNode.removeWall(selection.getDirection());
          selection.getDestination().addNeighbor(finalNode);
          finalNode.addNeighbor(selection.getDestination());
//        });

//        try {
//          Thread.sleep(40);
//        } catch (InterruptedException e) {}
        build(selection.getDestination(), level + 1);
      }
    }


    private synchronized List<Route> routes(Node node) {
//      List<Route> routes = node.getWalls().stream()
//          .map(direction -> Route.of(neighbor(node, direction), direction))
//          .filter(route -> route.getDestination() != null).collect(Collectors.toList());
//      Collections.shuffle(routes);
      List<Route> routes = new ArrayList<>();
      for (Direction wall : node.getWalls()) {
        Node n = neighbor(node, wall);
        if (n != null) {
          Route r = Route.of(n, wall);
          routes.add(r);
        }
      }

      return routes;
    }


    private synchronized Node neighbor(Node current, Direction direction) {
      int nextRowPos = current.getPosition().getRow() + direction.row;
      int nextColPos = current.getPosition().getCol() + direction.col;
      if (nextRowPos < 0 || nextRowPos > rows - 1 || nextColPos < 0 || nextColPos > columns - 1) {
        return null;
      }
      Node toBeAdded = cells[nextRowPos][nextColPos];
      if (toBeAdded.isVisited()) {
        return null;
      }
      return toBeAdded;
    }



  }

  class Solver implements Runnable {

    @Override
    public void run() {
      solve(cells[0][0], 0);
    }

    private void solve(Node start, int level) {
      solving.setValue(true);
      for (Node node : flatten()) {
        node.reset();
      }
      Queue<Path> queue = new LinkedList<>();
      queue.add(new Path(Collections.singletonList(start)));
      Path currentPath;
      Path target = null;

      while (!queue.isEmpty()){
        currentPath = queue.remove();
        if(currentPath.last().getPosition().equals(Position.of(size()-1,size()-1))){
          target = currentPath;
          break;
        }
        for (Node neighbor : currentPath.last().getNeighbors()) {
          if (!currentPath.contains(neighbor)){
            queue.add(currentPath.nextNode(neighbor));
          }
        }
      }
      for (Node node : target.getPath()) {
        node.setVisited(true);
        try {
          Thread.sleep(30);
        } catch (InterruptedException ignored) {}
      }
      solving.setValue(false);
    }
  }

}
