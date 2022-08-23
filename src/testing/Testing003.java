package testing;

import fosalgo.DataReader;
import fosalgo.Individu;
import java.io.File;

public class Testing003 {

    public void runGeneticAlgorithm() {
        //parameter for Genetic Algorithm
        int numIndividu = 1000;// = population size = jumlah individu dalam satu populasi
        int numGeneration = 200;

        //Dataset
        File f = new File("dataset001.fjs");
        DataReader dr = new DataReader(f);
        int[][][] dataMachine = dr.getDataMachine();
        int[][][] dataTime = dr.getDataTime();
        int MAX_MACHINE = dr.getMAX_MACHINE();

        //siapkan individu elitism
        Individu individuElitism = new Individu();

        //generate random first generation
        //bangkitkan populasi awal
        Individu[] populasi = new Individu[numIndividu];
        for (int i = 0; i < populasi.length; i++) {
            populasi[i] = new Individu(dataMachine, dataTime, MAX_MACHINE);
            populasi[i].compute();
            if (populasi[i].getFitness() > individuElitism.getFitness()) {
                individuElitism = populasi[i].clone();
            }
        }

        //PROSES EVOLUSI--------------------------------------------------------
        for (int g = 1; g <= numGeneration; g++) {

            //CHANGE POPULATION with new random POPULATIOON
            for (int i = 0; i < populasi.length; i++) {
                populasi[i] = new Individu(dataMachine, dataTime, MAX_MACHINE);
                populasi[i].compute();
                if (populasi[i].getFitness() > individuElitism.getFitness()) {
                    individuElitism = populasi[i].clone();
                }
            }

        }//END OF EVOLUTION

        //Individu Elitism
        System.out.println("----------------------------------------------------");
        System.out.println("Individu Terbaik");
        System.out.println(individuElitism);
        System.out.println("----------------------------------------------------");
    }

    public static void main(String[] args) {
        Testing003 test = new Testing003();
        test.runGeneticAlgorithm();
    }
}
