//package Demo;
//
//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Union;
//import net.sf.jsqlparser.JSQLParserException;
//import net.sf.jsqlparser.expression.*;
//import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
//import net.sf.jsqlparser.parser.CCJSqlParserManager;
//import net.sf.jsqlparser.schema.Table;
//import net.sf.jsqlparser.statement.select.*;
//
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * @Author: Donlin
// * @Date: Created in 17:13 2018/10/15
// * @Version: 1.0
// * @Description: jsqlparser官网给出的访问者模式的代码示例
// */
//
//public class TablesNamesFinder implements SelectVisitor, FromItemVisitor, JoinVisitor, ExpressionVisitor, ItemsListVisitor {
//    private List tables;
//
//    public List getTableList(Select select) {
//        tables = new ArrayList();
//        select.getSelectBody().accept(this);
//        return tables;
//    }
//
//    public void visit(PlainSelect plainSelect) {
//        plainSelect.getFromItem().accept(this);
//
//        if (plainSelect.getJoins() != null) {
//            for (Iterator joinsIt = plainSelect.getJoins().iterator(); joinsIt.hasNext();) {
//                Join join = (Join) joinsIt.next();
//                join.getRightItem().accept(this);
//            }
//        }
//        if (plainSelect.getWhere() != null)
//            plainSelect.getWhere().accept(this);
//
//    }
//
//    public void visit(Union union) {
//        for (Iterator iter = union.getPlainSelects().iterator(); iter.hasNext();) {
//            PlainSelect plainSelect = (PlainSelect) iter.next();
//            visit(plainSelect);
//        }
//    }
//
//    public void visit(Table tableName) {
//        String tableWholeName = tableName.getWholeTableName();
//        tables.add(tableWholeName);
//    }
//
//    public void visit(SubSelect subSelect) {
//        subSelect.getSelectBody().accept(this);
//    }
//
//    public void visit(Addition addition) {
//        visitBinaryExpression(addition);
//    }
//
//    public void visit(AndExpression andExpression) {
//        visitBinaryExpression(andExpression);
//    }
//
//    public void visit(Between between) {
//        between.getLeftExpression().accept(this);
//        between.getBetweenExpressionStart().accept(this);
//        between.getBetweenExpressionEnd().accept(this);
//    }
//
//    public void visit(Column tableColumn) {
//    }
//
//    public void visit(Division division) {
//        visitBinaryExpression(division);
//    }
//
//    public void visit(DoubleValue doubleValue) {
//    }
//
//    public void visit(EqualsTo equalsTo) {
//        visitBinaryExpression(equalsTo);
//    }
//
//    public void visit(Function function) {
//    }
//
//    public void visit(GreaterThan greaterThan) {
//        visitBinaryExpression(greaterThan);
//    }
//
//    public void visit(GreaterThanEquals greaterThanEquals) {
//        visitBinaryExpression(greaterThanEquals);
//    }
//
//    public void visit(InExpression inExpression) {
//        inExpression.getLeftExpression().accept(this);
//        inExpression.getItemsList().accept(this);
//    }
//
//    public void visit(InverseExpression inverseExpression) {
//        inverseExpression.getExpression().accept(this);
//    }
//
//    public void visit(IsNullExpression isNullExpression) {
//    }
//
//    public void visit(JdbcParameter jdbcParameter) {
//    }
//
//    public void visit(LikeExpression likeExpression) {
//        visitBinaryExpression(likeExpression);
//    }
//
//    public void visit(ExistsExpression existsExpression) {
//        existsExpression.getRightExpression().accept(this);
//    }
//
//    public void visit(LongValue longValue) {
//    }
//
//    public void visit(MinorThan minorThan) {
//        visitBinaryExpression(minorThan);
//    }
//
//    public void visit(MinorThanEquals minorThanEquals) {
//        visitBinaryExpression(minorThanEquals);
//    }
//
//    public void visit(Multiplication multiplication) {
//        visitBinaryExpression(multiplication);
//    }
//
//    public void visit(NotEqualsTo notEqualsTo) {
//        visitBinaryExpression(notEqualsTo);
//    }
//
//    public void visit(NullValue nullValue) {
//    }
//
//    public void visit(OrExpression orExpression) {
//        visitBinaryExpression(orExpression);
//    }
//
//    public void visit(Parenthesis parenthesis) {
//        parenthesis.getExpression().accept(this);
//    }
//
//    public void visit(StringValue stringValue) {
//    }
//
//    public void visit(Subtraction subtraction) {
//        visitBinaryExpression(subtraction);
//    }
//
//    public void visitBinaryExpression(BinaryExpression binaryExpression) {
//        binaryExpression.getLeftExpression().accept(this);
//        binaryExpression.getRightExpression().accept(this);
//    }
//
//    public void visit(ExpressionList expressionList) {
//        for (Iterator iter = expressionList.getExpressions().iterator(); iter.hasNext();) {
//            Expression expression = (Expression) iter.next();
//            expression.accept(this);
//        }
//
//    }
//
//    public void visit(DateValue dateValue) {
//    }
//
//    public void visit(TimestampValue timestampValue) {
//    }
//
//    public void visit(TimeValue timeValue) {
//    }
//
//    public void visit(CaseExpression caseExpression) {
//    }
//
//    public void visit(WhenClause whenClause) {
//    }
//
//    public void visit(AllComparisonExpression allComparisonExpression) {
//        allComparisonExpression.GetSubSelect().getSelectBody().accept(this);
//    }
//
//    public void visit(AnyComparisonExpression anyComparisonExpression) {
//        anyComparisonExpression.GetSubSelect().getSelectBody().accept(this);
//    }
//
//    public void visit(SubJoin subjoin) {
//        subjoin.getLeft().accept(this);
//        subjoin.getJoin().getRightItem().accept(this);
//    }
//
//    // jsqlparser用法，访问者模式，先读一下源码，看看如何实现这个四个statement的select、update、delete、insert
//    public static void main(String[] args) {
//        CCJSqlParserManager pm = new CCJSqlParserManager();
//        String sql = "SELECT * FROM MY_TABLE1, MY_TABLE2, (SELECT * FROM MY_TABLE3) LEFT OUTER JOIN MY_TABLE4 "+
//                " WHERE ID = (SELECT MAX(ID) FROM MY_TABLE5) AND ID2 IN (SELECT * FROM MY_TABLE6)" ;
//        net.sf.jsqlparser.statement.Statement statement = null;
//        try {
//            statement = pm.parse(new StringReader(sql));
//        } catch (JSQLParserException e) {
//            e.printStackTrace();
//        }
///*
//now you should use a class that implements StatementVisitor to decide what to do
//based on the kind of the statement, that is SELECT or INSERT etc. but here we are only
//interested in SELECTS
//*/
//        if (statement instanceof Select) {
//            Select selectStatement = (Select) statement;
//            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
//            List tableList = tablesNamesFinder.getTableList(selectStatement);
//            for (Iterator iter = tableList.iterator(); iter.hasNext();) {
//                System.out.println(tableName);
//            }
//        }
//    }
//}
