package testing;

import fosalgo.DataReader;
import fosalgo.Individu;
import java.io.File;

public class Test001 {

    public static void main(String[] args) {
        File f = new File("dataset001.fjs");
        DataReader dr = new DataReader(f);
        System.out.println("DATASET");
        System.out.println(dr.toString());
        
        int[][][] dataMachine = dr.getDataMachine();
        int[][][] dataTime = dr.getDataTime();
        int MAX_MACHINE = dr.getMAX_MACHINE();
        Individu indv01 = new Individu(dataMachine, dataTime, MAX_MACHINE);
        System.out.println("INDIVIDU");
        System.out.println(indv01);
        //System.out.println(indv01.printJobProcessMachine());
        
        indv01.compute();
        System.out.println("Durasi Total = "+indv01.getTotalDuration());
        System.out.println("Fitness      = "+indv01.getFitness());

    }
}