package dao;

import org.springframework.stereotype.Component;

@Component("dao")
public class DaoImpl implements IDao{
    @Override
    public double getData() {
        /*
        connect to data base to get température
         */
        System.out.println("version base de donnée");

        double temp=Math.random()*40;
        return temp;
    }
}
