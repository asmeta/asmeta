package asmeta.evotest.junit2avalla.javascenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class InitializerVisitor extends VoidVisitorAdapter<Context> {
    
	/* Constants */
	
	
	/** Logger */
	private final Logger logger = LogManager.getLogger(InitializerVisitor.class);
		
	@Override
    public void visit(ObjectCreationExpr oce, Context context) {
        logger.info("CONSTRUCTOR: {}.", oce);
        context.getCurrentJavaVariable().setValue(oce.getArgument(0).toString());
    }
    
    @Override
    public void visit(FieldAccessExpr fae, Context context) {
        logger.info("VALUE FIELD NAME: {}.", fae);
    }
    
    @Override
    public void visit(NameExpr ne, Context context) {
        logger.info("VALUE NAME: {}.", ne);
    }
    
    @Override
    public void visit(MethodCallExpr mce, Context context) {
        logger.info("VALUE METHOD CALL: {}.", mce);
    }

	@Override
	public void visit(BooleanLiteralExpr ble, Context context) {
		logger.debug("VALUE BOOLEAN: {}.", ble);
        context.getCurrentJavaVariable().setValue(Boolean.toString(ble.getValue()));
        context.getCurrentJavaVariable().setPrimitive(true);
	}

	@Override
	public void visit(CharLiteralExpr cle, Context context) {
		logger.debug("VALUE CHAR: {}.", cle);
        context.getCurrentJavaVariable().setValue(cle.getValue());
        context.getCurrentJavaVariable().setPrimitive(true);
	}

	@Override
	public void visit(DoubleLiteralExpr dle, Context context) {
		logger.debug("VALUE DOUBLE: {}.", dle);
        context.getCurrentJavaVariable().setValue(dle.getValue());
        context.getCurrentJavaVariable().setPrimitive(true);
	}

	@Override
	public void visit(IntegerLiteralExpr ile, Context context) {
        logger.debug("VALUE INTEGER: {}.", ile);
        context.getCurrentJavaVariable().setValue(ile.getValue());
        context.getCurrentJavaVariable().setPrimitive(true);
	}

	@Override
	public void visit(LongLiteralExpr lle, Context context) {
        logger.debug("VALUE LONG: {}.", lle);
        context.getCurrentJavaVariable().setValue(lle.getValue());
        context.getCurrentJavaVariable().setPrimitive(true);
	}

	@Override
	public void visit(StringLiteralExpr sle, Context context) {
        logger.debug("VALUE STRING: {}.", sle);
        context.getCurrentJavaVariable().setValue(sle.getValue());
        context.getCurrentJavaVariable().setPrimitive(true);
	}
    
}
