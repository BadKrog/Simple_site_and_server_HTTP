package server;

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

    private List<Film> filmList = new ArrayList();

    private DataManager(){init();};

    private int getIndexById(int id){
        int index = -1;

        for(int i=0; i<filmList.size(); i++){
            if(filmList.get(i).getId()==id){
                index = i;
                break;
            }
        }
        return index;
    }

    private void init(){
        filmList.add(new Film(1, "Гарри Поттер", 8.6, 154));
        filmList.add(new Film(2, "Шрек", 6.7, 95));
        filmList.add(new Film(3, "Мстители", 8.4, 183));
    }

    public void Add(String id, String name, String rating, String minutes){
        int myId = Integer.parseInt(id);
        double myRat = Double.parseDouble(rating);
        int myMin = Integer.parseInt(minutes);

        filmList.add(new Film(myId, name, myRat, myMin));
    }

    public void Delete(String aId){
        int id = Integer.parseInt(aId);
        int index = getIndexById(id);
        filmList.remove(index);
    }

    public void Editing(String aId, String name, String rating, String minutes){
        int myId = Integer.parseInt(aId);
        double myRat = Double.parseDouble(rating);
        int myMin = Integer.parseInt(minutes);

        int index = getIndexById(myId);

        Film film = filmList.get(index);
        film.setName(name);
        film.setRating(myRat);
        film.setMinutes(myMin);
    }

    public List<Film> getFilmList(){return filmList;}

    @Override
    public String toString() {
        String answer = filmList.size()+"\n";
        for(int i=0; i<filmList.size(); i++){
            answer += filmList.get(i).toString()+"\n";
        }
        return answer;
    }
}
