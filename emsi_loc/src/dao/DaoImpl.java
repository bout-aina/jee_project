package dao;

public class DaoImpl implements IDao{
    @Override
    public double getData() {
        /*
        connect to data base to get température
         */
        System.out.println("version base de donnée");


        double temp=Math.random()*50;
        return temp;
    }
}
