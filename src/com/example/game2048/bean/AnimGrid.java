package com.example.game2048.bean;

import com.example.game2048.Global;

public class AnimGrid {

    public int animType = AnimQueue.ANIM_NONE;
    public long startTime = 0;
    public long delayTime = 0;
    // 动画未执行时对应的cell情况
    public Grid grid;
    // 动画事件
    public AnimEvent[][] field;
    
    public AnimGrid(int animType) {
        this.animType = animType;
        this.startTime = 0;
        this.delayTime = 0;
        this.grid = new Grid();
        this.field = new AnimEvent[Global.cellNum][Global.cellNum];
        for (int i = 0; i < Global.cellNum; i++) {
            for (int j = 0; j < Global.cellNum; j++) {
                field[i][j] = new AnimEvent();
            }
        }
    }
    
   public void clearAllAnim() {
       this.grid.clearGrid();
       for (int i = 0; i < Global.cellNum; i++) {
           for (int j = 0; j < Global.cellNum; j++) {
               field[i][j].clearEvent();
           }
       }
       startTime = 0;
       delayTime = 0;
   }
   
   public void setAnimTime(long startTime, long delayTime) {
       this.startTime = startTime;
       this.delayTime = delayTime;
   }
   
   public int getCellType(int x, int y) {
       return this.grid.field[x][y].getCellType();
   }
   
   public void setGrid(Grid grid) {
       Grid.copyGrid(grid, this.grid);
   }
   
   public AnimEvent getAnimEvent(int x, int y) {
       return field[x][y];
   }
   
   public void setAnimEvent(int x, int y) {
       setAnimEvent(x, y, null);
   }
   
   public void setAnimEvent(int x, int y, int[] extras) {
       field[x][y].doAnim = true;
       field[x][y].extras = extras;
   }
   
   public long getEndTime() {
       return this.startTime + this.delayTime;
   }
   
}