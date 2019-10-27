package io.libsoft.view;


import io.libsoft.model.Maze;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MainView extends Application {
  public static final int MAZE_SIZE = 60;
  private MazeView mazeView;
  private Maze maze;

  @Override
  public void start(Stage stage) throws Exception {

    ColorBuilder.newMap(Color.WHITE,Color.WHITE,MAZE_SIZE*MAZE_SIZE);
    mazeView = createMazeView();
    createMaze();
    HBox buttons = buttons();
    stage.setScene(createScene(buttons, mazeView));
    stage.setOnCloseRequest(event -> {
      System.exit(1);
    });
    stage.show();
  }

  private MazeView createMazeView() {
    MazeView mazeView = new MazeView();
    mazeView.setAlignment(Pos.CENTER);
    mazeView.setPadding(new Insets(10));
    return mazeView;
  }

  private HBox buttons() {
    Button reset = new Button("New");
    Button action = new Button("Solve");
    ColorPicker colorPickerStart = new ColorPicker();
    ColorPicker colorPickerEnd = new ColorPicker();
    colorPickerStart.setOnAction(event -> {
      ColorBuilder.getInstance().newMap(colorPickerStart.getValue(),
          colorPickerEnd.getValue(),MAZE_SIZE*MAZE_SIZE*3/5);
    });
    colorPickerEnd.setOnAction(event -> {
      ColorBuilder.getInstance().newMap(colorPickerStart.getValue(),
          colorPickerEnd.getValue(),MAZE_SIZE*MAZE_SIZE*3/5);
    });
    reset.setOnAction(event -> {
      createMaze();
      buildMaze();
      action.setDisable(false);
    });
    action.setOnAction(event -> {
        solveMaze();
        action.setDisable(true);
    });


    HBox hBox = new HBox(reset,action, colorPickerStart, colorPickerEnd);
    hBox.setAlignment(Pos.CENTER);
    hBox.setPadding(new Insets(10));
    return hBox;
  }

  private void createMaze(){
    maze = Maze.newEmptyMaze(MAZE_SIZE);
    mazeView.setMaze(maze);
    maze.build();
  }
  private void buildMaze(){
    mazeView.build();
  }
  private void solveMaze(){
    mazeView.solve();
  }


  private Scene createScene(Node... node){
    VBox root = new VBox(node);
    Scene scene = new Scene(root);
    return scene;
  }
}
