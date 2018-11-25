import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Payment")
public class Payment extends HttpServlet {

private String paymentId;
private String userId;
private double amount;
private String cardNo;
private int cvv;
private int MM;
private int YY;
private String rountingNumber;
private String accountNumber;
private String bankName;

public Payment(){}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setaAmount(double amount) {
		this.amount = amount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public int getMM() {
		return MM;
	}

	public void setMM(int MM) {
		this.MM = MM;
	}


	public int getYY() {
		return YY;
	}

	public void setYY(int YY) {
		this.YY = YY;
	}

	public String getRountingNumber() {
		return rountingNumber;
	}

	public void setRountingNumber(String rountingNumber) {
		this.rountingNumber = rountingNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
