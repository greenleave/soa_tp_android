package ar.edu.unlam.soa.android.services;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * Created by sbogado on 14/11/16.
 */


public class StringRequest extends Request<String> {

    private Response.Listener<String> listener;

    public StringRequest(int method, String url, Response.Listener reponseListener,Response.ErrorListener listener) {
        super(method, url,listener);
        this.listener=reponseListener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String textResponse= new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return  Response.success(textResponse , HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return  Response.error(new VolleyError(e));
        }
    }

    //Una vez que parseamos la respuesta, el response de arriba lo manda aca.
    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

}
