package stp.cuonghq.upde.data.sources.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.data.models.dbentities.TripEntity;

@Database(entities = {TripEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public abstract TripDAO tripDAO();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null && context != null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DatabaseConstant.DB_NAME)
                    .allowMainThreadQueries().build();
        }
        return sInstance;
    }

    public static AppDatabase getInstance() {
        return getInstance(null);
    }

    public static void destroyInstance() {
        sInstance = null;
    }
}
