package io.libsoft.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

public class ColorBuilder {

  private static Map<Integer, Color> colorMap;
  private static Map<Integer, Color> rainbowMap;
  static{
    colorMap = new HashMap<>();
    rainbowMap = new HashMap<>();
    List<Color> rainbow = new ArrayList<>(Arrays.asList(
        Color.RED,
        Color.ORANGE,
        Color.YELLOW,
        Color.GREEN,
        Color.BLUE,
        Color.INDIGO,
        Color.VIOLET
    ));
    int inc = MainView.MAZE_SIZE;
    for (int i = 0; i < rainbow.size()-1; i++) {
      newMap(rainbow.get(i),rainbow.get(i+1),inc,rainbowMap,i*inc);
    }
  }
  public static void newMap(Color start, Color end, int increments){

    double redIncr = (start.getRed() - end.getRed())/increments;
    double greenIncr = (start.getGreen() - end.getGreen())/increments;
    double blueIncr = (start.getBlue() - end.getBlue())/increments;
    double red = start.getRed();
    double green = start.getGreen();
    double blue = start.getBlue();

    for (int i = 0; i < increments-1; i++) {
      red -= redIncr;
      green -= greenIncr;
      blue -= blueIncr;
      colorMap.put(i, Color.color(red, green, blue));
    }
  }
  public static void newMap(Color start, Color end, int increments, Map<Integer, Color> map, int startIdx){

    double redIncr = (start.getRed() - end.getRed())/increments;
    double greenIncr = (start.getGreen() - end.getGreen())/increments;
    double blueIncr = (start.getBlue() - end.getBlue())/increments;
    double red = start.getRed();
    double green = start.getGreen();
    double blue = start.getBlue();

    for (int i = startIdx; i < startIdx + increments-1; i++) {
      red -= redIncr;
      green -= greenIncr;
      blue -= blueIncr;
      map.put(i, Color.color(red, green, blue));
    }
  }

  public Map<Integer, Color> getColorMap() {
    return colorMap;
  }

  public Map<Integer, Color> getRainbowMap(){ return rainbowMap;}

  public static ColorBuilder getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {

    private static final ColorBuilder INSTANCE = new ColorBuilder();
  }
}

