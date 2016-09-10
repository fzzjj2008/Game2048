package com.example.game2048;

import com.example.game2048.utils.ScaleUtils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainSettingsView extends LinearLayout implements OnClickListener {

    private Context mContext;
    private ICommandListener mListener;

    private TextView mSettingsView;
    private TextView mRecordView;
    private TextView mResetView;
    private TextView mAboutView;

    public MainSettingsView(Context context) {
        super(context);
        mContext = context;

        initView();
        initLayout();
    }

    public void setListener(ICommandListener listener) {
        mListener = listener;
    }

    private void initView() {
        // settings
        mSettingsView = new TextView(mContext);
        mSettingsView.setId(Global.ID_SETTINGS_MAIN_SETTINGS);
        mSettingsView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mSettingsView.setTypeface(Global.font);
        mSettingsView.setText(R.string.settings_main_settings);
        mSettingsView.setClickable(true);
        mSettingsView.setOnClickListener(this);
        this.addView(mSettingsView);

        // record
        mRecordView = new TextView(mContext);
        mRecordView.setId(Global.ID_SETTINGS_MAIN_RECORD);
        mRecordView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mRecordView.setTypeface(Global.font);
        mRecordView.setText(R.string.settings_main_record);
        mRecordView.setClickable(true);
        mRecordView.setOnClickListener(this);
        this.addView(mRecordView);

        // reset
        mResetView = new TextView(mContext);
        mResetView.setId(Global.ID_SETTINGS_MAIN_RESET);
        mResetView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mResetView.setTypeface(Global.font);
        mResetView.setText(R.string.settings_main_reset);
        mResetView.setClickable(true);
        mResetView.setOnClickListener(this);
        this.addView(mResetView);

        // about
        mAboutView = new TextView(mContext);
        mAboutView.setId(Global.ID_SETTINGS_MAIN_ABOUT);
        mAboutView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mAboutView.setTypeface(Global.font);
        mAboutView.setText(R.string.settings_main_about);
        mAboutView.setClickable(true);
        mAboutView.setOnClickListener(this);
        this.addView(mAboutView);
    }

    private void initLayout() {
        LayoutParams params;

        // layout
        this.setGravity(Gravity.CENTER);
        this.setOrientation(LinearLayout.VERTICAL);

        // settings
        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_MAIN_TEXT_MARGIN);
        params.bottomMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_MAIN_TEXT_MARGIN);
        mSettingsView.setLayoutParams(params);

        // record
        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_MAIN_TEXT_MARGIN);
        params.bottomMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_MAIN_TEXT_MARGIN);
        mRecordView.setLayoutParams(params);

        // reset
        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_MAIN_TEXT_MARGIN);
        params.bottomMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_MAIN_TEXT_MARGIN);
        mResetView.setLayoutParams(params);

        // about
        params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_MAIN_TEXT_MARGIN);
        params.bottomMargin = ScaleUtils.scale(Global.SIZE_SETTINGS_MAIN_TEXT_MARGIN);
        mAboutView.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case Global.ID_SETTINGS_MAIN_SETTINGS:
            this.hideDialog();
            mListener.onCommand(Global.CMD_SHOW_SETTINGS, null);
            break;
        case Global.ID_SETTINGS_MAIN_RECORD:
            this.hideDialog();
            mListener.onCommand(Global.CMD_SHOW_RECORD, null);
            break;
        case Global.ID_SETTINGS_MAIN_RESET:
            this.hideDialog();
            mListener.onCommand(Global.CMD_RESET, null);
            break;
        case Global.ID_SETTINGS_MAIN_ABOUT:
            this.hideDialog();
            mListener.onCommand(Global.CMD_ABOUT, null);
            break;
        }
    }
    
    // style
    public void setBackgroundStyle() {
        if (Global.STYLE_DAY == Global.bgStyle) {
            // background
            Drawable bgDrawable = getResources().getDrawable(R.drawable.bg_dialog_day);
            this.setBackgroundDrawable(bgDrawable);
            // settings
            mSettingsView.setTextColor(getResources().getColorStateList(R.color.color_day_text_gray));
            // record
            mRecordView.setTextColor(getResources().getColorStateList(R.color.color_day_text_gray));
            // reset
            mResetView.setTextColor(getResources().getColorStateList(R.color.color_day_text_gray));
            // about
            mAboutView.setTextColor(getResources().getColorStateList(R.color.color_day_text_gray));
        } else if (Global.STYLE_NIGHT == Global.bgStyle) {
            // background
            Drawable bgDrawable = getResources().getDrawable(R.drawable.bg_dialog_night);
            this.setBackgroundDrawable(bgDrawable);
            // settings
            mSettingsView.setTextColor(getResources().getColorStateList(R.color.color_night_text_white));
            // record
            mRecordView.setTextColor(getResources().getColorStateList(R.color.color_night_text_white));
            // reset
            mResetView.setTextColor(getResources().getColorStateList(R.color.color_night_text_white));
            // about
            mAboutView.setTextColor(getResources().getColorStateList(R.color.color_night_text_white));
        }
    }

    // visible
    public boolean isVisible() {
        return this.getVisibility() == View.VISIBLE;
    }

    public void showDialogWithAnim() {
        this.setVisibility(View.VISIBLE);

        // anim
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        this.startAnimation(anim);
    }

    public void showDialog() {
        this.setVisibility(View.VISIBLE);
    }

    public void hideDialog() {
        this.setVisibility(View.GONE);
    }

}
