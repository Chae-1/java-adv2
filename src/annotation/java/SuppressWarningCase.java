package annotation.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SuppressWarningCase {


    @SuppressWarnings("unused")
    public void unusedWarning() {
        //
        int unusedVariable = 10;

    }

    @SuppressWarnings("deprecation")
    public void deprecatedWarning() {
        Date date = new Date();
        date.getDate();
    }

    @SuppressWarnings({"rawtypes"})
    public void uncheckedCast() {
        List list = new ArrayList();
    }
}
