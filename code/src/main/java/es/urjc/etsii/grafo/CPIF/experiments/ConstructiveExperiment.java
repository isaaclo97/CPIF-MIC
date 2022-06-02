package es.urjc.etsii.grafo.CPIF.experiments;

import es.urjc.etsii.grafo.CPIF.constructives.FixedSetSearch;
import es.urjc.etsii.grafo.CPIF.model.CPIFInstance;
import es.urjc.etsii.grafo.CPIF.model.CPIFSolution;
import es.urjc.etsii.grafo.solver.SolverConfig;
import es.urjc.etsii.grafo.solver.algorithms.Algorithm;
import es.urjc.etsii.grafo.solver.algorithms.SimpleAlgorithm;
import es.urjc.etsii.grafo.solver.services.AbstractExperiment;

import java.util.ArrayList;
import java.util.List;

public class ConstructiveExperiment extends AbstractExperiment<CPIFSolution, CPIFInstance> {

    public ConstructiveExperiment(SolverConfig solverConfig) {
        super(solverConfig);
    }

    @Override
    public List<Algorithm<CPIFSolution, CPIFInstance>> getAlgorithms() {
        // In this experiment we will compare a random constructive with several GRASP constructive configurations
        var algorithms = new ArrayList<Algorithm<CPIFSolution, CPIFInstance>>();
        algorithms.add(new SimpleAlgorithm<>("FixedSetSearch", new FixedSetSearch()));

        return algorithms;
    }

}
