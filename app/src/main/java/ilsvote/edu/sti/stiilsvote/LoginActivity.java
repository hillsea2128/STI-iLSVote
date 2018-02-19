package ilsvote.edu.sti.stiilsvote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    public ProgressDialog pd;
    String student_no;
    String student_code;
    String student_sect;
    String student_name;
    Integer student_tokens;
    TextInputLayout studNum;
    TextInputLayout studCode;
    CheckBox cbRemember;
    Spinner spinme1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView imageSTILOGO = (ImageView) findViewById(R.id.imageView3);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        studNum = (TextInputLayout) findViewById(R.id.studNum);
        studCode = (TextInputLayout) findViewById(R.id.studCode);
        spinme1 = (Spinner) findViewById(R.id.spinner2);
        cbRemember = (CheckBox) findViewById(R.id.cbRememberMe);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(studNum != null && studCode !=null) {
                    if (mayNetKaBa() == true) {
                        runall();
                    } else {
                        createAlert("No Connection", "You have no present internet connection. Please connect to your local STI WiFi or on your mobile data.");
                    }
                } else {
                    createAlert("Empty Fields!", "Please put something!");
                }
            }
        });
        imageSTILOGO.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivityForResult(intent, 0);
                return true;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        cbRemember.setChecked(false);
        spinme1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Animation animation1 = new AlphaAnimation(0.5f, 1.0f);
                animation1.setDuration(300);
                v.startAnimation(animation1);
                return false;
            }
        });
    }
    public void createAlert(String title, String message) {
        AlertDialog.Builder builder1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder1 = new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder1 = new AlertDialog.Builder(this);
        }

        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ah, Sige",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.setIcon(R.drawable.ic_warning);
        alert11.show();
    }
    private boolean mayNetKaBa() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

public void saveSettings_RememberLogin(String name, String studno, String studcode, String studsect, Integer studTokens) {
    SharedPreferences settings = getSharedPreferences("default_pref", 0);
    SharedPreferences.Editor editor = settings.edit();
    if (cbRemember.isChecked()) {
        editor.putBoolean("REMEMBER_LOGIN", true);
        editor.putString("USERNAME",name);
        editor.putString("USERCODE",studcode);
        editor.putString("USERNO",studno);
        editor.putString("USERSECTION",studsect);
        editor.putInt("USERTOKENS",studTokens);
        editor.apply();
    } else {
        editor.putBoolean("REMEMBER_LOGIN", false);
        editor.putString("USERNAME","");
        editor.putString("USERCODE","");
        editor.putString("USERNO","");
        editor.putString("USERSECTION","");
        editor.putInt("USERTOKENS",0);
        editor.apply();
    }


}
    Integer int1;
    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    void exitApp() {
        this.finish();
    }
    HttpClient client;
    HttpGet httpGet;
    HttpResponse response;
    String weGoodBruh = "weGoodareWe?";
    void actualrun() throws IOException {
        String studno_URL = studNum.getEditText().getText().toString();
        String studcode_URL = studCode.getEditText().getText().toString();
        String studsec_URL = spinme1.getSelectedItem().toString();
        String urrlmoto = "youarenotallowedtoacesathis.url";
        InputStream inputStream = null;
        String result= null;
        if (Objects.equals(studno_URL, "") || Objects.equals(studcode_URL, "")) {
            weGoodBruh = "DataEmptyBruh";
        } else {

    client = new DefaultHttpClient();
   httpGet = new HttpGet(urlmoto);

    HttpParams params = client.getParams();
    HttpConnectionParams.setConnectionTimeout(params, 30000);
    HttpConnectionParams.setSoTimeout(params, 30000);

            try {

            response = client.execute(httpGet);
            inputStream = response.getEntity().getContent();

            if(inputStream != null){

            JSONObject jobject = new JSONObject(convertInputStreamToString(inputStream));
            JSONArray Jarray  = jobject.getJSONArray("records");
            httpGet.abort();
            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject jsonChildNode = Jarray.getJSONObject(i);
                student_no = jsonChildNode.getString("student_number");
                student_code = jsonChildNode.getString("access_code");
                student_sect = jsonChildNode.getString("student_section");
                student_name = jsonChildNode.getString("student_name");
                student_tokens = jsonChildNode.getInt("student_tokens");
                if(Objects.equals(studsec_URL, student_sect) && Objects.equals(student_code, studcode_URL)) {
                    Intent intentmain1 = new Intent(this, ContestantsListActivity.class);
                    intentmain1.putExtra("STUDNAME",student_name);
                    intentmain1.putExtra("STUDCODE",student_code);
                    intentmain1.putExtra("STUDNO",student_no);
                    intentmain1.putExtra("STUDSECT",student_sect);
                    intentmain1.putExtra("STUDTOKENS",student_tokens);
                    weGoodBruh = "weGood";
                    saveSettings_RememberLogin(student_name,student_no,student_code,student_sect,student_tokens);
                    startActivity(intentmain1);
                    exitApp();

                } else {
                    weGoodBruh = "DataMismatchBruh";
                }
            }
    } else {
        weGoodBruh = "noDataAccessBruh";
    }
    }catch(Exception e){
                Log.i("App", "MAY ERROR: " + e.getMessage());
                weGoodBruh = "NoGoodInputParsingDataBruh";
            }
        }
    }
public AsyncTask<Void, Void, Void> task;
    void runall() {

        task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                try {
                    httpGet.abort();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pd = new ProgressDialog(LoginActivity.this);
                pd.setTitle("Logging in...");
                pd.setMessage("Please wait...");
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.show();

            }
            @Override
            protected Void doInBackground(Void... arg0) {
                try {

                    actualrun();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (pd!=null) {
                    pd.dismiss();
                }
                if (Objects.equals(weGoodBruh, "DataMismatchBruh")) {
                    createAlert("Error", "Student No. / Access code incorrect. Check your credentials and try again.");
                } else if (Objects.equals(weGoodBruh, "NoGoodInputParsingDataBruh")) {
                    createAlert("Error!","Your access code or student number is not correct / internet connection is poor. Please check out your internet connection and credentials and try again.");
                } else if (Objects.equals(weGoodBruh, "noDataAccessBruh")) {
                    createAlert("No Connection", "You have no present internet connection. Please connect to your local STI WiFi or on your mobile data.");
                } else if (Objects.equals(weGoodBruh, "timeoutNaBruh")) {
                    createAlert("Lost Connection", "We have lost our connection. Please connect to your local STI WiFi or on your strong mobile data.");
                } else if (Objects.equals(weGoodBruh, "DataEmptyBruh")) {
                    createAlert("Empty Fields!", "Please put your Student number and Access code first!");
                }else if (Objects.equals(weGoodBruh, "weGood")) {

                } else {
                    createAlert("Error!","Your access code or student number is not correct / internet connection is poor. Please check out your internet connection and credentials and try again.");
            }
                studCode.getEditText().setText(null);
            }

        };


        task.execute((Void[])null);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            pd.dismiss();
        } catch(Exception e) {
            Log.i("App", "NILAGAY KO LANG TO DITO PARA ALAM MO NA: " +e.getMessage());
        }

    }
}
