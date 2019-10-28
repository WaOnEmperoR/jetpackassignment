package id.govca.jetpackassignment.data.source.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;

import id.govca.jetpackassignment.EspressoIdlingResource;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowList;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataSourceTVShow extends PageKeyedDataSource<Integer, TVShow> {
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    private final String language;

    private final String TAG = this.getClass().getSimpleName();

    public RemoteDataSourceTVShow(String mLanguage){
        this.language = mLanguage;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, TVShow> callback) {
        EspressoIdlingResource.increment();

        mApiInterface.RxGetTVShowList(Constants.API_KEY, language, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TVShowList, ArrayList<TVShow>>() {
                    @Override
                    public ArrayList<TVShow> apply(TVShowList tvShowList) throws Exception {
                        return tvShowList.getTvShowArrayList();
                    }
                })
                .subscribeWith(new DisposableObserver<ArrayList<TVShow>>() {
                    @Override
                    public void onNext(ArrayList<TVShow> tvShows) {
                        callback.onResult(tvShows, null, FIRST_PAGE + 1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                        if (!EspressoIdlingResource.getEspressoIdlingResourcey().isIdleNow()) {
                            Log.d(TAG, "espresso");
                            EspressoIdlingResource.decrement();
                        }
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TVShow> callback) {
        mApiInterface.RxGetTVShowList(Constants.API_KEY, language, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TVShowList, ArrayList<TVShow>>() {
                    @Override
                    public ArrayList<TVShow> apply(TVShowList tvShowList) throws Exception {
                        return tvShowList.getTvShowArrayList();
                    }
                })
                .subscribeWith(new DisposableObserver<ArrayList<TVShow>>() {
                    @Override
                    public void onNext(ArrayList<TVShow> tvShows) {
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;

                        callback.onResult(tvShows, adjacentKey);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TVShow> callback) {
        mApiInterface.RxGetTVShowList(Constants.API_KEY, language, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TVShowList, ArrayList<TVShow>>() {
                    @Override
                    public ArrayList<TVShow> apply(TVShowList tvShowList) throws Exception {
                        return tvShowList.getTvShowArrayList();
                    }
                })
                .subscribeWith(new DisposableObserver<ArrayList<TVShow>>() {
                    @Override
                    public void onNext(ArrayList<TVShow> tvShows) {
                        if (tvShows.size() > 0)
                        {
                            Integer adjacentKey = params.key + 1;

                            callback.onResult(tvShows, adjacentKey);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                        if (!EspressoIdlingResource.getEspressoIdlingResourcey().isIdleNow()) {
                            Log.d(TAG, "espresso");
                            EspressoIdlingResource.decrement();
                        }
                    }
                });
    }
}
