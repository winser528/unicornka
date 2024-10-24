package org.mybatis.generator.ext.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @AUTO 修改生成DAO文件名称, 是否默认带有了所有get/set方法
 * @Author AIM
 * @DATE 2018/5/15
 */
public class RenameJavaMapperPlugin extends PluginAdapter {

    private String searchString;
    private String replaceString;
    private Pattern pattern;
    private boolean hasLombok = true;//默认不带

    public RenameJavaMapperPlugin() {
    }

    public boolean validate(List<String> warnings) {
        searchString = properties.getProperty("searchString"); //$NON-NLS-1$
        replaceString = properties.getProperty("replaceString"); //$NON-NLS-1$
        hasLombok = Boolean.parseBoolean(properties.getProperty("hasLombok"));

        boolean valid = stringHasValue(searchString) && stringHasValue(replaceString);

        if (valid) {
            pattern = Pattern.compile(searchString);
        } else {
            if (!stringHasValue(searchString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameExampleClassPlugin", //$NON-NLS-1$
                        "searchString")); //$NON-NLS-1$
            }
            if (!stringHasValue(replaceString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameExampleClassPlugin", //$NON-NLS-1$
                        "replaceString")); //$NON-NLS-1$
            }
        }

        return valid;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String oldType = introspectedTable.getMyBatis3JavaMapperType();
        Matcher matcher = pattern.matcher(oldType);
        oldType = matcher.replaceAll(replaceString);

        introspectedTable.setMyBatis3JavaMapperType(oldType);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (hasLombok) {
            //添加domain的import
            topLevelClass.addImportedType("lombok.Data");
            topLevelClass.addImportedType("lombok.Builder");
            topLevelClass.addImportedType("lombok.NoArgsConstructor");
            topLevelClass.addImportedType("lombok.AllArgsConstructor");
            //添加domain的注解
            topLevelClass.addAnnotation("@Data");
            topLevelClass.addAnnotation("@Builder");
            topLevelClass.addAnnotation("@NoArgsConstructor //无参数的构造方法");
            topLevelClass.addAnnotation("@AllArgsConstructor //包含所有变量构造方法");
        }
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成getter
        if (hasLombok) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成setter
        if (hasLombok) {
            return false;
        } else {
            return true;
        }
    }
}
