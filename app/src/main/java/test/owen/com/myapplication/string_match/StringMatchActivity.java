package test.owen.com.myapplication.string_match;

import android.app.Activity;

public class StringMatchActivity extends Activity {

    public static int StringContains_SunDay(String sourceString, String patternString) {
        // Covert the char array
        char[] sourceList = sourceString.toCharArray();
        char[] patternList = patternString.toCharArray();

        int sourceLength = sourceList.length;
        int patternLength = patternList.length;
        System.out.println(sourceLength + "  " + patternLength);
        int sCount = 0, pCount = 0;
        int loc = 0;

        if (sourceLength < patternLength) {
            return -1;
        }

        while (sCount < sourceLength && pCount < patternLength) {
            // if equals to move next character
            if (sourceList[sCount] == patternList[pCount]) {
                sCount++;
                pCount++;
            } else {
                // sAim:the location of char to judge
                // pAim:the last location of the pattern string
                int sAim = sCount + patternLength;
                if (sAim < sourceLength) {
                    char aimChar = sourceList[sAim];
                    int pAim = patternLength - 1;
                    // to judge char from back to front,the pAim is the equal location
                    while (pAim > 0) {
                        if (patternList[pAim] == aimChar) {
                            break;
                        }
                        pAim--;
                    }
                    // record the equal location with loc.
                    // sCount:move the judge location of source string
                    // pCount:move the begin of the pattern string
                    sCount = sCount + patternLength - pAim;
                } else if (sAim > sourceLength || sAim == sourceLength && pCount == 0) {
                    break;
                }

                loc = sCount;
                pCount = 0;
            }
        }
        // if pattern string don't match completed,return -1
        if (pCount < patternLength) {
            return -1;
        }
        // else return the location
        return loc;
    }
}
