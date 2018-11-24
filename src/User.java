import java.io.IOException;
import java.io.*;


public class User implements Serializable {

  private String emailId;
  private String name;
  private String password;
  private String userType;
  private String street;
  private String city;
  private String state;
  private int zipCode;
  private int contactNo;


  //Constructor for User
  User( String emailId, String password, String name, String userType, String street, String city, String state, int zipCode, int contactNo){
    this.emailId = emailId;
    this.name = name;
    this.password = password;
    this.userType = userType;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.contactNo = contactNo;
  }

  //user Id getters setters
  public String getEmailId(){
    return emailId;
  }
  public void setEmailId(String emailId){
    this.emailId = emailId;
  }

  public String getName(){
    return name;
  }
  public void setName(String name){
    this.name = name;
  }

  //user password getters setters
  public String getPassword(){
    return password;
  }
  public void setPassword(String password){
    this.password = password;
  }

  //user type getters setters
  public String getUserType(){
    return userType;
  }
  public void setUserType(String userType){
    this.userType = userType;
  }

  //user type getters setters
  public String getStreet(){
    return street;
  }
  public void setStreet(String street){
    this.street = street;
  }

  //user type getters setters
  public String getCity(){
    return city;
  }
  public void setCity(String city){
    this.city = city;
  }

  //user type getters setters
  public String getState(){
    return state;
  }
  public void setState(String state){
    this.state = state;
  }

  //user type getters setters
  public int getZipCode(){
    return zipCode;
  }
  public void setZipCode(int zipCode){
    this.zipCode = zipCode;
  }

  //user type getters setters
  public int getContactNo(){
    return contactNo;
  }
  public void setContactNo(int contactNo){
    this.contactNo = contactNo;
  }

}
