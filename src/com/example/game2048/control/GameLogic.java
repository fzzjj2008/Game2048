package com.example.game2048.control;

import com.example.game2048.DialogView;
import com.example.game2048.Global;
import com.example.game2048.MainView;
import com.example.game2048.R;
import com.example.game2048.bean.AnimGrid;
import com.example.game2048.bean.AnimQueue;
import com.example.game2048.bean.Cell;
import com.example.game2048.bean.Grid;
import com.example.game2048.utils.SettingsManager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class GameLogic {

    public static final int MOVE_LEFT = 0x00;
    public static final int MOVE_UP = 0x01;
    public static final int MOVE_RIGHT = 0x02;
    public static final int MOVE_DOWN = 0x03;

    // game logic
    private boolean wingame = false;
    private boolean over2048 = false;

    // data
    public int score = 0;
    public int best = 0;
    private int lastScore = 0;
    private int lastBest = 0;
    public Grid grid = null;
    public Grid pregrid = null;
    private int mergeScore = 0;

    // view
    private MainView mView;
    private Context mContext;

    // anim
    public long currentTime = 0;
    public int currentAnimType = AnimQueue.ANIM_NONE;
    public static final int REFRESH_INTERVAL = Global.animRefresh;
    private AnimGrid animCreate = null;
    private AnimGrid animMove = null;
    private AnimGrid animMerge = null;
    public AnimQueue animQueue = new AnimQueue();
    private boolean hasAnimMove = false;
    private boolean hasAnimMerge = false;
    private Animation addScoreAnim;
    private Animation subScoreAnim;
    // TODO 未解决
    // 这里为了解决滑动时闪屏，闪屏原因：invalidate()到ondraw有延时，在延时的时刻滑动
    public boolean cancelDrawCell;

    // handler
    private static final int MESSAGE_DO_ANIM = 0x01;
    private static final int MESSAGE_ADD_SCORE_ANIM = 0x02;
    private static final int MESSAGE_SUB_SCORE_ANIM = 0x03;

    private class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_DO_ANIM:
                tick();
                break;
            case MESSAGE_ADD_SCORE_ANIM:
                mView.mMergeScoreAnim.setText("+" + mergeScore);
                mView.mMergeScoreAnim.startAnimation(addScoreAnim);
                break;
            case MESSAGE_SUB_SCORE_ANIM:
                mView.mMergeScoreAnim.setText("-" + mergeScore);
                mView.mMergeScoreAnim.startAnimation(subScoreAnim);
                break;
            }
        }
    }
    private RefreshHandler mHandler = new RefreshHandler();

    
    public GameLogic(MainView view) {
        this.mView = view;
        this.mContext = mView.getContext();
        
        initGrids();
        
        addScoreAnim = AnimationUtils.loadAnimation(mContext, R.anim.anim_add_score);
        addScoreAnim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mView.mMergeScoreAnim.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mView.mMergeScoreAnim.setVisibility(View.GONE);
            }
        });
        
        subScoreAnim = AnimationUtils.loadAnimation(mContext, R.anim.anim_sub_score);
        subScoreAnim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mView.mMergeScoreAnim.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mView.mMergeScoreAnim.setVisibility(View.GONE);
            }
        });
        
    }
    
    public void initGrids() {
        grid = new Grid();
        pregrid = new Grid();
        animCreate = new AnimGrid(AnimQueue.ANIM_CREATE);
        animMove = new AnimGrid(AnimQueue.ANIM_MOVE);
        animMerge = new AnimGrid(AnimQueue.ANIM_MERGE);
    }

    public void newGame() {
        score = 0;
        lastScore = 0;
        mergeScore = 0;
        mView.setScore(score);
        wingame = false;
        over2048 = false;
        grid.clearGrid();
        animQueue.clearQueue();
        animCreate.clearAllAnim();
        addRandomCell();
        addRandomCell();
        // record grid
        Grid.copyGrid(grid, pregrid);
        // anim
        animCreate.setAnimTime(0, Global.animtimeCreate);
        animCreate.setGrid(grid);
        animQueue.addAnim(animCreate);
        mHandler.sendEmptyMessage(MESSAGE_DO_ANIM);
    }

    public void loadGame() {
        if (4 == Global.cellNum) {
            score = SettingsManager.getInteger(mContext, Global.PREF_SCORE_4X4);
            best = SettingsManager.getInteger(mContext, Global.PREF_BEST_4X4);
            lastScore = SettingsManager.getInteger(mContext, Global.PREF_LAST_SCORE_4X4);
            lastBest = SettingsManager.getInteger(mContext, Global.PREF_LAST_BEST_4X4);
        } else if (5 == Global.cellNum) {
            score = SettingsManager.getInteger(mContext, Global.PREF_SCORE_5X5);
            best = SettingsManager.getInteger(mContext, Global.PREF_BEST_5X5);
            lastScore = SettingsManager.getInteger(mContext, Global.PREF_LAST_SCORE_5X5);
            lastBest = SettingsManager.getInteger(mContext, Global.PREF_LAST_BEST_5X5);
        }
        mergeScore = 0;
        mView.setScore(score);
        mView.setBest(best);

        if (4 == Global.cellNum) {
            grid = Grid.loadGrid(mContext, Global.PREF_GRID_4X4);
            pregrid = Grid.loadGrid(mContext, Global.PREF_PREGRID_4X4); 
        } else if (5 == Global.cellNum) {
            grid = Grid.loadGrid(mContext, Global.PREF_GRID_5X5);
            pregrid = Grid.loadGrid(mContext, Global.PREF_PREGRID_5X5);            
        }

        // test
        // grid.clearGrid();
        // grid.setCell(0, 0, Cell.CELL_65536);
        // grid.setCell(0, 1, Cell.CELL_32768);
        // grid.setCell(0, 2, Cell.CELL_16384);
        // grid.setCell(0, 3, Cell.CELL_8192);
        // grid.setCell(1, 3, Cell.CELL_4096);
        // grid.setCell(1, 2, Cell.CELL_2048);
        // grid.setCell(1, 1, Cell.CELL_1024);
        // grid.setCell(1, 0, Cell.CELL_512);
        // grid.setCell(2, 0, Cell.CELL_256);
        // grid.setCell(2, 1, Cell.CELL_128);
        // grid.setCell(2, 2, Cell.CELL_64);
        // grid.setCell(2, 3, Cell.CELL_32);
        // grid.setCell(3, 3, Cell.CELL_16);
        // grid.setCell(3, 2, Cell.CELL_8);
        // grid.setCell(3, 0, Cell.CELL_4);
        // Grid.copyGrid(grid, pregrid);

        wingame = false;
        over2048 = grid.over2048();

        if (grid.checkNull()) {
            newGame();
        } else {
            invalidate();
        }
    }

    public void saveGame() {
        SettingsManager.putString(mContext, Global.PREF_STYLE, Global.bgStyle);
        SettingsManager.putString(mContext, Global.PREF_CELL_NUM, Global.cellNum);
        if (4 == Global.cellNum) {
            SettingsManager.putString(mContext, Global.PREF_SCORE_4X4, score);
            SettingsManager.putString(mContext, Global.PREF_BEST_4X4, best);
            SettingsManager.putString(mContext, Global.PREF_LAST_SCORE_4X4, lastScore);
            SettingsManager.putString(mContext, Global.PREF_LAST_BEST_4X4, lastBest);
            grid.saveGrid(mContext, Global.PREF_GRID_4X4);
            pregrid.saveGrid(mContext, Global.PREF_PREGRID_4X4);
        } else if (5 == Global.cellNum) {
            SettingsManager.putString(mContext, Global.PREF_SCORE_5X5, score);
            SettingsManager.putString(mContext, Global.PREF_BEST_5X5, best);
            SettingsManager.putString(mContext, Global.PREF_LAST_SCORE_5X5, lastScore);
            SettingsManager.putString(mContext, Global.PREF_LAST_BEST_5X5, lastBest);
            grid.saveGrid(mContext, Global.PREF_GRID_5X5);
            pregrid.saveGrid(mContext, Global.PREF_PREGRID_5X5);
        }
    }
    
    public void resetGame() {
        SettingsManager.removeAll(mContext);
        mView.setBackgroundStyle();
        best = 0;
        lastBest = 0;
        mView.setBest(best);
        this.newGame();
    }

    public void undo() {
        // 对话框出现时避免滑动
        if (mView.mDialogView.isVisible()) {
            return;
        }
        // 执行动画时直接结束上一动画
        if (currentAnimType != AnimQueue.ANIM_NONE) {
            endTick();
        }
        // 清空动画
        mView.mMergeScoreAnim.clearAnimation();
        
        Grid.copyGrid(pregrid, grid);
        mergeScore = score - lastScore;
        score = lastScore;
        best = lastBest;
        if (4 == Global.cellNum) {
            SettingsManager.putString(mContext, Global.PREF_BEST_4X4, best);
        } else if (5 == Global.cellNum) {
            SettingsManager.putString(mContext, Global.PREF_BEST_5X5, best);
        }
        mView.setScore(lastScore);
        mView.setBest(lastBest);
        
//        invalidate();
        
        if (mergeScore > 0) {
            mHandler.sendEmptyMessage(MESSAGE_SUB_SCORE_ANIM);
        }
    }

    public void addRandomCell() {
        if (grid.checkFull()) {
            return;
        }
        int cellType = Math.random() < Global.cellRatio2 ? Cell.CELL_2 : Cell.CELL_4;
        int x = 0, y = 0;
        do {
            x = (int) (Math.random() * Global.cellNum);
            y = (int) (Math.random() * Global.cellNum);
        } while (grid.field[x][y].getCellType() != Cell.CELL_NULL);
        createCell(x, y, cellType);
    }

    public void createCell(int x, int y, int cellType) {
        grid.setCell(x, y, cellType);
        animCreate.setAnimEvent(x, y);
    }

    public void move(int direction) {
        // 对话框出现时避免滑动
        if (mView.mDialogView.isVisible() || mView.mMainSettingsView.isVisible()
                || mView.mSettingsView.isVisible() || mView.mRecordView.isVisible()) {
            return;
        }
        // 执行动画时直接结束上一动画
        if (currentAnimType != AnimQueue.ANIM_NONE) {
            endTick();
        }
        mView.mMergeScoreAnim.clearAnimation();

        // record grid
        Grid tempgrid = new Grid();
        Grid.copyGrid(grid, tempgrid);
        
        // init
        animCreate.clearAllAnim();
        animMove.clearAllAnim();
        animMerge.clearAllAnim();
        hasAnimMove = false;
        hasAnimMerge = false;
        animMove.setGrid(grid);

        wingame = false;
        mergeScore = 0;
        
        // move
        switch (direction) {
        case MOVE_LEFT:
            moveLeft();
            break;
        case MOVE_UP:
            moveUp();
            break;
        case MOVE_RIGHT:
            moveRight();
            break;
        case MOVE_DOWN:
            moveDown();
            break;
        }

        // anim move
        if (!hasAnimMove) {
            if (grid.cannotMerge()) {
                lose();
            }
            return;
        }
        // record grid
        Grid.copyGrid(tempgrid, pregrid);
        
        animMove.setAnimTime(0, Global.animtimeMove);
        animQueue.addAnim(animMove);

        // anim merge
        if (hasAnimMerge) {
            animMerge.setAnimTime(Global.animtimeMove, Global.animtimeMerge);
            animMerge.setGrid(grid);
            animQueue.addAnim(animMerge);
        }

        // anim create
        addRandomCell();
        long animTimeCreate = hasAnimMerge ? (Global.animtimeMove + Global.animtimeMerge) : Global.animtimeMove;
        animCreate.setAnimTime(animTimeCreate, Global.animtimeCreate);
        animCreate.setGrid(grid);
        animQueue.addAnim(animCreate);
        
        // update score
        lastScore = score;
        score += mergeScore;
        mView.setScore(score);
        if (score > best) {
            lastBest = best;
            best = score;
            if (4 == Global.cellNum) {
                SettingsManager.putString(mContext, Global.PREF_BEST_4X4, best);
                SettingsManager.putString(mContext, Global.PREF_LAST_BEST_4X4, lastBest);
            } else if (5 == Global.cellNum) {
                SettingsManager.putString(mContext, Global.PREF_BEST_5X5, best);
                SettingsManager.putString(mContext, Global.PREF_LAST_BEST_5X5, lastBest);
            }
            mView.setBest(best);
        }
        
        cancelDrawCell = true;

        // do anim
        mHandler.sendEmptyMessage(MESSAGE_DO_ANIM);
    }

    private void moveLeft() {
        for (int row = 0; row < Global.cellNum; row++) {
            int dstPosY = 0;
            for (int col = 1; col < Global.cellNum; col++) {
                dstPosY = mergeRow(row, col, dstPosY, MOVE_LEFT);
            }
        }
    }

    private void moveUp() {
        for (int col = 0; col < Global.cellNum; col++) {
            int dstPosX = 0;
            for (int row = 1; row < Global.cellNum; row++) {
                dstPosX = mergeCol(col, row, dstPosX, MOVE_UP);
            }
        }
    }

    private void moveRight() {
        for (int row = 0; row < Global.cellNum; row++) {
            int dstPosY = Global.cellNum - 1;
            for (int col = Global.cellNum - 2; col >= 0; col--) {
                dstPosY = mergeRow(row, col, dstPosY, MOVE_RIGHT);
            }
        }
    }

    private void moveDown() {
        for (int col = 0; col < Global.cellNum; col++) {
            int dstPosX = Global.cellNum - 1;
            for (int row = Global.cellNum - 2; row >= 0; row--) {
                dstPosX = mergeCol(col, row, dstPosX, MOVE_DOWN);
            }
        }
    }

    // 行合并，左右移动
    private int mergeRow(int row, int srcY, int dstY, int dir) {
        int srcCellType = grid.field[row][srcY].getCellType();
        int dstCellType = grid.field[row][dstY].getCellType();
        if (srcCellType == Cell.CELL_NULL) {
            // [4 0] -> [4 0]
            // do nothing
        } else if (dstCellType == Cell.CELL_NULL) {
            // [0 4] -> [4 0]
            grid.field[row][dstY].setCellType(srcCellType);
            grid.field[row][srcY].setCellType(Cell.CELL_NULL);
            animMove.setAnimEvent(row, srcY, new int[] { row, dstY });
            hasAnimMove = true;
        } else if (srcCellType == dstCellType) {
            // [4 4] -> [8 0]
            grid.field[row][dstY].mergeCell();
            grid.field[row][srcY].setCellType(Cell.CELL_NULL);
            animMove.setAnimEvent(row, srcY, new int[] { row, dstY });
            animMerge.setAnimEvent(row, dstY);
            hasAnimMove = true;
            hasAnimMerge = true;

            // check win
            boolean is2048 = grid.field[row][dstY].getCellType() == Cell.CELL_2048;
            if (wingame == false && over2048 == false && is2048) {
                over2048 = true;
                wingame = true;
            }
            // update score
            mergeScore += Cell.getCellVal(grid.field[row][dstY].getCellType());           

            dstY += (dir == MOVE_LEFT ? 1 : -1);
        } else if (srcCellType != dstCellType) {
            dstY += (dir == MOVE_LEFT ? 1 : -1);
            if (srcY == dstY) {
                // [2 4] -> [2 4]
                // only update dstPos
            } else {
                // [2 0 4] -> [2 4 0]
                grid.field[row][dstY].setCellType(srcCellType);
                grid.field[row][srcY].setCellType(Cell.CELL_NULL);
                animMove.setAnimEvent(row, srcY, new int[] { row, dstY });
                hasAnimMove = true;
            }
        }
        return dstY;
    }

    // 列合并，上下移动
    private int mergeCol(int col, int srcX, int dstX, int dir) {
        int srcCellType = grid.field[srcX][col].getCellType();
        int dstCellType = grid.field[dstX][col].getCellType();
        if (srcCellType == Cell.CELL_NULL) {
            // [4 0] -> [4 0]
            // do nothing
        } else if (dstCellType == Cell.CELL_NULL) {
            // [0 4] -> [4 0]
            grid.field[dstX][col].setCellType(srcCellType);
            grid.field[srcX][col].setCellType(Cell.CELL_NULL);
            animMove.setAnimEvent(srcX, col, new int[] { dstX, col });
            hasAnimMove = true;
        } else if (srcCellType == dstCellType) {
            // [4 4] -> [8 0]
            grid.field[dstX][col].mergeCell();
            grid.field[srcX][col].setCellType(Cell.CELL_NULL);
            animMove.setAnimEvent(srcX, col, new int[] { dstX, col });
            animMerge.setAnimEvent(dstX, col);
            hasAnimMove = true;
            hasAnimMerge = true;

            // check win
            boolean is2048 = grid.field[dstX][col].getCellType() == Cell.CELL_2048;
            if (wingame == false && over2048 == false && is2048) {
                over2048 = true;
                wingame = true;
            }
            // update score
            mergeScore += Cell.getCellVal(grid.field[dstX][col].getCellType());
            
            dstX += (dir == MOVE_UP ? 1 : -1);

        } else if (srcCellType != dstCellType) {
            dstX += (dir == MOVE_UP ? 1 : -1);
            if (srcX == dstX) {
                // [2 4] -> [2 4]
                // only update dstPos
            } else {
                // [2 0 4] -> [2 4 0]
                grid.field[dstX][col].setCellType(srcCellType);
                grid.field[srcX][col].setCellType(Cell.CELL_NULL);
                animMove.setAnimEvent(srcX, col, new int[] { dstX, col });
                hasAnimMove = true;
            }
        }
        return dstX;
    }
    
    private void invalidate() {
//        mView.mGameView.invalidate();
//        mHandler.sendEmptyMessage(MESSAGE_INVALIDATE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mView.mGameView.postInvalidate();
            }
        }).start();
    }

    // animation
    private void tick() {
        cancelDrawCell = false;
        currentTime += REFRESH_INTERVAL;
        AnimGrid firstAnim = animQueue.getCurrentAnim();
        if (firstAnim == null) {
            // animQueue为空
            endTick();
            return;
        }

        if (currentTime < firstAnim.startTime) {
            // 动画未加载
            currentAnimType = AnimQueue.ANIM_NONE;
        } else if (currentTime >= firstAnim.startTime && currentTime < firstAnim.getEndTime()) {
            // 执行动画
            currentAnimType = firstAnim.animType;
        } else if (currentTime >= firstAnim.getEndTime()) {
            // 切换动画
            animQueue.queue.poll();
            firstAnim = animQueue.getCurrentAnim();
            if (firstAnim == null) {
                // 无动画，结束tick()
                endTick();
                afterTick();
                return;
            } else {
                // 切换动画
                currentAnimType = firstAnim.animType;
            }
        }

        invalidate();
        mHandler.sendEmptyMessageDelayed(MESSAGE_DO_ANIM, REFRESH_INTERVAL);
    }
    
    private void endTick() {
        if (!animQueue.queue.isEmpty()) {
            animQueue.clearQueue();
        }
        invalidate();
        currentTime = 0;
        currentAnimType = AnimQueue.ANIM_NONE;
        // 清除所有messages
        mHandler.removeCallbacksAndMessages(null);
    }
    
    private void afterTick() {
        // check win
        if (wingame) {
            win();
        }
        // merge anim
        if (mergeScore > 0) {
            mHandler.sendEmptyMessage(MESSAGE_ADD_SCORE_ANIM);
        }
    }

    // win
    private void win() {
        Log.e("game", "win");
        mView.mDialogView.setText(R.string.dialog_msg_win, R.string.dialog_btn_continue);
        mView.mDialogView.setType(DialogView.DIALOG_TYPE_ONE_BUTTON, new OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.mDialogView.hideDialog();
            }
        });
        mView.mDialogView.showDialogWithAnim();
    }

    // lose
    private void lose() {
        Log.e("game", "lose");
        mView.mDialogView.setText(R.string.dialog_msg_lose, R.string.dialog_btn_ok);        
        mView.mDialogView.setType(DialogView.DIALOG_TYPE_ONE_BUTTON, new OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.mDialogView.hideDialog();
            }
        });
        mView.mDialogView.showDialogWithAnim();
    }

}
