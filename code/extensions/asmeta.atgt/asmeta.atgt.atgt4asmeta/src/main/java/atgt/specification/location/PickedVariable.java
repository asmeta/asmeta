package atgt.specification.location;



import atgt.specification.type.DummyType;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.Type;

// to be used in pick
public class PickedVariable extends atgt.specification.location.Variable{
	
	static DummyType typeForChosenVar = new DummyType("for chosen var");
	
	static IdExpressionCreator icc = new IdExpressionCreator();
	
	private String name;
	private String inRuleDecl;

	public PickedVariable(String name, String inRuleDecl) {
		super(icc.createIdExpression(name, typeForChosenVar),null);
		this.name = name;
		this.inRuleDecl = inRuleDecl;
	}

	@Override
	public boolean isControlled() {
		return false;
	}

	@Override
	public boolean isMonitored() {
		return false;
	}

	
	@Override
	public Expression getValue() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IdExpression getIdExpression() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Type getType() {
		// the type is unkwon, it should be not necessary
		return null;
	}

	public String getInRuleDecl() {
		return inRuleDecl;
	}
	
}