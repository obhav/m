import java.util.*;

public class Tr{

public static void main(String args[]){

Scanner sc = new Scanner(System.in);

System.out.print("Enter the number of processes : ");
int n = sc.nextInt();
System.out.println();

System.out.println("Ring Forms like ");
for(int i=0;i<n;i++){
System.out.print(i+" -> ");
}
System.out.println("0");

int choice = 0;
int token = 0;

do{
System.out.println("Enter the Sender : ");
int sender = sc.nextInt();

System.out.println("Enter the Receiver : ");
int rev = sc.nextInt();

System.out.println("Enter the Data : ");
int data = sc.nextInt();
System.out.println();

System.out.println("Token Passing in Ring");
for(int i=token;i!=sender;i=(i+1)%n){
System.out.print(i+" --> ");
}
System.out.println(sender);
System.out.println();

System.out.println("Sender "+sender+" Sends data to Receiver "+rev);
System.out.println();

System.out.println("Data Passing from sender to Receiver");
for(int i=sender;i!=rev;i=(i+1)%n){
System.out.print(i+" --> ");
}
System.out.println(rev);
System.out.println();

System.out.println("Receiver "+rev+" Received the Data "+data);
token = sender;

System.out.println("Now Token is at Process "+token);

System.out.println("\n1.Send Again\n2.Exit");
choice = sc.nextInt();

if(choice>2 || choice<=0){
System.out.println("Invalid Choice"); 
break;
}
}while(choice!=2);
}
}

