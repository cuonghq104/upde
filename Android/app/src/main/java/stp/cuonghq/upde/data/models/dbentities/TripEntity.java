package stp.cuonghq.upde.data.models.dbentities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tbl_trip")
public class TripEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "id_trip")
    private String idTrip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(String idTrip) {
        this.idTrip = idTrip;
    }
}
