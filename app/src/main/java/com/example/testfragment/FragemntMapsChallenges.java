package com.example.testfragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testfragment.model.Challenge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.overlay.Polyline;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FragemntMapsChallenges extends Fragment {
    static Marker ancienMarker=null;
    List<LatLong>  latLongs;
    MapView mapView; TileCache tileCache;
    static Polyline polyline;
    TextView textViewDistance;
    TileRendererLayer tileRendererLayer;
    LatLong myPosition;
    static  List<Challenge> challengeSet;
    public FragemntMapsChallenges(){
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("maps challenegs");
        AndroidGraphicFactory.createInstance(getActivity().getApplication());
        View view=inflater.inflate(R.layout.fragement_maps_challenges, container, false);
        textViewDistance=(TextView)view.findViewById(R.id.distance);
        textViewDistance.setVisibility(View.INVISIBLE);
        mapView = (MapView) view.findViewById(R.id.mapId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mapView.setClickable(true);
        mapView.getMapScaleBar().setVisible(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setZoomLevelMin((byte) 10);
        mapView.setZoomLevelMax((byte) 20);

        tileCache = AndroidUtil.createTileCache(getActivity(), "mapcache",
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
        Button button=(Button) view.findViewById(R.id.btMaposision);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FragementProfile.y!=0&&FragementProfile.x!=0) {
                    LatLong latLong = new LatLong(FragementProfile.y, FragementProfile.x);
                    myPosition=latLong;
                    drawMarker(R.drawable.my_position_24dp, latLong);
                    mapView.setCenter(latLong
                    );
                    mapView.setZoomLevel((byte) 15);
                }
            }
        });


        recupererAllChallenge();
        return view;
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
                myPosition=tapLatLong;
                drawMarker(R.drawable.my_position_24dp, tapLatLong);
                return true;
            }

        };
        tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER);
        mapView.getLayerManager().getLayers().add(tileRendererLayer);
        mapView.setCenter(new LatLong(36.245138, 6.570929));
        mapView.setZoomLevel((byte) 19);
    }

 @Override
    public void onDestroy() {
     mapView.destroyAll();
     AndroidGraphicFactory.clearResourceMemoryCache();
     super.onDestroy();
 }

    public void drawMarker(int resourceId, LatLong geoPoint){
        if(ancienMarker!=null) {
            mapView.getLayerManager().getLayers().remove(ancienMarker);
        }
        Drawable drawable = getResources().getDrawable(resourceId);
        Bitmap bitmap = AndroidGraphicFactory.convertToBitmap(drawable);
        bitmap.scaleTo(130,130);
        Marker marker = new Marker(geoPoint, bitmap, 0, -bitmap.getHeight() / 2){
            @Override
            public boolean onTap(LatLong geoPoint, Point viewPos, Point tapPoint){
                if (contains(viewPos, tapPoint)) {

                    return true;
                }
                return false;
            }

        };

        mapView.getLayerManager().getLayers().add(marker);
        ancienMarker=marker;
    }

    public  void desingerChemin(LatLong depart,LatLong arrever){
        String url = "https://www.mapquestapi.com/directions/v2/route?key=R4vnaPhLfcyKuGeJbcdUeUSOY3e0GCzk&json={locations:[{latLng:{lat:"+depart.latitude+",lng:"+depart.longitude+"}},{latLng:{lat:"+arrever.latitude+",lng:"+arrever.longitude+"}}]}&outFormat=json&ambiguities=ignore&routeType=fastest&doReverseGeocode=false&enhancedNarrative=false&avoidTimedConditions=false";

        RequestQueue queue = Volley.newRequestQueue(getContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        double d=getDestenceFromJson(response);
                        latLongs=getPathFromJson(response);
                        drawPath(latLongs);
                        textViewDistance.setText("distance "+d+" km");
                        textViewDistance.setVisibility(View.VISIBLE);
                        queue.getCache().clear();
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                queue.getCache().clear();
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.

        queue.add(stringRequest);
    }



    public List<LatLong> getPathFromJson(String json){
        try{
            List<LatLong> path = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(json);
            JSONArray maneuversObj = jsonObj.getJSONObject("route")
                    .getJSONArray("legs").getJSONObject(0)
                    .getJSONArray("maneuvers");
            for(int i=0; i < maneuversObj.length(); i++){
                JSONObject obj = maneuversObj.getJSONObject(i)
                        .getJSONObject("startPoint");
                LatLong point=
                        new LatLong(obj.getDouble("lat"),obj.getDouble("lng"));
                path.add(point);
            }
            return path;
        } catch (JSONException e) {e.printStackTrace(); return null;}
    }

    public double getDestenceFromJson(String json){
        try{
            List<LatLong> path = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(json);
            double maneuversObj = jsonObj.getJSONObject("route")
                    .getDouble("distance");

            return maneuversObj;
        }
        catch (JSONException e) {e.printStackTrace();
        return -1;}
    }



    public void drawPath(List<LatLong> path){
        if(polyline!=null){
            mapView.getLayerManager().getLayers().remove(polyline);
        }
        org.mapsforge.core.graphics.Paint paint
                =  AndroidGraphicFactory.INSTANCE.createPaint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(15);
        paint.setStyle(Style.STROKE);
        polyline = new Polyline((org.mapsforge.core.graphics.Paint) paint,AndroidGraphicFactory.INSTANCE);
        List<LatLong> coordinateList = polyline.getLatLongs();
        for(LatLong geoPoint : path)
            coordinateList.add(geoPoint);
        mapView.getLayerManager().getLayers().add(polyline);
    }


    public Set<Challenge> recupererAllChallenge(){
        String url = "http://192.168.137.1:3000/challenge/";

        RequestQueue queue = Volley.newRequestQueue(getContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<LatLong> path = new ArrayList<>();
                        JSONObject jsonObj = null;
                        challengeSet = new ArrayList<>();
                        challengeSet = Challenge.getChallengesFromJson(response);
                        drawAllchallehnge(challengeSet);
                        queue.getCache().clear();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                queue.getCache().clear();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return  null;
    }

    public void drawMarkersanssupprimer(int resourceId, LatLong geoPoint){

        Drawable drawable = getResources().getDrawable(resourceId);
        Bitmap bitmap = AndroidGraphicFactory.convertToBitmap(drawable);
        bitmap.scaleTo(130,130);
        Marker marker = new Marker(geoPoint, bitmap, 0, -bitmap.getHeight() / 2){
            @Override
            public boolean onTap(LatLong geoPoint, Point viewPos, Point tapPoint){
                if (contains(viewPos, tapPoint)) {

                    if(myPosition!=null)
                    desingerChemin(myPosition,geoPoint);
                    else
                        Toast.makeText(getContext(),"choisir position",Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }

        };

        mapView.getLayerManager().getLayers().add(marker);
    }


    public void drawAllchallehnge(List<Challenge> challenges)throws NullPointerException{
        if(!challenges.isEmpty()) {
            for (int i = 0; i < challenges.size(); i++) {
                LatLong latLong = new LatLong(challenges.get(i).getAddress().getLatitude(),challenges.get(i).getAddress().getLongitude());

                drawMarkersanssupprimer(R.drawable.ic_place_black_24dp,latLong);
            }
        }

    }

}
