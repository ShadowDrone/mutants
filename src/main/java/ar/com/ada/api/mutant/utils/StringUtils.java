package ar.com.ada.api.mutant.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isMatch(String regex, String text) {

        if (text == null)
            return false;

        //Crear el objeto Pattern 
        Pattern pattern = Pattern.compile(regex);

        //obtener el objeto matcher
        //sera el buscador(matcheador) del pattern dentro del texto
        Matcher matcher = pattern.matcher(text);

        if (matcher == null)
            return false;

        return matcher.find();

        //otros lenguajes
        // return Regex.isMatch(regex,text);
    }

    public static boolean isMatchCaseInsensitive(String regex, String text) {

        if (text == null)
            return false;

        byte flag = 0;
        // 0,0,0,0,0,0,0,0 => 8 bits que en este caso estan en 0
        flag = 1;
        // 0,0,0,0,0,0,0,1 => 
        flag = 2;
        // 0,0,0,0,0,0,1,0 => 2 en binario
        flag = 4;
        // 0,0,0,0,0,1,0,0 => 4 en binarios
        flag = 8;
        // 0,0,0,0,1,0,0,0 => 8 en binarios
        flag = 2 + 8;
        flag = 10;
        // 0,0,0,0,1,0,1,0 => 10 en binario
        // 8 => multiline
        // 2 => case insensitive
        // si queremos saber si el flag Case insensitive esta activo.
        // if ( flag & 2 == 2)
        // if ( 0,0,0,0,1,0,1,0 & 0,0,0,0,0,0,1,0 == 0,0,0,0,0,0,1,0)
        //0,0,0,0,1,0,1,0 
        // & bitwise AND
        //0,0,0,0,0,0,1,0
        //===============
        //0,0,0,0,0,0,1,0 => 2
        //0x00000010

        //Crear el objeto Pattern 
        Pattern pattern = Pattern.compile(regex, 2);

        //obtener el objeto matcher
        //sera el buscador(matcheador) del pattern dentro del texto
        Matcher matcher = pattern.matcher(text);

        if (matcher == null)
            return false;

        return matcher.find();

        //otros lenguajes
        // return Regex.isMatch(regex,text);
    }

}
