package stp.cuonghq.upde.commons;

import java.util.List;

public interface ApiListCallback<T> {
    void success(List<T> list);
    void failed(String msg);
}
