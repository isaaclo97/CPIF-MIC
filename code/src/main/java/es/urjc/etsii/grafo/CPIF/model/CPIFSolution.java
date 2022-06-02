package es.urjc.etsii.grafo.CPIF.model;

import es.urjc.etsii.grafo.solution.Solution;
import es.urjc.etsii.grafo.util.random.RandomManager;

import java.util.*;

public class CPIFSolution extends Solution<CPIFSolution, CPIFInstance> {

    /**
     * Initialize solution from instance
     *
     * @param ins
     */
    private CPIFInstance instance;
    private int mark;
    private HashSet<Integer> S;
    private HashSet<Integer> remaining;

    boolean visited[];
    private boolean update = true;
    private HashSet<Integer> coverage;
    HashSet<Integer> newNodes = new HashSet<>();

    public CPIFSolution(CPIFInstance ins) {
        super(ins);
        S = new HashSet<>();
        coverage = new HashSet<>();
        instance = ins;
        remaining = new HashSet<>();
        for(int i=1; i<instance.getNodes();i++)
            remaining.add(i);
    }

    /**
     * Clone constructor
     *
     * @param solution Solution to clone
     */
    public CPIFSolution(CPIFSolution solution) {
        super(solution);

        this.instance = solution.getInstance();
        visited = new boolean[instance.getFacilities()];
        this.instance = solution.getInstance();
        this.S = new HashSet<>(solution.getS());
        this.coverage = new HashSet<>(solution.getCoverage());
        this.mark = solution.getMark();
        this.update = solution.isUpdate();
        this.visited = new boolean[instance.getFacilities()];
        this.remaining = new HashSet<>(solution.getRemaining());
    }

    @Override
    public CPIFSolution cloneSolution() {
        // You do not need to modify this method
        // Call clone constructor
        return new CPIFSolution(this);
    }

    @Override
    protected boolean _isBetterThan(CPIFSolution other) {
        return this.getScore() < other.getScore();
    }

    /**
     * Get the current solution score.
     * The difference between this method and recalculateScore is that
     * this result can be a property of the solution, or cached,
     * it does not have to be calculated each time this method is called
     *
     * @return current solution score as double
     */
    @Override
    public double getScore() {
        if(update) {
            update = false;
            this.mark = (int)recalculateScore();
        }
        return this.mark;
    }

    /**
     * Recalculate solution score and validate current solution state
     * You must check that no constraints are broken, and that all costs are valid
     * The difference between this method and getScore is that we must recalculate the score from scratch,
     * without using any cache/shortcuts.
     * DO NOT UPDATE CACHES / MAKE SURE THIS METHOD DOES NOT HAVE SIDE EFFECTS
     *
     * @return current solution score as double
     */
    @Override
    public double recalculateScore() {
        return this.mark = evaluateCoverage();
    }

    private int evaluateCoverage() {
        coverage.clear();
        for (Integer i: remaining) {
            int minDistance = 0x3f3f3f;
            for (int selected : S) {
                minDistance = Math.min(minDistance, instance.getGraph()[i][selected]);
                if(minDistance<=this.instance.getRadio()) {
                    coverage.add(i);
                    break;
                }
            }
        }
        return this.mark = this.instance.getNodes()-coverage.size()-S.size();
    }

    private int evaluateCoverageNode(int node) {
        newNodes.clear();
        for (Integer i: remaining) {
            if (i == node) continue;
            int minDistance = 0x3f3f3f;
            minDistance = Math.min(minDistance, instance.getGraph()[i][node]);
            if(minDistance<=this.instance.getRadio()) {
                if(!coverage.contains(i)) newNodes.add(i);
            }
        }
        int addOrCoverage = 1;
        if(coverage.contains(node)|| S.contains(node)) addOrCoverage = 0;
        return this.instance.getNodes()-coverage.size() - newNodes.size() - addOrCoverage - S.size();
    }

    private void addCoverageNode(int node) {
        for (Integer i: remaining) {
            if (i == node) continue;
            int minDistance = 0x3f3f3f;
            minDistance = Math.min(minDistance, instance.getGraph()[i][node]);
            if(minDistance<=this.instance.getRadio()) {
                coverage.add(i);
            }
        }
        coverage.remove(node);
        this.mark = this.instance.getNodes()-coverage.size()-S.size();
        this.update = false;
    }

    public boolean isFeasible(int add){
        if(add == 0 || isInSolution(add) || getS().size()>instance.getFacilities()) return false;
        addToSolution(add);
        int totalVisited = 1;
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        boolean visited[] = new boolean[getInstance().getFacilities()];
        int nodes[] = new int[getInstance().getFacilities()];
        int index = 0;
        for(int s:getS()){
            nodes[index]=s;
            if(s==0){
                visited[index]=true;
            }
            index++;
        }
        while(!q.isEmpty()){
            int curNode = q.poll();
            for(int i=0; i<getInstance().getFacilities(); i++){
                if(visited[i]) continue;
                if(getInstance().getGraph()[curNode][nodes[i]]<=getInstance().getDistance()){
                    visited[i]=true;
                    q.add(nodes[i]);
                    totalVisited++;
                }
            }
        }
        if(totalVisited != getInstance().getFacilities()) {
            removeToSolution(add);
            return false;
        }
        removeToSolution(add);
        return true;
    }

    /**
     * Generate a string representation of this solution. Used when printing progress to console,
     * show as minimal info as possible
     *
     * @return Small string representing the current solution (Example: id + score)
     */
    @Override
    public String toString() {
        return "CPIFSolution{" +
                "instance=" + instance.getName() +
                ", mark=" + mark +
                ", S=" + S +
                '}';
    }

    public int getMark() {
        if(this.update){
            evaluateCoverage();
            this.update = false;
        }
        return this.mark;
    }

    public HashSet<Integer> getS() {
        return S;
    }

    public void addToSolution(int node){
        S.add(node); this.update=true;
        remaining.remove(node);
    }
    public void removeToSolution(int node){
        S.remove(node); this.update=true;
        remaining.add(node);
    }
    public boolean isInSolution(int node){
        return S.contains(node);
    }

    public boolean isUpdate() {
        return update;
    }


    public void printSelectedNodes(){
        ArrayList<Integer> arr = new ArrayList<>();
        for(Integer s:S){
            arr.add(s);
        }
        Collections.sort(arr);
        for(Integer s:arr){
            System.out.print(s + " - ");
        }
        System.out.println();
    }
    public HashSet<Integer> getRemaining() {
        return remaining;
    }

    public void RGF(double factor){
        this.getMark();
        var rand = RandomManager.getRandom();
        int totalToAdd = this.getInstance().getFacilities() - this.getS().size();
        HashSet<Integer> analizedNodes = new HashSet<>();
        ArrayList<Integer> candidates = new ArrayList<>();
        for(int i=0; i<totalToAdd;i++) {
            int curValue = 0x3f3f3f3f;
            int selectedNode = -1;
            for(int j=1; j<this.getInstance().getNodes();j++){
                if(this.isInSolution(j)) continue;
                if(!analizedNodes.contains(j)) {
                    boolean possible = false;
                    for (int s : this.getS()) {
                        if (this.getInstance().getGraph()[s][j] <= (this.getInstance().getDistance() * factor)) {
                            possible = true;
                            analizedNodes.add(j);
                            break;
                        }
                    }
                    if(!possible) continue;
                }
                int valueAux = evaluateCoverageNode(j);
                if(curValue>valueAux){
                    curValue = valueAux;
                    selectedNode = j;
                    candidates.clear();
                    candidates.add(selectedNode);
                }
                else if(curValue==valueAux){
                    candidates.add(j);
                }
            }
            int rndElement = rand.nextInt(candidates.size());
            selectedNode = candidates.get(rndElement);
            this.addToSolution(selectedNode);
            addCoverageNode(selectedNode);
        }
    }


    public HashSet<Integer> getCoverage() {
        return coverage;
    }

}
