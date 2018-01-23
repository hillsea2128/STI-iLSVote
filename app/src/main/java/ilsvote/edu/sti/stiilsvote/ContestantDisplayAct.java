package ilsvote.edu.sti.stiilsvote;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

public class ContestantDisplayAct extends AppCompatActivity  {
    String project_title;
    String project_creator;
    String project_desc;
    String project_ID;
    String project_bytes;
    String student_number;
    Integer studentVotes;
    Button btnVote;
    TextView txtProjTitle;
    TextView txtSection;
    TextView txtContent;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contestant_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnVote = (Button) findViewById(R.id.btnVote);
        txtProjTitle = (TextView) findViewById(R.id.txtProjTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);
        txtSection = (TextView) findViewById(R.id.txtSection);
        img = (ImageView) findViewById(R.id.imgMain);
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"VOTING SYSTEM COMING SOON!",Toast.LENGTH_SHORT).show();
                doPostThatYeah();
            }
        });
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            project_title = extras.getString("PROJECT_NAME");
            project_creator = extras.getString("PROJECT_SECTION");
            project_desc = extras.getString("PROJECT_DESC");
            project_ID = extras.getString("PROJECT_ID");
            project_bytes = extras.getString("PROJECT_BYTES");
            student_number = ContestantsListActivity.studno;
            studentVotes = ContestantsListActivity.studtokens;
        }

        setContent();
actionBarSetup();
        Integer finalID = Integer.parseInt(project_ID) + 1;
        project_ID = Integer.toString(finalID);
    }

void doPostThatYeah() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setTitle("Is this the one?");
    builder.setMessage("Are you sure you want to vote " + project_title + "?");

    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface dialog, int which) {
            new PostDataAsyncTask().execute();
        }
    });
    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });

    AlertDialog alert = builder.create();
    alert.show();
}

    class loadImageParaDiLag extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public loadImageParaDiLag(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return loadImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.placeholder);
                        imageView.setImageDrawable(placeholder);
                    }
                }
            }
        }
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

    public Bitmap loadImage(String fileee ) {
        try {
            File f = new File(fileee);
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
    private void actionBarSetup() {
            ActionBar ab = getSupportActionBar();
            ab.setTitle(project_title);
            ab.setSubtitle(project_creator);
    }
    void updateButton() {

        if(studentVotes == 0) {
            btnVote.setEnabled(false);
            btnVote.setText("No more vote trials!");
        } else if (studentVotes == 1) {
            btnVote.setEnabled(true);
            btnVote.setText("Vote Now! (" + studentVotes + " vote left.");
        } else {
            btnVote.setEnabled(true);
            btnVote.setText("Vote Now! (" + studentVotes + " votes left.");

        }
    }
    void setContent() {
        txtProjTitle.setText(project_title);
        txtSection.setText(project_creator);
        txtContent.setText(project_desc);
        new loadImageParaDiLag(img).execute(project_bytes);
        updateButton();
    }
    ProgressDialog pd;
    String loggerBruh = "weGoodBruh";
    public class PostDataAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            try {

            } catch (Exception e) {
                httpPost.abort();
            }
            super.onPreExecute();
            pd = new ProgressDialog(ContestantDisplayAct.this);
            pd.setTitle("Sending vote request...");
            pd.setMessage("Voting " + project_title + ". Please wait...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                    postText();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String lenghtOfFile) {
            if (pd!=null) {
                pd.dismiss();
            }
            httpPost.abort();
            if (Objects.equals(loggerBruh, "weGoodBruh")) {
                createAlert("They are the one!","Vote success! You now have only " + ContestantsListActivity.studtokens + " votes left!");
            } else if (Objects.equals(loggerBruh, "noSuccessBruh")) {
                createAlert("Vote failed!","Sorry, the voting failed because of internet connection problems or server problems. Please try again.");
            }
        }
    }
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse response ;
    private void postText(){
        try{
            String postReceiverUrl = "http://ilsvote.azurewebsites.net/vote?student_number=0" + student_number + "&product_id=" + project_ID;
            Log.v("hey", "postURL: " + postReceiverUrl);
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(postReceiverUrl);
            response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {
                String responseStr = EntityUtils.toString(resEntity).trim();
                Log.v("HEEEEEEEEEEEEEEEEY", "Response: " +  responseStr);
                //Toast.makeText(this,"Response: " + responseStr,Toast.LENGTH_LONG).show();
                if (Objects.equals(responseStr, "Vote successful!")) {
                    ContestantsListActivity.minusVotesCount(1);
                    studentVotes = studentVotes - 1;
                    SharedPreferences settings = getSharedPreferences("default_pref", 0);
                    boolean RememberedLogin = settings.getBoolean("REMEMBER_LOGIN", false);
                    SharedPreferences.Editor editor = settings.edit();
                    if (RememberedLogin) {
                        editor.putBoolean("REMEMBER_LOGIN", true);
                        editor.putString("USERNAME",ContestantsListActivity.studname);
                        editor.putString("USERCODE",ContestantsListActivity.studcode);
                        editor.putString("USERNO",ContestantsListActivity.studno);
                        editor.putString("USERSECTION",ContestantsListActivity.studsect);
                        editor.putInt("USERTOKENS",ContestantsListActivity.studtokens);
                        editor.apply();
                    } else {

                    }
                    loggerBruh = "weGoodBruh";
                    updateButton();
                    httpPost.abort();
                } else if  (Objects.equals(responseStr, "Vote failed")) {
                    loggerBruh = "noSuccessBruh";
                    httpPost.abort();
                }
            } else {
                loggerBruh = "noSuccessBruh";
                httpPost.abort();
            }

        } catch (IOException e) {
            e.printStackTrace();
            loggerBruh = "noSuccessBruh";
            httpPost.abort();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
