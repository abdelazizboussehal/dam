package com.example.testfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testfragment.model.Address;
import com.example.testfragment.model.Challenge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class CreeChallenges extends AppCompatActivity {

    MapView mapView;
    TileCache tileCache;
    Marker ancienMarker=null;
    double lat,lon;
    TileRendererLayer tileRendererLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidGraphicFactory.createInstance(this.getApplication());
        setContentView(R.layout.activity_cree_challenges);
        lat=FragementProfile.y;
        lon=FragementProfile.x;
        setTitle("maps challenegs");
        mapView = (MapView) findViewById(R.id.mapId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mapView.setClickable(true);
        mapView.getMapScaleBar().setVisible(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setZoomLevelMin((byte) 10);
        mapView.setZoomLevelMax((byte) 20);

        tileCache = AndroidUtil.createTileCache(this, "mapcache",
                mapView.getModel().displayModel.getTileSize(), 1f,
                mapView.getModel().frameBufferModel.getOverdrawFactor());
        if(FragementProfile.y!=0&&FragementProfile.x!=0) {
            LatLong latLong = new LatLong(FragementProfile.y, FragementProfile.x);
            LatLong arrever = new LatLong(36.24477, 6.57030);
            drawMarker(R.drawable.ic_place_black_24dp, latLong);
            mapView.setCenter(latLong
            );
            mapView.setZoomLevel((byte) 19);
        }

        Button button=(Button) findViewById(R.id.btAddChalng);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creeChallengePost(lat,lon);
                LatLong latLong=new LatLong(lat,lon);
                getAdressFromMapquest(latLong,latLong);
                finish();

            }
        });
    }

    public  void creeChallenge(double lat,double lon){
        String url = "http://192.168.137.1:3000/challenge/0/"+lat+"/"+lon+"/smara";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void drawMarker(int resourceId, LatLong geoPoint){
        if(ancienMarker!=null) {
            mapView.getLayerManager().getLayers().remove(ancienMarker);
        }
        Drawable drawable = getResources().getDrawable(resourceId);
        Bitmap bitmap = AndroidGraphicFactory.convertToBitmap(drawable);
        bitmap.scaleTo(130,130);
        Marker marker = new Marker(geoPoint, bitmap, 0, -bitmap.getHeight() / 2){

        };

        mapView.getLayerManager().getLayers().add(marker);
        ancienMarker=marker;


    }

    @Override
    public void onStart() {
        super.onStart();
        File file = new File(Environment.getExternalStorageDirectory(),"/maps/constantine.map");
        MapDataStore mapDataStore = new MapFile(file);
        tileRendererLayer = new TileRendererLayer(tileCache, mapDataStore,
                mapView.getModel().mapViewPosition,
                AndroidGraphicFactory.INSTANCE){
            @Override
            public boolean onLongPress(LatLong tapLatLong,
                                       Point layerXY,
                                       Point tapXY){
                lat=tapLatLong.latitude;
                lon=tapLatLong.longitude;
                drawMarker(R.drawable.ic_place_black_24dp, tapLatLong);
                return true;
            }

        };
        tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER);
        mapView.getLayerManager().getLayers().add(tileRendererLayer);
        if(lon!=0){
            mapView.setCenter(new LatLong(lat, lon));
        }
        else {
            mapView.setCenter(new LatLong(36.245138, 6.570929));
        }
        mapView.setZoomLevel((byte) 19);


    }

    @Override
    public void onDestroy() {
        mapView.destroyAll();
        AndroidGraphicFactory.clearResourceMemoryCache();
        super.onDestroy();
    }
    public  void creeChallengePost(double lat,double lon){
        String url = "http://192.168.137.1:3000/challenge/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Calendar calobj = Calendar.getInstance();
        System.out.println(df.format(calobj.getTime()));

        Challenge challenge =new Challenge(123,df.format(calobj.getTime()),null,null
                ,new Address(1,lon,lat,"cnep","el khroub ","212121","algieria"));
        JSONParser parser = new JSONParser();
        JSONObject jsonBody=null;
        String a=challenge.tojson();
        a=challenge.tojson();
        try {
            jsonBody = new JSONObject(a);

        }  catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_RESPONSE", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_RESPONSE", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }

    public  void getAdressFromMapquest(LatLong depart,LatLong arrever){
        String url = "https://www.mapquestapi.com/directions/v2/route?key=R4vnaPhLfcyKuGeJbcdUeUSOY3e0GCzk&json={locations:[{latLng:{lat:"+depart.latitude+",lng:"+depart.longitude+"}},{latLng:{lat:"+arrever.latitude+",lng:"+arrever.longitude+"}}]}&outFormat=json&ambiguities=ignore&routeType=fastest&doReverseGeocode=false&enhancedNarrative=false&avoidTimedConditions=false";

        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Bundle d=getAdressFromJson(response);
                        Toast.makeText(getApplicationContext(),d.getString("ville"),Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public Bundle getAdressFromJson(String json){
        try{
            Bundle bundle=new Bundle();
            JSONObject jsonObj = new JSONObject(json);
            JSONArray jsonArray = jsonObj.getJSONObject("route")
                    .getJSONArray("locations");
               bundle.putString("ville",jsonArray.getJSONObject(0).getString("adminArea3"));
            bundle.putString("street",jsonArray.getJSONObject(0).getString("street"));
            bundle.putString("city",jsonArray.getJSONObject(0).getString("adminArea5"));
            bundle.putString("zipeCode",jsonArray.getJSONObject(0).getString("postalCode"));
            return bundle;
        }
        catch (JSONException e) {e.printStackTrace();
            return null;}
    }
}
