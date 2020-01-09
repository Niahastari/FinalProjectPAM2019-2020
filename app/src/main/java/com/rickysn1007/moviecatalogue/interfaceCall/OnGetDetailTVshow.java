package com.rickysn1007.moviecatalogue.interfaceCall;

import com.rickysn1007.moviecatalogue.model.TVshow;

public interface OnGetDetailTVshow {
    void onSuccess(TVshow tv);

    void onError();
}
