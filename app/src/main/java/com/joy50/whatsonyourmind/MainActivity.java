package com.joy50.whatsonyourmind;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView a;
    Button b;
    EditText c;
    String s;
    private class AskWatsonTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... TextToAnalyse) {
            System.out.println(c.getText());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    a.setText("What is happening inside a thread - we are running Watson API");
                }
            });
            AlchemyLanguage service= new AlchemyLanguage();
            service.setApiKey("<Here goes AlchemyAPI key from BlueMix>");
            Map<String,Object>params=new HashMap<String, Object>();
            params.put(AlchemyLanguage.TEXT,c.getText());
            DocumentSentiment sentiment=service.getSentiment(params).execute();
            System.out.println(sentiment);
            return sentiment.getSentiment().getType().name();
        }

        @Override
        protected void onPostExecute(String r) {
            a.setText("The Massage's sentiment is : "+r);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a=(TextView)findViewById(R.id.textView);
        b=(Button)findViewById(R.id.button);
        c=(EditText)findViewById(R.id.editText);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Logging to the console thet the button for the text :"+c.getText());
                a.setText("Displaying at the UI the sentiment to be checked for : "+c.getText());
                AskWatsonTask d= new AskWatsonTask();
                d.execute(new String[]{});
            }
        });
    }
}
