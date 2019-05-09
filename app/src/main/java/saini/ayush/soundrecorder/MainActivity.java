package saini.ayush.soundrecorder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.nfc.Tag;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ohoussein.playpause.PlayPauseView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    MediaRecorder mediaRecorder=null;
    String filename;
    int count=0;
    PlayPauseView view, stopView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        view = (PlayPauseView) findViewById(R.id.play_pause_view);
        stopView = (PlayPauseView) findViewById(R.id.stopView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.toggle();
                if(!view.isPlay()){
                    if(count==0){
                        count++;
                        startRecording();
                        Toast.makeText(MainActivity.this,"Recording Started",Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(MainActivity.this,"Recording Resumed",Toast.LENGTH_LONG).show();
                        resumeRec();
                    }

                }
                if(view.isPlay()){
                    Toast.makeText(MainActivity.this,"Recording Paused",Toast.LENGTH_LONG).show();
                    pauseRec();
                }
            }
        });
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.RECORD_AUDIO)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, 0);
            }
        } else {
            // Permission has already been granted
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 20);
            }
        } else {
            // Permission has already been granted
        }
    }

    public void startRecording( ){
        if(mediaRecorder==null) {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
            filename = getFilename();
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/rec_audio");
            myDir.mkdirs();

            File file = new File(myDir, "/" + filename + ".3gp");
            if (Build.VERSION.SDK_INT < 26) {
                mediaRecorder.setOutputFile(file.getAbsolutePath());
            } else {
                mediaRecorder.setOutputFile(file);
            }
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public void stopRecording(View v){
        if(mediaRecorder!=null) {
            Toast.makeText(MainActivity.this,filename+".3gp Saved!",Toast.LENGTH_LONG).show();
            view.toggle();
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            count = 0;
        }
    }

    public void pauseRec(){
        if(mediaRecorder!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaRecorder.pause();
            }
        }

        }

    public void resumeRec(){
        if(mediaRecorder!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaRecorder.resume();
            }
        }
    }

    public String getFilename() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy, HH:mm");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public void showPlaylist(View v){
        Intent intent = new Intent(MainActivity.this,PlaylistActivity.class);
        startActivity(intent);
    }
}
