package com.coducation.smallbasic;

public class PrettyPrinter {
	BlockStmt tree;
	int numberOfIndent;

	PrettyPrinter() {
	}

	PrettyPrinter(BlockStmt tree) {
		this.tree = tree;
	}

	public void printIndent() {
		for (int i = 0; i <= numberOfIndent; i++) {
			System.out.print("    ");
		}
	}

	public void prettyPrint() {
		numberOfIndent = 0;
		prettyPrint(this.tree);
	}

	public void prettyPrint(Assign assignStmt) {
		printIndent();

		prettyPrint(assignStmt.getLSide());
		System.out.print(" = ");
		prettyPrint(assignStmt.getRSide());
		System.out.println();
	}

	public void prettyPrint(BlockStmt blockStmt) {
		numberOfIndent++;

		for (int i = 0; i < blockStmt.getAL().size(); i++) {
			prettyPrint(blockStmt.getAL().get(i));
		}

		numberOfIndent--;
	}

	public void prettyPrint(ExprStmt exprStmt) {
		printIndent();

		prettyPrint(exprStmt.getExpr());
		System.out.println();
	}

	public void prettyPrint(ForStmt forStmt) {
		printIndent();

		System.out.print("For ");
		prettyPrint(forStmt.getVar());
		System.out.print(" = ");
		prettyPrint(forStmt.getInit());
		System.out.print(" To ");
		prettyPrint(forStmt.getEnd());
		if (forStmt.getStep() != null) {
			System.out.print(" Step ");
			prettyPrint(forStmt.getStep());
		}
		System.out.println();
		prettyPrint(forStmt.getBlock());

		printIndent();

		System.out.println("EndFor");
	}

	public void prettyPrint(GotoStmt gotoStmt) {
		printIndent();

		System.out.println("Goto " + gotoStmt.getTargetLabel());
	}

	public void prettyPrint(IfStmt ifStmt) {
		printIndent();

		System.out.print("If (");
		prettyPrint(ifStmt.getCond());
		System.out.println(") Then");

		prettyPrint(ifStmt.getThen());
		if (ifStmt.getElse() != null) {
			printIndent();

			System.out.println("Else");
			prettyPrint(ifStmt.getElse());
		}

		printIndent();
		System.out.println("EndIf");
	}

	public void prettyPrint(Label labelStmt) {
		printIndent();
		System.out.println(labelStmt.getLabel() + ":");
	}

	public void prettyPrint(SubDef subDefStmt) {
		printIndent();

		System.out.println("Sub " + subDefStmt.getName());
		prettyPrint(subDefStmt.getBlock());

		printIndent();

		System.out.println("EndSub");
	}

	public void prettyPrint(WhileStmt whileStmt) {
		printIndent();

		System.out.print("While(");
		prettyPrint(whileStmt.getCond());
		System.out.println(")");
		prettyPrint(whileStmt.getBlock());

		printIndent();
		System.out.println("EndWhile");
	}

	public void prettyPrint(Stmt stmt) {
		if (stmt instanceof Assign)
			prettyPrint((Assign) stmt);
		else if (stmt instanceof BlockStmt)
			prettyPrint((BlockStmt) stmt);
		else if (stmt instanceof ExprStmt)
			prettyPrint((ExprStmt) stmt);
		else if (stmt instanceof ForStmt)
			prettyPrint((ForStmt) stmt);
		else if (stmt instanceof GotoStmt)
			prettyPrint((GotoStmt) stmt);
		else if (stmt instanceof IfStmt)
			prettyPrint((IfStmt) stmt);
		else if (stmt instanceof Label)
			prettyPrint((Label) stmt);
		else if (stmt instanceof SubDef)
			prettyPrint((SubDef) stmt);
		else if (stmt instanceof WhileStmt)
			prettyPrint((WhileStmt) stmt);
		else
			System.err.println("Syntax Error!" + stmt.getClass());
	}

	public void prettyPrint(ArithExpr arithExpr) {
		switch (arithExpr.GetOp()) {
		case 1:
			prettyPrint(arithExpr.GetOperand()[0]);
			System.out.print(" + ");
			break;
		case 2:
			prettyPrint(arithExpr.GetOperand()[0]);
			System.out.print(" - ");
			break;
		case 3:
			prettyPrint(arithExpr.GetOperand()[0]);
			System.out.print(" * ");
			break;
		case 4:
			prettyPrint(arithExpr.GetOperand()[0]);
			System.out.print(" / ");
			break;
		case 5:
			System.out.print("- ");
			prettyPrint(arithExpr.GetOperand()[0]);
			break;
		}
		if (arithExpr.GetOperand()[1] != null)
			prettyPrint(arithExpr.GetOperand()[1]);
	}

	public void prettyPrint(Array arrayExpr) {
		System.out.print(arrayExpr.getVar());
		for (int i = 0; i < arrayExpr.getDim(); i++) {
			System.out.print("[");
			prettyPrint(arrayExpr.getIndex(i));
			System.out.print("]");
		}
	}

	public void prettyPrint(CompExpr compExpr) {
		prettyPrint(compExpr.GetOperand()[0]);
		switch (compExpr.GetOp()) {
		case 1:
			System.out.print(" > ");
			break;
		case 2:
			System.out.print(" < ");
			break;
		case 3:
			System.out.print(" >= ");
			break;
		case 4:
			System.out.print(" <= ");
			break;
		case 5:
			System.out.print(" = ");
			break;
		case 6:
			System.out.print(" <> ");
			break;
		default:
			System.err.println("Unknown CompExpr Operator " + compExpr.GetOp());
			break;
		}
		prettyPrint(compExpr.GetOperand()[1]);
	}

	public void prettyPrint(Lit litExpr) {
		System.out.print(litExpr.gets());
	}

	public void prettyPrint(LogicalExpr logicalExpr) {
		prettyPrint(logicalExpr.GetOperand()[0]);
		switch (logicalExpr.GetOp()) {
		case 1:
			System.out.print(" And ");
			break;
		case 2:
			System.out.print(" Or ");
			break;
		default:
			System.err.println("Unknown Logical Operator " + logicalExpr.GetOp());
			break;
		}
		prettyPrint(logicalExpr.GetOperand()[1]);
	}

	public void prettyPrint(MethodCallExpr methodCallExpr) {
		System.out.print(methodCallExpr.getObj() + "." + methodCallExpr.getName() + "(");
		if (methodCallExpr.getArgs() != null) {
			int size = methodCallExpr.getArgs().size();
			for (int i = 0; i < size; i++) {
				prettyPrint(methodCallExpr.getArgs().get(i));
				if (i != size - 1)
					System.out.print(", ");
			}
		}
		System.out.print(")");

	}
	public void prettyPrint(ParenExpr parenExpr) {
		System.out.print("(");
		prettyPrint(parenExpr.get());
		System.out.print(")");
	}
	public void prettyPrint(PropertyExpr propertyExpr) {
		System.out.print(propertyExpr.getObj() + "." + propertyExpr.getName());
	}

	public void prettyPrint(SubCallExpr subCallExpr) {
		System.out.print(subCallExpr.getName() + "()");
	}

	public void prettyPrint(Var var) {
		System.out.print(var.getVarName());
	}

	public void prettyPrint(Expr expr) {
		if (expr instanceof ArithExpr)
			prettyPrint((ArithExpr) expr);
		else if (expr instanceof Array)
			prettyPrint((Array) expr);
		else if (expr instanceof CompExpr)
			prettyPrint((CompExpr) expr);
		else if (expr instanceof Lit)
			prettyPrint((Lit) expr);
		else if (expr instanceof LogicalExpr)
			prettyPrint((LogicalExpr) expr);
		else if (expr instanceof MethodCallExpr)
			prettyPrint((MethodCallExpr) expr);
		else if (expr instanceof ParenExpr)
			prettyPrint((ParenExpr) expr);
		else if (expr instanceof PropertyExpr)
			prettyPrint((PropertyExpr) expr);
		else if (expr instanceof SubCallExpr)
			prettyPrint((SubCallExpr) expr);
		else if (expr instanceof Var)
			prettyPrint((Var) expr);
		else
			System.err.println("Syntax Error! " + expr.getClass());
	}
}