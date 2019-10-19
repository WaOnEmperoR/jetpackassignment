package id.govca.jetpackassignment.helper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import id.govca.jetpackassignment.fragment.FavoriteFragment;
import id.govca.jetpackassignment.fragment.MovieFragment;
import id.govca.jetpackassignment.fragment.TVShowFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoofTabs) {
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MovieFragment movie = new MovieFragment();
                return movie;
            case 1:
                TVShowFragment tvShow = new TVShowFragment();
                return tvShow;
            case 2:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                return favoriteFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
