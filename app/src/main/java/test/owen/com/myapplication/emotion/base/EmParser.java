package test.owen.com.myapplication.emotion.base;

import java.util.regex.Pattern;

/**
 * Created by winghe on 2015/11/2.
 */
public class EmParser {
    public Pattern pattern;
    public IEmParser filter;
    public int index = 1;

    public EmParser(){

    }

    public EmParser(Pattern pattern, IEmParser filter){
        this.pattern = pattern;
        this.filter = filter;
    }

    public EmParser(Pattern pattern, IEmParser filter, int index){
        this.pattern = pattern;
        this.filter = filter;
        this.index = index;
    }

    public void setPattern(Pattern pattern){
        this.pattern = pattern;
    }

    public void setFilter(IEmParser filter){
        this.filter = filter;
    }
}
