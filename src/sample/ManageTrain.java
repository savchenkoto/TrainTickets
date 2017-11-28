package sample;

import DAO.DaoImpl.TrainDaoImpl;
import model.Train;
import org.hibernate.SessionFactory;

public class ManageTrain {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {

        TrainDaoImpl TrainDao = new TrainDaoImpl();
        TrainDao.add(new Train("707H", "Voronezh", "Saint-Petersburg"));
    }

    public static String getTime(int minutes) {
        return minutes % 60 + ":" + minutes;
    }


}
