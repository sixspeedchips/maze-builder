package io.libsoft.view;

import io.libsoft.model.Direction;
import io.libsoft.model.Node;
import javafx.animation.FillTransition;
import javafx.beans.property.ListProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;

public class CellView extends Pane {
  public static final int LINE_SIZE = 2;
  public static final int CELL_SIZE = 10;
  private Node node;
  private Rectangle background;
  private Walls walls;

  public CellView(Node node) {
    this.node = node;
    walls = new Walls();
    init();
  }

  private void init() {
    getChildren().clear();
    background = new Rectangle();
    background.setFill(Color.WHITESMOKE);
    background.setWidth(CELL_SIZE);
    background.setHeight(CELL_SIZE);
    getChildren().add(background);
    getChildren().addAll(walls.setWalls(node.wallsProperty()));
    node.visitedProperty().addListener((observable, oldValue, newValue) -> {
      if(newValue){
        FillTransition ft = new FillTransition();
        ft.setFromValue(Color.BLACK);
        ft.setToValue(ColorBuilder.getInstance().getColorMap().get(node.getLevel()));
        ft.setDuration(new Duration(1000));
        ft.setShape(background);
        ft.playFromStart();
      }
      else {
        background.setFill(Color.WHITE);
      }
    });

  }
  public void drawWalls(){
    walls.setWalls(node.wallsProperty());
  }

  private class Walls extends Group {

    private Walls setWalls(ListProperty<Direction> walls) {
      getChildren().clear();
      walls.addListener((observable, oldValue, newValue) -> {
        getChildren().clear();
        for (Direction direction : newValue) {
          setWall(direction);
        }
      });

      for (Direction wall : walls) {
        setWall(wall);
      }
      return this;
    }

    private void setWall(Direction wall) {
      Line line = new Line();
      switch (wall) {
        case EAST:
          line.setStartX(background.getWidth());
          line.setStartY(0);
          line.setEndX(background.getWidth());
          line.setEndY(background.getHeight());
          break;
        case WEST:
          line.setStartX(0);
          line.setStartY(0);
          line.setEndX(0);
          line.setEndY(background.getHeight());
          break;
        case NORTH:
          line.setStartX(0);
          line.setStartY(0);
          line.setEndX(background.getWidth());
          line.setEndY(0);
          break;
        case SOUTH:
          line.setStartX(0);
          line.setStartY(background.getHeight());
          line.setEndX(background.getWidth());
          line.setEndY(background.getHeight());
          break;
      }
      line.setFill(Color.BLACK);
      line.setStrokeLineCap(StrokeLineCap.BUTT);
      line.setStrokeWidth(LINE_SIZE);
      getChildren().add(line);
    }

  }
}
