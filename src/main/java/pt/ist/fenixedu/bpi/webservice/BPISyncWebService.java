package pt.ist.fenixedu.bpi.webservice;

import java.util.Locale;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.fenixedu.academic.domain.Degree;
import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.domain.person.Gender;
import org.joda.time.Period;
import org.joda.time.YearMonthDay;

import com.qubit.solution.fenixedu.bennu.webservices.domain.webservice.WebServiceConfiguration;
import com.qubit.solution.fenixedu.bennu.webservices.domain.webservice.WebServiceServerConfiguration;
import com.qubit.solution.fenixedu.bennu.webservices.services.server.BennuWebService;

@WebService
public class BPISyncWebService extends BennuWebService {

    private static final String DATE_FORMAT = "YYMMdd";

    @WebMethod
    public BPISyncBean getUser(String fiscalCode) throws BPISyncException{
        Person person = (Person) Person.readByContributorNumber(fiscalCode);

        if(person == null){
            throw new BPISyncException("User not found");
        }

        char gender = person.getGender() == Gender.MALE? 'M': 'F';

        Period age = new Period(person.getDateOfBirthYearMonthDay(), new YearMonthDay());

        if (age.getYears() < 18){
            throw new BPISyncException("User is underage");
        }

        if(person.getUser().getBpiCard() == null || !person.getUser().getBpiCard().getAllowSendDetails()){
            throw new BPISyncException("User does not allow to see details");
        }

        Degree degree = person.getStudent().getLastRegistration().getDegree();

        BPISyncBean bean = new BPISyncBean();
        bean.setFiscal(person.getSocialSecurityNumber());
        bean.setPhone(person.getDefaultPhoneNumber());
        bean.setEmail(person.getDefaultEmailAddressValue());
        bean.setName(person.getName());
        bean.setGender(gender);
        bean.setNationality(person.getCountry().getThreeLetterCode());
        bean.setDateOfBirth(person.getDateOfBirthYearMonthDay().toString(DATE_FORMAT));
        bean.setIdDocumentNumber(person.getDocumentIdNumber());
        bean.setIdDocumentValidity(person.getExpirationDateOfDocumentIdYearMonthDay().toString(DATE_FORMAT));
        bean.setPlaceOfBirth(person.getCountryOfBirth().getThreeLetterCode());
        bean.setAddress(person.getAddress());
        bean.setDistrict(person.getDistrictOfResidence());
        bean.setCounty(person.getDistrictSubdivisionOfResidence());
        bean.setBorough(person.getParishOfResidence());

        String postalCode = person.getDefaultPhysicalAddress().getAreaCode();
        if (postalCode.contains("-")){

            String[] codes = postalCode.split("-");
            bean.setZipCode(codes[0]);
            bean.setStreetLayoutCode(codes[1]);
        }else{
            bean.setZipCode(postalCode);
            bean.setStreetLayoutCode("");
        }

        bean.setDegreeType(degree.getDegreeType().getName().getContent(new Locale("pt-PT")));
        bean.setDegree(degree.getFilteredName(ExecutionYear.readCurrentExecutionYear(), new Locale("pt-PT")));
        bean.setId(person.getUsername());
        bean.setEnrolmentAgreement(new byte[0]);

        return bean;
    }

    public static boolean validate(final String username, final String password) {
        final WebServiceConfiguration config =
                WebServiceServerConfiguration.readByImplementationClass(BPISyncWebService.class.getName());
        if (config instanceof WebServiceServerConfiguration) {
            final WebServiceServerConfiguration serverConfig = (WebServiceServerConfiguration) config;
            return username != null && password != null && username.equals(serverConfig.getServiceUsername())
                    && password.equals(serverConfig.getServicePassword());
        }
        return false;
    }
}