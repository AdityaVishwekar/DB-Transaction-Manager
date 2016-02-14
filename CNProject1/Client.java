/*
Name    :-  Aditya Chandrakant Vishwekar    

UTA ID  :-  1001228861

*/
import java.util.*;
import java.io.*;
import java.net.*;

public class Client
{
    public static void main( String[] args ) throws Exception
    {
        

        //Initialize parameters
        String server,port,path;
        if( args.length < 3 )
        {
            server = args[ 0 ];
            port="8080";
            path = args[1];
        }
        else {
        server = args[ 0 ];
        port = args[ 1 ];
        path = args[ 2 ];
        }

        //If no port number is specified then default port number is set to '80'
        if( args.length < 3 )
        {
            port="80";
            path = args[1];
        }

        System.out.println( "Loading contents of URL: " + server );
        long startTime = System.currentTimeMillis();
        try
        {
            long connectionstartTime = System.currentTimeMillis();

            // Connect to the server
            Socket socket = new Socket( server, Integer.parseInt(port) );

            // Create input and output streams to read from and write to the server
            PrintStream out = new PrintStream( socket.getOutputStream() );
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            // Follow the HTTP protocol of GET <path> HTTP/1.0 followed by an empty line
            out.println( "GET " + path + " HTTP/1.1" );
            out.println();

            // Read data from the server until we finish reading the document
            String line = in.readLine();
            while( line != null )
            {
                System.out.println( line );
                line = in.readLine();
            }

            System.out.println("Client Details :");
            long elapsedTime = System.currentTimeMillis() - startTime;// Calculating the Response Time 
            System.out.println("Total elapsed http request/response time (RTT) in milliseconds: " + elapsedTime);

            long elapsedTime1 = System.currentTimeMillis() - connectionstartTime;
            System.out.println("Time taken by client to connect to server in milliseconds: " + elapsedTime1);

            System.out.println("Host Name   :" + socket.getInetAddress().getHostName() );
            System.out.println("Port Number : " + socket.getPort());
            // Close our streams
            in.close();
            out.close();
            socket.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}