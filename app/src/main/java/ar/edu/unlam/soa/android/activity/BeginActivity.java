package ar.edu.unlam.soa.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import ar.edu.unlam.soa.android.R;
import ar.edu.unlam.soa.android.utils.ConstantsEnum;
import ar.edu.unlam.soa.android.utils.Ipvalidator;

import static android.view.View.GONE;

public class BeginActivity extends AppCompatActivity {


    private static  final String IP= "ip";
    private static  final String PUERTO= "puerto";
    private String ip;
    private String puerto; 
    private EditText ipPlaca;
    private Button siguienteButton;
    private EditText puertoPlaca;
    private Button defaultData;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        errorMessage = (TextView) findViewById(R.id.errorMessage);
        errorMessage.setVisibility(GONE);
        ipPlaca = (EditText)findViewById(R.id.ipPlaca);
        puertoPlaca = (EditText) findViewById(R.id.puertoPlaca);
        inicializarBotones();
    }

    private void inicializarBotones() {
        siguienteButton = (Button) findViewById(R.id.begin_button);
        defaultData = (Button) findViewById(R.id.default_data);
        siguienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMessage.setVisibility(GONE);
                try {
                    Thread.sleep(1000);
                    if(BeginActivity.this.getDataFromView()){
                        BeginActivity.this.pasarALaSiguientePantalla();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        defaultData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeginActivity.this.setDefaultData();
            }
        });
    }


    private void pasarALaSiguientePantalla() {
        Toast.makeText(this,"Deberia pasar a la siguiente pantalla", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString(IP, ip);
        bundle.putString(PUERTO, puerto);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        //Elimino la actividad de memoria. No se puede volver a este activity.
        this.finish();
    }

    private boolean getDataFromView() {
        if(validate()){
            this.puerto = puertoPlaca.getText().toString();
            this.ip = ipPlaca.getText().toString();
            return true;
        }else{
            showErrorMessage();
            return  false;
        }
    }

    private void showErrorMessage() {
        this.errorMessage.setVisibility(View.VISIBLE);

    }

    private boolean validate() {
        try{
            Long puerto = Long.parseLong(puertoPlaca.getText().toString());
            return Ipvalidator.validate(ipPlaca.getText().toString()) && puerto <= 65000 && puerto > 0;
        }catch(NumberFormatException ex){
            Log.e("Begin activity: ",ex.getMessage());
            return false;
        }
    }


    private void setDefaultData(){
        this.puertoPlaca.setText(ConstantsEnum.DEFAULT_PUERTO);
        this.ipPlaca.setText(ConstantsEnum.DEFAULT_IP);
    }
}
