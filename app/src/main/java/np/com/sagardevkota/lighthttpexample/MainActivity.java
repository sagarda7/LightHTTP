package np.com.sagardevkota.lighthttpexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;

import np.com.sagardevkota.lighthttp.LightHTTP;
import np.com.sagardevkota.lighthttp.listeners.HttpListener;
import np.com.sagardevkota.lighthttp.utils.CacheManager;
import np.com.sagardevkota.lighthttp.datatypes.*;
import np.com.sagardevkota.lighthttpexample.common.Const;


public class MainActivity extends AppCompatActivity {
    final String TAG=getClass().getSimpleName();
    Type<Bitmap> bitmap;
    CacheManager<JSONArray> jsonCacheManager;
    CacheManager<Bitmap> bitmapCacheManager;
    ImageView imgData;
    ProgressBar imgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        imgData= (ImageView) findViewById(R.id.imgView);
        imgLoading =(ProgressBar) findViewById(R.id.imgLoading);
        imgLoading.setVisibility(View.GONE);


        jsonCacheManager=new CacheManager<JSONArray>(40*1024*1024);
        bitmapCacheManager= new CacheManager<>(40*1024*1024);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void btnCancelRequestClicked(View v){
        if(bitmap!=null)
            bitmap.cancel();
    }

    public void btnImageRequestClicked(View v){

        bitmap=LightHTTP
                .from(MainActivity.this)
                .load(LightHTTP.Method.GET, "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128&s=622a88097cf6661f84cd8942d851d9a2")
                .asBitmap()
                .setCacheManager(bitmapCacheManager)
                .setCallback(new HttpListener<Bitmap>() {
                    @Override
                    public void onRequest() {
                        imgLoading.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(Bitmap data) {
                        if(data!=null){
                            imgData.setImageBitmap(data);
                            imgLoading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        imgLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancel() {
                        imgLoading.setVisibility(View.GONE);
                    }
                });
    }

    public void btnClearImage(View v) {
        imgData.setImageResource(R.drawable.placeholder);
    }

    public void btnClearCache(View v) {
        bitmapCacheManager.removeDataFromCache(Const.SINGLE_IMAGE_URL);
    }

    public void btnLoadApiClicked(View v) {
        Intent intent= new Intent(this, UserListActivity.class);
        startActivity(intent);
    }


}
