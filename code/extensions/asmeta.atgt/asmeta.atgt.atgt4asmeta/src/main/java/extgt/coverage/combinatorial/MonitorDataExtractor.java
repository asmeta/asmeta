package extgt.coverage.combinatorial;

import tgtlib.specification.Specification;
import tgtlib.specification.SpecificationAnalyzer;

/** must return teh monitored data from a spec
 * 
 * @author garganti
 *
 * @param <S>
 */
public interface MonitorDataExtractor<S extends Specification> extends SpecificationAnalyzer<MonitoredData,S> {

}