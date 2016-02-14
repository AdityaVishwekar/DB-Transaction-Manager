Name 	: Aditya Chandrakant Vishwekar

UTA ID	: 1001228861

INTRODUCTION
------------

A multithreaded Web server which interacts with any standard Web Clients ( You may use any web browser of your choice ).
The Web server and Web client communicate using a text-based protocol called HTTP (Hypertext Transfer Protocol).

 * For a full description of the project, visit the project page:
   http://crystal.uta.edu/~datta/teaching/cse5344-4/CSE5344_Project1_Fall2015.pdf

REQUIREMENTS
------------
This project requires the following modules:

JAVA(jdk1.7.0_75)


COMPILATION AND EXECUTION
-------------------------
Go to the which contains all the files

First compile and execute WebServer.java file
 
1001228861_AdityaVishwekar> javac WebServer.java 

1001228861_AdityaVishwekar> java WebServer

Now, WebServer is running

Compile and execute Client.java file

1001228861_AdityaVishwekar> javac Client.java

1001228861_AdityaVishwekar> java Client localhost 6789 /HelloWorld.html

OR

1001228861_AdityaVishwekar> java Client localhost /HelloWorld.html

OUTPUT
------
You will recieve a response message and contents of the requested file.


Test files
----------
1)HelloWorld.html
2)test1.html
3)test2.txt