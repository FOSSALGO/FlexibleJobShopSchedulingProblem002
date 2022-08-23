package fosalgo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataReader {

    //field
    private int[][][] dataMachine = null;
    private int[][][] dataTime = null;
    private int MAX_MACHINE = 0;

    public DataReader(File f) {
        try {
            //read dataset from text file
            FileInputStream fis = new FileInputStream(f);
            Scanner sc = new Scanner(fis, "UTF-8");

            String baris = sc.nextLine().trim();
            String[] segmen = baris.split("\\s+");//lakukan split terhadap string baris menggunakan simbol spasi (berapapun spasinya)

            //segmen[0] menyatakan banyaknya job
            int nJob = Integer.parseInt(segmen[0]);

            //segmen[1] menyatakan banyaknya mesin yang tersedia
            MAX_MACHINE = Integer.parseInt(segmen[1]);

            //inisialisasi array untuk menyimpan data mesin dan waktu yang dibaca dari dataset
            //array 3 dimensi
            dataMachine = new int[nJob][][];
            dataTime = new int[nJob][][];

            for (int j = 0; j < nJob; j++) {
                baris = sc.nextLine().trim();
                segmen = baris.split("\\s+");
                //kolom 1 (dalam hal ini diwakili oleh segmen[0]
                //segmen[0] menyatakan banyaknya proses yang ada di job tersebut
                int nProcess = Integer.parseInt(segmen[0]);//nProcess = banyaknya proses di setiap job

                //inisialisasi kolom array dataMachine dan dataTime dengan nilai variabel nProcess
                dataMachine[j] = new int[nProcess][];
                dataTime[j] = new int[nProcess][];

                //baca segmen berikutnya
                int k = 1;
                int p = 0;
                while (k < segmen.length && p < nProcess) {
                    int nMachine = Integer.parseInt(segmen[k++]);//segmen ini menyatakan banyaknya mesin yang digunakan
                    //inisialisasi layer array dataMachine dan dataTime dengan nilai variabel nMachine
                    //keterangan elemen array: [baris][kolom][layer]
                    dataMachine[j][p] = new int[nMachine];
                    dataTime[j][p] = new int[nMachine];
                    for (int m = 0; m < nMachine; m++) {
                        int machine = Integer.parseInt(segmen[k++]);
                        int time = Integer.parseInt(segmen[k++]);
                        machine = machine - 1;//kita akan menyimpan index machine (atau kita memulai nomor mesin dari 0 untuk nomor mesin terkecil
                        dataMachine[j][p][m] = machine;
                        dataTime[j][p][m] = time;
                    }
                    p++;//jangan lupa increment variabel p sebelum melanjutkan loop while
                }//end of while
            }//end of for(int j=0;j<nJob;j++)
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        String result = "NULL";
        if (dataMachine != null && dataTime != null && dataMachine.length == dataTime.length) {
            result = "";
            StringBuilder sb = new StringBuilder();
            int nJob = dataMachine.length;
            sb.append("num job    : " + nJob + "\n");
            sb.append("---------------------------------------------------------\n");
            for (int j = 0; j < nJob; j++) {
                int nProcess = dataMachine[j].length;
                sb.append("Job-" + j + " nProcess = " + nProcess);
                for (int p = 0; p < nProcess; p++) {
                    sb.append(" [");
                    int nMachine = dataMachine[j][p].length;
                    sb.append(" nMachine = " + nMachine);
                    for(int m=0;m<nMachine;m++){
                        sb.append(" (");
                        int machine = dataMachine[j][p][m];
                        int time = dataTime[j][p][m];
                        sb.append(machine+","+time);                        
                        sb.append(")");
                    }
                    sb.append("]");
                }
                sb.append("\n");
            }
            sb.append("---------------------------------------------------------\n");
            result = sb.toString();
        }
        return result;
    }

    public int[][][] getDataMachine() {
        return dataMachine;
    }

    public int[][][] getDataTime() {
        return dataTime;
    }

    public int getMAX_MACHINE() {
        return MAX_MACHINE;
    }
    
}
