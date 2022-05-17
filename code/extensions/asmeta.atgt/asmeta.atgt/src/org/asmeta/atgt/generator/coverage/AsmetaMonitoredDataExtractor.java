package org.asmeta.atgt.generator.coverage;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import extgt.coverage.combinatorial.MonitorDataExtractor;
import extgt.coverage.combinatorial.MonitoredData;
import tgtlib.definitions.TypedInitExpression;

/**
 * given a specification extracts the monitored data. is a Specification Visitor
 * which returns a MonitoredData instance
 * 
 * standard: only finite domains of type EnumType
 */
public class AsmetaMonitoredDataExtractor implements MonitorDataExtractor<AsmetaAsSpec>{

	
	static private AsmetaMonitoredDataExtractor INSTANCE =  new AsmetaMonitoredDataExtractor();
	
	public static AsmetaMonitoredDataExtractor getMonitoredDataExtractor() {
		return INSTANCE;
	}

	public static void setMonitoredDataExtractor(AsmetaMonitoredDataExtractor mde) {
		INSTANCE = mde;
	}
	
	
	protected AsmetaMonitoredDataExtractor(){}
	
	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(AsmetaMonitoredDataExtractor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.SpecificationAnalyzer#analyze(atgt.specification.ASMSpecification)
	 */
	
	/**
	 * returns the monitored data for the SP. It takes into account only boolean and enumerative. Other types are ignored.
	 * 
	 * @param SP
	 *            the sP
	 * 
	 * @return the monitored data
	 */
	@Override
	public MonitoredData analyze(AsmetaAsSpec SP) {
		// 
		MonitoredData result = new MonitoredData();
		// take the monitored variables
		EList<Function> functions = SP.asm.getHeaderSection().getSignature().getFunction();
		for (Function f: functions) {
			if (f instanceof ControlledFunction){
				logger.info("ignoring variable " + f.getName() +" because is controlled"); //$NON-NLS-1$
				continue;
			}
			if (!(f instanceof ControlledFunction)){
				logger.info("ignoring variable " + f.getName() +" because is not monitored"); //$NON-NLS-1$
				continue;
			}
			// process var
			if (f.getDomain() != null ){
				logger.info("ignoring variable " + f.getName() +" because ha arity not 0 "); //$NON-NLS-1$
				continue;
			}
			Domain type = f.getDomain();
			if (! (type instanceof EnumTd || type instanceof BooleanDomain)) {
				logger.info("ignoring variable " + f.getName() +" because of type " + type); //$NON-NLS-1$
				continue;
			}
			logger.info("adding " + f.getName() +" of type " + type); //$NON-NLS-1$
			// TODO FIX
			result.add(new TypedInitExpression(null, null));
		}
		return result;
	}
}