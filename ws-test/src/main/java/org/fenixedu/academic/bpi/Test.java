package org.fenixedu.academic.bpi;

import org.fenixedu.academic.bpi.webservice.BPISyncException_Exception;
import org.fenixedu.academic.bpi.webservice.BPISyncWebServiceService;
import org.fenixedu.academic.bpi.webservice.BpiSyncBean;

import java.util.Scanner;

/**
 * Created by nurv on 26/07/16.
 */
public class Test {
    public static void main(String ... args) throws BPISyncException_Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Username:");
        String username=scanner.next();

        BpiSyncBean bean = new BPISyncWebServiceService().getBPISyncWebServicePort().getUser(username);

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
