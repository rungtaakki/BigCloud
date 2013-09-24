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

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.MediaController;
import android.widget.SearchView;
import android.widget.SeekBar;


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
    CountDownTimer timer;

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
        setupActionBar();
        hideActionBar();
    }

    @TargetApi(11)
    private void hideActionBar() {
		getActionBar().hide();
	}
	
	@TargetApi(11)
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		setSearchView(menu);

		return true;
	}

	@TargetApi(11)
	private void setSearchView(Menu menu) { 
		// Associate searchable configuration with the SearchView
	    SearchManager searchManager =
		           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		    SearchView searchView =
		            (SearchView) menu.findItem(R.id.search).getActionView();
		    searchView.setSearchableInfo(
		            searchManager.getSearchableInfo(getComponentName()));
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
			break;
		case R.id.favourites:			
			startActivity(new Intent(this, FavouritesActivity.class));
			break;
		case R.id.search:
			break;		default:
			startActivity(item.getIntent());
		}
		return true;
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
        int topContainerId1 = getResources().getIdentifier("mediacontroller_progress", "id", "android");
        SeekBar seekbar = (SeekBar) mediaController.findViewById(topContainerId1);
        seekbar.setSecondaryProgress(percent);
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
      handleActionBar();
      return false;
    }
    
    @TargetApi(11)
    private void handleActionBar() { 
    	getActionBar().show();
    
    timer = new CountDownTimer(3000,1000)
		{

			@Override
			public void onFinish() {
				getActionBar().hide();
			}			

			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}.start();
		
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
        if(timer != null)
        {
        	timer.cancel();
        timer = null;
        }
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
