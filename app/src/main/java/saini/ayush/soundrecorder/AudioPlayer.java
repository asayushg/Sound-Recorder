package saini.ayush.soundrecorder;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ohoussein.playpause.PlayPauseView;

import java.io.FileInputStream;

public class AudioPlayer extends AppCompatActivity {
    String path;
    PlayPauseView view;
    MediaPlayer player=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        view = (PlayPauseView) findViewById(R.id.play_pause_button);
        Toast.makeText(AudioPlayer.this, path, Toast.LENGTH_SHORT).show();
        PlayRecording();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.toggle();
                if(!view.isPlay()){
                    Toast.makeText(AudioPlayer.this, "resumed", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AudioPlayer.this, "paused", Toast.LENGTH_SHORT).show();
                }


            }

        });
        PlayRecording();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }


    private void PauseRecording() {
        if(player!=null){
            player.pause();
        }
    }

    private void PlayRecording() {
        player = new MediaPlayer();
        FileInputStream fis;
        try {
            fis = new FileInputStream(path);
            player.setDataSource(fis.getFD());
            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    view.toggle();
                }
            });
        } catch (Exception e) {
            System.out.println("Exception of type : " + e.toString());
            e.printStackTrace();
        }


    }
}
