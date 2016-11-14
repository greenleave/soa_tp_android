package ar.edu.unlam.soa.android.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sbogado on 14/11/16.
 */

public class Ipvalidator {

    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        private static Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);



        /**
         * Validate ip address with regular expression
         * @param ip ip address for validation
         * @return true valid ip address, false invalid ip address
         */
        public static boolean validate(final String ip){
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        }
}
