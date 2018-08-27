package Demo;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;

import java.io.StringReader;

/**
 * @Author: Donlin
 * @Date: Created in 20:43 2018/7/2
 * @Version: 1.0
 * @Description: 一个使用Jsqlparser进行SQL语句解析的demo
 */
public class JsqlparserDemo {

    public static void main(String[] args) throws JSQLParserException {
        demo();
    }

    /**
     * demo函数
     * @throws JSQLParserException
     */
    public static void demo() throws JSQLParserException{
        CCJSqlParserManager parserManager = new CCJSqlParserManager();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("update ac_operator op ");
        stringBuffer.append("set op.errcount=(");
        stringBuffer.append("(select case when op1.errcount is null then 0 else op1.errcount end as errcount ");
        stringBuffer.append("from ac_operator op1 ");
        stringBuffer.append("where op1.loginname = '中国' )+1");
        stringBuffer.append("),lastlogin='中国' ");
        stringBuffer.append("where PROCESS_ID=");
        stringBuffer.append("(select distinct g.id from tempTable g where g.ID='中国')");
        stringBuffer.append("and columnName2 = '890' and columnName3 = '678' and columnName4 = '456'");

        System.out.println(stringBuffer.toString());

        // 解析String类型的SQL语句成为一个Statement
        Statement statement = parserManager.parse(new StringReader(stringBuffer.toString()));

        if (statement instanceof Update){
            // 获得Update对象
            Update updateStatement = (Update) statement;
            // 获得表名
            System.out.println("table:" + updateStatement.getTables().get(0));
            // 获得where条件表达式
            Expression where = updateStatement.getWhere();
            // 初始化接收获得的字段名
            StringBuffer allColumnNames = new StringBuffer();
            // BinaryExpression包括了整个where条件
            if (where instanceof BinaryExpression){
                allColumnNames = getColumnName((BinaryExpression) (where), allColumnNames);
                System.out.println("allColumnNames:" + allColumnNames.toString());
            }
        }
    }

    /**
     * 获得where条件字段中的列名，以及对应的操作符
     * @param expression
     * @param allColumnNames
     * @return
     */
    private static StringBuffer getColumnName(Expression expression, StringBuffer allColumnNames){

        String columName = null;
        if (expression instanceof BinaryExpression){
            // 获得左边表达式
            Expression leftExperssion = ((BinaryExpression) expression).getLeftExpression();
            // 如果左边表达式为Column对象，则直接获得列名
            if (leftExperssion instanceof Column){
                // 获得列名
                columName = ((Column) leftExperssion).getColumnName();
                allColumnNames.append(columName);
                allColumnNames.append(":");
                // 拼接操作符
                //allColumnNames.append(((BinaryExpression) leftExperssion).getStringExpression());
            }// 否则，进行迭代
            else if (leftExperssion instanceof BinaryExpression){
                getColumnName((BinaryExpression) leftExperssion, allColumnNames);
            }

            // 获得右边表达式，并分解
            Expression rightExpression = ((BinaryExpression) expression).getRightExpression();

            if (rightExpression instanceof BinaryExpression){
                Expression leftExpression2 = ((BinaryExpression) rightExpression).getLeftExpression();
                if (leftExpression2 instanceof Column ){
                    // 获得列名
                    columName = ((Column) leftExpression2).getColumnName();
                    allColumnNames.append("-");
                    allColumnNames.append(columName);
                    allColumnNames.append(":");
                    //allColumnNames.append(((BinaryExpression) rightExpression).getStringExpression());
                }
            }
        }
        return allColumnNames;

    }

}
