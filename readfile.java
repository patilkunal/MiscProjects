import java.io.*;

public class readfile
{
	public static void main(String args[])
	{
	String x = "Message sender failed to send message for process \"63.l680dg2u.12qpv3.4.l2o778ph.ddo4ud..Peregrine Connectivity.608981833.3A1-V02_00-ARROW_FSC.1.0.1\", and message \"6a.l680dg2u.ddo4ud.\".";
		x = x + "Message sender failed to send message. Transport-level retry max of \"3\" has been hit.";
	try{
	FileInputStream fso = new FileInputStream("D:\\Projects\\x.txt");
	//DataInputStream oos = new DataInputStream(fso);
	
	byte buf[] = new byte[1024];
	fso.skip(47);
	int readcount = fso.read(buf);
	
	String x1 = new String(buf, 0, readcount);
	System.out.println(x1);
	//int i = oos.readInt();
	//oos.writeObject(x);
	//oos.close();
	//oos = null;
	fso.close();
	fso= null; 
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	}
}