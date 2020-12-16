package server;

import server.db.FilmManager;

import java.util.ArrayList;
import java.util.List;

// Класс, созданный по патерну Singleton - всегда имеет один экземпляр класса
public class DataManager {
    private static DataManager instance;

    public static DataManager getInstance(){
        if(instance==null){
            instance = new DataManager();
        }
        return instance;
    }

    private DataManager(){};


    public void Add(String id, String name, String rating, String minutes){
        int myId = Integer.parseInt(id);
        double myRat = Double.parseDouble(rating);
        int myMin = Integer.parseInt(minutes);

        Film film = new Film(name, myRat, myMin);
        FilmManager.getInstance().add(film);
    }

    public void Delete(String aId){
        int id = Integer.parseInt(aId);
        FilmManager.getInstance().delete(id);
    }

    public void Editing(String aId, String name, String rating, String minutes){
        int myId = Integer.parseInt(aId);
        double myRat = Double.parseDouble(rating);
        int myMin = Integer.parseInt(minutes);

        Film film = new Film(myId, name, myRat, myMin);
        FilmManager.getInstance().editing(film);
    }


    //public List<Film> getFilmList(){return filmList;}

    public List<Film> getFilmList(){
        return FilmManager.getInstance().getFilmList();
    }

    @Override
    public String toString() {
        List<Film> filmList = getFilmList();
        String answer = filmList.size()+"\n";
        for(int i=0; i<filmList.size(); i++){
            answer += filmList.get(i).toString()+"\n";
        }
        return answer;
    }
}
