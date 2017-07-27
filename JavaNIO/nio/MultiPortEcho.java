// $Id$

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class MultiPortEcho
{
  private int ports[];
  private ByteBuffer echoBuffer = ByteBuffer.allocate( 1024 );
  String cmd  = null;
  public MultiPortEcho( int ports[] ) throws IOException {
    this.ports = ports;

    go();
  }

  private void go() throws IOException {
    // Create a new selector
    Selector selector = Selector.open();

    // Open a listener on each port, and register each one
    // with the selector
    for (int i=0; i<ports.length; ++i) {
      ServerSocketChannel ssc = ServerSocketChannel.open();
      ssc.configureBlocking( false );
      ServerSocket ss = ssc.socket();
      InetSocketAddress address = new InetSocketAddress( ports[i] );
      ss.bind( address );

      SelectionKey key = ssc.register( selector, SelectionKey.OP_ACCEPT );

      System.out.println( "Going to listen on "+ports[i] );
    }

    while (true) {
      int num = selector.select();

      Set selectedKeys = selector.selectedKeys();
      Iterator it = selectedKeys.iterator();

      while (it.hasNext()) {
        SelectionKey key = (SelectionKey)it.next();

        if ((key.readyOps() & SelectionKey.OP_ACCEPT)
          == SelectionKey.OP_ACCEPT) {
          // Accept the new connection
          ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
          SocketChannel sc = ssc.accept();
          sc.configureBlocking( false );

          // Add the new connection to the selector
          SelectionKey newKey = sc.register( selector, SelectionKey.OP_READ );
          it.remove();

          System.out.println( "Got connection from "+sc );
        } else if ((key.readyOps() & SelectionKey.OP_READ)
          == SelectionKey.OP_READ) {
          // Read the data
          SocketChannel sc2 = (SocketChannel)key.channel();

          // Echo data
          int bytesEchoed = 0;
          while (true) {

            int r = sc2.read( echoBuffer );

            if (r<=0) {
              break;
            }

            
            /*if((echoBuffer.get(echoBuffer.position()) == 13) || (echoBuffer.get(echoBuffer.position()) == 10))
            {
            	byte command[] = new byte[1024];
            	echoBuffer.get(command);
            	cmd = new String(command);            	
      		break;
            }
            */
            //System.out.println("Buffer at " + echoBuffer.position());
            if(echoBuffer.get(echoBuffer.position()-1) == 10)
            {
            	echoBuffer.flip();
            	byte parse[] = new byte[4];
            	echoBuffer.get(parse);
            	cmd = new String(parse);            	
            	System.out.println(cmd);
	          if(cmd != null)
	    	     if(cmd.equals("quit"))
	    	     {
		    		sc2.close();
		    		selectedKeys.clear();
			}
           echoBuffer.clear();
            }
            //sc.write( echoBuffer );
            bytesEchoed += r;
          }
	  cmd = null;
	  
          System.out.println( "Echoed "+bytesEchoed+" from "+sc2 );

          it.remove();
        }
      }

//System.out.println( "going to clear" );
//      selectedKeys.clear();
//System.out.println( "cleared" );
    }
  }

  static public void main( String args[] ) throws Exception {
    if (args.length<=0) {
      System.err.println( "Usage: java MultiPortEcho port [port port ...]" );
      System.exit( 1 );
    }

    int ports[] = new int[args.length];

    for (int i=0; i<args.length; ++i) {
      ports[i] = Integer.parseInt( args[i] );
    }

    new MultiPortEcho( ports );
  }
}
