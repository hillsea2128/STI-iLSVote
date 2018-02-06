package ilsvote.edu.sti.stiilsvote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public List<Contestants> mData;
    public SwipeRefreshLayout swiperTheFox;
    private OnFragmentInteractionListener mListener;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1Fragment newInstance(String param1, String param2) {
        Tab1Fragment fragment = new Tab1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ListView listmoto;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    View lnEmpty;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        listmoto = (ListView) view.findViewById(R.id.mainListView);
        lnEmpty = (LinearLayout) view.findViewById(R.id.emptyViewList);
        swiperTheFox = (SwipeRefreshLayout) view.findViewById(R.id.swiperTheFox);
        swiperTheFox.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((ContestantsListActivity)getActivity()).execute_http_things(true);

            }
        });
        return view;
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
            sGetDataInterface= (GetDataInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public ContestantAdapter2 adapterr2;
    void puteverythingtolistview() {
        adapterr2 = new ContestantAdapter2(getActivity(), mData);
        listmoto.setAdapter(adapterr2);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        mData = sGetDataInterface.getDataList();
        if(sGetDataInterface != null && mData != null) {
            listmoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Contestants cont2 = filteredData.get(position);

                    Animation animation1 = new AlphaAnimation(0.5f, 1.0f);
                    animation1.setDuration(500);
                    view.startAnimation(animation1);
                    //NO IMAGE YET!
                    Intent intent = new Intent(getActivity(), ContestantDisplayAct.class);
                    intent.putExtra("PROJECT_NAME", cont2.proj_title);
                    intent.putExtra("PROJECT_SECTION", cont2.getProjGroupSec());
                    intent.putExtra("PROJECT_DESC", cont2.proj_desc);
                    intent.putExtra("PROJECT_ID", cont2.proj_ID);
                    intent.putExtra("PROJECT_BYTES", cont2.raw_img);
                    startActivityForResult(intent,0);
                }
            });
            puteverythingtolistview();
            lnEmpty.setVisibility(View.GONE);
        } else {
            lnEmpty.setVisibility(View.VISIBLE);
            listmoto.setEmptyView(lnEmpty);
        }
    }

    GetDataInterface sGetDataInterface;

    public interface GetDataInterface {
        List<Contestants> getDataList();
    }

    GetStudentNumbah sGetStudentNumbah;

    public interface GetStudentNumbah {
        String getStudentNumbahh();
    }
    GetStudentBoto sGetStudentBoto;

    public interface GetStudentBoto {
        Integer getGetStudentBoto();
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
    private List<Contestants>filteredData = null;
    public class ContestantAdapter2 extends ArrayAdapter<Contestants> {

        private ValueFilter mFilter = new ValueFilter();

        public ContestantAdapter2(Context context, List<Contestants> objects) {
            super(context, 0, objects);
            filteredData = mData;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.panibagongbuhaypangalawa, parent, false);
                }

                TextView txtProjName = (TextView)convertView.findViewById(R.id.ttproj);
                TextView txtProjDesc  = (TextView)convertView.findViewById(R.id.txtProjDesc);
                TextView txtSection  = (TextView)convertView.findViewById(R.id.txtSection);
                ImageView img = (ImageView) convertView.findViewById(R.id.imghold);

                final Contestants contestants1 = filteredData.get(position);

                txtProjName.setText(contestants1.proj_title);
                txtProjDesc.setText(contestants1.getlistviewdesc());
                txtSection.setText(contestants1.getProjGroupSec());
                //img.setImageBitmap(loadImage_thumb(contestants1.raw_img));
                new loadImageParaDiLag(img).execute(contestants1.raw_img);
                Animation animation = AnimationUtils
                        .loadAnimation(getActivity(), R.anim.kaliwakananhaa);
                convertView.startAnimation(animation);

            } catch (Exception e) {
                Log.i("ERROR POPULATION","LISTVIEW ERROR IN POPULATING DUE TO "+ e.getMessage());
            }
            return convertView;
        }

        @NonNull
        public Filter getFilter() {
            return mFilter;
        }

        private class ValueFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                final List<Contestants> list = mData;
                Log.i("filtered ",constraint.toString().toLowerCase());
                int count = list.size();
                if (constraint != null && constraint.length() > 0) {
                    ArrayList<Contestants> filterList = new ArrayList<Contestants>(count);
                    for (int i = 0; i < list.size(); i++) {
                        if ((list.get(i).class_sect.toLowerCase()).contains(constraint.toString().toLowerCase())  ||  (list.get(i).proj_title.toLowerCase()).contains(constraint.toString().toLowerCase())  ||  (list.get(i).proj_group.toLowerCase()).contains(constraint.toString().toLowerCase())) {
                            Contestants babydata = new Contestants(list.get(i).proj_title,list.get(i).class_sect,list.get(i).proj_desc,list.get(i).raw_img,list.get(i).proj_ID,list.get(i).proj_group);
                            filterList.add(babydata);
                        } else {

                        }
                    }
                    if (filterList.size() <=0) {
                        Toast.makeText(getActivity(),"No results found.",Toast.LENGTH_SHORT).show();
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = list.size();
                    results.values = list;
                }
                return results;

            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                filteredData = (ArrayList<Contestants>) results.values;
                notifyDataSetChanged();
            }


        }


    }
}
