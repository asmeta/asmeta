package tgtlib.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequenceFactory;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.definitions.normalform.cnf.CNFExprConverter;
import tgtlib.definitions.normalform.cnf.CNFExprConverter.CNFExprConverterFactory;
import tgtlib.definitions.normalform.cnf.CNFExpression;
import tgtlib.definitions.normalform.cnf.Dimacs;
import tgtlib.specification.Axiom;

/** SAT SOLVERS USING CNF */
public abstract class SatCNFTestGenerator<Q extends TestPredicate<? extends T,?>, T extends tgtlib.definitions.TestSequence<? extends Q>> extends SatTestGeneratorCLI<Q, T> {

	private static final Logger logger = Logger.getLogger(SatCNFTestGenerator.class);

	private static final boolean COMPLETETESTRND = true;
	
	// get a real random
	private static final Random rnd = new Random();

	
	protected CNFExprConverterFactory cnfConverterFactory;
	

	protected SatCNFTestGenerator(TestSequenceFactory<T, ? super Q> q, CNFExprConverterFactory cnfConverterFactory, List<? extends Variable> variables, Collection<Axiom> constraints) {
		super(q,variables,constraints);
		this.cnfConverterFactory = cnfConverterFactory;
	}

	/**
	 * return the variable for a given id
	 * @param id
	 * @return
	 */
	protected Variable getVar(IdExpression id) {
		for(Variable var: inputs) {
			if(var.getIdExpression().equals(id)) {
				return var;
			}
		}
		//since some variables could be introduced by Tseitin,
		//it could return null
		return null;
	}

	/** build the dimacs including the constraints
	 */
	protected Dimacs getDimacs(Q tp) {
		assert tp != null;
		assert tp.getCondition() != null;		
		Expression condition = extractExpression(tp);
		logger.debug("converting expression to dimacs "+ condition);
		CNFExprConverter cnfConverter = cnfConverterFactory.getCNFExprConverter();
		CNFExpression cnf = cnfConverter.getCNF(condition);
		Dimacs dimacs = cnf.toDimacs(inputs);
		return dimacs;
	}

	protected void addAssignment(T tp, IdExpression id, String boolvalue, List<Variable> varsInModel) {
		Variable var = getVar(id);
		//if var is null, it means that it is a variable introduced by Tseitin
		if(var != null) { 
			varsInModel.add(var);
			//tp.addAssignment(id.toString(), lit>0 ? BoolType.TRUE_STR:BoolType.FALSE_STR);
			tp.addAssignment(var, boolvalue);
		}
	}
	
	protected  void completeAssignemts(T tp, List<Variable> varsInModel){
		if (COMPLETETESTRND) {
			List<Variable> allVars = new ArrayList<>(inputs);
			allVars.removeAll(varsInModel);
			for (Variable var: allVars) {
				//tp.addAssignment(id.getIdString(),Boolean.toString(rnd.nextBoolean()));
				tp.addAssignment(var, Boolean.toString(rnd.nextBoolean()));
			}
		}
	}

	//abstract Iterator<Pair<IdExpression, BoolType>> readModel();	
}
