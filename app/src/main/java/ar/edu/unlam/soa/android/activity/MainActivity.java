package ar.edu.unlam.soa.android.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ar.edu.unlam.soa.android.R;
import ar.edu.unlam.soa.android.services.ConnectionService;

public class MainActivity extends AppCompatActivity {


    private IntentFilter intentFilter;
    private boolean estadoPlaca=false;
    private ConnectionReceiver connectionReceiver;
    private String ip;
    private String puerto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        ip = bundle.getString("IP");
        puerto = bundle.getString("PUERTO");
        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectionService.ACTION_CONNECTION_SUCCESS);
        intentFilter.addAction(ConnectionService.ACTION_CONNECTION_ERROR);
    }

    /**
     *  El fragment no tienen un contexto. getActivity o getContext
     *
     * */
    @Override
    public void onStart() {
        super.onStart();
        this.registerReceiver(connectionReceiver, intentFilter);
        LocalBroadcastManager.getInstance(this);
    }

    /**
     * GetContext y getActivity son lo mismo
     */
    @Override
    public void onStop() {
        super.onStop();
        this.unregisterReceiver(connectionReceiver);
        LocalBroadcastManager.getInstance(this);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }




    private void sendRequest(){
        Intent downloadIntent = new Intent(this, ConnectionService.class);
        Bundle bundle = new Bundle();
        bundle.putString("IP", ip);
        bundle.putString("PUERTO", puerto);
        downloadIntent.putExtras(bundle);
        this.startService(downloadIntent);
    }



    private void cambiarEstadoPlaca(){
        this.estadoPlaca = ! this.estadoPlaca;
    }

    //Son un componente principal. Se necesita declararla en el manifest.
    //Pero al ser una innerclass nosotros nos encargamos de inicializar
    // por lo cual no es necesariol decalrarlo en el manifest
    private class ConnectionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ConnectionService.ACTION_CONNECTION_ERROR)){
                Toast.makeText(context, "Error al sincronizar", Toast.LENGTH_SHORT).show();
            }
            cambiarEstadoPlaca();
        }
    }
}
