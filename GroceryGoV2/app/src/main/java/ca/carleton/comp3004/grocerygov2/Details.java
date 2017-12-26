package ca.carleton.comp3004.grocerygov2;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by natsu on 12/26/2017.
 */

public class Details extends Application {
    private ArrayList<Product> data;
    private String rawData;

    public String getRawData(){
        return rawData;
    }
    public void setRawData(String source){
        rawData=source;
    }
    public void updataRawData(){

    }
}
