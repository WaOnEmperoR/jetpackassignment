package id.govca.jetpackassignment.fragment.favorite;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import id.govca.jetpackassignment.EspressoIdlingResource;
import id.govca.jetpackassignment.R;
import id.govca.jetpackassignment.adapter.ListFavoriteAdapter;
import id.govca.jetpackassignment.data.source.local.entity.Favorite;
import id.govca.jetpackassignment.viewmodel.FavoriteListViewModel;
import id.govca.jetpackassignment.viewmodel.ViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteTVShowFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteTVShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteTVShowFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private final String TAG = this.getClass().getSimpleName();
    private View mProgressView;

    private ListFavoriteAdapter listFavoriteAdapter;

    private RecyclerView rvMovies;
    private String param_lang;

    private FavoriteListViewModel favoriteListViewModel;

    public FavoriteTVShowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteTVShowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteTVShowFragment newInstance(String param1, String param2) {
        FavoriteTVShowFragment fragment = new FavoriteTVShowFragment();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressView = view.findViewById(R.id.progressBarFavoriteMovie);
        rvMovies = view.findViewById(R.id.recyclerView_favorite_movie);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EspressoIdlingResource.increment();

        if (getActivity()!=null)
        {
            showLoading(true);

            favoriteListViewModel = obtainViewModel(getActivity());
            listFavoriteAdapter = new ListFavoriteAdapter(getActivity());

            favoriteListViewModel.getAllFavorites(1).observe(this, favorites -> {
                showLoading(false);
                listFavoriteAdapter.submitList(favorites);

                if (!EspressoIdlingResource.getEspressoIdlingResourcey().isIdleNow()) {
                    EspressoIdlingResource.decrement();
                }
            });

//            favoriteListViewModel.getListFavoritesLiveData(1).observe(this, favoriteList -> {
//                showLoading(false);
//                listFavoriteAdapter.setData(favoriteList);
//
//                listFavoriteAdapter.setOnItemClickCallback(new ListFavoriteAdapter.OnItemClickCallback() {
//                    @Override
//                    public void onItemClicked(Favorite data) {
//                        Log.d(TAG, String.valueOf(data.getFavId()));
//
////                        Intent intent = new Intent(getActivity(), DetailActivity.class);
////                        intent.putExtra("Movie_ID", data.getId());
////                        intent.putExtra("Category", 0);
////                        startActivity(intent);
//                    }
//                });
//            });

            rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
            rvMovies.setHasFixedSize(false);
            rvMovies.setAdapter(listFavoriteAdapter);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @NonNull
    private static FavoriteListViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(FavoriteListViewModel.class);
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

    private void showLoading(Boolean state) {
        if (state) {
            mProgressView.setVisibility(View.VISIBLE);
        } else {
            mProgressView.setVisibility(View.GONE);
        }
    }
}
