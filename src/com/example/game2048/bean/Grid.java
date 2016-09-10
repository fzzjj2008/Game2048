package com.example.game2048.bean;

import com.example.game2048.Global;
import com.example.game2048.utils.SettingsManager;

import android.content.Context;

public class Grid {
    
    public Cell[][] field;

    public Grid() {
        field = new Cell[Global.cellNum][Global.cellNum];
        for (int i = 0; i < Global.cellNum; i++) {
            for (int j = 0; j < Global.cellNum; j++) {
                field[i][j] = new Cell(Cell.CELL_NULL);
            }
        }
    }
     
    public void clearGrid() {
        for (int i = 0; i < Global.cellNum; i++) {
            for (int j = 0; j < Global.cellNum; j++) {
                field[i][j].setCellType(Cell.CELL_NULL);
            }
        }
    }
    
    public void setCell(int x, int y, int cellType) {
        field[x][y].setCellType(cellType);
    }
    
    public static void copyGrid(Grid grid1, Grid grid2) {
        for (int i = 0; i < Global.cellNum; i++) {
            for (int j = 0; j < Global.cellNum; j++) {
                int cellType = grid1.field[i][j].getCellType();
                grid2.field[i][j].setCellType(cellType);
            }
        }
    }
    
    // 是否全为空
    public boolean checkNull() {
        for (int i = 0; i < Global.cellNum; i++) {
            for (int j = 0; j < Global.cellNum; j++) {
                if (field[i][j].getCellType() != Cell.CELL_NULL) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // 是否全满
    public boolean checkFull() {
        for (int i = 0; i < Global.cellNum; i++) {
            for (int j = 0; j < Global.cellNum; j++) {
                if (field[i][j].getCellType() == Cell.CELL_NULL) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // 是否超过2048
    public boolean over2048() {
        for (int i = 0; i < Global.cellNum; i++) {
            for (int j = 0; j < Global.cellNum; j++) {
                if (field[i][j].getCellType() >= Cell.CELL_2048) {
                    return true;
                }
            }
        }
        return false;
    }
    
    // 无法合并
    public boolean cannotMerge() {
        for (int row = 0; row < Global.cellNum; row++) {
            if (!cannotMergeRow(row)) {
                return false;
            }
        }
        for (int col = 0; col < Global.cellNum; col++) {
            if (!cannotMergeCol(col)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean cannotMergeRow(int row) {
        for (int col = 0; col < Global.cellNum; col++) {
            if (field[row][col].getCellType() == Cell.CELL_NULL) {
                return false;
            }
            if (col < Global.cellNum - 1 && field[row][col].getCellType() == field[row][col+1].getCellType()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean cannotMergeCol(int col) {
        for (int row = 0; row < Global.cellNum; row++) {
            if (field[row][col].getCellType() == Cell.CELL_NULL) {
                return false;
            }
            if (row < Global.cellNum - 1 && field[row][col].getCellType() == field[row+1][col].getCellType()) {
                return false;
            }
        }
        return true;
    }
    
    public void saveGrid(Context context, String gridName) {
        for (int i = 0; i < Global.cellNum; i++) {
            for (int j = 0; j < Global.cellNum; j++) {
                String index = String.valueOf(i * Global.cellNum + j);
                int cellType = field[i][j].getCellType();
                if (cellType != Cell.CELL_NULL) {
                    SettingsManager.putString(context, gridName + "_" + index, field[i][j].getCellType());
                }
            }
        }
    }
    
    public static Grid loadGrid(Context context, String gridName) {
        Grid grid = new Grid();
        for (int i = 0; i < Global.cellNum * Global.cellNum; i++) {
            String key = gridName + "_" + String.valueOf(i);
            int x = i / Global.cellNum;
            int y = i % Global.cellNum;
            int cellType = SettingsManager.getInteger(context, key);
            grid.setCell(x, y, cellType);
        }
        return grid;
    }
    
//    public void printGrid() {
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < Global.cellNum; i++) {
//            sb.append("[");
//            for (int j = 0; j < Global.cellNum; j++) {
//                sb.append(field[i][j].getCellType() + " ");
//            }
//            sb.append("]");
//        }
//        Log.e("printGrid", sb.toString());
//    }
    
}
