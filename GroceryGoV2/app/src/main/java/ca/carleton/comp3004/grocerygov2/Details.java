package ca.carleton.comp3004.grocerygov2;

import android.app.Application;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.google.android.gms.internal.zzagr.runOnUiThread;

/**
 * Created by natsu on 12/26/2017.
 */

public class Details extends Application {
    private ArrayList<Product> data;
    private String rawData;
    private ServerRequset request = null;//new ServerRequset();
    private int version = 0;
    private boolean updating = false;

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String source) {
        rawData = source;
    }

    public ArrayList<Product> getData() {
        return data;
    }

    public void setData(ArrayList<Product> p) {
        data = p;
    }

    public boolean isUpdating() {
        return updating;
    }

    public int getVersion() {
        return version;
    }

    public void updateRawData() {
        if (updating) {
            int counter=0;
            while(updating){
                try{
                    Thread.sleep(500);
                    System.out.println("waiting....... for the "+(counter++)+" times");
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return;
        }
        updating = true;
        if (version == 1) {//here will add version check with the server. so that if the version are the same, there's no need to retrieve again from the server
            updating = false;
            return;
        }
        System.out.println("Update finished Not");
        try {
            request = new ServerRequset();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    rawData = request.initialize("all");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (rawData != null) {
                    try {
                        data = request.readGetAll(rawData);
                        version = version + 1;
                        updating = false;
                        System.out.println("Update finished");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
          /*      runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(rawData != null) {
                            try {
                                data = request.readGetAll(rawData);
                                updating=false;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });*/

            }
        });
        thread.start();
    }
  /*   @Override
   public void onCreate(){
        super.onCreate();
        updateRawData();
    }*/
}

