package es.urjc.etsii.grafo.CPIF.model;

import es.urjc.etsii.grafo.io.Instance;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CPIFInstance extends Instance {

    private String name;
    private int nodes,edges,facilities=-1,distance,radio;
    private int graph[][];

    public CPIFInstance(String name){
        super(name);
        readInstance("instances/"+name);
    }

    public void readInstance(String path) {
        this.name = path.substring(path.lastIndexOf('\\') + 1);
        FileReader fr= null;
        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);

        // read line by line
        String line;
        try{
            line = br.readLine();
            String[] l = line.split(" ");
            nodes = Integer.parseInt(l[1]);
            edges = Integer.parseInt(l[2]);
            facilities = Integer.parseInt(l[3]);
            distance = Integer.parseInt(l[4]);
            radio = Integer.parseInt(l[5]);
            graph = new int[nodes][nodes];
            //System.out.println(facilities);
            for(int i=0; i<nodes;i++){
                Arrays.fill(graph[i],0x3f3f3f3f);
            }

            while ((line = br.readLine()) != null) {
                l = line.split(" ");
                int start = Integer.parseInt(l[1])-1;
                int end = Integer.parseInt(l[2])-1;
                int cost = Integer.parseInt(l[3]);
                graph[start][end]=cost;
                graph[end][start]=cost;
            }
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        FloydWarshall();
    }

    public void FloydWarshall(){
        for (int k = 0; k < nodes; k++)
            for (int i = 0; i < nodes; i++)
                for (int j = 0; j < nodes; j++)
                    graph[i][j]=Math.min(graph[i][j],graph[i][k]+graph[k][j]);
    }

    public String getName() {
        return name;
    }

    public int getNodes() {
        return nodes;
    }

    public int getFacilities() {
        return facilities;
    }

    public int[][] getGraph() {
        return graph;
    }

    public int getDistance(){
        if(distance==0){
            System.out.println("Error in distance");
        }
        return distance;
    }

    public int getRadio() {
        return radio;
    }

    // compareTo may be overriden to specify a custom instance solving order (default ordering by instance file name)
}
