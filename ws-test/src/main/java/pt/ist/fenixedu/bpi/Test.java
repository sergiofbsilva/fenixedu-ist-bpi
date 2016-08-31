package pt.ist.fenixedu.bpi;

import pt.ist.fenixedu.bpi.webservice.BPISyncException_Exception;
import pt.ist.fenixedu.bpi.webservice.BPISyncWebService;
import pt.ist.fenixedu.bpi.webservice.BPISyncWebServiceService;
import pt.ist.fenixedu.bpi.webservice.BpiSyncBean;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.util.*;

/**
 * Created by nurv on 26/07/16.
 */
public class Test {
    public static void main(String ... args) throws BPISyncException_Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Username:");
        String username=scanner.next();
        BPISyncWebService port = new BPISyncWebServiceService().getBPISyncWebServicePort();


        /*******************UserName & Password ******************************/
        Map<String, Object> req_ctx = ((BindingProvider)port).getRequestContext();

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("authorization", Collections.singletonList("Basic " + Base64.getEncoder().encodeToString("user:pass".getBytes())));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        /**********************************************************************/

        BpiSyncBean bean = port.getUser(username);

        System.out.println(bean.getFiscal());
        System.out.println(bean.getPhone());
        System.out.println(bean.getEmail());
        System.out.println(bean.getName());
        System.out.println(bean.getGender());
        System.out.println(bean.getNationality());
        System.out.println(bean.getDateOfBirth());
        System.out.println(bean.getIdDocumentNumber());
        System.out.println(bean.getIdDocumentValidity());
        System.out.println(bean.getPlaceOfBirth());
        System.out.println(bean.getAddress());
        System.out.println(bean.getDistrict());
        System.out.println(bean.getCounty());
        System.out.println(bean.getBorough());
        System.out.println(bean.getZipCode());
        System.out.println(bean.getStreetLayoutCode());
        System.out.println(bean.getDegree());
        System.out.println(bean.getDegreeType());
        System.out.println(bean.getId());
        System.out.println(bean.getEnrolmentAgreement());
    }
}
