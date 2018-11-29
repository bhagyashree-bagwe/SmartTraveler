import java.io.*;

public class DataExplorationPOJO
{
  String state;
  String count;

  public  DataExplorationPOJO(String state,String count)
  {
	this.state = state;
	this.count = count;
  }

  public DataExplorationPOJO(){}

  public String getState(){
   return state;
  }
  
  public void setState(String state){
	this.state=state;
  }

  public String getCount() {
   return count;
  }

  public void setCount(String count) {
	this.count=count;
  }


}
