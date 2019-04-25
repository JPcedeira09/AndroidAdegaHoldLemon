package br.com.hold.adega.adega.Interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.stripe.android.EphemeralKeyUpdateListener;



public interface EphemeralKeyProvider {


    void createEphemeralKey(
            @NonNull @Size(min = 4) String apiVersion,
            @NonNull final EphemeralKeyUpdateListener keyUpdateListener);



}
