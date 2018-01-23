package ilsvote.edu.sti.stiilsvote;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List contestantsList = new ArrayList<>();
    private List<Contestants_Votes> mainContestants;
    ContestantAdapter2 adapterr2;
    ListView listmoto;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2Fragment newInstance(String param1, String param2) {
        Tab2Fragment fragment = new Tab2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    TextView textStudentNumber;
    TextView textStudentName;
    TextView textStudentVotes;
    TextView textStudentSection;
    String studNo;
    void updateVotes() {
        Integer votesMoTo = ContestantsListActivity.studtokens;
        if (votesMoTo == 0 || votesMoTo == null) {
            textStudentVotes.setText("No more votes left!");
        } else if(votesMoTo == 1) {
            textStudentVotes.setText(votesMoTo + " more vote left.");
        } else {
            textStudentVotes.setText(votesMoTo + " more votes left.");
        }
        if(votesMoTo == 3) {
            emptyOne.setVisibility(View.GONE);
            listmoto.setEmptyView(emptyTwo);

        } else {
            emptyTwo.setVisibility(View.GONE);
            listmoto.setEmptyView(emptyOne);
        }
    }
    View emptyOne;
    View emptyTwo;

    void goLayoutsandFight(View v) {
        listmoto = (ListView) v.findViewById(R.id.mainListViewTwo);
        textStudentName = (TextView) v.findViewById(R.id.studNameTwo);
        textStudentNumber = (TextView) v.findViewById(R.id.studNumberTwo);
        textStudentSection = (TextView) v.findViewById(R.id.studSectionTwo);
        textStudentVotes = (TextView) v.findViewById(R.id.txtVotesLeft);
        textStudentName.setText(ContestantsListActivity.studname);
        textStudentNumber.setText(ContestantsListActivity.studno);
        textStudentSection.setText(ContestantsListActivity.studsect);
        Integer votesMoTo = ContestantsListActivity.studtokens;
        if (votesMoTo == 0 || votesMoTo == null) {
            textStudentVotes.setText("No more votes left!");
        } else if(votesMoTo == 1) {
            textStudentVotes.setText(votesMoTo + " more vote left.");
        } else {
            textStudentVotes.setText(votesMoTo + " more votes left.");
        }
        String studNoTwo = ContestantsListActivity.studno;
        if (!studNoTwo.startsWith("0")) {
            studNo = "0" + studNoTwo;
        } else {
            studNo = studNoTwo;
        }
        emptyOne = v.findViewById(R.id.emptyViewForVotedContestants);
        emptyTwo = v.findViewById(R.id.emptyViewForVotedContestantsTwo);
        updateVotes();
    }
    View viewMoTo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewMoTo =  inflater.inflate(R.layout.fragment_tab2, container, false);
        goLayoutsandFight(viewMoTo);
        return viewMoTo;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void createAlert(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
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
    void clearListView() {
        contestantsList.clear();
        mainContestants.clear();
        adapterr2 = new ContestantAdapter2(getActivity(), mainContestants);
        listmoto.setAdapter(adapterr2);
    }
    void puteverythingtolistview() {
        adapterr2 = new ContestantAdapter2(getActivity(), mainContestants);
        listmoto.setAdapter(adapterr2);
    }
    Integer iyay;
    String loggerThing;
    HttpClient client;
    HttpGet httpGet;
    HttpResponse response;
    public AsyncTask<Void, Void, Void> task;

    void execute_http_things() {
                task = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected void onPreExecute() {
                        try {
                            //createAlert("Refreshing account details...");

                            httpGet.abort();
                            clearListView();
                            updateVotes();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Handler mHandler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (task.getStatus() == AsyncTask.Status.RUNNING || task.getStatus() == AsyncTask.Status.PENDING) {
                                task.cancel(true);

                                createAlert("We have lost our connection to our servers. Please connect to your local STI WiFi or on your strong mobile data.");
                                //swiperthefox.setRefreshing(false);
                            }
                        }
                    };
                    @Override
                    protected Void doInBackground(Void... arg0) {
                        try {
                            mHandler.postDelayed(runnable, 60000);
                            String urlmoto = "http://ilsvote.azurewebsites.net/student/get-votes/" + studNo;
                            InputStream inputStream = null;
                            String result= null;
                            client = new DefaultHttpClient();
                            httpGet = new HttpGet(urlmoto);
                            response = client.execute(httpGet);
                            inputStream = response.getEntity().getContent();

                            if(inputStream != null){
                                //result = convertInputStreamToString(inputStream);
                                try{
                                    //JSONArray jsonMainNode = new JSONArray(convertInputStreamToString(inputStream));
                                    final JSONArray jarray = new JSONArray(convertInputStreamToString(inputStream));
                                    httpGet.abort();
                                    for (iyay = 0; iyay < jarray.length(); iyay++) {
                                        try {
                                            JSONObject jsonChildNode = jarray.getJSONObject(iyay);

                                            //String img_shortcut = jsonChildNode.getString("product_image");
                                            String naaaaaaame = jsonChildNode.getString("product_name");
                                            contestantsList.add(new Contestants_Votes(naaaaaaame, jsonChildNode.getString("product_section"), "NO_DESC", "NO_IMAGE", Integer.toString(iyay), jsonChildNode.getString("product_group_name"),jsonChildNode.getString("votes")));
                                        } catch(JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    mainContestants = contestantsList;

                                    loggerThing = "weGoodBruh";
                                }catch(Exception e){
                                    Log.i("App", "MAY ERROR: " +e.getMessage());
                                    loggerThing = "NoGoodInputParsingDataBruh";
                                }
                            } else {
                                loggerThing = "noDataAccessBruh";
                            }

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            loggerThing = "noDataAccessBruh";
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        puteverythingtolistview();

                        if (Objects.equals(loggerThing, "DataMismatchBruh")) {
                            createAlert("Student No. / Access code incorrect. Check your credentials and try again.");
                        } else if (Objects.equals(loggerThing, "NoGoodInputParsingDataBruh")) {
                            createAlert("Something is not right. Please try again.");
                        } else if (Objects.equals(loggerThing, "noDataAccessBruh")) {
                            createAlert("You have no present internet connection. Please connect to your local STI WiFi or on your mobile data.");
                        } else if (Objects.equals(loggerThing, "weGoodBruh")) {

                        }
                    }

                };
                task.execute((Void[])null);

            }

    public class ContestantAdapter2 extends ArrayAdapter<Contestants_Votes> {


        public ContestantAdapter2(Context context, List<Contestants_Votes> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.panibagongbuhaypangatlo, parent, false);
            }

            TextView txtProjName = (TextView)convertView.findViewById(R.id.ttproj);
            TextView txtVotes  = (TextView)convertView.findViewById(R.id.txtVotes);
            TextView txtSection  = (TextView)convertView.findViewById(R.id.txtSection);
            ImageView img = (ImageView) convertView.findViewById(R.id.imghold);

            final Contestants_Votes contestants1 = mainContestants.get(position);

            String vv = contestants1.proj_votes;
            if (Objects.equals(vv, "0")) {
                txtVotes.setText("You didn't vote this contestant, which doesn't make sense...");
            } else if (Objects.equals(vv, "1")) {
                txtVotes.setText("You voted this contestant " + contestants1.proj_votes + " time.");
            } else {
                txtVotes.setText("You voted this contestant " + contestants1.proj_votes + " times.");
            }
            txtProjName.setText(contestants1.proj_title);
            txtSection.setText(contestants1.getProjGroupSec());
            //img.setImageBitmap(loadImage_thumb(contestants1.raw_img));
            //new ContestantsListActivity.loadImageParaDiLag(img).execute(contestants1.raw_img);
            Animation animation = AnimationUtils
                    .loadAnimation(getActivity(), R.anim.kaliwakananhaa);
            convertView.startAnimation(animation);
            return convertView;
        }


}
}