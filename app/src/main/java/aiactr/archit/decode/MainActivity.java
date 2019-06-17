package aiactr.archit.decode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String ElectionDay = "2019-05-12";      //yyyy-mm-dd
    int flagBack = 1;
    LatLng cc = new LatLng(28.6505,77.2303);
    EditText epicNoEditText;
    EditText mobileNoEdiText;
    private ProgressDialog pDialog;

    ExpandableListView polling_listView;
    ExpandableListAdapter polling_ListAdapter;
    List<String> listPollingStationNames;
    HashMap<String, String> listPSDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        HomeFragment fragment = new HomeFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        //checking if elections complete
        checkDay();
//        textEpic = findViewById(R.id.etepicno);
//        pollingStationName = findViewById(R.id.name);
        epicNoEditText= findViewById(R.id.idEpicNo);
        mobileNoEdiText = findViewById(R.id.idMobileNo);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(true);

        polling_listView = findViewById(R.id.expandable_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    @Override
    public void onBackPressed() {
        if (flagBack==0)
        {
            flagBack=1;
            goHome();
        }
        else {
            flagBack=0;
            super.onBackPressed();
        }
    }

    public void checkDay()
    {
//        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try{
            Date theDay = format.parse(ElectionDay);

//            if (today.after(theDay)> 0) {
            if (new Date().after(theDay)){
                Log.i("Election status","Not over");
                //Election not over enabling the home fragment
                HomeFragment fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }else
            {
                Log.i("Election status","over");
                AfterFragment afterFragment = new AfterFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, afterFragment).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            ParliamentConstituencyFragment fragment = new ParliamentConstituencyFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    ////////////////////////////////////////////////////////
    // For Fragment Home   /////////////////////////////////
    ////////////////////////////////////////////////////////
    public void goHome()
    {
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
    }
    public void openCC(View view)
    {
        flagBack=0;
        ParliamentConstituencyFragment parliamentConstituencyFragment = new ParliamentConstituencyFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, parliamentConstituencyFragment).commit();
    }
    public void openPS(View view)
    {
        flagBack=0;
        PollingStationFragment pollingStationFragment = new PollingStationFragment();
        getSetPollingStations();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,pollingStationFragment).commit();
    }
    public void openInfo(View view)
    {
        flagBack=0;
        InfoFragment infoFragment = new InfoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, infoFragment).commit();
    }
    public void openPWD(View view)
    {
        flagBack=0;
        FacilitiesFragment facilitiesFragment = new FacilitiesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, facilitiesFragment).commit();
    }
    public void openThirdG(View view)
    {
        flagBack=0;
        FacilitiesFragment facilitiesFragment = new FacilitiesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, facilitiesFragment).commit();
    }
    public void openKYC(View view)
    {
        flagBack=0;
        CandidateFragment candidateFragment = new CandidateFragment();
        getSetCandidates();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, candidateFragment).commit();
    }
    public void openFeedbackPage(View view)
    {
        flagBack=0;
        FeedbackFragment feedbackFragment = new FeedbackFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, feedbackFragment).commit();
    }

    ////////////////////////////////////////////////////////
    // For Fragment Parliament Constituency ////////////////
    ////////////////////////////////////////////////////////
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map=googleMap;
//        map.getUiSettings().setAllGesturesEnabled(true);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(cc);
//        map.addMarker(markerOptions);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.6632701,77.4291378),12));  //map position
//
////        UiSettings uiSettings = map.getUiSettings();
////        uiSettings.setAllGesturesEnabled(true);
//    }

    ////////////////////////////////////////////////////////
    // For Fragment Polling Station ////////////////////////
    ////////////////////////////////////////////////////////

    public void goButton(View view)
    {
        //TODO search the names of polling station
    }

    public void getSetPollingStations()
    {
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_GETLISTOFPS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Download Candidate Data ", "Download Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("success");

                    // Check for error node in json
                    if (!error) {
                        //TODO change the list view using the details received
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Download Failed", "Download Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {};

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    public void getSelection(View view)
    {
        String id = "";
        //get the id of the selected option
        getPollingStationDetails(id);
//        Boolean facility1;
//        Boolean facility2;
//        Boolean facility3;
//        Boolean facility4;
//        Boolean facility5;
//        Boolean facility6;
//        Boolean facility7;
//        Boolean facility8;
//        Boolean facility9;
//        Boolean facility10;
//        Boolean facility11;
//        Boolean facility12;
//        setAnswers(facility1, facility2, facility3, facility4, facility5, facility6, facility7, facility8, facility9, facility10, facility11, facility12);
    }

    public void setAnswers(Boolean facility1,Boolean facility2,Boolean facility3,Boolean facility4,Boolean facility5,Boolean facility6,Boolean facility7,Boolean facility8,Boolean facility9,Boolean facility10,Boolean facility11,Boolean facility12)
    {
//        if (facility1)
//            setYes();
//        if (facility2)
//            setYes();
//        if (facility3)
//            setYes();
//        if (facility4)
//            setYes();
//        if (facility5)
//            setYes();
//        if (facility6)
//            setYes();
//        if (facility7)
//            setYes();
//        if (facility8)
//            setYes();
//        if (facility9)
//            setYes();
//        if (facility10)
//            setYes();
//        if (facility11)
//            setYes();
//        if (facility12)
//            setYes();
    }

    public void getPollingStationDetails(final String ps_id)
    {
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_GETPS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Download Candidate Data ", "Download Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        //TODO change the list view using the details received

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Download Failed", "Download Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("polling_station_id", ps_id);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    public void setYes(TextView view)
    {
        view.setBackgroundColor(getColor(R.color.bg_yes));
    }
    public void setNo(TextView view)
    {
        view.setBackgroundColor(getColor(R.color.bg_no));
    }
    ////////////////////////////////////////////////////////
    // For Fragment Information ////////////////////////////
    ////////////////////////////////////////////////////////

    public void openWebsite(View view)
    {
        //Opening the eci training website
        String uri = "http://ecisveep.nic.in/voters/how-to-vote/";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
    }

    ////////////////////////////////////////////////////////
    // For Fragment Know your candidate ////////////////////
    ////////////////////////////////////////////////////////

    public void downloadPdf(View view)
    {
        //give the download link of the pdf
        String uri = "https://";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
    }

    public void getSetCandidates()
    {
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_CANDIDATES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Download Candidate Data ", "Download Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        //TODO change the list view using the details received

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Download Failed", "Download Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {};

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    ////////////////////////////////////////////////////////
    // For Fragment Facilities /////////////////////////////
    ////////////////////////////////////////////////////////

    EditText mobile;
    EditText epic;
    String epicEntered;
    String phoneNoEntered;

    public void submitEpic(View view)
    {
        mobile = findViewById(R.id.idMobileNo);
        epic = findViewById(R.id.idEpicNo);
        phoneNoEntered = mobile.getText().toString();
        epicEntered = epic.getText().toString();
        if (checkAllFields(epicEntered.trim(), phoneNoEntered.trim()))
        {
            saveInDatabase(epicEntered, phoneNoEntered);
            Toast.makeText(this, "You will get a SMS for confirmation", Toast.LENGTH_LONG).show();
//            goHome();
        }
    }

    public void saveInDatabase(final String epic, final String phone)
    {

        Log.i("here","1111111111111111111111111111111");
        pDialog.setMessage("Saving Request...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_FACILITIES, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("here","222222222222222222"+response);
                    hideDialog();
                    goHome();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("here","333333333333333333333333333"+error);
                    Log.e("ERROR: ", "Server Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Log.i("here","44444444444444444444444444444");
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("epic_number", epic);
                    params.put("phone_no", phone);

                    return params;
                }
            };
            AppController appController = new AppController();
            appController.addToRequestQueue(stringRequest);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

//    private void minimizeKeypad() {
//        //code to minimize the keypad opened after entering the mobile no
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }

    public void openPlayStore(View view)
    {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=pwd.eci.com.pwdapp&hl=en_IN"));
            startActivity(intent);
        }catch (Exception ex){
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=pwd.eci.com.pwdapp&hl=en_IN"));
            startActivity(intent);
        }
    }

    private boolean checkAllFields(String epic, String phone){
        if (epic.length()<9) {
            Toast.makeText(getApplicationContext(), "Error!!! Epic No Not entered", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (phone.length()<10) {
            Toast.makeText(getApplicationContext(), "Error!!! Invalid Mobile No entered", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /////////////////////////////////////
    //// Log In /////////////////////////
    /////////////////////////////////////

    public void goToLogIn(View view)
    {
        //Open Log in Activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
