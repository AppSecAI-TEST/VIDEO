package com.example.administrator.testmediaplayer1;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    VideoView videoView;
    int viTri = 0;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView=(VideoView)findViewById(R.id.videoView2);

        //Tao media Controller
        if(mediaController == null){
            mediaController = new MediaController(MainActivity.this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
        }

        try{
            int id = this.getRawResIdByName("sample3");
            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName()+ "/"+id));
         //   int id  = this.getRawResIDByName();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
           Log.e("Error", e.getMessage());
            e.printStackTrace();
        }


        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.seekTo(viTri);
                if(viTri == 0 ){
                    videoView.start();
                }

                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });
    }

    public int getRawResIdByName(String resName){
        String pkgName = this.getPackageName();
        int resID = this.getResources().getIdentifier(resName,"raw",pkgName);
        Log.i("AndroidVideoView","Res Name:" + resName+ "==> Res ID" + resID);
        return resID;
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("CurrentPosition",videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        viTri = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(viTri);
    }

}
