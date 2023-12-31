package org.asmeta.atgt.generator;

import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.coverage.AsmetaAsSpec;

import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import extgt.coverage.combinatorial.MonitorDataExtractor;
import extgt.coverage.combinatorial.MonitoredData;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.Type;

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
		for (Enumeration<Variable> e = SP.allVariables(); e.hasMoreElements();) {
			Variable var = e.nextElement();
			if (var.isControlled()){
				logger.info("ignoring variable " + var.getName() +" because is controlled"); //$NON-NLS-1$
				continue;
			}
			if (!var.isMonitored()){
				logger.info("ignoring variable " + var.getName() +" because is not monitored"); //$NON-NLS-1$
				continue;
			}
			// process var
			Type type = var.getType();
			if (! (type instanceof ElementsType)) {
				logger.info("ignoring variable " + var.getName() +" because of type " + type); //$NON-NLS-1$
				continue;
			}
			assert (type instanceof ElementsType);
			logger.info("adding " + var.getName() +" of type " + type); //$NON-NLS-1$
			result.add(var);
		}
		return result;
	}
}
