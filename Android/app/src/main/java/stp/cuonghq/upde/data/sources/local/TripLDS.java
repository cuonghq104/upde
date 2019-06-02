package stp.cuonghq.upde.data.sources.local;

import java.util.List;

import stp.cuonghq.upde.data.models.dbentities.TripEntity;
import stp.cuonghq.upde.data.sources.TripDatasource;

public class TripLDS implements TripDatasource.LDS {

    public static TripLDS sInstance;

    public static TripLDS getInstance() {
        if (sInstance == null) {
            sInstance = new TripLDS();
        }
        return sInstance;
    }

    @Override
    public void insertNewTrip(TripEntity... entities) {
        AppDatabase database = AppDatabase.getInstance();
        database.tripDAO().insert(entities);
    }

    @Override
    public void removeTrip(TripEntity entity) {
        AppDatabase database = AppDatabase.getInstance();
        database.tripDAO().delete(entity);
    }

    @Override
    public boolean isRead(TripEntity entity) {
        AppDatabase database = AppDatabase.getInstance();
        return (database.tripDAO().getAll(entity.getIdTrip()).size() == 0);
    }

    @Override
    public TripEntity getTrip(String id) {
        AppDatabase database = AppDatabase.getInstance();
        List<TripEntity> entities = database.tripDAO().getAll(id);
        return (entities.size() == 0) ? null : entities.get(0);
    }
}
