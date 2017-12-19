package lbstest.example.com.pptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AudioBarGraph audioBarGraph = findViewById(R.id.id_abg);
        final  float[] m = new float[20];
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                    for (int i=0;i<m.length;i++){
                        m[i] =(float)(Math.random()*100);
                    }
                    audioBarGraph.setmCurrentHeight(m); //设置高度
                    try{
                        Thread.sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
