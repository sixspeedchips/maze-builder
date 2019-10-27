package io.libsoft.view;

import io.libsoft.model.Maze;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.layout.GridPane;

public class MazeView extends GridPane {

  private Maze maze;
  private List<CellView> cells;

  public MazeView() {
    setHgap(-CellView.LINE_SIZE);
    setVgap(-CellView.LINE_SIZE);
    cells = new ArrayList<>();
  }

  public void setMaze(Maze maze) {
    this.maze = maze;
    drawMaze();
  }

  private void drawMaze() {
    getChildren().clear();
    cells.clear();
    for (int i = 0; i < maze.size(); i++) {
      for (int j = 0; j < maze.size(); j++) {
        CellView cellView = new CellView(maze.getCell(i, j));
        cells.add(cellView);
        add(cellView, j, i);
      }
    }

  }

  private void redraw(){
    for (CellView cell : cells) {
      cell.drawWalls();
    }
  }

  public void build() {
    maze.build();
  }


  public void solve() {
    maze.solve();
  }
}

