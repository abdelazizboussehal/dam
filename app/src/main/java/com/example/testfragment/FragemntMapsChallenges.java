package com.example.testfragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragemntMapsChallenges extends Fragment {

    MapView mapView; TileCache tileCache;

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
}
