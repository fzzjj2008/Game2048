package com.example.game2048;

import com.example.game2048.bean.AnimEvent;
import com.example.game2048.bean.AnimGrid;
import com.example.game2048.bean.AnimQueue;
import com.example.game2048.bean.Cell;
import com.example.game2048.control.GameLogic;
import com.example.game2048.utils.ScaleUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.util.Log;
import android.graphics.RectF;
import android.view.View;

public class GameView extends View {

    private Paint mPaint = new Paint();

    // game view
    private int width;
    private int cellWidth;
    private int gridWidth;
    private int edgeWidth;
    private static final int BASE_CELL_WIDTH = 100;

    private Bitmap bBackground;
    private Bitmap[] baseCells;
    private Bitmap[] bCells;

    private GameLogic game;

    public GameView(Context context) {
        super(context);

        mPaint = new Paint();
        mPaint.setTypeface(Global.font);
        mPaint.setAntiAlias(true);
    }

    public void setGameLogic(GameLogic gameLogic) {
        this.game = gameLogic;
    }

    private void createBaseBitmaps() {
        int len = Cell.TOTAL_TYPE_NUM;
        baseCells = new Bitmap[len];
        int radius = ScaleUtils.scale(2);
        
        // 参考：http://blog.csdn.net/hursing/article/details/18703599
        FontMetrics fontMetrics;
        mPaint.setTextAlign(Align.CENTER);
        for (int i = 0; i < len; i++) {
            baseCells[i] = Bitmap.createBitmap(BASE_CELL_WIDTH, BASE_CELL_WIDTH, Config.ARGB_8888);
            Canvas canvas = new Canvas(baseCells[i]);
            mPaint.setColor(Cell.getCellColor(i));
            canvas.drawRoundRect(new RectF(0, 0, BASE_CELL_WIDTH, BASE_CELL_WIDTH), radius, radius, mPaint);
            mPaint.setColor(Cell.getCellTextColor(i));
            mPaint.setTextSize(Cell.getCellTextSize(i));
            fontMetrics = mPaint.getFontMetrics();
            float baseline = (BASE_CELL_WIDTH - fontMetrics.bottom - fontMetrics.top) / 2;
            canvas.drawText(Cell.getCellText(i), BASE_CELL_WIDTH / 2, baseline, mPaint);
        }
    }

    private void createCellBitmaps() {
        if (baseCells == null) {
            createBaseBitmaps();
        }
        if (bCells != null) {
            for (int i = 0; i < bCells.length; i++) {
                bCells[i].recycle();
            }
        }
        bCells = new Bitmap[2 + Global.cellNum * Global.cellNum];
        Matrix matrix = new Matrix();
        matrix.postScale((float) cellWidth / BASE_CELL_WIDTH, (float) cellWidth / BASE_CELL_WIDTH);

        for (int i = 0; i < bCells.length; i++) {
            bCells[i] = Bitmap.createBitmap(baseCells[i], 0, 0, BASE_CELL_WIDTH, BASE_CELL_WIDTH, matrix, true);
        }
    }

    public void recycleBitmaps() {
        if (baseCells != null) {
            for (int i = 0; i < baseCells.length; i++) {
                baseCells[i].recycle();
            }
        }
        if (bCells != null) {
            for (int i = 0; i < bCells.length; i++) {
                bCells[i].recycle();
            }
        }
        baseCells = null;
        bCells = null;
    }

    private void createBackgroundBitmap() {
        bBackground = Bitmap.createBitmap(width, width, Config.ARGB_8888);
        Canvas canvas = new Canvas(bBackground);
        int radius = ScaleUtils.scale(5);
        mPaint.setColor(getResources().getColor(R.color.color_bg_game_view));
        canvas.drawRoundRect(new RectF(0, 0, width, width), radius, radius, mPaint);
        for (int i = 0; i < Global.cellNum; i++) {
            for (int j = 0; j < Global.cellNum; j++) {
                int left = edgeWidth + i * (cellWidth + gridWidth);
                int top = edgeWidth + j * (cellWidth + gridWidth);
                canvas.drawBitmap(bCells[Cell.CELL_NULL], left, top, mPaint);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        synchronized (this) {            
            // draw background cells
            canvas.drawBitmap(bBackground, 0, 0, mPaint);
    
            // draw cells
            drawCells(canvas);
        }
    }

    private void drawCells(Canvas canvas) {
        if (game.cancelDrawCell) {
            return;
        }

        AnimGrid curAnim = game.animQueue.getCurrentAnim();
        for (int i = 0; i < Global.cellNum; i++) {
            for (int j = 0; j < Global.cellNum; j++) {
                int curType = game.currentAnimType;
                if (curType == AnimQueue.ANIM_NONE) {
                    int cellType = game.grid.field[i][j].getCellType();
                    if (cellType == Cell.CELL_NULL) {
                        // not draw
                    } else {
                        float left = edgeWidth + j * (cellWidth + gridWidth);
                        float top = edgeWidth + i * (cellWidth + gridWidth);
                        canvas.drawBitmap(bCells[cellType], left, top, mPaint);
                    }
                } else {
                    int cellType = curAnim.getCellType(i, j);
                    AnimEvent animEvent = curAnim.getAnimEvent(i, j);
                    if (cellType == Cell.CELL_NULL) {
                        // not draw
                        continue;
                    }
                    if (animEvent.doAnim == false) {
                        float left = edgeWidth + j * (cellWidth + gridWidth);
                        float top = edgeWidth + i * (cellWidth + gridWidth);
                        canvas.drawBitmap(bCells[cellType], left, top, mPaint);
                        continue;
                    }
                    float animDone = game.animQueue.getAnimDone(game.currentTime);
                    if (curType == AnimQueue.ANIM_MOVE) {
                        int fromX = i;
                        int fromY = j;
                        int toX = animEvent.extras[0];
                        int toY = animEvent.extras[1];
                        float left = fromY + (toY - fromY) * animDone;
                        float top = fromX + (toX - fromX) * animDone;
                        left = edgeWidth + left * (cellWidth + gridWidth);
                        top = edgeWidth + top * (cellWidth + gridWidth);
                        canvas.drawBitmap(bCells[cellType], left, top, mPaint);

                    } else if (curType == AnimQueue.ANIM_CREATE) {
                        float width = cellWidth * animDone;
                        float centerX = edgeWidth + j * (cellWidth + gridWidth) + cellWidth / 2.0f;
                        float centerY = edgeWidth + i * (cellWidth + gridWidth) + cellWidth / 2.0f;
                        Matrix matrix = new Matrix();
                        matrix.postScale(animDone, animDone);
                        matrix.postTranslate(centerX - width / 2.0f, centerY - width / 2.0f);
                        canvas.drawBitmap(bCells[cellType], matrix, mPaint);

                    } else if (curType == AnimQueue.ANIM_MERGE) {
                        float scale = (float) (1.2f - Math.abs(0.5 - animDone) / 0.5 * 0.2);
                        float width = cellWidth * scale;
                        float centerX = edgeWidth + j * (cellWidth + gridWidth) + cellWidth / 2.0f;
                        float centerY = edgeWidth + i * (cellWidth + gridWidth) + cellWidth / 2.0f;
                        Matrix matrix = new Matrix();
                        matrix.postScale(scale, scale);
                        matrix.postTranslate(centerX - width / 2.0f, centerY - width / 2.0f);
                        canvas.drawBitmap(bCells[cellType], matrix, mPaint);
                    }
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = View.MeasureSpec.getSize(widthMeasureSpec);

        // size
        int ratio = Global.SIZE_GAME_CELL_GRID_RATIO;
        gridWidth = width / (ratio * Global.cellNum + Global.cellNum + 1);
        cellWidth = ratio * gridWidth;
        edgeWidth = (width - cellWidth * Global.cellNum - gridWidth * (Global.cellNum - 1)) / 2;

        // create bitmap
        createCellBitmaps();
        createBackgroundBitmap();

        setMeasuredDimension(width, width);
    }

}
