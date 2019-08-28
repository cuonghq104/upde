package stp.cuonghq.upde.data.models.dbentities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by cuong.hq1 on 6/17/2019.
 */

@Entity(tableName = "tbl_month_statistic")
public class MonthStatisticEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "numbertrip")
    private String numbertrip;


    @ColumnInfo(name = "page_number")
    private int pageNumber;

    public MonthStatisticEntity(){}


    public MonthStatisticEntity(String time, String price, String numbertrip, int pageNumber) {
        this.time = time;
        this.price = price;
        this.numbertrip = numbertrip;
        this.pageNumber = pageNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumbertrip() {
        return numbertrip;
    }

    public void setNumbertrip(String numbertrip) {
        this.numbertrip = numbertrip;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

}
