package com.example.game2048;

import com.example.game2048.utils.ScaleUtils;
import com.example.game2048.utils.SettingsManager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecordView extends LinearLayout implements OnClickListener {

    private Context mContext;
    private ICommandListener mListener;

    private TextView mRecordTitle;
    private LinearLayout mRecordLayout_4x4;
    private LinearLayout mRecordLayout_5x5;
    private TextView mRecordTitle_4x4;
    private TextView mRecordTitle_5x5;
    private TextView mRecord_4x4;
    private TextView mRecord_5x5;
    private TextView mBack;

    public RecordView(Context context) {
        super(context);
        mContext = context;

        initView();
        initLayout();
    }

    public void setListener(ICommandListener listener) {
        mListener = listener;
    }

    private void initView() {
        // record
        mRecordTitle = new TextView(mContext);
        mRecordTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        mRecordTitle.setTypeface(Global.font);
        mRecordTitle.setText("Record Board");
        this.addView(mRecordTitle);

        mRecordLayout_4x4 = new LinearLayout(mContext);
        this.addView(mRecordLayout_4x4);

        mRecordTitle_4x4 = new TextView(mContext);
        mRecordTitle_4x4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mRecordTitle_4x4.setTypeface(Global.font);
        mRecordTitle_4x4.setText("Mode 4x4:");
        mRecordLayout_4x4.addView(mRecordTitle_4x4);

        mRecord_4x4 = new TextView(mContext);
        mRecord_4x4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mRecord_4x4.setTypeface(Global.font);
        mRecordLayout_4x4.addView(mRecord_4x4);

        mRecordLayout_5x5 = new LinearLayout(mContext);
        this.addView(mRecordLayout_5x5);

        mRecordTitle_5x5 = new TextView(mContext);
        mRecordTitle_5x5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mRecordTitle_5x5.setTypeface(Global.font);
        mRecordTitle_5x5.setText("Mode 5x5:");
        mRecordLayout_5x5.addView(mRecordTitle_5x5);

        mRecord_5x5 = new TextView(mContext);
        mRecord_5x5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mRecord_5x5.setTypeface(Global.font);
        mRecordLayout_5x5.addView(mRecord_5x5);

        // back
        mBack = new TextView(mContext);
        mBack.setId(Global.ID_SETTINGS_RECORD_BACK);
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

        // record
        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_RECORD_TITLE_TOP_MARGIN);
        params.bottomMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_RECORD_TITLE_BOTTOM_MARGIN);
        mRecordTitle.setLayoutParams(params);

        params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_RECORD_TEXT_TOP_MARGIN);
        mRecordLayout_4x4.setLayoutParams(params);
        mRecordLayout_5x5.setLayoutParams(params);

        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_RECORD_TEXT_TITLE_LEFT_MARGIN);
        mRecordTitle_4x4.setLayoutParams(params);
        mRecordTitle_5x5.setLayoutParams(params);

        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_RECORD_TEXT_CONTENT_LEFT_MARGIN);
        mRecord_4x4.setLayoutParams(params);
        mRecord_5x5.setLayoutParams(params);

        // back
        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_RECORD_BACK_TOP_MARGIN);
        mBack.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case Global.ID_SETTINGS_RECORD_BACK:
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
            // record
            mRecordTitle.setTextColor(getResources().getColor(R.color.color_day_text_normal_gray));
            mRecordTitle_4x4.setTextColor(getResources().getColor(R.color.color_day_text_normal_gray));
            mRecord_4x4.setTextColor(getResources().getColor(R.color.color_day_text_normal_orange));
            mRecordTitle_5x5.setTextColor(getResources().getColor(R.color.color_day_text_normal_gray));
            mRecord_5x5.setTextColor(getResources().getColor(R.color.color_day_text_normal_orange));
            // back
            mBack.setTextColor(getResources().getColorStateList(R.color.color_day_text_gray));
        } else if (Global.STYLE_NIGHT == Global.bgStyle) {
            // background
            Drawable bgDrawable = getResources().getDrawable(R.drawable.bg_dialog_night);
            this.setBackgroundDrawable(bgDrawable);
            // record
            mRecordTitle.setTextColor(getResources().getColor(R.color.color_night_text_normal_white));
            mRecordTitle_4x4.setTextColor(getResources().getColor(R.color.color_night_text_normal_white));
            mRecord_4x4.setTextColor(getResources().getColor(R.color.color_night_text_normal_yellow));
            mRecordTitle_5x5.setTextColor(getResources().getColor(R.color.color_night_text_normal_white));
            mRecord_5x5.setTextColor(getResources().getColor(R.color.color_night_text_normal_yellow));
            // back
            mBack.setTextColor(getResources().getColorStateList(R.color.color_night_text_white));
        }
    }

    // visible
    public boolean isVisible() {
        return this.getVisibility() == View.VISIBLE;
    }

    public void showDialog() {
        String score_4x4 = SettingsManager.getString(mContext, Global.PREF_BEST_4X4);
        String score_5x5 = SettingsManager.getString(mContext, Global.PREF_BEST_5X5);
        mRecord_4x4.setText(score_4x4);
        mRecord_5x5.setText(score_5x5);

        this.setVisibility(View.VISIBLE);
    }

    public void hideDialog() {
        this.setVisibility(View.GONE);
    }

}
