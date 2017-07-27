import java.sql.*;
import java.io.*;

/**
*
* @author Kunal Patil
* @version 1.0
**/

public class readblob
{
	public static void main(String args[])
	{	
		String url = "jdbc:oracle:thin:@pridb1u.bspri.itlogon.com:1526:ALP9IPRD";
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
			Connection con = DriverManager.getConnection(url,"b2bhub","b2bhub123");
			String sqlstr = "select args from AUDIT_ERR_PROCESS_TBL where process_id='6j.l680dg2u.ddo4ud.e.l4shrqwo.ddo4ud..Peregrine Connectivity.608981833.3A1CUST-V02_00-Arrow-FSC-QuoteResponse.1.0.1'";
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
