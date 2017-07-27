import java.sql.*;
import java.io.*;

/**
*
* @author Kunal Patil
* @version 1.0
**/

public class readclob2
{
	public static void main(String args[])
	{	
		String url = "jdbc:oracle:thin:@mozart.harbinger.net:1521:USG2CDEV";
		Driver driver;
		if(args.length == 0)
		{
			System.out.println("\n\tUsage: java readclob <out_filename>\n");
			return;
		}
		String fileName = args[0];
		try
		{
			driver = (Driver)Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			DriverManager.registerDriver(driver);
			Connection con = DriverManager.getConnection(url,"deca","deca123");
			String sqlstr = "select filedata from ctable where filename='classes102.zip'";
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sqlstr);
			rs.next();
			Blob filedata = rs.getBlob(1);
			
			FileOutputStream fileout = new FileOutputStream(fileName);
			InputStream datastream = filedata.getBinaryStream();
			byte[] buf = new byte[255];
			int howmany=0;
			//while(!datastream.ready()) { /*do nothing */ } ;
			System.out.println("now entering the read/write loop");
			while( (howmany=datastream.read(buf,0,255)) != -1 )
			{
				fileout.write(buf, 0, howmany);
				//datastream.skip(255);
				//while(!datastream.ready()) { /*do nothing */ } ;
				//System.out.println("in the loop");
				
				
			}
			fileout.close();
			//System.out.println("out of loop");
			fileout = null;
		 }
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		catch (ClassNotFoundException ce)
		{
			ce.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch(IllegalAccessException iae)
		{
			iae.printStackTrace();
		}
		catch(InstantiationException ie)
		{
			ie.printStackTrace();
		}
		 
		 
	}
}
