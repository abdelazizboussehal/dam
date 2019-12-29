package com.example.testfragment;

import android.app.DownloadManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

public class FragemntMapsChallenges extends Fragment {
    static Marker ancienMarker=null;
    List<LatLong>  latLongs;
    MapView mapView; TileCache tileCache;
    static Polyline polyline;
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
            desingerChemin(latLong, arrever);
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
                    LatLong arrever = new LatLong(36.24477, 6.57030);
                    desingerChemin(latLong, arrever);
                    drawMarker(R.drawable.ic_place_black_24dp, latLong);
                    mapView.setCenter(latLong
                    );
                    mapView.setZoomLevel((byte) 15);
                }
            }
        });

        return view;
    }

    TileRendererLayer tileRendererLayer;
    @Override
    public void onStart() {
        super.onStart();
        File file = new File(Environment.getExternalStorageDirectory(),"/maps/constantine.map");
       MapDataStore mapDataStore = new MapFile(file);
        tileRendererLayer = new TileRendererLayer(tileCache, mapDataStore,
                mapView.getModel().mapViewPosition,
                AndroidGraphicFactory.INSTANCE);
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
                    LatLong dep=new LatLong(36.26888,6.70143);
                    LatLong arr=new LatLong(36.3598,6.6044);
                    desingerChemin(dep,arr);
                    return true;
                }
                return false;
            }

        };

        mapView.getLayerManager().getLayers().add(marker);
        ancienMarker=marker;
    }

    public  void desingerChemin(LatLong depart,LatLong arrever){
        String url = "https://www.mapquestapi.com/directions/v2/route?key=deamSBfbxULjOkFvP9dW1QiAKewVYxVg&json={locations:[{latLng:{lat:"+depart.latitude+",lng:"+depart.longitude+"}},{latLng:{lat:"+arrever.latitude+",lng:"+arrever.longitude+"}}]}&outFormat=json&ambiguities=ignore&routeType=fastest&doReverseGeocode=false&enhancedNarrative=false&avoidTimedConditions=false";

        RequestQueue queue = Volley.newRequestQueue(getContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        latLongs=getPathFromJson(response);
                        drawPath(latLongs);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

}
