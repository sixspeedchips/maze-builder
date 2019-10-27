package io.libsoft.model;

public class Position {

  private int row, col;

  private Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public static Position of(int row, int col){
    return new Position(row, col);
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position = (Position) o;

    if (getRow() != position.getRow()) {
      return false;
    }
    return getCol() == position.getCol();
  }

  @Override
  public int hashCode() {
    int result = getRow();
    result = 31 * result + getCol();
    return result;
  }

  @Override
  public String toString() {
    return "Position{" +
        "row=" + row +
        ", col=" + col +
        '}';
  }
}
