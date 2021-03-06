package transformador;

import iter2rec.transformation.loop.Do;
import iter2rec.transformation.loop.For;
import iter2rec.transformation.loop.Loop;
import iter2rec.transformation.loop.While;
import iter2rec.transformation.variable.LoopVariables;
import iter2rec.transformation.variable.Variable;
import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.*;
import japa.parser.ast.stmt.*;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.visitor.ModifierVisitorAdapter;

import java.util.LinkedList;
import java.util.List;

public class Visitador extends ModifierVisitorAdapter<Object>
{
	/********************************************************/
	/********************** Atributos ***********************/
	/********************************************************/
	
	// Usamos un contador para numerar los m�todos que creemos
	int contador=1;
	String nameMethod = "method_loop_";
	// Variable usada para conocer la lista de m�todos visitados
	LinkedList<MethodDeclaration> previousMethodDeclarations = new LinkedList<MethodDeclaration>();
	// Variable usada para saber cu�l es el �ltimo m�todo visitado (el que estoy visitando ahora)
	MethodDeclaration methodDeclaration;
	// Variable usada para conocer la lista de clases visitadas
	LinkedList<ClassOrInterfaceDeclaration> previousClassDeclarations = new LinkedList<ClassOrInterfaceDeclaration>();
	// Variable usada para saber cu�l es la �ltima clase visitada (la que estoy visitando ahora)	
	ClassOrInterfaceDeclaration classDeclaration;

	/********************************************************/
	/*********************** Metodos ************************/
	/********************************************************/

	// Visitador de clases
	// Este visitador no hace nada, simplemente registra en una lista las clases que se van visitando
	public Node visit(ClassOrInterfaceDeclaration classDeclaration, Object args)
	{
		this.previousClassDeclarations.add(classDeclaration);
		this.classDeclaration = classDeclaration;
		Node newClassDeclaration = super.visit(classDeclaration, args);
		this.previousClassDeclarations.removeLast();
		this.classDeclaration = this.previousClassDeclarations.isEmpty() ? null : this.previousClassDeclarations.getLast();
		
		return newClassDeclaration;
	}
	// Visitador de m�todos
	// Este visitador no hace nada, simplemente registra en una lista los m�etodos que se van visitando	
	public Node visit(MethodDeclaration methodDeclaration, Object args)
	{
		this.previousMethodDeclarations.add(methodDeclaration);
		this.methodDeclaration = methodDeclaration;
		Node newMethodDeclaration = super.visit(methodDeclaration, args);
		this.previousMethodDeclarations.removeLast();
		this.methodDeclaration = this.previousMethodDeclarations.isEmpty() ? null : this.previousMethodDeclarations.getLast();

		return newMethodDeclaration;
	}
	
	// Visitador de sentencias "while"
	public Node visit(WhileStmt whileStmt, Object args)
	{
		return loopToIf(whileStmt, args);
	}

	// Visitador de sentencias "do while"
	public Node visit(DoStmt doStmt, Object args){

		BlockStmt blockStmt = new BlockStmt();
		List<Statement> statements = new LinkedList<Statement>();
		statements.add(doStmt.getBody());
		statements.add(loopToIf(doStmt,args));

		blockStmt.setStmts(statements);

		return blockStmt;
	}

	// Visitador de sentencias "for"
	@Override
	public Node visit(ForStmt forStmt, Object args){

		BlockStmt blockStmt = new BlockStmt();
		List<Statement> statements = new LinkedList<Statement>();
		//statements.add(forStmt.getBody());

		List<Expression> iniFor = forStmt.getInit();
		ExpressionStmt expressionStmt = new ExpressionStmt();
		for(Expression expression : iniFor){
			expressionStmt.setExpression(expression);
			statements.add(expressionStmt);
		}

		statements.add(loopToIf(forStmt,args));
		blockStmt.setStmts(statements);
		return blockStmt;
	}

	public IfStmt loopToIf(Statement statement, Object args){
		/**************************/
		/******** LLAMADOR ********/
		/**************************/
		// Creamos un objeto Loop que sirve para examinar bucles
		Loop loop = null;
		if(statement instanceof WhileStmt){
			loop = new While(null, null, statement);
		} else if(statement instanceof DoStmt) {
			loop = new Do(null, null, statement);
		} else {
			loop = new For(null, null, statement);
		}
		// El objeto Loop nos calcula la lista de variables declaradas en el m�todo y usadas en el bucle (la intersecci�n)
		List<Variable> variables = loop.getUsedVariables(methodDeclaration);
		// Creamos un objeto LoopVariables que sirve para convertir la lista de variables en lista de argumentos y par�metros
		LoopVariables loopVariables = new LoopVariables(variables);
		// El objeto LoopVariables nos calcula la lista de argumentos del m�todo
		List<Expression> arguments = loopVariables.getArgs();

		//Creamos el if
		IfStmt ifStmt = new IfStmt();
		Expression cond = null;
		if(statement instanceof WhileStmt) {
			WhileStmt whileStmt = (WhileStmt) statement;
			cond = whileStmt.getCondition();
		} else if(statement instanceof DoStmt) {
			DoStmt doStmt = (DoStmt) statement;
			cond = doStmt.getCondition();
		} else {
			ForStmt forStmt = (ForStmt) statement;
			cond = forStmt.getCompare();
		}

		//Asignamos la condicion
		ifStmt.setCondition(cond);

		//Object[] result = this.***metodo_x***()
		MethodCallExpr methodCall = new MethodCallExpr();
		methodCall.setName(nameMethod + contador);
		methodCall.setArgs(arguments);

		//***Object***[] result = this.metodo_x()
		ClassOrInterfaceType objType = new ClassOrInterfaceType();
		objType.setName("Object");

		//***Object[]*** result = this.metodo_x()
		ReferenceType refType = new ReferenceType();
		refType.setArrayCount(1);
		refType.setType(objType);

		//Object[] result ***=*** this.metodo_x()
		NameExpr resultExpr = new NameExpr("result");
		List<VariableDeclarator> vars = new LinkedList<VariableDeclarator>();
		VariableDeclarator var = new VariableDeclarator();
		VariableDeclaratorId varId = new VariableDeclaratorId();
		varId.setName(resultExpr.getName());
		vars.add(var);
		var.setId(varId);
		var.setInit(methodCall);

		//***Object[] result*** = this.metodo_x()
		VariableDeclarationExpr varDecExpr = new VariableDeclarationExpr();
		varDecExpr.setType(refType);
		varDecExpr.setVars(vars);

		//***Object[] result = this.metodo_x()***
		ExpressionStmt exprStmt = new ExpressionStmt();
		exprStmt.setExpression(varDecExpr);

		//Creamos el bloque de statements
		BlockStmt blockStmt = new BlockStmt();
		//Asignamos el then
		ifStmt.setThenStmt(blockStmt);
		List<Statement> statements = new LinkedList<Statement>();
		//Añadimos el body del while
		statements.add(exprStmt);

		//Object[] result = this.metodo_x(***int*** x, ***int*** y)
		//Obtenemos los tipos de las variables
		List<Type> types = loopVariables.getReturnTypes();
		//Object[] result = this.metodo_x(int ***x***, int ***y***)
		//Obtenemos los nombres de las variables
		List<String> names = loopVariables.getReturnNames();
		//Asignacion de las variables del bucle
		for(int i = 0; i < variables.size(); i++){
			//result[0]
			ArrayAccessExpr arrayAccessExpr = new ArrayAccessExpr();
			IntegerLiteralExpr literalExpr = new IntegerLiteralExpr();
			literalExpr.setValue(String.valueOf(i));
			arrayAccessExpr.setIndex(literalExpr);
			arrayAccessExpr.setName(resultExpr);
			//Integer
			Type type = types.get(i);
			//Cast que une los dos
			CastExpr castExpr = new CastExpr();
			castExpr.setType(getWrapper(type));
			castExpr.setExpr(arrayAccessExpr);
			//=
			AssignExpr.Operator operator = AssignExpr.Operator.assign;
			//Var1
			String name = names.get(i);
			NameExpr nameExpr = new NameExpr(name);
			//AssingExpr que contiene la Var1, el = y el Integer result[0]
			AssignExpr assignExpr = new AssignExpr();
			assignExpr.setValue(castExpr);
			assignExpr.setOperator(operator);
			assignExpr.setTarget(nameExpr);
			ExpressionStmt stmt = new ExpressionStmt();
			stmt.setExpression(assignExpr);
			statements.add(stmt);
		}

		//Establecemos el bloque de statements
		blockStmt.setStmts(statements);

		/**************************/
		/********* METODO *********/
		/**************************/

		MethodDeclaration newMethod = new MethodDeclaration();
		BlockStmt methodBody = new BlockStmt();
		List<Statement> bodyStmts = new LinkedList<Statement>();
		if(statement instanceof WhileStmt) {
			WhileStmt whileStmt = (WhileStmt) statement;
			bodyStmts.add(whileStmt.getBody()); //cuerpo del while
		} else if(statement instanceof DoStmt) {
			DoStmt doStmt = (DoStmt) statement;
			bodyStmts.add(doStmt.getBody()); //cuerpo del do
		} else {
			ForStmt forStmt = (ForStmt) statement;
			bodyStmts.add(forStmt.getBody()); //cuerpo del for
			List<Expression> updFor = forStmt.getUpdate();
			ExpressionStmt expressionStmt = new ExpressionStmt();
			for (Expression expression : updFor) {
				expressionStmt.setExpression(expression);
				bodyStmts.add(expressionStmt);
			}
		}

		//Creamos el if
		IfStmt ifMethod = new IfStmt();
		//Asignamos la condicion
		ifMethod.setCondition(cond);

		ReturnStmt ifReturn = new ReturnStmt();
		ifReturn.setExpr(methodCall);

		//Creamos el bloque de statements
		BlockStmt blockIf = new BlockStmt();
		List<Statement> ifStmts = new LinkedList<Statement>();
		//Asignamos el then
		ifMethod.setThenStmt(blockIf);
		ifStmts.add(ifReturn);
		blockIf.setStmts(ifStmts);
		bodyStmts.add(ifMethod); //if en el cuerpo del metodo

		//return new object[] {vars};
		ReturnStmt returnStmt = new ReturnStmt();

		ArrayCreationExpr arrayCreationExpr = new ArrayCreationExpr();
		arrayCreationExpr.setType(objType);
		arrayCreationExpr.setArrayCount(1);
		ArrayInitializerExpr arrayInitializerExpr = new ArrayInitializerExpr();
		arrayInitializerExpr.setValues(arguments);
		arrayCreationExpr.setInitializer(arrayInitializerExpr);

		returnStmt.setExpr(arrayCreationExpr);
		bodyStmts.add(returnStmt);
		methodBody.setStmts(bodyStmts);

		newMethod.setBody(blockWrapper(methodBody));
		newMethod.setName(nameMethod + contador);
		newMethod.setType(refType);
		newMethod.setModifiers(methodDeclaration.getModifiers());
		newMethod.setParameters(loopVariables.getParameters());

		// Anyadimos el nuevo metodo a la clase actual
		this.classDeclaration.getMembers().add(newMethod);
		contador++; //Incrementamos el contador de whiles
		return ifStmt;
	}

	// Dada un tipo, 
	// Si es un tipo primitivo, devuelve el wrapper correspondiente 
	// Si es un tipo no primitivo, lo devuelve
	private Type getWrapper(Type type)
	{
		if (!(type instanceof PrimitiveType))
			return type;

		PrimitiveType primitiveType = (PrimitiveType) type;
		String primitiveName = primitiveType.getType().name();
		String wrapperName = primitiveName;

		if (wrapperName.equals("Int"))
			wrapperName = "Integer";
		else if (wrapperName.equals("Char"))
			wrapperName = "Character";

		return new ClassOrInterfaceType(wrapperName);
	}
	// Dada una sentencia, 
	// Si es una �nica instrucci�n, devuelve un bloque equivalente 
	// Si es un bloque, lo devuelve
	private BlockStmt blockWrapper(Statement statement)
	{
		if (statement instanceof BlockStmt)
			return (BlockStmt) statement;

		BlockStmt block = new BlockStmt();
		List<Statement> blockStmts = new LinkedList<Statement>();
		blockStmts.add(statement);

		block.setStmts(blockStmts);

		return block;
	}
}