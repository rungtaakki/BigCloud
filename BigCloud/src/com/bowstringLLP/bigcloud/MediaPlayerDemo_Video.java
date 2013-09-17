/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bowstringLLP.bigcloud;

import android.app.Activity;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.MediaController;


public class MediaPlayerDemo_Video extends Activity implements
        OnBufferingUpdateListener, OnCompletionListener,
        OnPreparedListener, OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl {

    private static final String TAG = "MediaPlayerDemo";
    private MediaPlayer mMediaPlayer;
    private SurfaceView mPreview;
    private SurfaceHolder holder;
    private Bundle extras;
    private boolean mIsVideoSizeKnown = false;
    private boolean mIsVideoReadyToBePlayed = false;
    MediaController mediaController;
    private Handler handler = new Handler();

    /**
     * 
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.mediaplayer_2);
        mPreview = (SurfaceView) findViewById(R.id.surface);
        holder = mPreview.getHolder();
        holder.addCallback(this);
        extras = getIntent().getExtras();

    }

    private void playVideo(String path) {
        doCleanUp();
        try {
        	
        	// Create a new media player and set the listeners
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setDisplay(holder);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaController = new MediaController(this);
        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
    }

    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
        Log.d(TAG, "onBufferingUpdate percent:" + percent);

    }

    public void onCompletion(MediaPlayer arg0) {
        Log.d(TAG, "onCompletion called");
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.v(TAG, "onVideoSizeChanged called");
        if (width == 0 || height == 0) {
            Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
            return;
        }
        mIsVideoSizeKnown = true;
        
        if(mp != null)
        {       
        	Point outSize = new Point();
        	getWindowManager().getDefaultDisplay().getSize(outSize);
            Integer screenWidth = outSize.x;
            Integer screenHeight = outSize.y;
            
            LayoutParams videoParams = mPreview.getLayoutParams();


            if (width > height)
            {
                videoParams.width = screenWidth;
                videoParams.height = screenWidth * height / width;
            }
            else
            {
                videoParams.width = screenHeight * width / height;
                videoParams.height = screenHeight;
            }


            mPreview.setLayoutParams(videoParams);
        }
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            //startVideoPlayback();
        	mp.start();
        }
    }

    public void onPrepared(MediaPlayer mediaplayer) {
        Log.d(TAG, "onPrepared called");
        mIsVideoReadyToBePlayed = true;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
           // startVideoPlayback();
        	mediaplayer.start();
        	
        	mediaController.setMediaPlayer(this);
            mediaController.setAnchorView(mPreview);
            
            handler.post(new Runnable() {
              public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
              }
            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      //the MediaController will hide after 3 seconds - tap the screen to make it appear again
      mediaController.show();
      return false;
    }
    
    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.d(TAG, "surfaceChanged called");

    }

    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.d(TAG, "surfaceDestroyed called");
    }


    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated called");
        playVideo(extras.getString("PATH"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
        doCleanUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
        doCleanUp();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();            

            mediaController.hide();
            mMediaPlayer = null;
        }
    }

    private void doCleanUp() {
        mIsVideoReadyToBePlayed = false;
        mIsVideoSizeKnown = false;
    }

    /*private void startVideoPlayback() {
        Log.v(TAG, "startVideoPlayback");
        
        mMediaPlayer.start();
    }
*/
    public void start() {
    	mMediaPlayer.start();
      }

      public void pause() {
    	  mMediaPlayer.pause();
      }

      public int getDuration() {
        return mMediaPlayer.getDuration();
      }

      public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
      }

      public void seekTo(int i) {
    	  mMediaPlayer.seekTo(i);
      }

      public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
      }

      public int getBufferPercentage() {
        return 0;
      }

      public boolean canPause() {
        return true;
      }

      public boolean canSeekBackward() {
        return true;
      }

      public boolean canSeekForward() {
        return true;
      }

}
