import java.rmi.*;

public class Server{


public static void main(String args[]){

try{
ServerImpl serverImpl = new ServerImpl();
Naming.rebind("Server" , serverImpl);

}catch(Exception e){
System.out.println("Exception Occurred at Server Side : "+e.getMessage());
}

}

}
