package pt.ist.fenixedu.bpi.webservice;

import java.io.Serializable;

/**
 * Created by nurv on 26/07/16.
 */
public class BPISyncException extends Exception implements Serializable{
    public BPISyncException(String reason){
        super(reason);
    }
}
