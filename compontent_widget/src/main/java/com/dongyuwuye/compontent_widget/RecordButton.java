package com.dongyuwuye.compontent_widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;


import com.dongyuwuye.compontent_sdk.listener.VoiceListener;
import com.dongyuwuye.compontent_sdk.utils.LogUtils;
import com.dongyuwuye.compontent_sdk.utils.VoiceDictationUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class RecordButton extends AppCompatButton {

    private String TAG = "RecordButton";
    private Context context;
    private VoiceDictationUtils utils;
    private Timer timer;

    public RecordButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private String mFilePath = BuildConfig.APPLICATION_ID + "/voiceCache/";
    private String mFileName = "voice_" + System.currentTimeMillis() + ".wav";


    private OnFinishedRecordListener finishedListener;
    /**
     * 最短录音时间
     **/
    private int MIN_INTERVAL_TIME = 1000;
    /**
     * 最长录音时间
     **/
    private int MAX_INTERVAL_TIME = 1000 * 60;


    private View view;

    private TextView mStateTV;

    private ImageView mStateIV;

    private MediaRecorder mRecorder;
    private ObtainDecibelThread mThread;
    private Handler volumeHandler;

    private boolean isLast = false;

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    private float y;

    private HashMap<String, String> mIatResults = new HashMap<>();

    public void setOnFinishedRecordListener(OnFinishedRecordListener listener) {
        finishedListener = listener;
    }


    private static long startTime;
    private Dialog recordDialog;
    private static int[] res = {R.drawable.ic_volume_0, R.drawable.ic_volume_1, R.drawable.ic_volume_2,
            R.drawable.ic_volume_3, R.drawable.ic_volume_4, R.drawable.ic_volume_5, R.drawable.ic_volume_6
            , R.drawable.ic_volume_7, R.drawable.ic_volume_8};


    @SuppressLint("HandlerLeak")
    private void init() {
        this.setText("按住说话");
        volumeHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == -100) {
                    recordDialog.dismiss();
                } else if (msg.what != -1) {
                    mStateIV.setImageResource(res[msg.what]);
                }
            }
        };
    }

    private AnimationDrawable anim;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        y = event.getY();
        if (mStateTV != null && mStateIV != null && y < 0) {
            mStateTV.setText("松开手指,取消说话");
            mStateIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_volume_cancel));
        } else if (mStateTV != null) {
            mStateTV.setText("手指上滑,取消说话");
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setText("松开结束");
                setBackground(getResources().getDrawable(R.drawable.shape_btn_blue1_5));
                startRecording();
                initDialogAndStartRecord();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.setText("按住说话");
                setBackground(getResources().getDrawable(R.drawable.shape_bg_white_5));
                if (y >= 0 && (System.currentTimeMillis() - startTime <= MAX_INTERVAL_TIME)) {
                    LogUtils.e(TAG, "结束录音:");
                    finishRecord();

                } else if (y < 0) {  //当手指向上滑，会cancel
                    cancelRecord();
                }
                break;
        }

        return true;
    }

    /**
     * 初始化录音对话框 并 开始录音
     */
    private void initDialogAndStartRecord() {
        startTime = System.currentTimeMillis();
        recordDialog = new Dialog(getContext(), R.style.like_toast_dialog_style);
        // view = new ImageView(getContext());
        view = View.inflate(getContext(), R.layout.dialog_record, null);
        mStateIV = (ImageView) view.findViewById(R.id.rc_audio_state_image);
        mStateTV = (TextView) view.findViewById(R.id.rc_audio_state_text);
        mStateIV.setImageDrawable(getResources().getDrawable(R.drawable.anim_mic));
        anim = (AnimationDrawable) mStateIV.getDrawable();
        anim.start();
        mStateIV.setVisibility(View.VISIBLE);
        //mStateIV.setImageResource(R.drawable.ic_volume_1);
        mStateTV.setVisibility(View.VISIBLE);
        mStateTV.setText("手指上滑,取消发送");
        recordDialog.setContentView(view, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        recordDialog.setOnDismissListener(onDismiss);
        WindowManager.LayoutParams lp = recordDialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        recordDialog.show();
    }

    /**
     * 放开手指，结束录音处理
     */
    private void finishRecord() {
        long intervalTime = System.currentTimeMillis() - startTime;
        if (intervalTime < MIN_INTERVAL_TIME) {
            LogUtils.e(TAG, "录音时间太短");
            volumeHandler.sendEmptyMessageDelayed(-100, 500);
            //view.setBackgroundResource(R.drawable.ic_voice_cancel);
            mStateIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_volume_wraning));
            mStateTV.setText("录音时间太短");
            anim.stop();
            File file = new File(mFilePath + mFileName);
            file.delete();
        /*    stopRecording();
            recordDialog.dismiss();*/
            utils.cancel();
            return;
        } else {
            recordDialog.dismiss();
        }
    }

    /**
     * 取消录音对话框和停止录音
     */
    public void cancelRecord() {
        recordDialog.dismiss();
        File file = new File(mFilePath + mFileName);
        file.delete();
    }

    /**
     * 执行录音操作
     */
    //int num = 0 ;
    private void startRecording() {
        mIatResults.clear();
        isLast = false;
        utils = new VoiceDictationUtils(context, new VoiceListener() {
            @Override
            public void getVoiceResult(@Nullable String sn, @Nullable String text) {
                mIatResults.put(sn, text);
                if (isLast) {
                    setAudio();
                }
            }

            @Override
            public void setFileName(@NotNull String filePath) {

            }
        }, mFilePath + mFileName);
        utils.startRecode();
        setTimer();
    }

    private void setTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finishRecord();
            }
        }, MAX_INTERVAL_TIME);
    }

    private void setAudio() {
        LogUtils.e(TAG, "录音完成的路径:" + mFilePath + mFileName);
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mFilePath + mFileName);
            mediaPlayer.prepare();
            mediaPlayer.getDuration();
            LogUtils.e(TAG, "获取到的时长:" + mediaPlayer.getDuration() / 1000);
        } catch (Exception e) {

        }
        if (mediaPlayer.getDuration() < MIN_INTERVAL_TIME) {
            LogUtils.e(TAG, "录音时间太短:");
            File file = new File(mFilePath + mFileName);
            file.delete();
        } else {
            StringBuilder resultBuffer = new StringBuilder();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }
            LogUtils.e(TAG, resultBuffer.toString());
            if (finishedListener != null) {
                finishedListener.onFinishedRecord(mFilePath + mFileName, mediaPlayer.getDuration() / 1000, resultBuffer.toString());
            }
        }
    }


    private void stopRecording() {
        utils.stopRecoder();
    }

    private class ObtainDecibelThread extends Thread {

        private volatile boolean running = true;

        public void exit() {
            running = false;
        }

        @Override
        public void run() {
            LogUtils.e(TAG, "检测到的分贝001:");
            while (running) {
                if (mRecorder == null || !running) {
                    break;
                }
                // int x = recorder.getMaxAmplitude(); //振幅
                int db = mRecorder.getMaxAmplitude() / 600;
                LogUtils.e(TAG, "检测到的分贝002:" + mRecorder);
                if (db != 0 && y >= 0) {

                    int f = (int) (db / 5);
                    if (f == 0)
                        volumeHandler.sendEmptyMessage(0);
                    else if (f == 1)
                        volumeHandler.sendEmptyMessage(1);
                    else if (f == 2)
                        volumeHandler.sendEmptyMessage(2);
                    else if (f == 3)
                        volumeHandler.sendEmptyMessage(3);
                    else if (f == 4)
                        volumeHandler.sendEmptyMessage(4);
                    else if (f == 5)
                        volumeHandler.sendEmptyMessage(5);
                    else if (f == 6)
                        volumeHandler.sendEmptyMessage(6);
                    else
                        volumeHandler.sendEmptyMessage(7);
                }

                volumeHandler.sendEmptyMessage(-1);
                if (System.currentTimeMillis() - startTime > 20000) {
                    finishRecord();
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private DialogInterface.OnDismissListener onDismiss = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            isLast = true;
            stopRecording();
        }
    };

    public interface OnFinishedRecordListener {
        public void onFinishedRecord(String audioPath, int time, String voiceText);
    }


}
