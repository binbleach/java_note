package leetcode;

import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        String s = "chr(123";
//        Pattern p = Pattern.compile("(\\s*\\d*\\s*=\\s*\\d*\\s*)|(SELECT|update|and|or|delete|insert|trancate|char|substr|ascii|declare|exec|count|master|into|drop|execute\\s*)|(XMLType\\()|(chr\\(\\d*\\))|(\\s+(UNION|INTERSECT|WHERE|like|MINUS|FROM|or|and|WHEN|THEN|ELSE)\\s+)|(\\s+DUAL)|(DBMS_PIPE\\.RECEIVE_MESSAGE)|(TZ_OFFSET|TO_TIMESTAMP_TZ|BFILENAME|FROM_TZ|NUMTOYMINTERVAL|NUMTODSINTERVAL|DBMS_DATAPUMP|DBMS_REGISTRY|DBMS_METADATA|REQUEST|DBMS_JAVA_TEST|DBMS_LOCK|DBMS_PIPE|DBMS_RANDOM|UTL_FILE|UTL_HTTP|UTL_SMTP|UTL_TCP)", Pattern.MULTILINE);
//        Pattern p = Pattern.compile("(=|SELECT|update|and|or|delete|insert|trancate|char|substr|ascii|declare|exec|count|master|into|drop|execute\\s*)|(XMLType\\()|(chr\\(\\d*\\))|(\\s+(UNION|INTERSECT|WHERE|like|MINUS|FROM|or|and|WHEN|THEN|ELSE)\\s+)|(\\s+DUAL)|(DBMS_PIPE\\.RECEIVE_MESSAGE)|(TZ_OFFSET|TO_TIMESTAMP_TZ|BFILENAME|FROM_TZ|NUMTOYMINTERVAL|NUMTODSINTERVAL|DBMS_DATAPUMP|DBMS_REGISTRY|DBMS_METADATA|REQUEST|DBMS_JAVA_TEST|DBMS_LOCK|DBMS_PIPE|DBMS_RANDOM|UTL_FILE|UTL_HTTP|UTL_SMTP|UTL_TCP)", Pattern.MULTILINE);
        String regx = "=|SELECT|update|and|or|delete|insert|trancate|char|substr|ascii|declare|exec|count|master|into|drop|execute|XMLType(|" +
                "TZ_OFFSET|TO_TIMESTAMP_TZ|BFILENAME|FROM_TZ|NUMTOYMINTERVAL|NUMTODSINTERVAL|DBMS_DATAPUMP|DBMS_REGISTRY|" +
                "DBMS_METADATA|REQUEST|DBMS_JAVA_TEST|DBMS_LOCK|DBMS_PIPE|DBMS_RANDOM|UTL_FILE|UTL_HTTP|UTL_SMTP|UTL_TCP" ;
        String[] split = regx.split("\\|");
        for(String a : split){
            if(s.contains(a)) System.out.println("1："+true);;
        }
        Pattern p = Pattern.compile(
                "(\\s+(UNION|INTERSECT|WHERE|like|MINUS|FROM|or|and|WHEN|THEN|ELSE)\\s+)|" +
                        "(\\s+DUAL)|"+"(chr\\(\\d*\\))", Pattern.MULTILINE);
        System.out.println("2："+p.matcher(s).find());
    }
}
