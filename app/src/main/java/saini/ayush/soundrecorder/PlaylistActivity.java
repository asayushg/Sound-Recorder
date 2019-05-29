package saini.ayush.soundrecorder;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {
     String root;
     ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        listView = (ListView) findViewById(R.id.recFilesList);
        addRecordings();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String root = Environment.getExternalStorageDirectory().toString();
                final String path = root + '/' + recFiles.get(position);
                Intent intent = new Intent(PlaylistActivity.this,AudioPlayer.class);
                intent.putExtra("path",path);
                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }
    String name;
    List<String> recFiles = new ArrayList<>();

    public void addRecordings(){
        root = Environment.getExternalStorageDirectory().toString();
        File dir = new File(root + "/rec_audio");
        for (File f : dir.listFiles()) {
            if (f.isFile()){
                name = f.getName();
                recFiles.add(name);
            }

        }

        ArrayAdapter<String> files = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,recFiles);
        listView.setAdapter(files);

    }
}
