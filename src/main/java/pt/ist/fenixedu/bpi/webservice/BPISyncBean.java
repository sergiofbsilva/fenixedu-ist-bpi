package pt.ist.fenixedu.bpi.webservice;

import java.io.Serializable;

/**
 * Created by nurv on 25/07/16.
 */
public class BPISyncBean implements Serializable{
    private String fiscal;
    private String phone;
    private String email;
    private String name;
    private char gender;

    private String nationality;
    private String dateOfBirth;
    private String idDocument;
    private String idDocumentValidity;
    private String placeOfBirth;
    private String address;
    private String district;
    private String county;
    private String borough;
    private String zipCode;
    private String streetLayoutCode;
    private String degreeType;
    private String degree;

    private String id;
    private byte[] enrolmentAgreement;

    public BPISyncBean(){
        super();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] isEnrolmentAgreement() {
        return enrolmentAgreement;
    }

    public void setEnrolmentAgreement(byte[] enrolmentAgreement) {
        this.enrolmentAgreement = enrolmentAgreement;
    }

    public String getFiscal() {
        return fiscal;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdDocumentNumber() {
        return idDocument;
    }

    public void setIdDocumentNumber(String idDocument) {
        this.idDocument = idDocument;
    }

    public String getIdDocumentValidity() {
        return idDocumentValidity;
    }

    public void setIdDocumentValidity(String idDocumentValidity) {
        this.idDocumentValidity = idDocumentValidity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetLayoutCode() {
        return streetLayoutCode;
    }

    public void setStreetLayoutCode(String streetLayoutCode) {
        this.streetLayoutCode = streetLayoutCode;
    }
}
