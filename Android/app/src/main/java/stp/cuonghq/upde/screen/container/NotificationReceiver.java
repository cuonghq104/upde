package stp.cuonghq.upde.screen.container;

import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.services.fcm.UpdeFCM;

public interface NotificationReceiver {
    void receiveBooking(BookingResp booking);
}
