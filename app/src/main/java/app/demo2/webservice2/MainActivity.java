package app.demo2.webservice2;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

 ProgressBar pb;
    List<MyTask> tasks;
    List<flower> flowers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb= (ProgressBar) findViewById(R.id.prog);
        tasks = new ArrayList<>();
        pb.setVisibility(View.INVISIBLE);
        for(int i=0;i<=4;i++){
            updateDisplay("line"+i);
            TextView tv = (TextView) findViewById(R.id.textview);
            tv.setMovementMethod(new ScrollingMovementMethod());

        }
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
        if (id == R.id.action_settings ) {
            if (isOnline()) {
              requestdata("http://services.hanselandpetal.com/feeds/flowers.xml");
            } else {
                Toast.makeText(this, "no internet", Toast.LENGTH_LONG).show();
            }

        }

            return false;
    }
          private void requestdata (String uri){
             MyTask task = new MyTask();
                 task.execute(uri);
}


    protected void updateDisplay(String message){
        TextView tv = (TextView) findViewById(R.id.textview);
        tv.append(message + "\n");
    }
    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo!=null&&netInfo.isConnectedOrConnecting())
        { return true;}
        else
        {
            return false;
        }
    }

    private class MyTask extends AsyncTask<String,String,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateDisplay(s);
            tasks.remove(this);
            if (tasks.size()==0){
                pb.setVisibility(View.INVISIBLE);
            }
            pb.setVisibility(View.INVISIBLE);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            updateDisplay("acess main thread");
            pb.setVisibility(View.VISIBLE);
            if (tasks.size()==0){
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }
        @Override
        protected String doInBackground(String... params) {
            String content= HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            updateDisplay(values[0]);


        }
    }



}
