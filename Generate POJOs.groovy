import com.intellij.database.model.DasTable
import com.intellij.database.model.ObjectKind
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil
import java.io.*
import java.text.SimpleDateFormat

/*
 * Available context bindings:
 *   SELECTION   Iterable<DasObject>
 *   PROJECT     project
 *   FILES       files helper
 */
packageName = ""
typeMapping = [
        (~/(?i)tinyint|smallint|mediumint|int/)      : "int",
        (~/(?i)bigint/)                             : "long",
        (~/(?i)bool|bit/)                        : "boolean",
        (~/(?i)float|double|decimal|real/)       : "double",
        (~/(?i)datetime|timestamp|date|time/)    : "Date",
        (~/(?i)blob|binary|bfile|clob|raw|image/): "InputStream",
        (~/(?i)/)                                : "String"
]


FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    SELECTION.filter { it instanceof DasTable && it.getKind() == ObjectKind.TABLE }.each { generate(it, dir) }
}

def generate(table, dir) {
    def className = javaName(table.getName(), true)
    def fields = calcFields(table)
    packageName = getPackageName(dir)
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(dir, className + ".java")), "UTF-8"))
    printWriter.withPrintWriter {out -> generate(out, className, fields,table)}

//    new File(dir, className + ".java").withPrintWriter { out -> generate(out, className, fields,table) }
}

// 获取包所在文件夹路径
def getPackageName(dir) {
    return dir.toString().replaceAll("\\\\", ".").replaceAll("/", ".").replaceAll("^.*src(\\.main\\.java\\.)?", "") + ";"
}

def generate(out, className, fields,table) {
    out.println "package $packageName"
    out.println ""
    out.println "import lombok.Data;"
//    out.println "import javax.persistence.Column;"
//    out.println "import javax.persistence.Entity;"
//    out.println "import javax.persistence.Table;"
//    out.println "import tk.mybatis.mapper.annotation.KeySql;"
    out.println "import javax.persistence.*;;"
    out.println "import java.io.Serializable;"
    Set types = new HashSet()

    fields.each() {
        types.add(it.type)
    }

    if (types.contains("Date")) {
        out.println "import java.util.Date;"
        out.println "import org.springframework.format.annotation.DateTimeFormat;"
        out.println "import com.fasterxml.jackson.annotation.JsonFormat;"
    }

    if (types.contains("InputStream")) {
        out.println "import java.io.InputStream;"
    }
    out.println ""
    out.println "/**\n" +
            " * @Description  \n" +
            " * @Author  \n" +
            " * @Date "+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " \n" +
            " */"
    out.println ""
    out.println "@Entity"
    out.println "@Data"
    out.println "@Table ( name =\""+table.getName() +"\" )"
    def primaryKey = DasUtil.getPrimaryKey(table)
    boolean isPrimaryKeyUnique = primaryKey?.getColumnsRef()?.size() > 1
    def innerClassName = "${className}Id"
    if(isPrimaryKeyUnique){
        out.println "@IdClass(${className}.${innerClassName}.class)"
    }
    out.println "public class $className implements Serializable {"
    out.println ""
    out.println genSerialID()
    fields.each() {
        out.println ""
        // 输出注释
        if (isNotEmpty(it.commoent)) {
            out.println "\t/**"
            out.println "\t * ${it.commoent.toString()}"
            out.println "\t */"
        }
        if (it.isId) out.println "    @Id"
//        if (it.isId) out.println "    @KeySql(useGeneratedKeys = true)"
//        if (it.annos != "") out.println "   ${it.annos.replace("[@Id]", "")}"
        if (it.type == "Date") out.println "    @JsonFormat(pattern=\"yyyy-MM-dd HH:mm:ss\",timezone = \"GMT+8\") \n" +
                "    @DateTimeFormat(pattern = \"yyyy-MM-dd\") "
        if (it.annos != "") out.println "   ${it.annos}"

        // 输出成员变量
        out.println "\tprivate ${it.type} ${it.name};"
    }

//    // 输出get/set方法
//    fields.each() {
//        out.println ""
//        out.println "\tpublic ${it.type} get${it.name.capitalize()}() {"
//        out.println "\t\treturn this.${it.name};"
//        out.println "\t}"
//        out.println ""
//
//        out.println "\tpublic void set${it.name.capitalize()}(${it.type} ${it.name}) {"
//        out.println "\t\tthis.${it.name} = ${it.name};"
//        out.println "\t}"
//    }
    out.println ""

    if(isPrimaryKeyUnique){
        out.println "\tpublic static class $innerClassName implements Serializable {"
        fields.each() {
            if(it.isId){
                out.println ""
                // 输出注释
                if (isNotEmpty(it.commoent)) {
                    out.println "\t/**"
                    out.println "\t * ${it.commoent.toString()}"
                    out.println "\t */"
                }
                out.println "\t    @Id"
                if (it.type == "Date") out.println "    @JsonFormat(pattern=\"yyyy-MM-dd HH:mm:ss\",timezone = \"GMT+8\") \n" +
                        "    @DateTimeFormat(pattern = \"yyyy-MM-dd\") "
                if (it.annos != "") out.println "\t   ${it.annos}"
                // 输出成员变量
                out.println "\t    private ${it.type} ${it.name};"
            }
        }


        out.println "\t}"
    }


    out.println "}"




}

def calcFields(table) {
    def primaryKey = DasUtil.getPrimaryKey(table)
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
        def colName = col.getName()
        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
        def comm =[
                colName : col.getName(),
                name :  javaName(col.getName(), false),
                type : typeStr,
                commoent: col.getComment(),
                annos: "\t@Column(name = \""+colName+"\" )",
                isId : primaryKey != null && DasUtil.containsName(colName, primaryKey.getColumnsRef()),
        ]

//        if("id".equals(Case.LOWER.apply(col.getName())))
//            comm.annos +=["@Id"]
        fields += [comm]
    }
}

// 处理类名（这里是因为我的表都是以t_命名的，所以需要处理去掉生成类名时的开头的T，
// 如果你不需要那么请查找用到了 javaClassName这个方法的地方修改为 javaName 即可）
def javaClassName(str, capitalize) {
    def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
            .collect { Case.LOWER.apply(it).capitalize() }
            .join("")
            .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
    // 去除开头的T  http://developer.51cto.com/art/200906/129168.htm
    s = s[1..s.size() - 1]
    capitalize || s.length() == 1? s : Case.LOWER.apply(s[0]) + s[1..-1]
}

def javaName(str, capitalize) {
//    def s = str.split(/(?<=[^\p{IsLetter}])/).collect { Case.LOWER.apply(it).capitalize() }
//            .join("").replaceAll(/[^\p{javaJavaIdentifierPart}]/, "_")
//    capitalize || s.length() == 1? s : Case.LOWER.apply(s[0]) + s[1..-1]
    def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
            .collect { Case.LOWER.apply(it).capitalize() }
            .join("")
            .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
    capitalize || s.length() == 1? s : Case.LOWER.apply(s[0]) + s[1..-1]
}

def isNotEmpty(content) {
    return content != null && content.toString().trim().length() > 0
}

static String changeStyle(String str, boolean toCamel){
    if(!str || str.size() <= 1)
        return str

    if(toCamel){
        String r = str.toLowerCase().split('_').collect{cc -> Case.LOWER.apply(cc).capitalize()}.join('')
        return r[0].toLowerCase() + r[1..-1]
    }else{
        str = str[0].toLowerCase() + str[1..-1]
        return str.collect{cc -> ((char)cc).isUpperCase() ? '_' + cc.toLowerCase() : cc}.join('')
    }
}

static String genSerialID()
{
//    return "\tprivate static final long serialVersionUID =  "+Math.abs(new Random().nextLong())+"L;"
    return "\tprivate static final long serialVersionUID =  1L;"
}
