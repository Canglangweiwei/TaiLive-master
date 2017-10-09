package jc.geecity.taihua.base;

@SuppressWarnings("ALL")
public interface BaseView {
    void onFailureCallback(Throwable throwable);

    void onFailureCallback(int errorCode, String errorMsg);
}
