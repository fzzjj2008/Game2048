package com.example.game2048.bean;

import com.example.game2048.utils.ScaleUtils;

import android.graphics.Color;

public class Cell {

    // 最多是27种，包括CELL_NULL和最大的CELL_(2^26)
    public static final int TOTAL_TYPE_NUM = 27;
    // type
    public static final int CELL_NULL = 0x00;
    public static final int CELL_2 = 0x01;
    public static final int CELL_4 = 0x02;
    public static final int CELL_2048 = 0x0b;

    // color
    private static final String[] CELL_COLOR = { "#d6cdc4", "#eee4da", "#ede0c8", "#f2b179", "#f59563", "#f67c5f",
            "#f65e3b", "#edcf72", "#edcc61", "#edc850", "#edc53f", "#edc22e", "#3c3a32" };

    private int cellType = CELL_NULL;

    public Cell(int cellType) {
        this.setCellType(cellType);
    }

    public int getCellType() {
        return cellType;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }
    
    public static int getCellVal(int cellType) {
        if (CELL_NULL == cellType) {
            return 0;
        } else {
            return 1 << cellType;
        }
    }
    
    public static String getCellText(int cellType) {
        if (CELL_NULL == cellType) {
            return "";
        } else {
            String text = String.valueOf(1 << cellType);
            if (text.length() <= 5) {
                return text;
            } else {
                return "2^" + cellType;
            }
        }
    }
    
    public static int getCellColor(int cellType) {
        if (cellType < CELL_COLOR.length) {
            return Color.parseColor(CELL_COLOR[cellType]);
        } else {
            return Color.parseColor(CELL_COLOR[CELL_COLOR.length - 1]);
        }
    }
    
    public static int getCellTextColor(int cellType) {
        if (CELL_NULL == cellType) {
            return Color.TRANSPARENT;
        } else if (CELL_2 == cellType || CELL_4 == cellType) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
    
    public static int getCellTextSize(int cellType) {
        int len = getCellText(cellType).length();
        if (len <= 4) {
            return ScaleUtils.scaleSp(12);
        } else {
            return ScaleUtils.scaleSp(10);
        }
    }

    public void clearCell() {
        this.cellType = CELL_NULL;
    }

    public boolean isNull() {
        return (this.cellType == CELL_NULL);
    }

    public void mergeCell() {
        if (this.cellType != CELL_NULL)
            this.cellType++;
    }

}
