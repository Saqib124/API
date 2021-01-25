package com.intigral.api.utilities;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.List;

public class Helper {

    /*schema files base path*/
    public static final String schema_base_path = System.getProperty("user.dir") + "/src/test/resources/data/response/";

    /**
     * search any particular string in the list of string
     *
     * @param  s  contains any string value
     * @param l contains list of string
     */
    public static boolean containsCaseInsensitive(String s, List<String> l){
        return l.stream().anyMatch(x -> x.equalsIgnoreCase(s));
    }

    /**
     * search any particular string in the list of string
     *
     * @param  url  contains url which we are validating
     * @return   true if url is valid else false
     */
    public static boolean urlValidator(String url) {
        UrlValidator defaultValidator = new UrlValidator();
        return defaultValidator.isValid(url);
    }
}
