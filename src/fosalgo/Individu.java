package fosalgo;

import java.util.Arrays;
import java.util.Random;

public final class Individu {

    //data
    private int[][][] dataMachine = null;
    private int[][][] dataTime = null;

    //fields
    private int[] chromosome = null;
    private int[][] selectedMachine = null;
    private int nJob = 0;
    private int size = 0;//chromosome size (panjang kromosom)

    //field fjs
    private int[][] indexJobProcessMachine = null;
    private int MAX_MACHINE = 0;

    //OUTPUT
    int totalDuration = 0;
    double fitness = 0;

    public Individu(int[][][] dataMachine, int[][][] dataTime, int MAX_MACHINE) {
        if (dataMachine != null && dataTime != null && dataMachine.length == dataTime.length) {
            this.dataMachine = dataMachine;
            this.dataTime = dataTime;
            this.nJob = dataMachine.length;
            this.MAX_MACHINE = MAX_MACHINE;
            //set selected machine
            //this.setDefaultSelectedMachine();
            this.randomSelectedMachine();
            this.generateRandomChromosome();
        }
    }

    public Individu() {
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public double getFitness() {
        return fitness;
    }

    public Individu clone() {
        Individu cloneIndividu = null;
        if (dataMachine != null && dataTime != null && selectedMachine != null && chromosome != null) {
            cloneIndividu = new Individu();
            cloneIndividu.dataMachine = dataMachine;
            cloneIndividu.dataTime = dataTime;
            cloneIndividu.selectedMachine = selectedMachine;
            
            //clone chromosome
            cloneIndividu.chromosome = new int[chromosome.length];
            cloneIndividu.indexJobProcessMachine = new int[3][chromosome.length];
            for (int k = 0; k < chromosome.length; k++) {
                cloneIndividu.chromosome[k] = chromosome[k];
                cloneIndividu.indexJobProcessMachine[0][k]=indexJobProcessMachine[0][k];
                cloneIndividu.indexJobProcessMachine[1][k]=indexJobProcessMachine[1][k];
                cloneIndividu.indexJobProcessMachine[2][k]=indexJobProcessMachine[2][k];
            }

            cloneIndividu.nJob = nJob;
            cloneIndividu.size = size;
            cloneIndividu.MAX_MACHINE = MAX_MACHINE;
            cloneIndividu.totalDuration = totalDuration;
            cloneIndividu.fitness = fitness;
        }
        return cloneIndividu;
    }

    public int compute() {
        int MAX_DURATION = -1;
        if (dataMachine != null && dataTime != null && selectedMachine != null && chromosome != null) {
            MAX_DURATION = 0;
            int[] endTimeMachine = new int[MAX_MACHINE];
            int[] endTimeJob = new int[nJob];

            //set indexJobProcessMachine
            setIndexBaseOnChromosome();

            //memulai penelusuran semua gen-gen di chrmosome
            for (int k = 0; k < size; k++) {
                int job = indexJobProcessMachine[0][k];//index job  = baris
                int proc = indexJobProcessMachine[1][k];//index process = kolom
                int mac = indexJobProcessMachine[2][k];//index machine = layer 

                int machine = dataMachine[job][proc][mac];//mesin
                int duration = dataTime[job][proc][mac];//durasi mesin

                //masukkan mesin dan durasinya ke dalam gantt diagram
                int etMac = endTimeMachine[machine];//end time untuk mesih
                int etJob = endTimeJob[job];//end time untuk job
                int start = Math.max(etMac, etJob);
                int finish = start + duration;

                //nanti ditambahkan bagian untuk visualisasi gantt diagram
                endTimeMachine[machine] = finish;
                endTimeJob[job] = finish;

                if (finish > MAX_DURATION) {
                    MAX_DURATION = finish;
                }

            }//end of for(int i=0;i<size;i++)

            //compute output
            totalDuration = MAX_DURATION;
            fitness = 1.0 / totalDuration;
        }
        return MAX_DURATION;
    }

    public int[][] setIndexBaseOnChromosome() {
        indexJobProcessMachine = null;
        if (dataMachine != null && dataTime != null && selectedMachine != null && dataMachine.length == dataTime.length && nJob > 0 && size > 0 && chromosome != null) {
            indexJobProcessMachine = new int[3][size];
            int[] maxProc = new int[nJob];
            for (int k = 0; k < size; k++) {
                int job = chromosome[k];
                int proc = maxProc[job];
                int mac = selectedMachine[job][proc];

                //set index
                indexJobProcessMachine[0][k] = job;//[index job]
                indexJobProcessMachine[1][k] = proc;//[index process]
                indexJobProcessMachine[2][k] = mac;//[index machine]

                proc++;
                maxProc[job] = proc;
            }
        }
        return indexJobProcessMachine;
    }

    public void generateRandomChromosome() {
        if (dataMachine != null && dataTime != null && selectedMachine != null && dataMachine.length == dataTime.length && nJob > 0 && size > 0) {
            this.chromosome = new int[size];
            indexJobProcessMachine = new int[3][size];//[index job][index process][index machine]
            int[] maxProc = new int[nJob];

            int k = 0;
            while (k < size) {
                int job = randomBetweenInteger(0, nJob - 1);//random job
                int proc = maxProc[job];
                int maxProcessInThisJob = dataMachine[job].length;//jumlah proses maksimum yang tersedia di job ini
                if (proc < maxProcessInThisJob) {
                    this.chromosome[k] = job;

                    //increment
                    proc++;
                    maxProc[job] = proc;
                    k++;
                }
            }
        }
    }

    public int[][] setDefaultSelectedMachine() {
        this.selectedMachine = null;
        if (dataMachine != null && dataTime != null && dataMachine.length == dataTime.length && nJob > 0) {
            this.selectedMachine = new int[nJob][];
            size = 0;
            for (int j = 0; j < nJob; j++) {
                int nProcess = dataMachine[j].length;
                this.selectedMachine[j] = new int[nProcess];
                for (int p = 0; p < nProcess; p++) {
                    int value = 0;//default value
                    this.selectedMachine[j][p] = value;
                    size++;
                }
            }
        }
        return this.selectedMachine;
    }

    public int[][] randomSelectedMachine() {
        this.selectedMachine = null;
        if (dataMachine != null && dataTime != null && dataMachine.length == dataTime.length && nJob > 0) {
            this.selectedMachine = new int[nJob][];
            size = 0;
            for (int j = 0; j < nJob; j++) {
                int nProcess = dataMachine[j].length;
                this.selectedMachine[j] = new int[nProcess];
                for (int p = 0; p < nProcess; p++) {
                    int nMac = dataMachine[j][p].length;
                    int value = randomBetweenInteger(0, nMac - 1);//random value
                    this.selectedMachine[j][p] = value;
                    size++;
                }
            }
        }
        return this.selectedMachine;
    }

    private int randomBetweenInteger(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        Random r = new Random();
        int value = r.nextInt((max - min) + 1) + min;
        return value;
    }

    public String getStringJobProcessMachine() {
        String result = "NULL";
        setIndexBaseOnChromosome();
        if (indexJobProcessMachine != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("--------------------------------------------------------------------------------------\n");
            sb.append(Arrays.toString(indexJobProcessMachine[0]) + "\n");
            sb.append(Arrays.toString(indexJobProcessMachine[1]) + "\n");
            sb.append(Arrays.toString(indexJobProcessMachine[2]) + "\n");
            sb.append("--------------------------------------------------------------------------------------");
            result = sb.toString();
        }
        return result;
    }

    public String toString() {
        String result = "NULL";
        if (chromosome != null) {
            result = Arrays.toString(chromosome);
            result += ("\nduration = " + totalDuration);
            result += ("\nfitness  = " + fitness);
        }
        return result;
    }

}//end of class Individu
