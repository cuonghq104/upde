package stp.cuonghq.upde.commons;

public interface ApiCallback<T> {
    void success(T data, String msg);

    void failed(String msg);
}
