package abdul.malik.intaihere;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import abdul.malik.intaihere.App.AppController;
import abdul.malik.intaihere.Model.User;
import abdul.malik.intaihere.Utils.RequestHandler;
import abdul.malik.intaihere.Utils.Server;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_login;
    TextView btn_register;
    EditText txt_password, txt_username;
    Intent intent;
    int success;
    ConnectivityManager conMgr;
    private String url = Server.URL_LOGIN + "login.php";
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_ALAMAT = "alamat";
    public static final String TAG_ID = "id";

    String tag_json_obj = "json_obj_reg";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, email, username, telephone, alamat, image;
    public static final String my_shared_preferences = "my_shared_preference";
    public static final String session_status = "session_status";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        sessionManager = new SessionManager(getApplicationContext());

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_login = (Button) findViewById(R.id.btn_loginlog);
        btn_register = (TextView) findViewById(R.id.btn_registerlogin);
        txt_username = (EditText) findViewById(R.id.txt_usernamelogin);
        txt_password = (EditText) findViewById(R.id.txt_passwordlogin);


        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);
        alamat = sharedpreferences.getString("alamat", null);
        telephone = sharedpreferences.getString("telp", null);
        image = sharedpreferences.getString("image", null);

        if (session) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String email = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                // mengecek kolom yang kosong
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(email, password);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);

            }
        });

    }

    private void checkLogin(final String username, final String password) {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        String email = jObj.getString(TAG_EMAIL);
                        String id = jObj.getString(TAG_ID);
                        String username = jObj.getString(TAG_USERNAME);
                        String alamat= jObj.getString("alamat");
                        String telephone= jObj.getString("telephone");
                        String image= jObj.getString("image");

                        Log.e("Login Berhasil!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID, id);
                        editor.putString(TAG_EMAIL, email);
                        editor.putString(TAG_USERNAME, username);
                        editor.putString("alamat", alamat);
                        editor.putString("telephone", telephone);
                        editor.putString("image", image);
                        editor.commit();

                        // Memanggil main activity
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra(TAG_ID, id);
                        intent.putExtra(TAG_USERNAME, username);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    //tombol kembali
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}
