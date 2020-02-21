package transformador;

import japa.parser.ast.Node;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.visitor.ModifierVisitorAdapter;

public class Visitador_2 extends ModifierVisitorAdapter<Object>
{
	// Reemplaza la condicion del while por true
	public Node visit(WhileStmt whileStmt, Object args)
	{
		//creamos la expression con true
		BooleanLiteralExpr condition = new BooleanLiteralExpr();
		condition.setValue(true);
		
		//Cambiamos la condicion a true
		whileStmt.setCondition(condition);
		
		return super.visit(whileStmt, args);
	}
}