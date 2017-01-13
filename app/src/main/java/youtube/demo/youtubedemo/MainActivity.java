package youtube.demo.youtubedemo;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import youtube.demo.youtubedemo.Fragments.ChatFragment;
import youtube.demo.youtubedemo.Fragments.HomeFragment;
import youtube.demo.youtubedemo.Fragments.ImportFragment;
import youtube.demo.youtubedemo.Fragments.LocaisFragment;
import youtube.demo.youtubedemo.Fragments.MensagensFragment;
import youtube.demo.youtubedemo.Fragments.NewPlaceFragment;
import youtube.demo.youtubedemo.Fragments.SobreFragment;
import youtube.demo.youtubedemo.entity.LatLongEntity;
import youtube.demo.youtubedemo.entity.LocalEntity;
import youtube.demo.youtubedemo.entity.UserEntity;
import youtube.demo.youtubedemo.util.ChatActivity;
import youtube.demo.youtubedemo.util.JsonUtil;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapLongClickListener{

    SupportMapFragment sMapFragment;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected double currentLat;
    protected double currentLong;
    private HashMap<String, LocalEntity> markerMap;
    GoogleMap mapa;
    TextView txtLat;
    String lat;
    String provider;
    private boolean movedOnce;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    //json util
    private JsonUtil json;
    boolean pgBar = false;
    private UserEntity user;
    private SharedPreferences settings;
    public static final String PREFS_NAME="LocalePrefs";


    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);

        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvEndereco = ((TextView)myContentsView.findViewById(R.id.snippet));
            tvEndereco.setText(marker.getSnippet());

            //return null;

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub

            return null;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        user = (UserEntity) i.getSerializableExtra("extra");
        sMapFragment = SupportMapFragment.newInstance();
        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        movedOnce = false;
        settings = getSharedPreferences(PREFS_NAME, 1);
        markerMap = new HashMap<String, LocalEntity>();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Criteria cri= new Criteria();
        String bbb = locationManager.getBestProvider(cri, true);
        if(bbb == null){
            Toast.makeText(this, R.string.error_permission_map, Toast.LENGTH_LONG).show();}
        // }else {

        Location myLocation;
        try {
            myLocation = locationManager.getLastKnownLocation(bbb);
            currentLat = myLocation.getLatitude();
            currentLong = myLocation.getLongitude();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(permissionGranted) {
            // {Some Code}
            //Toast.makeText(this, R.string.ok_permission_map, Toast.LENGTH_LONG).show();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        if (!sMapFragment.isAdded()) {
            sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
        }else {
            sFm.beginTransaction().show(sMapFragment).commit();
        }
        final Snackbar snackBar = Snackbar.make(findViewById(R.id.content_frame), "Para marcar um estabelecimento, toque na sua localização no mapa por 2 segundos.", Snackbar.LENGTH_INDEFINITE);
        snackBar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();

        sMapFragment.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //if (getFragmentManager().getBackStackEntryCount() > 0) {
            //    getFragmentManager().popBackStack();
            //}else{
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
                Fragment fragment = fm.findFragmentByTag("LOCAL_FRAG");
                if(fragment != null && fragment.isVisible() && pgBar){
                    pgBar = false;
                    //Toast.makeText(this,"entrei no if",Toast.LENGTH_SHORT).show();
                    if (!sMapFragment.isAdded())
                        sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
                    else
                        sFm.beginTransaction().show(sMapFragment).commit();
                }else {
                    //Toast.makeText(this,"entrei no else",Toast.LENGTH_SHORT).show();
                    sFm.beginTransaction().hide(sMapFragment).commit();
                    Fragment frag = HomeFragment.newInstance(user);
                    fm.beginTransaction().replace(R.id.content_frame, frag).commit();

                    //super.onBackPressed();
                }
            //}
        }
    }

    public boolean isPgBar() {
        return pgBar;
    }

    public void setPgBar(boolean pgBar) {
        this.pgBar = pgBar;
    }

    public void onLocationChanged(Location location) {
        //txtLat = (TextView) findViewById(R.id.textview1);
        //txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        currentLat = location.getLatitude();
        currentLong = location.getLongitude();
        LatLng latlong = new LatLng(currentLat, currentLong);
        try {
            CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(latlong, 17);
            if(!movedOnce){
                mapa.moveCamera(cameraPosition);
                movedOnce = true;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }


        //Toast.makeText(this, "moved!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
         //   return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();

        int id = item.getItemId();

        if (sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();

        if (id == R.id.nav_inicio) {
            sFm.beginTransaction().hide(sMapFragment).commit();
            Fragment frag = HomeFragment.newInstance(user);
            fm.beginTransaction().replace(R.id.content_frame, frag).commit();

        } else if (id == R.id.nav_mapa) {

            if (!sMapFragment.isAdded()) {
                sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
            }else {
                sFm.beginTransaction().show(sMapFragment).commit();
            }
            final Snackbar snackBar = Snackbar.make(findViewById(R.id.content_frame), "Para marcar um estabelecimento, toque na sua localização no mapa por 2 segundos.", Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                }
            });
            snackBar.show();

        } else if (id == R.id.nav_msgs) {
            //user = (UserEntity) i.getParcelableExtra("user");
            if(user != null && user.get_id() != null){
                Fragment fragment = MensagensFragment.newInstance(user);
                fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
                //fm.beginTransaction().replace(R.id.content_frame, new MensagensFragment()).commit();
            }else{
                final Snackbar snackBar = Snackbar.make(findViewById(R.id.content_frame), "User nulo...", Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        } else if (id == R.id.nav_busca) {
            fm.beginTransaction().replace(R.id.content_frame, new ImportFragment()).commit();

        } else if (id == R.id.nav_editperfil) {
            Intent nav = new Intent(getApplicationContext(), ChatActivity.class);
            startActivity(nav);
        } else if (id == R.id.nav_config) {

        }  else if (id == R.id.nav_sobre) {
            item.setChecked(true);
            fm.beginTransaction().replace(R.id.content_frame, new SobreFragment()).commit();
        } else if (id == R.id.nav_encsessao) {
            SharedPreferences.Editor edit = settings.edit();
            //edit.clear();
            edit.putBoolean("signed", false);
            edit.commit();
            Intent nav = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(nav);
        }
        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap map) {

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        };
        map.setInfoWindowAdapter(new MyInfoWindowAdapter());
        map.setOnInfoWindowClickListener(this);
        map.setOnMapLongClickListener(this);
        map.setIndoorEnabled(false);
        map.setBuildingsEnabled(false);
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style_json);
        map.setMapStyle(style);
        map.setOnMarkerClickListener(this);
        //map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
              //          public void onMapLongClick(LatLng latLng) {
             //   map.addMarker(new MarkerOptions().position(latLng));
           // }
        //});
        map.setMapType(1);
        LatLng latlong = new LatLng(currentLat, currentLong);

        map.getUiSettings().setMyLocationButtonEnabled(true);
        //map.addMarker(new MarkerOptions().position(latlong).title("Where am I."));
        json = new JsonUtil();
        String urlPlaces = "https://boraws.herokuapp.com/places";
        JSONArray retornoGet = json.getJSONFromUrlGetArray(urlPlaces);
        //JSONArray retornoGet = null;

        if(retornoGet != null){
            JSONObject jsonObj = null;
            for(int i= 0; i<retornoGet.length(); i++){

                try {
                    jsonObj = retornoGet.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Marker marker = map.addMarker(new MarkerOptions()
                                .title(jsonObj.getString("name"))
                                .snippet(jsonObj.getString("endereco")+"\nFunc: "+jsonObj.getString("funcionamento")+"\nTel: "+jsonObj.getString("telefone"))
                                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                                .position(new LatLng(jsonObj.getDouble("lat"), jsonObj.getDouble("long"))));
                    markerMap.put(jsonObj.getString("name"), new LocalEntity(jsonObj.getString("_id"), jsonObj.getString("name"), jsonObj.getString("email"), jsonObj.getString("funcionamento"), jsonObj.getString("url"), jsonObj.getString("telefone"), jsonObj.getString("endereco"), jsonObj.getDouble("lat"), jsonObj.getDouble("long")));

                    //marker.showInfoWindow();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }



        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(latlong, 17);
        map.moveCamera(cameraPosition);
        map.animateCamera(cameraPosition);
        mapa = map;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        //Toast.makeText(this,marker.getTitle(),Toast.LENGTH_LONG).show();
        final LocalEntity local = new LocalEntity(marker.getTitle());
        //marker.showInfoWindow();


        //AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //dialog.setTitle("your title");
        //dialog.setMessage("youmessage");
        //dialog.setNegativeButton("Cancel", null);
        //dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

          //  @Override
          //  public void onClick(DialogInterface dialog, int id) {
          //      FragmentManager fm = getFragmentManager();
          //      android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
          //      sFm.beginTransaction().hide(sMapFragment).commit();
          //      Fragment fragment = LocaisFragment.newInstance(local);
          //      fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
         //   }
        //});
       // dialog.show();



        Snackbar snack = Snackbar.make(findViewById(R.id.drawer_layout), marker.getTitle(), Snackbar.LENGTH_INDEFINITE)
                .setAction("HIDE", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(R.id.drawer_layout), "xxx", Snackbar.LENGTH_SHORT).dismiss();
            }
        }).setAction("SHOW", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(findViewById(R.id.drawer_layout), "abc", Snackbar.LENGTH_SHORT).show();
                    }
                });
        //snack.show();


        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        LocalEntity local = markerMap.get(marker.getTitle());
        pgBar = true;
        FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        sFm.beginTransaction().hide(sMapFragment).commit();
        Fragment fragment = LocaisFragment.newInstance(local, user);
        fm.beginTransaction().replace(R.id.content_frame, fragment, "LOCAL_FRAG").commit();
    }


    @Override
    public void onMapLongClick (LatLng point){
    //Snackbar.make(findViewById(R.id.content_frame), "novo estab", Snackbar.LENGTH_SHORT).show();
        //System.out.println("teste3292942390423");
        if((currentLat - point.latitude <= 0.00050 && currentLong - point.longitude <= 0.00050) && (point.latitude - currentLat <= 0.00050 && point.longitude - currentLong <= 0.00050)){

            Marker marker = mapa.addMarker(new MarkerOptions()
                    .title("Novo Local")
                    .snippet("Sob aprovação! Aguarde!")
                    .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                    .position(point));
            LatLongEntity latLong = new LatLongEntity(point.latitude, point.longitude);
            FragmentManager fm = getFragmentManager();
            android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
            sFm.beginTransaction().hide(sMapFragment).commit();
            Fragment fragment = NewPlaceFragment.newInstance(user, latLong);
            fm.beginTransaction().replace(R.id.content_frame, fragment, "NEWPLACE_FRAG").commit();

        }else {
            final Snackbar snackBar = Snackbar.make(findViewById(R.id.content_frame), "Você está muito longe do local, aproxime-se do estabelecimento para poder cadastrá-lo!", Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                }
            });
            snackBar.show();

        }


    }


}
