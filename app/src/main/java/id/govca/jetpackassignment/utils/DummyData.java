package id.govca.jetpackassignment.utils;

import java.util.ArrayList;

import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.TVShow;

public class DummyData {

    public DummyData(){
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

    public static ArrayList<TVShow> generateDummyTVShows(){
        ArrayList<TVShow> tvShows = new ArrayList<>();

        TVShow tvShow = new TVShow();

        tvShow.setName("Fear the Walking Dead");
        tvShow.setFirst_air_date("2015-08-23");
        tvShow.setOverview("What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.");
        tvShow.setId(62286);
        tvShow.setPopularity(452.849);
        tvShow.setVote_average(6.3);
        tvShows.add(tvShow);
        //===================================
        tvShow.setName("Batwoman");
        tvShow.setFirst_air_date("2019-10-06");
        tvShow.setOverview("Armed with a great passion for social justice and with a great facility to always say what she thinks, Kate Kane is known in the streets of Gotham as Batwoman, a lesbian highly trained to fight crime that resurfaces in the city. However, before becoming a savior, she must fight the demons that prevent her from being the symbol of the hope of a corrupt city.");
        tvShow.setId(89247);
        tvShow.setPopularity(298.277);
        tvShow.setVote_average(6.9);
        tvShows.add(tvShow);
        //===================================
        tvShow.setName("Arrow");
        tvShow.setFirst_air_date("2012-10-10");
        tvShow.setOverview("Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.");
        tvShow.setId(1412);
        tvShow.setPopularity(332.397);
        tvShow.setVote_average(5.8);
        tvShows.add(tvShow);

        return tvShows;

    }
}
