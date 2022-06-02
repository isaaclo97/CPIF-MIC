package es.urjc.etsii.grafo.CPIF.constructives;

import es.urjc.etsii.grafo.CPIF.model.CPIFInstance;
import es.urjc.etsii.grafo.CPIF.model.CPIFSolution;
import es.urjc.etsii.grafo.CPIF.model.Pair;
import es.urjc.etsii.grafo.solver.create.Constructive;
import es.urjc.etsii.grafo.util.random.RandomManager;

import java.util.*;

public class FixedSetSearch extends Constructive<CPIFSolution, CPIFInstance> {


    @Override
    public CPIFSolution construct(CPIFSolution solution) {
        int n=10, iters = 20;
        int sizeSet = (int)(solution.getInstance().getFacilities()*0.5);
        int itersWithoutImprovement = 0;
        int iterslimit = 10;
        CPIFSolution bestSolution = new CPIFSolution(solution);
        ArrayList<CPIFSolution> P = new ArrayList<>();
        HashMap<Integer, Integer> counter = new HashMap<>();
        PriorityQueue<Pair> pQueue = new PriorityQueue<Pair>();
        HashMap<Integer,Integer> bestSolutionsSelecteds = new HashMap<>();

        bestSolution = randomSolutions(n, solution, bestSolution, P, pQueue, bestSolutionsSelecteds);

        if(bestSolution.getMark()==0){
            return bestSolution;
        }
        var rand = RandomManager.getRandom();
        int newID = n;

        for(int it = 0; it<iters;it++) {
            //Line 4
            int rndSolution = rand.nextInt(newID-1); //seleccionar una solucion de las buenas
            while(bestSolutionsSelecteds.get(rndSolution)== null || bestSolutionsSelecteds.get(rndSolution)!=1){
                rndSolution = rand.nextInt(newID-1);
            }
            CPIFSolution S = P.get(rndSolution);
            //Line 5
            RND(n, P, counter, rand);
            clearSolutions(solution);
            ArrayList<Pair> elements = orderCounters(counter);
            solution.addToSolution(0);
            //Line 6
            FIX(solution, sizeSet, S, elements);
            //Line 7
            solution.RGF(1);
            if(solution.getS().size()!=solution.getInstance().getFacilities()){
                continue;
            }
            bestSolution = bestSolution(solution, bestSolution);
            if(bestSolution.getMark()==0){
                bestSolution.printSelectedNodes();
                return bestSolution;
            }
            //Line 8 to Line 11
            double mark = solution.getMark();
            if(pQueue.peek().getValue()>mark){
                Pair res = pQueue.poll();
                bestSolutionsSelecteds.put(res.getNode(),0);
                bestSolutionsSelecteds.put(newID,1);
                pQueue.add(new Pair(newID,(int)mark));
                itersWithoutImprovement=0;
            }
            else{
                itersWithoutImprovement++;
            }
            P.add(solution);
            newID++;
        }
        bestSolution.printSelectedNodes();
        return bestSolution;
    }

    private void FIX(CPIFSolution solution, int sizeSet, CPIFSolution random_solution_generated, ArrayList<Pair> elements) {
        for(int j = 1; j< sizeSet; j++) {
            for (int i = 0; i < elements.size(); i++) {
                int possible_node = elements.get(elements.size() - i - 1).getNode();
                if(random_solution_generated.getS().contains(possible_node)) {
                    for (int s : solution.getS()) {
                        if (solution.getInstance().getGraph()[s][possible_node] <= (solution.getInstance().getDistance())) {
                            solution.addToSolution(possible_node);
                            break;
                        }
                    }
                }
            }
        }
    }


    private ArrayList<Pair> orderCounters(HashMap<Integer, Integer> counter) {
        ArrayList<Pair> elements = new ArrayList<Pair>();
        for (int elem : counter.keySet()) {
            elements.add(new Pair(elem, counter.get(elem)));
        }
        Collections.sort(elements);
        return elements;
    }

    private void clearSolutions(CPIFSolution solution) {
        HashSet<Integer> sols = new HashSet<>(solution.getS());
        for(int s:sols)
            solution.removeToSolution(s);
    }

    private void RND(int n, ArrayList<CPIFSolution> solution_set, HashMap<Integer, Integer> counter, java.util.random.RandomGenerator rand) {
        int rndSolution;
        counter.clear();
        HashSet<Integer> selectedS = new HashSet<>();
        for(int i=0; i<n/2;i++) {
            rndSolution = rand.nextInt(n);
            while(selectedS.contains(rndSolution))
                rndSolution = rand.nextInt(n);
            selectedS.add(rndSolution);

            //Counter
            for (int node : solution_set.get(rndSolution).getS()) {
                int res = counter.getOrDefault(node, 0);
                counter.put(node, res + 1);
            }
            //Counter
        }
    }

    private CPIFSolution randomSolutions(int n, CPIFSolution solution, CPIFSolution bestSolution, ArrayList<CPIFSolution> solution_set, PriorityQueue<Pair> pQueue, HashMap<Integer,Integer> hm) {
        for (int i = 0; i < n; i++) {
            CPIFSolution new_solution = solution.cloneSolution();
            clearSolutions(new_solution);
            generateRandomSolution(new_solution);
            pQueue.add(new Pair(i, new_solution.getMark()));
            hm.put(i,1);
            bestSolution = bestSolution(new_solution, bestSolution);
            if(bestSolution.getMark()==0){
                return bestSolution;
            }
            solution_set.add(new_solution);
        }
        return bestSolution;
    }

    private CPIFSolution bestSolution(CPIFSolution solution, CPIFSolution bestSolution) {
        double bestMark = bestSolution.getMark();
        double currentMark = solution.getMark();
        if(Double.compare(bestMark,currentMark)>0){
            solution.updateLastModifiedTime();
            bestSolution = solution.cloneSolution();
        }
        return bestSolution;
    }


    private void generateRandomSolution(CPIFSolution solution) {
        solution.getS().clear();
        solution.addToSolution(0);
        while(solution.getS().size()!=solution.getInstance().getFacilities()) {
            int newNode = (int) (Math.random() * (solution.getInstance().getNodes() - 1) + 1);
            while (!solution.isFeasible(newNode)) {
                newNode = (int) (Math.random() * (solution.getInstance().getNodes() - 1) + 1);
            }
            solution.addToSolution(newNode);
        }
    }

    @Override
    public String toString() {
        return "FSS{}";
    }
}
