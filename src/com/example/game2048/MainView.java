package com.example.game2048;

import com.example.game2048.control.GameLogic;
import com.example.game2048.utils.ScaleUtils;
import com.example.game2048.utils.SettingsManager;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainView extends RelativeLayout implements OnClickListener, ICommandListener {
    
    private Context mContext;
    
    // 控件
    private ImageView mSettingButton;
    private RelativeLayout mTitleLayout;
    private TextView mTitle;
    private RelativeLayout mScoreBoard;
    private TextView mScoreTitle;
    private TextView mScore;
    private RelativeLayout mBestBoard;
    private TextView mBestTitle;
    private TextView mBest;
    private TextView mRestartButton;
    private TextView mUndoButton;
    public GameView mGameView;
    public DialogView mDialogView;
    public TextView mMergeScoreAnim;
    public MainSettingsView mMainSettingsView;
    public SettingsView mSettingsView;
    public RecordView mRecordView;
    
    // game view
    private int gameWidth;
    
    // gesture
    private GestureDetector mGestureDetector;
    
    // logic
    public GameLogic game;

    public MainView(Context context) {
        super(context);
        mContext = context;
        
        // read preferences
        Global.bgStyle = SettingsManager.getInteger(mContext, Global.PREF_STYLE);
        Global.cellNum = SettingsManager.getInteger(mContext, Global.PREF_CELL_NUM);
        
        game = new GameLogic(this);
        
        // init view
        initView();
        initLayout();
        // set style
        this.setBackgroundStyle();
        
        mGestureDetector = new GestureDetector(mContext, new GestureListener());
        
        game.loadGame(); 
    }
    
    private void initView() {
        // background
        this.setId(Global.ID_MAIN_LAYOUT);
        
        // Setting Button
        mSettingButton = new ImageView(mContext); 
        mSettingButton.setId(Global.ID_MAIN_SETTING);
        mSettingButton.setClickable(true);
        mSettingButton.setImageResource(R.drawable.ic_action_settings);
        mSettingButton.setOnClickListener(this);
        this.addView(mSettingButton);
        
        // Title
        mTitleLayout = new RelativeLayout(mContext);
        mTitleLayout.setId(Global.ID_MAIN_TITLE);
        this.addView(mTitleLayout);
        
        mTitle = new TextView(mContext);
        mTitle.setText(R.string.main_title);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        mTitleLayout.addView(mTitle);
        
        // Score board
        mScoreBoard = new RelativeLayout(mContext);
        mScoreBoard.setId(Global.ID_MAIN_SCORE_BOARD);
        this.addView(mScoreBoard);
        
        mScoreTitle = new TextView(mContext);
        mScoreTitle.setId(Global.ID_MAIN_SCORE_TITLE);
        mScoreTitle.setText(R.string.main_score);
        mScoreTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mScoreTitle.setTypeface(Global.font);
        mScoreBoard.addView(mScoreTitle);
        
        mScore = new TextView(mContext);
        mScore.setId(Global.ID_MAIN_SCORE);
        mScore.setText("0");
        mScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mScore.setTypeface(Global.font);
        mScoreBoard.addView(mScore);
        
        // Best board
        mBestBoard = new RelativeLayout(mContext);
        mBestBoard.setId(Global.ID_MAIN_BEST_BOARD);
        this.addView(mBestBoard);
        
        mBestTitle = new TextView(mContext);
        mBestTitle.setId(Global.ID_MAIN_BEST_TITLE);
        mBestTitle.setText(R.string.main_best);
        mBestTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mBestTitle.setTypeface(Global.font);
        mBestBoard.addView(mBestTitle);
        
        mBest = new TextView(mContext);
        mBest.setId(Global.ID_MAIN_BEST);
        mBest.setText("0");
        mBest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mBest.setTypeface(Global.font);
        mBestBoard.addView(mBest);
        
        // restart
        mRestartButton = new TextView(mContext);
        mRestartButton.setId(Global.ID_MAIN_RESTART);
        mRestartButton.setText(R.string.main_restart);
        mRestartButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mRestartButton.setTypeface(Global.font);
        mRestartButton.setClickable(true);
        mRestartButton.setOnClickListener(this);
        this.addView(mRestartButton);
        
        // undo
        mUndoButton = new TextView(mContext);
        mUndoButton.setId(Global.ID_MAIN_UNDO);
        mUndoButton.setText(R.string.main_undo);
        mUndoButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mUndoButton.setTypeface(Global.font);
        mUndoButton.setClickable(true);
        mUndoButton.setOnClickListener(this);
        this.addView(mUndoButton);
        
        // Game View
        mGameView = new GameView(mContext);
        mGameView.setId(Global.ID_MAIN_GAME_VIEW);
        mGameView.setGameLogic(game);
        this.addView(mGameView);
        
        // Dialog View
        mDialogView = new DialogView(mContext);
        mDialogView.setId(Global.ID_MAIN_DIALOG_VIEW);
        mDialogView.setVisibility(View.GONE);
        this.addView(mDialogView);
        
        // Add/Sub Score Anim TextView
        mMergeScoreAnim = new TextView(mContext);
        mMergeScoreAnim.setId(Global.ID_MAIN_MERGE_ANIM);
        mMergeScoreAnim.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mMergeScoreAnim.setTypeface(Global.font);
        mMergeScoreAnim.setGravity(Gravity.CENTER_HORIZONTAL);
        this.addView(mMergeScoreAnim);
        
        // settings
        mMainSettingsView = new MainSettingsView(mContext);
        mMainSettingsView.setId(Global.ID_MAIN_MAIN_SETTINGS_VIEW);
        mMainSettingsView.setVisibility(View.GONE);
        mMainSettingsView.setListener(this);
        this.addView(mMainSettingsView);

        mSettingsView = new SettingsView(mContext);
        mSettingsView.setId(Global.ID_MAIN_SETTINGS_VIEW);
        mSettingsView.setVisibility(View.GONE);
        mSettingsView.setListener(this);
        this.addView(mSettingsView);

        mRecordView = new RecordView(mContext);
        mRecordView.setId(Global.ID_MAIN_RECORD_VIEW);
        mRecordView.setVisibility(View.GONE);
        mRecordView.setListener(this);
        this.addView(mRecordView);
    }
    
    private void initLayout() {
        LayoutParams params;
        
        int padding = ScaleUtils.scale(Global.SIZE_MAIN_PADDING);
        this.setPadding(padding, padding, padding, padding);
        
        // Setting Button
        params = new LayoutParams(ScaleUtils.scale(Global.SIZE_MAIN_SETTING_WIDTH),
                ScaleUtils.scale(Global.SIZE_MAIN_SETTING_HEIGHT));
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        mSettingButton.setLayoutParams(params);
        
        // Title
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        ScaleUtils.scale(Global.SIZE_MAIN_TITLE_HEIGHT));
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.BELOW, Global.ID_MAIN_SETTING);
        mTitleLayout.setLayoutParams(params);
        int titlePadding = ScaleUtils.scale(-50);
        mTitleLayout.setPadding(0, titlePadding, 0, titlePadding);
        
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        mTitle.setLayoutParams(params);
        
        // Best Board
        params = new LayoutParams(ScaleUtils.scale(Global.SIZE_MAIN_BEST_BOARD_WIDTH),
                ScaleUtils.scale(Global.SIZE_MAIN_BEST_BOARD_HEIGHT));
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_TOP, Global.ID_MAIN_TITLE);
        mBestBoard.setLayoutParams(params);
        
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = ScaleUtils.scale(Global.SIZE_MAIN_BEST_TITLE_TOP_MARGIN);
        mBestTitle.setLayoutParams(params);
        
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = ScaleUtils.scale(Global.SIZE_MAIN_BEST_TOP_MARGIN);
        mBest.setLayoutParams(params);        
        
        // Score Board
        params = new LayoutParams(ScaleUtils.scale(Global.SIZE_MAIN_SCORE_BOARD_WIDTH),
                ScaleUtils.scale(Global.SIZE_MAIN_SCORE_BOARD_HEIGHT));
        params.addRule(RelativeLayout.LEFT_OF, Global.ID_MAIN_BEST_BOARD);
        params.addRule(RelativeLayout.ALIGN_TOP, Global.ID_MAIN_TITLE);
        params.rightMargin = ScaleUtils.scale(Global.SIZE_MAIN_BOARD_MARGIN);
        mScoreBoard.setLayoutParams(params);
        
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = ScaleUtils.scale(Global.SIZE_MAIN_SCORE_TITLE_TOP_MARGIN);
        mScoreTitle.setLayoutParams(params);
        
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = ScaleUtils.scale(Global.SIZE_MAIN_SCORE_TOP_MARGIN);
        mScore.setLayoutParams(params);     
        
        // restart
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.BELOW, Global.ID_MAIN_TITLE);
        params.topMargin = ScaleUtils.scale(Global.SIZE_MAIN_RESTART_MARGIN);
        mRestartButton.setLayoutParams(params);
        
        // undo
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_TOP, Global.ID_MAIN_RESTART);
        mUndoButton.setLayoutParams(params);
        
        // GameView
        gameWidth = ScaleUtils.screenWidth - 2 * ScaleUtils.scale(Global.SIZE_MAIN_PADDING);
        params = new LayoutParams(gameWidth, gameWidth);
        params.addRule(RelativeLayout.BELOW, Global.ID_MAIN_RESTART);
        params.topMargin = ScaleUtils.scale(Global.SIZE_MAIN_GAME_MARGIN);
        mGameView.setLayoutParams(params);
        
        // DialogView
        mDialogView.setLayoutParams(params);

        // settings
        mMainSettingsView.setLayoutParams(params);
        mSettingsView.setLayoutParams(params);
        mRecordView.setLayoutParams(params);
        
        // Add/Sub score Anim TextView
        params = new LayoutParams(ScaleUtils.scale(Global.SIZE_MAIN_SCORE_BOARD_WIDTH),
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_LEFT, Global.ID_MAIN_SCORE_BOARD);
        params.addRule(RelativeLayout.BELOW, Global.ID_MAIN_SCORE_BOARD);
        mMergeScoreAnim.setLayoutParams(params);
    }
    
    public void setBackgroundStyle() {
        if (Global.STYLE_DAY == Global.bgStyle) {
            this.setBackgroundColor(getResources().getColor(R.color.color_day_bg));
            mTitle.setTextColor(getResources().getColor(R.color.color_day_text_normal_gray));
            mScoreBoard.setBackgroundResource(R.drawable.bg_score_board_day);
            mBestBoard.setBackgroundResource(R.drawable.bg_score_board_day);
            mRestartButton.setTextColor(getResources().getColorStateList(R.color.color_day_text_orange));
            mUndoButton.setTextColor(getResources().getColorStateList(R.color.color_day_text_orange));
            mMergeScoreAnim.setTextColor(getResources().getColor(R.color.color_day_text_normal_gray));
        } else if (Global.STYLE_NIGHT == Global.bgStyle) {
            this.setBackgroundColor(getResources().getColor(R.color.color_night_bg));
            mTitle.setTextColor(getResources().getColor(R.color.color_night_text_normal_white));
            mScoreBoard.setBackgroundResource(R.drawable.bg_score_board_night);
            mBestBoard.setBackgroundResource(R.drawable.bg_score_board_night);
            mRestartButton.setTextColor(getResources().getColorStateList(R.color.color_night_text_yellow));
            mUndoButton.setTextColor(getResources().getColorStateList(R.color.color_night_text_yellow));
            mMergeScoreAnim.setTextColor(getResources().getColor(R.color.color_night_text_normal_white));
        }
        mDialogView.setBackgroundStyle();
        mMainSettingsView.setBackgroundStyle();
        mSettingsView.setBackgroundStyle();
        mRecordView.setBackgroundStyle();
    }
    
    public void setScore(int score) {
        mScore.setText(String.valueOf(score));
    }
    
    public void setBest(int best) {
        mBest.setText(String.valueOf(best));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case Global.ID_MAIN_SETTING:
            Log.e("click", "setting button");
            if (mDialogView.isVisible()) {
                mDialogView.hideDialog();
                break;
            }
            if (mMainSettingsView.isVisible()) {
                mMainSettingsView.hideDialog();
                break;
            }
            if (mSettingsView.isVisible()) {
                mSettingsView.hideDialog();
                break;
            }
            if (mRecordView.isVisible()) {
                mRecordView.hideDialog();
                break;
            }
            
            mMainSettingsView.showDialogWithAnim();
            break;
        case Global.ID_MAIN_RESTART:
            Log.e("click", "restart button");
            
            if (mDialogView.isVisible()) {
                mDialogView.hideDialog();
                break;
            }
            if (mMainSettingsView.isVisible()) {
                mMainSettingsView.hideDialog();
                break;
            }
            if (mSettingsView.isVisible()) {
                mSettingsView.hideDialog();
                break;
            }
            if (mRecordView.isVisible()) {
                mRecordView.hideDialog();
                break;
            }
            
            mDialogView.setText(R.string.dialog_msg_restart, R.string.dialog_btn_yeah, R.string.dialog_btn_nope);
            mDialogView.setType(DialogView.DIALOG_TYPE_TWO_BUTTON, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    game.newGame();
                    mDialogView.hideDialog();
                }
            }, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogView.hideDialog();
                }
            });
            mDialogView.showDialogWithAnim();
            break;
        case Global.ID_MAIN_UNDO:
            Log.e("click", "undo button");
            game.undo();
            break;
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event); 
    }
    
    class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();

//            Log.e("x=", x + "");
//            Log.e("y=", y + "");
//            Log.e("vx=", velocityX + "");
//            Log.e("vy=", velocityY + "");

            float dx = Math.abs(x);
            float dy = Math.abs(y);
            // float vx = Math.abs(velocityX);
            // float vy = Math.abs(velocityY);

            if (x < 0 && dx > dy) {
                // move left
                Log.e("gesture", "move left");
                game.move(GameLogic.MOVE_LEFT);
            } else if (x > 0 && dx > dy) {
                // move right
                Log.e("gesture", "move right");
                game.move(GameLogic.MOVE_RIGHT);
            } else if (y < 0 && dy > dx) {
                // move up
                Log.e("gesture", "move up");
                game.move(GameLogic.MOVE_UP);
            } else if (y > 0 && dy > dx) {
                // move down
                Log.e("gesture", "move down");
                game.move(GameLogic.MOVE_DOWN);
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("gesture", "down");
//            if (mDialogView.isVisible()) {
//                mDialogView.hideDialog();
//            }
            return true;
        }
    }
    

    /**
     * MainView自定义监听器
     * @param cmd
     */
    @Override
    public void onCommand(int cmd, Object params) {
        switch (cmd) {
        case Global.CMD_SHOW_MAIN_SETTINGS:
            mMainSettingsView.showDialog();
            break;
        case Global.CMD_SHOW_SETTINGS:
            mSettingsView.showDialog();
            break;
        case Global.CMD_SHOW_RECORD:
            mRecordView.showDialog();
            break;
        case Global.CMD_RESET:
            mDialogView.setText(R.string.dialog_msg_reset, R.string.dialog_btn_yeah, R.string.dialog_btn_nope);
            mDialogView.setType(DialogView.DIALOG_TYPE_TWO_BUTTON, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    game.resetGame();
                    mDialogView.hideDialog();
                }
            }, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogView.hideDialog();
                }
            });
            mDialogView.showDialog();
            break;
        case Global.CMD_ABOUT:
            mDialogView.setText(R.string.dialog_msg_about_info, R.string.dialog_btn_ok);
            mDialogView.setType(DialogView.DIALOG_TYPE_ONE_BUTTON, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogView.hideDialog();
                }
            });
            mDialogView.showDialog();
            break;
        case Global.CMD_SETTINGS_STYLE:
            if (Global.bgStyle == Global.STYLE_DAY) {
                Global.bgStyle = Global.STYLE_NIGHT;
            } else {
                Global.bgStyle = Global.STYLE_DAY;
            }
            this.setBackgroundStyle();
            mSettingsView.setStyleText();
            break;
        case Global.CMD_SETTINGS_CELL_NUM:
            game.saveGame();
            if (4 == Global.cellNum) {
                Global.cellNum = 5;
            } else if (5 == Global.cellNum) {
                Global.cellNum = 4;
            }
            mSettingsView.setModeText();
            game.initGrids();
            game.loadGame();
            break;
        }
    }
}
