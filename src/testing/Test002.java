package testing;

import fosalgo.DataReader;
import fosalgo.Individu;
import java.io.File;

public class Test002 {

    public static void main(String[] args) {

        //parameter for Genetic Algorithm
        int numIndividu = 100000;// = population size = jumlah individu dalam satu populasi
        int numGeneration = 20;

        //Dataset
        File f = new File("dataset002.fjs");
        DataReader dr = new DataReader(f);
        int[][][] dataMachine = dr.getDataMachine();
        int[][][] dataTime = dr.getDataTime();
        int MAX_MACHINE = dr.getMAX_MACHINE();

        //siapkan individu elitism
        Individu individuElitism = new Individu();
        
        //generate random first generation
        Individu[] populasi = new Individu[numIndividu];
        for (int i = 0; i < populasi.length; i++) {
            populasi[i] = new Individu(dataMachine, dataTime, MAX_MACHINE);
            populasi[i].compute();
            if(populasi[i].getFitness()>individuElitism.getFitness()){
                individuElitism = populasi[i].clone();
            }
        }
        
        //Individu Elitism
        System.out.println("----------------------------------------------------");
        System.out.println("Individu Terbaik");       
        System.out.println(individuElitism);
        System.out.println("----------------------------------------------------");
    }
}
