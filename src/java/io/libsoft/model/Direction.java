package io.libsoft.model;

public enum Direction {

  NORTH(-1,0){
    @Override
    Direction opposite() {
      return SOUTH;
    }
  },
  SOUTH(1,0){
    @Override
    Direction opposite() {
      return NORTH;
    }
  },
  EAST(0,1){
    @Override
    Direction opposite() {
      return WEST;
    }
  },
  WEST(0,-1){
    @Override
    Direction opposite() {
      return EAST;
    }
  };

  abstract Direction opposite();
  int row;
  int col;

  Direction(int row, int col) {
    this.row = row;
    this.col = col;
  }
}
