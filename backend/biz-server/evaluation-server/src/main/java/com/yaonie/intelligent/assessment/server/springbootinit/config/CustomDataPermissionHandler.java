package com.yaonie.intelligent.assessment.server.springbootinit.config;

import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 武春利
 */
//@Component
public class CustomDataPermissionHandler implements MultiDataPermissionHandler {

    // 注入 Spring Boot 自动配置的 DataSource
//    @Autowired
    private DataSource dataSource;

    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {

        // 假设有方法获取当前用户的角色、单位和科室信息
        // 获取当前用户的 dataScope
        int dataScope = 5;/*getCurrentUserDataScope()*/
        // 获取用户所在单位
        String userDepartment = getCurrentUserDepartment();
        // 获取下属科室列表
        List<String> subDepartments = getSubDepartments(userDepartment);
        // 获取当前用户的 ID
        String userId = getCurrentUserId();

        Expression dataPermissionCondition = null;

        // 获取表名
        String tableName = table.getName();

        // 验证表中是否包含所需的字段
        boolean hasDepartmentColumn = hasColumn(tableName, "department_id");
        boolean hasUserIdColumn = hasColumn(tableName, "userId");

        if (dataScope == 1) {
            // 查看全部数据，无需额外条件
            return null;
        } else if (dataScope == 2 && hasDepartmentColumn) {
            // 查看本单位数据
            dataPermissionCondition = new EqualsTo(new Column("department_id"), new StringValue(userDepartment));
        } else if (dataScope == 3 && hasDepartmentColumn) {
            // 查看本单位及其下属科室数据
            InExpression inExpression = new InExpression();
            inExpression.setLeftExpression(new Column("department_id"));
            ExpressionList<StringValue> stringValues = new ExpressionList<>(subDepartments.stream().map(StringValue::new).toList());
            inExpression.setRightExpression(stringValues);
            dataPermissionCondition = inExpression;
        } else if (dataScope == 5 && hasUserIdColumn) {
            // 查看当前用户的数据
            dataPermissionCondition = new EqualsTo(new Column("userId"), new StringValue(userId));
        }

        // 将新条件与现有 where 条件组合
        if (dataPermissionCondition != null) {
                return dataPermissionCondition;
        }

        return null;
    }

    // 模拟获取当前用户的 dataScope
    private int getCurrentUserDataScope() {
        return 2; // 示例返回值
    }

    // 模拟获取当前用户所在单位
    private String getCurrentUserDepartment() {
        return "D001"; // 示例返回值
    }

    // 模拟获取下属科室
    private List<String> getSubDepartments(String departmentId) {
        return List.of("D001", "D002", "D003"); // 示例返回值
    }

    // 模拟获取当前用户 ID
    private String getCurrentUserId() {
        return "1834145438407852033"; // 示例返回值
    }

    // 验证字段是否存在于表中
    private boolean hasColumn(String tableName, String columnName) {
        try (Connection connection = dataSource.getConnection()) {
            // 获取数据库元数据
            DatabaseMetaData metaData = connection.getMetaData();

            // 获取表的列信息
            ResultSet columns = metaData.getColumns(null, null, tableName, columnName);

            // 检查列是否存在
            return columns.next();  // 如果找到了列，则返回 true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // 如果发生异常或没有找到列，返回 false
    }
}