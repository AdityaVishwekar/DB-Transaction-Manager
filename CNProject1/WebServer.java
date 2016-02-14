/*
Name 	:-	Aditya Chandrakant Vishwekar	

UTA ID 	:-	1001228861

*/
import java.io.*;
import java.util.*;
import java.net.*;

/*
This class creates a Webserver to serve multiple clients in parallel, or
to perform multiple file transfers to a single client in parallel. 

*/
public final class WebServer
{
	public static void main(String args[]) throws Exception
	{
		//set the port number
		int port = 6789;
		//Establish the listen socket.
		ServerSocket listenSocket = new ServerSocket(6789);
		//Process HTTP service requests in an infinite loop
		while (true)
		{
			//Listen for a TCP connection request
			Socket connectionSocket = listenSocket.accept();
	 		//Construct an object to process the HTTP request message.
        		HttpRequest request = new HttpRequest(connectionSocket);
        		// Create a new thread to process the request.
        		Thread thread = new Thread(request);
        		// Start the thread.
        		thread.start();
		}
	}
}

final class HttpRequest implements Runnable
{
	final static String CRLF = "\r\n";
	Socket socket;
	//Constructor
	public HttpRequest(Socket socket) throws Exception
	{
		this.socket = socket;
	}
	// Implement the run() method of the Runnable interface.
	public void run()
	{
		try {
			processRequest();
		    
		} catch (Exception e) {
			 System.out.println(e);
			// e.printStackTrace();
		}
	}
	private void processRequest() throws Exception
	{
		long startTime = System.currentTimeMillis();

		//Get a reference to the socket's input and output streams.
		InputStream is = socket.getInputStream(); 
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());

		//Set up input stream filters.
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		Calendar today = new GregorianCalendar();

		int sec = today.get(Calendar.SECOND);

		//Get the request line of the HTTP request message.
		String requestLine = br.readLine(); 

		//Display request line.
		System.out.println();
		System.out.println(requestLine);

		// Extract the filename from the request line
		StringTokenizer tokens = new StringTokenizer (requestLine);
		tokens.nextToken();
		String fileName = tokens.nextToken();

		// Prepend a "." so that file request is within the current directory.
		fileName = "." + fileName;

		// Open the requested file.
		FileInputStream fis=null;
		boolean fileExists=true;
		try{
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e){
			fileExists=false;
		}

		// Construct the response message.
		String statusLine=null;
		String contentTypeLine=null;
		String entityBody=null;
		if (fileExists) {
			statusLine="200 OK" + CRLF;
			contentTypeLine="Content-type:"+contentType(fileName)+CRLF;
		}
		else {
			statusLine="404 NOT FOUND" + CRLF;
			contentTypeLine="Content Not Found!" + CRLF;
			entityBody= "<HTML>"+"<HEAD><TITLE>Not Found</TITLE></HEAD>" + "<BODY>Not Found</BODY></HTML>";
		}
		
		// Send the status line.
		os.writeBytes(statusLine);

		// Send the content type line
		os.writeBytes(contentTypeLine);

		// Send a blank line to indicate the end of the header lines
		os.writeBytes(CRLF);
		
		// Send the entity body.
		if(fileExists== true) {
			sendBytes(fis, os);
			fis.close();
		}
		else {os.writeBytes(entityBody);}

		System.out.println("Server Details :");
	    System.out.println("Connection received from (HostName) : " + socket.getInetAddress().getHostName());
	    System.out.println("Socket Number : " + socket.getPort());
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time taken by the server to process client in milliseconds: : " + elapsedTime);
		
		// Close streams
		os.close();
		br.close();
		socket.close();
	}

// sending the file to the client.
private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
	byte[] buffer =new byte[1024];
	int bytes=0;
	while((bytes=fis.read(buffer)) != -1) {
		os.write(buffer,0,bytes);
	}
}

private static String contentType(String fileName)
{
	if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
		return "text/html";
	}
	 if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
      return "image/jpeg";
    }
    if (fileName.endsWith(".gif")) {
      return "image/gif";
    }
    if(fileName.endsWith(".txt")) {
      return "text/plain";
    }
    if(fileName.endsWith(".pdf")) {
      return "application/pdf";
    }
	return "application/octet-stream";// If the file extension is unknown, we return the type application/octet stream.
}
}