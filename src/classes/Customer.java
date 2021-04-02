package classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty CustomerId;
    private SimpleStringProperty CustFirstName;
    private SimpleStringProperty CustLastName;
    private SimpleStringProperty CustAddress;
    private SimpleStringProperty CustCity;
    private SimpleStringProperty CustProv;
    private SimpleStringProperty CustPostal;
    private SimpleStringProperty CustCountry;
    private SimpleStringProperty CustHomePhone;
    private SimpleStringProperty CustBusPhone;
    private SimpleStringProperty CustEmail;
    private SimpleIntegerProperty AgentId;

    public Customer(int customerId, String custFirstName, String custLastName,
                    String custAddress, String custCity,String custProv, String custPostal, String custCountry,
                    String custHomePhone, String custBusPhone, String custEmail, int agentId) {
        CustomerId = new SimpleIntegerProperty(customerId);
        CustFirstName = new SimpleStringProperty(custFirstName);
        CustLastName = new SimpleStringProperty(custLastName);
        CustAddress = new SimpleStringProperty(custAddress);
        CustCity = new SimpleStringProperty(custCity);
        CustProv = new SimpleStringProperty(custProv);
        CustPostal = new SimpleStringProperty(custPostal);
        CustCountry = new SimpleStringProperty(custCountry);
        CustHomePhone = new SimpleStringProperty(custHomePhone);
        CustBusPhone = new SimpleStringProperty(custBusPhone);
        CustEmail = new SimpleStringProperty(custEmail);
        AgentId = new SimpleIntegerProperty(agentId);
    }

    public int getCustomerId() {
        return CustomerId.get();
    }


    public void setCustomerId(int customerId) {
        this.CustomerId.set(customerId);
    }

    public String getCustFirstName() {
        return CustFirstName.get();
    }


    public void setCustFirstName(String custFirstName) {
        this.CustFirstName.set(custFirstName);
    }

    public String getCustLastName() {
        return CustLastName.get();
    }

    public void setCustLastName(String custLastName) {
        this.CustLastName.set(custLastName);
    }

    public String getCustAddress() {
        return CustAddress.get();
    }

    public void setCustAddress(String custAddress) {
        this.CustAddress.set(custAddress);
    }

    public String getCustCity() {
        return CustCity.get();
    }

    public void setCustCity(String custCity) {
        this.CustCity.set(custCity);
    }

    public String getCustProv() {
        return CustProv.get();
    }

    public void setCustProv(String custProv) {
        this.CustProv.set(custProv);
    }

    public String getCustPostal() {
        return CustPostal.get();
    }

    public void setCustPostal(String custPostal) {
        this.CustPostal.set(custPostal);
    }

    public String getCustCountry() {
        return CustCountry.get();
    }


    public void setCustCountry(String custCountry) {
        this.CustCountry.set(custCountry);
    }

    public String getCustHomePhone() {
        return CustHomePhone.get();
    }


    public void setCustHomePhone(String custHomePhone) {
        this.CustHomePhone.set(custHomePhone);
    }

    public String getCustBusPhone() {
        return CustBusPhone.get();
    }


    public void setCustBusPhone(String custBusPhone) {
        this.CustBusPhone.set(custBusPhone);
    }

    public String getCustEmail() {
        return CustEmail.get();
    }


    public void setCustEmail(String custEmail) {
        this.CustEmail.set(custEmail);
    }

    public int getAgentId() {
        return AgentId.get();
    }


    public void setAgentId(int agentId) {
        this.AgentId.set(agentId);
    }
}
