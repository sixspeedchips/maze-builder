package io.libsoft.model;

public class Route {

  private Node destination;
  private Direction direction;

  public Route(Node destination, Direction direction) {
    this.destination = destination;
    this.direction = direction;
  }

  public static Route of(Node destination, Direction direction) {
    return new Route(destination, direction);
  }

  public Node getDestination() {
    return destination;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public String toString() {
    return "Route{" +
        "destination=" + destination +
        ", direction=" + direction +
        '}';
  }
}
