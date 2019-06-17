package aiactr.archit.decode;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.widget.TextView;
//import java.util.HashMap;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//import java.lang.reflect.Method;
//import activity.LoginActivity;
//import helper.SQLiteHandler;
//import helper.SessionManager;
//import java.util.HashMap;

public class LoggedInActivity extends AppCompatActivity {

//    private TextView txtName;
//    private TextView txtEmail;
//    private Button btnSubmit;
//    private SQLiteHandler db;
    EditText stationId;
    EditText queue;
    String id;
    String queueLn;
    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

//        txtName = findViewById(R.id.name);
//        txtEmail = findViewById(R.id.email);
//        btnSubmit = findViewById(R.id.btnSubmitData);
        stationId = findViewById(R.id.station_id);
        queue = findViewById(R.id.queue);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SqLite database handler
//        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
//        HashMap<String, String> user = db.getUserDetails();

//        String name = user.get("name");
//        String email = user.get("email");

        // Displaying the user details on the screen
//        txtName.setText(name);
//        txtEmail.setText(email);
    }

    public void logOut(View view)
    {
        logoutUser();
    }

    private void logoutUser() {
        session.setLogin(false);
//        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(LoggedInActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveQueue(View view)
    {
        id = stationId.getText().toString();
        queueLn = queue.getText().toString();

        pDialog.setMessage("Updating Queue...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Method.POST, AppConfig.URL_QUEUE_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                Intent intent = new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR: ", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("polling_station_id", id);
                params.put("queue", queueLn);

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
}
