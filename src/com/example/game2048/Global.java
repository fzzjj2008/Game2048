package com.example.game2048;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Typeface;

public class Global {

    // id
    private static final int BASE_ID_MAIN = 0x00000000;
    public static final int ID_MAIN_LAYOUT = BASE_ID_MAIN + 0x0001;
    public static final int ID_MAIN_SETTING = BASE_ID_MAIN + 0x0002;
    public static final int ID_MAIN_TITLE = BASE_ID_MAIN + 0x0003;
    public static final int ID_MAIN_SCORE_BOARD = BASE_ID_MAIN + 0x0004;
    public static final int ID_MAIN_SCORE_TITLE = BASE_ID_MAIN + 0x0005;
    public static final int ID_MAIN_SCORE = BASE_ID_MAIN + 0x0006;
    public static final int ID_MAIN_BEST_BOARD = BASE_ID_MAIN + 0x0007;
    public static final int ID_MAIN_BEST_TITLE = BASE_ID_MAIN + 0x0008;
    public static final int ID_MAIN_BEST = BASE_ID_MAIN + 0x0009;
    public static final int ID_MAIN_RESTART = BASE_ID_MAIN + 0x000a;
    public static final int ID_MAIN_UNDO = BASE_ID_MAIN + 0x000b;
    public static final int ID_MAIN_GAME_VIEW = BASE_ID_MAIN + 0x000c;
    public static final int ID_MAIN_DIALOG_VIEW = BASE_ID_MAIN + 0x000d;
    public static final int ID_MAIN_MERGE_ANIM = BASE_ID_MAIN + 0x000e;
    public static final int ID_MAIN_MAIN_SETTINGS_VIEW = BASE_ID_MAIN + 0x000f;
    public static final int ID_MAIN_SETTINGS_VIEW = BASE_ID_MAIN + 0x0010;
    public static final int ID_MAIN_RECORD_VIEW = BASE_ID_MAIN + 0x0011;
    
    private static final int BASE_ID_DIALOG = 0x00000100;
    public static final int ID_DIALOG_LAYOUT = BASE_ID_DIALOG + 0x0001;
    public static final int ID_DIALOG_MSG = BASE_ID_DIALOG + 0x0002;
    public static final int ID_DIALOG_BUTTON1 = BASE_ID_DIALOG + 0x0003;
    public static final int ID_DIALOG_BUTTON2 = BASE_ID_DIALOG + 0x0004;
    
    private static final int BASE_ID_SETTINGS = 0x00000200;
    public static final int ID_SETTINGS_MAIN_SETTINGS = BASE_ID_SETTINGS + 0x0001;
    public static final int ID_SETTINGS_MAIN_RECORD = BASE_ID_SETTINGS + 0x0002;
    public static final int ID_SETTINGS_MAIN_RESET = BASE_ID_SETTINGS + 0x0003;
    public static final int ID_SETTINGS_MAIN_ABOUT = BASE_ID_SETTINGS + 0x0004;
    
    public static final int ID_SETTINGS_STYLE = BASE_ID_SETTINGS + 0x0011;
    public static final int ID_SETTINGS_MODE = BASE_ID_SETTINGS + 0x0012;
    public static final int ID_SETTINGS_BACK = BASE_ID_SETTINGS + 0x0013;

    public static final int ID_SETTINGS_RECORD_BACK = BASE_ID_SETTINGS + 0x0021;
    
    // command
    public static final int CMD_SHOW_MAIN_SETTINGS = 0x01;
    public static final int CMD_SHOW_SETTINGS = 0x02;
    public static final int CMD_SHOW_RECORD = 0x03;
    public static final int CMD_RESET = 0x04;
    public static final int CMD_ABOUT = 0x05;
    public static final int CMD_SETTINGS_STYLE = 0x06;
    public static final int CMD_SETTINGS_CELL_NUM = 0x07;

    // size
    public static final int SIZE_MAIN_PADDING = 16;
    public static final int SIZE_MAIN_SETTING_WIDTH = 48;
    public static final int SIZE_MAIN_SETTING_HEIGHT = 48;
    public static final int SIZE_MAIN_TITLE_HEIGHT = 50;
    public static final int SIZE_MAIN_BOARD_MARGIN = 10;
    public static final int SIZE_MAIN_SCORE_BOARD_WIDTH = 90;
    public static final int SIZE_MAIN_SCORE_BOARD_HEIGHT = 50;
    public static final int SIZE_MAIN_SCORE_TITLE_TOP_MARGIN = 5;
    public static final int SIZE_MAIN_SCORE_TOP_MARGIN = 25;
    public static final int SIZE_MAIN_BEST_BOARD_WIDTH = 90;
    public static final int SIZE_MAIN_BEST_BOARD_HEIGHT = 50;
    public static final int SIZE_MAIN_BEST_TITLE_TOP_MARGIN = 5;
    public static final int SIZE_MAIN_BEST_TOP_MARGIN = 25;
    public static final int SIZE_MAIN_RESTART_MARGIN = 60;
    public static final int SIZE_MAIN_GAME_MARGIN = 10;
    
    public static final int SIZE_DIALOG_MSG_TOP_MARGIN = 100;
    public static final int SIZE_DIALOG_BUTTON_TOP_MARGIN = 60;
    public static final int SIZE_DIALOG_BUTTON1_LEFT_MARGIN = 80;
    public static final int SIZE_DIALOG_BUTTON2_LEFT_MARGIN = 200;
    
    public static final int SIZE_SETTINGS_MAIN_TEXT_MARGIN = 10;
    
    public static final int SIZE_SETTINGS_TOP_MARGIN = 100;
    public static final int SIZE_SETTINGS_TEXT_TOP_MARGIN = 15;
    public static final int SIZE_SETTINGS_TEXT_TITLE_LEFT_MARGIN = 80;
    public static final int SIZE_SETTINGS_TEXT_CONTENT_LEFT_MARGIN = 30;
    public static final int SIZE_SETTINGS_BACK_TOP_MARGIN = 50;
    
    public static final int SIZE_SETTINGS_RECORD_TITLE_TOP_MARGIN = 50;
    public static final int SIZE_SETTINGS_RECORD_TITLE_BOTTOM_MARGIN = 30;
    public static final int SIZE_SETTINGS_RECORD_TEXT_TOP_MARGIN = 10;  
    public static final int SIZE_SETTINGS_RECORD_TEXT_TITLE_LEFT_MARGIN = 60;  
    public static final int SIZE_SETTINGS_RECORD_TEXT_CONTENT_LEFT_MARGIN = 30;  
    public static final int SIZE_SETTINGS_RECORD_BACK_TOP_MARGIN = 30;

    // 一共cellNum*cellNum个方块
    public static int cellNum = 4;
    // 游戏界面下，cell_width和grid_width的比例是7:1
    public static int SIZE_GAME_CELL_GRID_RATIO = 7;
    // 随机出现的方格中，生成2的比例
    public static float cellRatio2 = 0.9f;
    // 字体
    public static Typeface font;
    // 动画时长
    public static int animtimeMove = 180;
    public static int animtimeCreate = 80;
    public static int animtimeMerge = 80;
    public static int animRefresh = 20;
    // style
    public static final int STYLE_DAY = 0x00;
    public static final int STYLE_NIGHT = 0x01;
    public static int bgStyle = STYLE_DAY;
    
    // 存储的数据
    public static Map<String, String> defaultMap = new HashMap<String, String>();
    public static final String PREF_STYLE = "style";
    public static final String PREF_CELL_NUM = "cell_num";
    public static final String PREF_SCORE_4X4 = "score_4x4";
    public static final String PREF_BEST_4X4 = "best_4x4";
    public static final String PREF_LAST_SCORE_4X4 = "last_score_4x4";
    public static final String PREF_LAST_BEST_4X4 = "last_best_4x4";
    public static final String PREF_GRID_4X4 = "grid_4x4";
    public static final String PREF_PREGRID_4X4 = "pregrid_4x4";
    public static final String PREF_SCORE_5X5 = "score_5x5";
    public static final String PREF_BEST_5X5 = "best_5x5";
    public static final String PREF_LAST_SCORE_5X5 = "last_score_5x5";
    public static final String PREF_LAST_BEST_5X5 = "last_best_5x5";
    public static final String PREF_GRID_5X5 = "grid_5x5";
    public static final String PREF_PREGRID_5X5 = "pregrid_5x5";
    public static int defaultStyle = STYLE_DAY;
    public static int defaultCellNum = 4;
    public static int defaultScore = 0;
    public static int defaultBest = 0;
    public static int defaultLastScore = 0;
    public static int defaultLastBest = 0;
}
