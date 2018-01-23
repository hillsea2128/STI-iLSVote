package ilsvote.edu.sti.stiilsvote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ContestantsListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Tab1Fragment.OnFragmentInteractionListener,Tab2Fragment.OnFragmentInteractionListener, Tab1Fragment.GetDataInterface, Tab1Fragment.GetStudentBoto, Tab1Fragment.GetStudentNumbah {
    static String studno;
    static String studcode;
    static String studsect;
    static String studname;
    static int studtokens;
    public ViewPager viewPager;
    public Context recquiredContext;
    private List contestantsList = new ArrayList<>();
    private List<Contestants> mainContestants;
    ContestantAdapter2 adapterr2;
    int index_main;
    TabPagerAdapter adapter22;
    ListView listmoto;
    private boolean mayNetKaBa() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contestants_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        listmoto = (ListView) findViewById(R.id.mainListView);

        process_intents();
        actionBarSetup();
        if (mayNetKaBa()) {
            execute_http_things(true);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet Connection!");
            builder.setMessage("Hi, " + studname + "! \n You are previously logged in but you don't have any internet connectivity. Plesae connect to your local STI WiFi or to your mobile data and try again.");
            builder.setPositiveButton("Ah, Sige", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    exitApp();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

    }
public static void minusVotesCount(Integer intToAdd) {
    studtokens = studtokens - intToAdd;
}
    TabLayout tabLayout;
    PagerAdapter adapterPagerhakhak;
    TabPagerAdapter reserbaLang;
    String Barstate = "ShowAllBruh";
    public void TabsSetup() {
        tabLayout =
                (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("Your Votes"));

        viewPager =
                (ViewPager) findViewById(R.id.pager);
        reserbaLang = new TabPagerAdapter
                (getSupportFragmentManager(),
                        tabLayout.getTabCount());
        viewPager.setAdapter(reserbaLang);
        tabLayout.setTabTextColors(
                ContextCompat.getColor(getApplicationContext(), android.R.color.white),
                ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)
        );
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new
                                                   TabLayout.OnTabSelectedListener() {
                                                       @Override
                                                       public void onTabSelected(TabLayout.Tab tab) {
                                                           viewPager.setCurrentItem(tab.getPosition());
                                                           if (tab.getPosition() == 0) {
                                                               Barstate = "ShowAllBruh";
                                                               invalidateOptionsMenu();
                                                           } else {
                                                               Barstate = "HideSearchBruh";
                                                               invalidateOptionsMenu();
                                                           }
                                                           int pos = viewPager.getCurrentItem();
                                                           Fragment fragment = reserbaLang.getRegisteredFragment(pos);
                                                           ActionBar ab = getSupportActionBar();
                                                           switch (pos) {
                                                               case 0: {
                                                                   //((Tab1Fragment) fragment).refreshT();
                                                                   ab.setSubtitle(studname);
                                                                   break;
                                                               }
                                                               case 1: {
                                                                   ab.setSubtitle(null);
                                                                   if (((Tab2Fragment) fragment).task != null) {

                                                                       if(((Tab2Fragment) fragment).task.getStatus() == AsyncTask.Status.RUNNING) {

                                                                       } else {

                                                                           ((Tab2Fragment) fragment).execute_http_things();
                                                                       }

                                                                   } else {
                                                                       ((Tab2Fragment) fragment).execute_http_things();
                                                                   }

                                                                   break;
                                                               }
                                                           }

                                                       }

                                                       @Override
                                                       public void onTabUnselected(TabLayout.Tab tab) {

                                                       }

                                                       @Override
                                                       public void onTabReselected(TabLayout.Tab tab) {

                                                       }

                                                   });
    }

    private void actionBarSetup() {
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.app_name);
        ab.setSubtitle(studname);

    }
    @Override
    public String getStudentNumbahh() {
        return studno;
    }

    @Override
    public Integer getGetStudentBoto() {
        return studtokens;
    }
    public void process_intents() {
        studno = "";
        studcode = "";
        studname = "";
        studsect = "";
        studtokens = 0;
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            studno = extras.getString("STUDNO");
           studcode = extras.getString("STUDCODE");
            studsect = extras.getString("STUDSECT");
            studname = extras.getString("STUDNAME");
            studtokens = extras.getInt("STUDTOKENS");
            //Toast.makeText(this,studname + "\n" + studcode + "\n" + studsect + "\n" + studno + "\n" + studtokens,Toast.LENGTH_SHORT).show();

        }


    }
    void CallSearchingMethod(String toSquery) {

        Fragment activeFragment = reserbaLang.getItem(1);
        if (((Tab2Fragment)activeFragment).adapterr2 != null) {
            ((Tab2Fragment)activeFragment).adapterr2.getFilter().filter(toSquery);
        } else {

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
    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    public String loggerThing;
    public ProgressDialog pd;
    public Contestants[] condata = null;
    int iyay;

    void clearListView() {
        contestantsList.clear();
        mainContestants.clear();
        adapterr2 = new ContestantAdapter2(getApplicationContext(), mainContestants);
        listmoto.setAdapter(adapterr2);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public List<Contestants> getDataList() {
        return mainContestants;
    }

    private class loadImageParaDiLag extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public loadImageParaDiLag(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return loadImage_thumb(params[0]);
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
    public static String saveImage(final Context context, final String imageData, final String sigStr) throws IOException {
        final byte[] imgBytesData = android.util.Base64.decode(imageData,
                android.util.Base64.DEFAULT);

        final File file = File.createTempFile(sigStr, null, context.getCacheDir());
        final String dirfile = file.getPath();
        final FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dirfile;
    }
    public Bitmap loadImage_thumb(String fileee ) {
        try {
            File f = new File(fileee);
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=30;
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

    void puteverythingtolistview() {
        adapterr2 = new ContestantAdapter2(this, mainContestants);
        listmoto.setAdapter(adapterr2);
    }
    HttpClient client;
    HttpGet httpGet;
    HttpResponse response;
    public AsyncTask<Void, String, Void> task;
    void execute_http_things(final Boolean withDialog) {

        task = new AsyncTask<Void, String, Void>() {

            @Override
            protected void onPreExecute() {
                try {
                    clearListView();
                    httpGet.abort();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                if(withDialog) {
                    pd = new ProgressDialog(ContestantsListActivity.this);
                    pd.setTitle("Refreshing contestant lists...");
                    pd.setMessage("Please wait...");
                    pd.setCancelable(false);
                    pd.setIndeterminate(true);
                    pd.show();

                } else {

                }

            }


            Handler mHandler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (task.getStatus() == AsyncTask.Status.RUNNING || task.getStatus() == AsyncTask.Status.PENDING) {
                        task.cancel(true);
                        try {
                            pd.dismiss();
                        } catch (Exception e) {
                            Log.i("HOY","WALANG PROGRESS DIALOG");
                        }

                        createAlert("Lost connection","We have lost our connection to our servers. Please connect to your local STI WiFi or on your strong mobile data.");
                        //swiperthefox.setRefreshing(false);
                    }
                }
            };

            @Override
            protected Void doInBackground(Void... arg0) {
                try {
                    mHandler.postDelayed(runnable, 60000);
                    String urlmoto = "http://ilsvote.azurewebsites.net/product";
                    InputStream inputStream = null;
                    String result= null;
                    client = new DefaultHttpClient();
                    httpGet = new HttpGet(urlmoto);
                    publishProgress("Trying to get response from our servers...");
                    response = client.execute(httpGet);
                    publishProgress("Fetching contestants data...");
                    inputStream = response.getEntity().getContent();
                    Log.i("ASASASASASa","DONW0");
                    if(inputStream != null){
                        Log.i("ASASASASASa","DONW1");
                        try{
                            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
                            String line = "";
                            String resulttt = "";
                            while((line = bufferedReader.readLine()) != null) {
                                resulttt += line;
                            }
                            inputStream.close();
                            httpGet.abort();
                            final JSONArray jarray = new JSONArray(resulttt);
                            Log.i("ASASASASASa","DONW2");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (iyay = 0; iyay < jarray.length(); iyay++) {
                                        publishProgress("Loading contestant ID " + Integer.toString(iyay));
                                        try {
                                            JSONObject jsonChildNode = jarray.getJSONObject(iyay);

                                            String img_shortcut = jsonChildNode.getString("product_image");
                                            String naaaaaaame = jsonChildNode.getString("product_name");
                                            String uniqueID = naaaaaaame.replaceAll(" ","_");
                                            contestantsList.add(new Contestants(naaaaaaame, jsonChildNode.getString("product_section"), jsonChildNode.getString("product_description"), saveImage(getApplicationContext(),img_shortcut,uniqueID), Integer.toString(iyay), jsonChildNode.getString("product_group_name")));

                                        } catch(JSONException | IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    mainContestants = contestantsList;
                                    TabsSetup();
                                    loggerThing = "weGoodBruh";
                                }
                            });

                        }catch(Exception e){
                            Log.i("App", "MAY ERROR: " +e.getMessage());
                            loggerThing = "NoGoodInputParsingDataBruh";
                        }
                    } else {
                        loggerThing = "noDataAccessBruh";
                    }

                } catch (UnknownHostException e2) {
                    loggerThing = "noDataAccessBruh";
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... progress) {
                super.onProgressUpdate(progress);
                pd.setMessage("Please wait... \n"+ progress[0]);
            }
            @Override
            protected void onPostExecute(Void result) {
                if (withDialog) {
                    if (pd!=null) {
                        pd.dismiss();
                    }
                    Fragment activeFragment = reserbaLang.getItem(0);
                    if (((Tab1Fragment)activeFragment).swiperTheFox != null) {
                        ((Tab1Fragment)activeFragment).swiperTheFox.setRefreshing(false);
                    } else {

                    }
                    //swiperthefox.setRefreshing(false);
                } else {
                   // swiperthefox.setRefreshing(false);
                }

                if (Objects.equals(loggerThing, "DataMismatchBruh")) {
                    createAlert("Error", "Student No. / Access code incorrect. Check your credentials and try again.");
                } else if (Objects.equals(loggerThing, "NoGoodInputParsingDataBruh")) {
                    createAlert("Error!","Something is not right. Please try again.");
                } else if (Objects.equals(loggerThing, "noDataAccessBruh")) {
                    createAlert("No Connection", "You have no present internet connection. Please connect to your local STI WiFi or on your mobile data.");
                } else if (Objects.equals(loggerThing, "weGoodBruh")) {

                }
            }

        };
        task.execute((Void[])null);
    }
    public class ContestantAdapter2 extends ArrayAdapter<Contestants> {


        public ContestantAdapter2(Context context, List<Contestants> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.panibagongbuhaypangalawa, parent, false);
            }

            TextView txtProjName = (TextView)convertView.findViewById(R.id.ttproj);
            TextView txtProjDesc  = (TextView)convertView.findViewById(R.id.txtProjDesc);
            TextView txtSection  = (TextView)convertView.findViewById(R.id.txtSection);
            ImageView img = (ImageView) convertView.findViewById(R.id.imghold);

            final Contestants contestants1 = mainContestants.get(position);

            txtProjName.setText(contestants1.proj_title);
            txtProjDesc.setText(contestants1.getlistviewdesc());
            txtSection.setText(contestants1.getProjGroupSec());
            //img.setImageBitmap(loadImage_thumb(contestants1.raw_img));
            new loadImageParaDiLag(img).execute(contestants1.raw_img);
            Animation animation = AnimationUtils
                    .loadAnimation(getApplicationContext(), R.anim.kaliwakananhaa);
            convertView.startAnimation(animation);
            return convertView;
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            if (charText.length() == 0) {
                mainContestants.clear();
                mainContestants.addAll(contestantsList);
            } else {
                for(Iterator<Contestants> itr = mainContestants.iterator(); itr.hasNext();){
                    Contestants sst = itr.next();

                    if (sst.class_sect.toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        mainContestants.clear();
                        mainContestants.add(sst);
                    }
                }
            }
            notifyDataSetChanged();
        }

        private class ValueFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    ArrayList<Contestants> filterList = new ArrayList<Contestants>();
                    for (int i = 0; i < mainContestants.size(); i++) {
                        if ((mainContestants.get(i).class_sect.toLowerCase()).contains(constraint.toString().toLowerCase())) {

                            Contestants babydata = new Contestants(mainContestants.get(i).proj_title,mainContestants.get(i).class_sect,mainContestants.get(i).proj_desc,mainContestants.get(i).raw_img,mainContestants.get(i).proj_ID,mainContestants.get(i).proj_group);
                            filterList.add(babydata);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = mainContestants.size();
                    results.values = mainContestants;
                }
                return results;

            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mainContestants = (ArrayList<Contestants>) results.values;
                notifyDataSetChanged();
            }


        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void trimCache() {
        try {
            File dir = getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        trimCache();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contestants_list, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search anything...");

        MenuItem searchitem = menu.findItem(R.id.menu_search);
        if (Objects.equals(Barstate, "HideSearchBruh")) {
            searchitem.setVisible(false);
        } else if (Objects.equals(Barstate, "ShowAllBruh")){
            searchitem.setVisible(true);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO write your code what you want to perform on search
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                //TODO write your code what you want to perform on search text change
                String text0 = query.toLowerCase(Locale.getDefault());
                CallSearchingMethod(text0);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            execute_http_things(true);
        } else if (id == R.id.action_Logout) {
            log_out_user();
        }

            return super.onOptionsItemSelected(item);
    }
    void exitApp() {
        trimCache();
        this.finish();
    }
void log_out_user() {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setTitle("Confirm");
    builder.setMessage("Are you sure you want to logout?");

    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface dialog, int which) {
            SharedPreferences settings = getSharedPreferences("default_pref", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("REMEMBER_LOGIN", false);
            editor.putString("USERNAME","");
            editor.putString("USERCODE","");
            editor.putString("USERNO","");
            editor.putString("USERSECTION","");
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            exitApp();
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_participants) {

        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {
            log_out_user();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
