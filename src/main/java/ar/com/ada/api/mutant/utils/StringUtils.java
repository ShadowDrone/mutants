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
}
