package ar.edu.unlam.soa.android.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class ConnectionService extends Service implements Response.ErrorListener, Response.Listener<String>{

    private static final String  TAG = "ConnectionService";
    public static final String ACTION_CONNECTION_ERROR = "ar.edu.unlam.soa.android.activity.CONNECTION_SUCCESS";
    public static final String ACTION_CONNECTION_SUCCESS = "ar.edu.unlam.soa.android.activity.CONNECTION_FAIL";

    private String response;
    private StringRequest request;

    public ConnectionService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "DownloadService created  "+ startId);
        /*Se autostopea*/
        /*stopSelf();*/

        /*No levante cuando pueda*/
        Volley.newRequestQueue(this).add(request);
        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "Paso por el onBind");
        return null;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Log.v(TAG, "Connection fail");
        Intent errorEvent = new Intent(ACTION_CONNECTION_ERROR);
        //Esta clase se usa solo para eventos internos de la application
        LocalBroadcastManager.getInstance(this).sendBroadcast(errorEvent);
        stopSelf();
    }


    @Override
    public void onResponse(String response) {
        Log.v(TAG, "Connection completed");
        Intent successEvent = new Intent(ACTION_CONNECTION_SUCCESS);
        LocalBroadcastManager.getInstance(this).sendBroadcast(successEvent);



        stopSelf();

    }
}
