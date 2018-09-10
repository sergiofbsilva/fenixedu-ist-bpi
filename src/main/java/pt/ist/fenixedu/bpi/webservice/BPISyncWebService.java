package pt.ist.fenixedu.bpi.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.fenixedu.academic.domain.Degree;
import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.domain.person.Gender;
import org.fenixedu.academic.domain.student.Student;
import org.fenixedu.bennu.BPIIntegrationConfiguration;
import org.fenixedu.bennu.BennuSpringContextHelper;
import org.fenixedu.bennu.core.rest.JsonBodyReaderWriter;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
import org.joda.time.Period;
import org.joda.time.YearMonthDay;

import com.qubit.solution.fenixedu.bennu.webservices.domain.webservice.WebServiceConfiguration;
import com.qubit.solution.fenixedu.bennu.webservices.domain.webservice.WebServiceServerConfiguration;
import com.qubit.solution.fenixedu.bennu.webservices.services.server.BennuWebService;

import pt.ist.fenixedu.integration.ui.spring.service.RegistrationDeclarationForBanksService;
import pt.ist.registration.process.domain.RegistrationDeclarationFile;

@WebService
public class BPISyncWebService extends BennuWebService {

    private static final String DATE_FORMAT = "yyyyMMdd";
    private final Client client;

    public BPISyncWebService() {
        this.client = ClientBuilder.newClient();
        this.client.register(MultiPartFeature.class);
        this.client.register(JsonBodyReaderWriter.class);
    }

    @WebMethod
    public BPISyncBean getUser(String fiscalCode) throws BPISyncException {
        Person person = (Person) Person.readByContributorNumber(fiscalCode);

        if (person == null) {
            throw new BPISyncException("User not found");
        }

        String gender = person.getGender() == Gender.MALE ? "M" : "F";

        Period age = new Period(person.getDateOfBirthYearMonthDay(), new YearMonthDay());

        if (age.getYears() < 18) {
            throw new BPISyncException("User is underage");
        }

        if (person.getUser().getBpiCard() == null || !person.getUser().getBpiCard().getAllowSendBankDetails()) {
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
        bean.setDistrict(person.getDistrictOfBirth());
        bean.setCounty(person.getDistrictSubdivisionOfBirth());
        bean.setBorough(person.getParishOfBirth());

        bean.setAddress(person.getAddress());
        String postalCode = person.getDefaultPhysicalAddress().getAreaCode();
        if (postalCode.contains("-")) {

            String[] codes = postalCode.split("-");
            bean.setZipCode(codes[0]);
            bean.setStreetLayoutCode(codes[1]);
        } else {
            bean.setZipCode(postalCode);
            bean.setStreetLayoutCode("");
        }

        bean.setDegreeType(degree.getDegreeType().getName().getContent(new Locale("pt-PT")));
        bean.setDegree(degree.getFilteredName(ExecutionYear.readCurrentExecutionYear(), new Locale("pt-PT")));
        bean.setId(person.getUsername());
        bean.setEnrolmentAgreement(getRegistrationDeclaration(person));

        return bean;
    }

    private byte[] getRegistrationDeclaration(Person person) {
        Student student = person.getStudent();
        RegistrationDeclarationForBanksService registrationDeclarationForBanksService = BennuSpringContextHelper.getBean
        (RegistrationDeclarationForBanksService.class);

        InputStream registrationDeclarationFileForBanks =
                registrationDeclarationForBanksService.getRegistrationDeclarationFileForBanks(student.getLastRegistration());

        final StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("file",
                registrationDeclarationFileForBanks, "declaracao.pdf", new MediaType("application", "pdf"));
        try (final FormDataMultiPart formDataMultiPart = new FormDataMultiPart()) {
            formDataMultiPart.bodyPart(streamDataBodyPart);
            return this.client.target(BPIIntegrationConfiguration.getConfiguration().converterUrl())
                    .path("api").path("v1").path("convert").request()
                    .header("Authorization", BPIIntegrationConfiguration.getConfiguration().converterSecret())
                    .post(Entity.entity(formDataMultiPart, MediaType.MULTIPART_FORM_DATA_TYPE),  byte[].class);
        } catch (IOException e) {
            return new byte[0];
        }
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
