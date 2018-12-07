package com.example.josecubillas.bookwishlist;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Alumno on 2/11/2018.
 */

public class SingletonVolley {

    private static SingletonVolley singletonVolley = null;

    //La cola que utilizara nuestra aplicacion
    private RequestQueue ColaPeticiones;

    private SingletonVolley(Context context) {
        ColaPeticiones = Volley.newRequestQueue(context);
    }

    public static SingletonVolley getInstance(Context context) {
        if (singletonVolley == null) {
            singletonVolley = new SingletonVolley(context);
        }
        return singletonVolley;
    }

    public RequestQueue getRequestQueue() {
        return ColaPeticiones;
    }
}
