package com.example.game2048;

import com.example.game2048.utils.ScaleUtils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogView extends RelativeLayout {
    
    private Context mContext;
    
    private TextView mDialogMsgView;
    private TextView mDialogButton1;
    private TextView mDialogButton2;

    public DialogView(Context context) {
        super(context);
        mContext = context;
        
        initView();
        initLayout();
    }

    private void initView() {
        // dialog layout
        this.setId(Global.ID_DIALOG_LAYOUT);
        
        // message text
        mDialogMsgView = new TextView(mContext);
        mDialogMsgView.setId(Global.ID_DIALOG_MSG);
        mDialogMsgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        mDialogMsgView.setTypeface(Global.font);
        this.addView(mDialogMsgView);
        
        // button 1
        mDialogButton1 = new TextView(mContext);
        mDialogButton1.setId(Global.ID_DIALOG_BUTTON1);
        mDialogButton1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        mDialogButton1.setTypeface(Global.font);
        mDialogButton1.setClickable(true);
        mDialogButton1.setOnClickListener(null);
        this.addView(mDialogButton1);
        
        // button 2
        mDialogButton2 = new TextView(mContext);
        mDialogButton2.setId(Global.ID_DIALOG_BUTTON2);
        mDialogButton2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        mDialogButton2.setTypeface(Global.font);
        mDialogButton2.setClickable(true);
        mDialogButton2.setOnClickListener(null);
        this.addView(mDialogButton2);
    }
    
    private void initLayout() {
        LayoutParams params;
        
        // message text
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = ScaleUtils.scale(Global.SIZE_DIALOG_MSG_TOP_MARGIN);
        mDialogMsgView.setLayoutParams(params);
        
        // button 1
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, Global.ID_DIALOG_MSG);
        params.topMargin = ScaleUtils.scale(Global.SIZE_DIALOG_BUTTON_TOP_MARGIN);
        mDialogButton1.setLayoutParams(params);
        
        // button 2
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, Global.ID_DIALOG_MSG);
        params.topMargin = ScaleUtils.scale(Global.SIZE_DIALOG_BUTTON_TOP_MARGIN);
        mDialogButton2.setLayoutParams(params);
    }
    
    // type
    public static final int DIALOG_TYPE_ONE_BUTTON = 0x00;
    public static final int DIALOG_TYPE_TWO_BUTTON = 0x01;
    
    public void setType(int type, OnClickListener listener) {
        this.setType(type, listener, null);
    }
    
    public void setType(int type, OnClickListener listener1, OnClickListener listener2) {
        switch (type) {
        case DIALOG_TYPE_ONE_BUTTON:
            setOneButtonLayoutParams();
            break;
        case DIALOG_TYPE_TWO_BUTTON:
            setTwoButtonLayoutParams();
            break;
        }
        // event
        mDialogButton1.setOnClickListener(listener1);
        mDialogButton2.setOnClickListener(listener2);
    }
    
    private void setOneButtonLayoutParams() {
        mDialogButton1.setVisibility(View.VISIBLE);
        mDialogButton2.setVisibility(View.GONE);
        LayoutParams params;
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.BELOW, Global.ID_DIALOG_MSG);
        params.topMargin = ScaleUtils.scale(Global.SIZE_DIALOG_BUTTON_TOP_MARGIN);
        mDialogButton1.setLayoutParams(params);
    }
    
    private void setTwoButtonLayoutParams() {
        mDialogButton1.setVisibility(View.VISIBLE);
        mDialogButton2.setVisibility(View.VISIBLE);
        
        LayoutParams params;
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, Global.ID_DIALOG_MSG);
        params.addRule(RelativeLayout.ALIGN_LEFT);
        params.topMargin = ScaleUtils.scale(Global.SIZE_DIALOG_BUTTON_TOP_MARGIN);
        params.leftMargin = ScaleUtils.scale(Global.SIZE_DIALOG_BUTTON1_LEFT_MARGIN);
        mDialogButton1.setLayoutParams(params);
        
        params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, Global.ID_DIALOG_MSG);
        params.addRule(RelativeLayout.ALIGN_LEFT);
        params.topMargin = ScaleUtils.scale(Global.SIZE_DIALOG_BUTTON_TOP_MARGIN);
        params.leftMargin = ScaleUtils.scale(Global.SIZE_DIALOG_BUTTON2_LEFT_MARGIN);
        mDialogButton2.setLayoutParams(params);
    }
    
    public void setText(String msg, String button) {
        mDialogMsgView.setText(msg);
        mDialogButton1.setText(button);
    }
    
    public void setText(int msgRes, int buttonRes) {
        mDialogMsgView.setText(msgRes);
        mDialogButton1.setText(buttonRes);
    }
    
    public void setText(String msg, String button1, String button2) {
        mDialogMsgView.setText(msg);
        mDialogButton1.setText(button1);
        mDialogButton2.setText(button2);
    }
    
    public void setText(int msgRes, int button1Res, int button2Res) {
        mDialogMsgView.setText(msgRes);
        mDialogButton1.setText(button1Res);
        mDialogButton2.setText(button2Res);        
    }
    
    // style
    public void setBackgroundStyle() {
        if (Global.STYLE_DAY == Global.bgStyle) {
            // background
            Drawable bgDrawable = getResources().getDrawable(R.drawable.bg_dialog_day);
            this.setBackgroundDrawable(bgDrawable);
            // message
            mDialogMsgView.setTextColor(getResources().getColor(R.color.color_day_text_normal_gray));
            // button1
            mDialogButton1.setTextColor(getResources().getColorStateList(R.color.color_day_text_gray));
            // button2
            mDialogButton2.setTextColor(getResources().getColorStateList(R.color.color_day_text_gray));
        } else if (Global.STYLE_NIGHT == Global.bgStyle) {
            Drawable bgDrawable = getResources().getDrawable(R.drawable.bg_dialog_night);
            this.setBackgroundDrawable(bgDrawable);
            // message
            mDialogMsgView.setTextColor(getResources().getColor(R.color.color_night_text_normal_white));
            // button1
            mDialogButton1.setTextColor(getResources().getColorStateList(R.color.color_night_text_white));
            // button2
            mDialogButton2.setTextColor(getResources().getColorStateList(R.color.color_night_text_white));
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
