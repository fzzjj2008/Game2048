package com.example.game2048;

import com.example.game2048.utils.ScaleUtils;
import com.example.game2048.utils.SettingsManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
    
    private MainView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ScaleUtils.init();
        SettingsManager.initDefaultMap(getBaseContext());
        
        mView = new MainView(getBaseContext());
        setContentView(mView);
    }
    
    @Override
    public void onBackPressed() {
        if (mView.mDialogView.isVisible()) {
            mView.mDialogView.hideDialog();
            return;
        }
        if (mView.mMainSettingsView.isVisible()) {
            mView.mMainSettingsView.hideDialog();
            return;
        }
        if (mView.mSettingsView.isVisible()) {
            mView.mSettingsView.hideDialog();
            mView.mMainSettingsView.showDialog();
            return;
        }
        if (mView.mRecordView.isVisible()) {
            mView.mRecordView.hideDialog();
            mView.mMainSettingsView.showDialog();
            return;
        }
        
        mView.mDialogView.setText(R.string.dialog_msg_exit, R.string.dialog_btn_yeah, R.string.dialog_btn_nope);
        mView.mDialogView.setType(DialogView.DIALOG_TYPE_TWO_BUTTON, new OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.game.saveGame();
                finish();
            }
        }, new OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.mDialogView.hideDialog();
            }
        });
        mView.mDialogView.showDialogWithAnim();
    }
    
    @Override
    protected void onResume() {
        mView.game.loadGame();
        super.onResume();
    }
    
    @Override
    protected void onPause() {
        mView.game.saveGame();
        mView.mGameView.recycleBitmaps();
        super.onPause();
    }

}
