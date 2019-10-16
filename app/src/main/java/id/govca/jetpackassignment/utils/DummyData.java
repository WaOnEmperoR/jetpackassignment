package id.govca.jetpackassignment.utils;

import java.util.ArrayList;

import id.govca.jetpackassignment.pojo.Movie;

public class DummyData {

    public DummyData(){
        Movie movie = new Movie();
        movie.setOverview("Haha");
    }

    public static ArrayList<Movie> generateDummyMovies()
    {
        ArrayList<Movie> movies = new ArrayList<>();

        Movie movie = new Movie();

        movie.setTitle("Joker");
        movie.setOverview("During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.");
        movie.setId(475557);
        movie.setVote_average(8.7);
        movie.setRelease_date("2019-10-04");
        movie.setPopularity(697.866);
        movies.add(movie);
        //===================================
        movie.setTitle("The Lion King");
        movie.setOverview("Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.");
        movie.setId(420818);
        movie.setVote_average(7.1);
        movie.setRelease_date("2019-07-19");
        movie.setPopularity(304.445);
        movies.add(movie);
        //===================================
        movie.setTitle("It Chapter Two");
        movie.setOverview("27 years after overcoming the malevolent supernatural entity Pennywise, the former members of the Losers' Club, who have grown up and moved away from Derry, are brought back together by a devastating phone call.");
        movie.setId(474350);
        movie.setVote_average(7);
        movie.setRelease_date("2019-09-06");
        movie.setPopularity(221.765);
        movies.add(movie);
        //===================================
        movie.setTitle("Gemini Man");
        movie.setOverview("Henry Brogen, an aging assassin tries to get out of the business but finds himself in the ultimate battle: fighting his own clone who is 25 years younger than him and at the peak of his abilities.");
        movie.setId(453405);
        movie.setVote_average(5.9);
        movie.setRelease_date("2019-10-11");
        movie.setPopularity(171.766);
        movies.add(movie);

        return movies;
    }
}
