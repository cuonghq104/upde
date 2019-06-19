package stp.cuonghq.upde.data.models;

import java.util.ArrayList;

/**
 * Created by cuong.hq1 on 6/13/2019.
 */

public class DataStatisticDateTime {

    public String type;
    public ArrayList<String> mListKey;
    public ArrayList<String> mListValue;
    public ArrayList<DataStatisticTime> mListDataStatistic;
    public int mPageNumber;

    public DataStatisticDateTime(String _type){
        this.type = _type;
        this.mListKey = new ArrayList<>();
        this.mListValue = new ArrayList<>();
        this.mListDataStatistic = new ArrayList<>();
        this.mPageNumber = -1;
    }

    public int getmPageNumber() {
        return mPageNumber;
    }

    public void setmPageNumber(int mPageNumber) {
        this.mPageNumber = mPageNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getmListKey() {
        return mListKey;
    }

    public void setmListKey(ArrayList mListKey) {
        this.mListKey = mListKey;
    }

    public ArrayList<String> getmListValue() {
        return mListValue;
    }

    public void setmListValue(ArrayList mListValue) {
        this.mListValue = mListValue;
    }

    public ArrayList<DataStatisticTime>  getmListDataStatistic() {
        return mListDataStatistic;
    }

    public void setmListDataStatistic(ArrayList mListDataStatistic) {
        this.mListDataStatistic = mListDataStatistic;
    }

    public void reset(){
        mListKey = new ArrayList<>();
        mListValue = new ArrayList<>();
        mListDataStatistic = new ArrayList<>();
    }
}
