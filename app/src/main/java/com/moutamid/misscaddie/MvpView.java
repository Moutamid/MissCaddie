package com.moutamid.misscaddie;

import androidx.fragment.app.FragmentActivity;

public interface MvpView {


    void showLoading();

    void hideLoading() throws Exception;

    void onSuccessLogout(Object object);

    void onError(Throwable throwable);
}
