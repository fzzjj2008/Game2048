package com.example.game2048;

import com.example.game2048.utils.ScaleUtils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingsView extends LinearLayout implements OnClickListener {

    private Context mContext;
    private ICommandListener mListener;

    private LinearLayout mStyleLayout;
    private LinearLayout mModeLayout;
    private TextView mStyleTitle;
    private TextView mModeTitle;
    private TextView mStyle;
    private TextView mMode;
    private TextView mBack;

    public SettingsView(Context context) {
        super(context);
        mContext = context;

        initView();
        initLayout();
    }

    public void setListener(ICommandListener listener) {
        mListener = listener;
    }

    private void initView() {
        // style
        mStyleLayout = new LinearLayout(mContext);
        this.addView(mStyleLayout);

        mStyleTitle = new TextView(mContext);
        mStyleTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mStyleTitle.setTypeface(Global.font);
        mStyleTitle.setText("Style:");
        mStyleLayout.addView(mStyleTitle);

        mStyle = new TextView(mContext);
        mStyle.setId(Global.ID_SETTINGS_STYLE);
        mStyle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mStyle.setTypeface(Global.font);
        mStyle.setClickable(true);
        mStyle.setOnClickListener(this);
        mStyleLayout.addView(mStyle);

        // mode
        mModeLayout = new LinearLayout(mContext);
        this.addView(mModeLayout);

        mModeTitle = new TextView(mContext);
        mModeTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mModeTitle.setTypeface(Global.font);
        mModeTitle.setText("Mode:");
        mModeLayout.addView(mModeTitle);

        mMode = new TextView(mContext);
        mMode.setId(Global.ID_SETTINGS_MODE);
        mMode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mMode.setTypeface(Global.font);
        mMode.setClickable(true);
        mMode.setOnClickListener(this);
        mModeLayout.addView(mMode);

        // back
        mBack = new TextView(mContext);
        mBack.setId(Global.ID_SETTINGS_BACK);
        mBack.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mBack.setTypeface(Global.font);
        mBack.setText(R.string.settings_main_back);
        mBack.setClickable(true);
        mBack.setOnClickListener(this);
        this.addView(mBack);
    }

    private void initLayout() {
        LayoutParams params;

        // layout
        this.setOrientation(LinearLayout.VERTICAL);

        // style mode
        params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_TOP_MARGIN);
        mStyleLayout.setLayoutParams(params);

        params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_TEXT_TOP_MARGIN);
        mModeLayout.setLayoutParams(params);

        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_TEXT_TITLE_LEFT_MARGIN);
        mStyleTitle.setLayoutParams(params);
        mModeTitle.setLayoutParams(params);

        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_TEXT_CONTENT_LEFT_MARGIN);
        mStyle.setLayoutParams(params);
        mMode.setLayoutParams(params);

        // back
        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_BACK_TOP_MARGIN);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        mBack.setLayoutParams(params);
    }

    public void setStyleText() {
        String style = Global.bgStyle == Global.STYLE_DAY ? "Day" : "Night";
        mStyle.setText(style);
    }

    public void setModeText() {
        String mode = Global.cellNum == 4 ? "4x4" : "5x5";
        mMode.setText(mode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case Global.ID_SETTINGS_STYLE:
            mListener.onCommand(Global.CMD_SETTINGS_STYLE, null);
            break;
        case Global.ID_SETTINGS_MODE:
            mListener.onCommand(Global.CMD_SETTINGS_CELL_NUM, null);
            break;
        case Global.ID_SETTINGS_BACK:
            this.hideDialog();
            mListener.onCommand(Global.CMD_SHOW_MAIN_SETTINGS, null);
            break;
        }
    }

    // style
    public void setBackgroundStyle() {
        if (Global.STYLE_DAY == Global.bgStyle) {
            // background
            Drawable bgDrawable = getResources().getDrawable(R.drawable.bg_dialog_day);
            this.setBackgroundDrawable(bgDrawable);
            // style mode
            mStyleTitle.setTextColor(getResources().getColor(R.color.color_day_text_normal_gray));
            mModeTitle.setTextColor(getResources().getColor(R.color.color_day_text_normal_gray));
            mStyle.setTextColor(getResources().getColorStateList(R.color.color_day_text_orange));
            mMode.setTextColor(getResources().getColorStateList(R.color.color_day_text_orange));
            // back
            mBack.setTextColor(getResources().getColorStateList(R.color.color_day_text_gray));
        } else if (Global.STYLE_NIGHT == Global.bgStyle) {
            // background
            Drawable bgDrawable = getResources().getDrawable(R.drawable.bg_dialog_night);
            this.setBackgroundDrawable(bgDrawable);
            // style mode
            mStyleTitle.setTextColor(getResources().getColor(R.color.color_night_text_normal_white));
            mModeTitle.setTextColor(getResources().getColor(R.color.color_night_text_normal_white));
            mStyle.setTextColor(getResources().getColorStateList(R.color.color_night_text_yellow));
            mMode.setTextColor(getResources().getColorStateList(R.color.color_night_text_yellow));
            // back
            mBack.setTextColor(getResources().getColorStateList(R.color.color_night_text_white));
        }
    }

    // visible
    public boolean isVisible() {
        return this.getVisibility() == View.VISIBLE;
    }

    public void showDialog() {
        this.setStyleText();
        this.setModeText();

        this.setVisibility(View.VISIBLE);
    }

    public void hideDialog() {
        this.setVisibility(View.GONE);
    }

}
