package bangkokguy.development.android.leddirect.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.io.DataOutputStream;
import java.io.IOException;

import bangkokguy.android.charger_buddy_ok.L;


public class MainActivity extends ActionBarActivity {

    private static Runtime p;

    private void ledtest() throws Exception {
        try{
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            for (int i=0; i<256; i=i+32) {
                outputStream.writeBytes("echo " + Integer.toString(i) + " > /sys/devices/virtual/sec/led/led_r\n");
                for (int j=0; j<256; j=j+32) {
                    outputStream.writeBytes("echo " + Integer.toString(j) + " > /sys/devices/virtual/sec/led/led_g\n");
                    for (int k=0; k<256; k=k+32) {
                        outputStream.writeBytes("echo " + Integer.toString(k) + " > /sys/devices/virtual/sec/led/led_b\n");
                    }
                }
            }
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
        }catch(IOException e){
            throw new Exception(e);
        }catch(InterruptedException e){
            throw new Exception(e);
        }
        /*for (int i=0; i<256; i++) {
            L.i("p:"+Integer.toString(i));
            try {
                p.exec("echo " + Integer.toString(i) + " > /sys/devices/virtual/sec/led/led_r");
                //"busybox mount rw,remount /system"
            } catch (IOException e) {
                L.i("error p");
                L.e(e.toString());
            }
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        L.setModuleName("LEDDirect");
        p=Runtime.getRuntime();
        /*try {
            p.exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        L.i("onTouch");
        try {
            ledtest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
