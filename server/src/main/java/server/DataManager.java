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

    private void init(){
        filmList.add(new Film(1, "Гарри Поттер", 8.6, 154));
        filmList.add(new Film(2, "Шрек", 6.7, 95));
        filmList.add(new Film(3, "Мстители", 8.4, 183));
    }

    public void Add(String strFilm){ }

    public void Delete(String strFilm){}

    public void Editing(String strFilm){}

    public List<Film> getFilmList(){return filmList;}
}
