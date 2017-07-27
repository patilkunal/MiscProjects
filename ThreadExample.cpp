//
//  Project    :  MultiThread Sample Code
//  Author    :  Josh Fruits
//  Website    :  http://www.programmerscorner.com
//  Date    :  May 16, 2003
//  Comments  :  feedback@programmerscorner.com
//  Description  :  This is a demonstration of using multithreading in your c++ app
//          You may freely use and distribute this code
//          For ease of posting, the header and source files have been combined
//          To create the App:
//            Create a Win32 Console App
//            Add a C++ Source File 
//            Paste in the entire code block and compile


#include <windows.h> 

//Header declarations
long WINAPI ThreadTwo(long lParam);
long WINAPI ThreadThree(long lParam);
long WINAPI ThreadFour(long lParam);


int main(void)
{
  HANDLE hThread[3];
  DWORD dwID[3];
  DWORD dwRetVal = 0;

  //before multithreading, do something to show the messagebox will
  //stop processing in the current thread
  MessageBox(NULL,"This messagebox will stop processing of the thread. No other \nmessageboxes will appear.","Main Messagebox", NULL);  

  //release the threads. Remember, ThreadOne is our main thread
  hThread[0] = CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)ThreadTwo,NULL,0,&dwID[0]);
  hThread[1] = CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)ThreadThree,NULL,0,&dwID[1]);
  hThread[2] = CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)ThreadFour,NULL,0,&dwID[2]);
  
  //Run the main thread as well
  MessageBox(NULL,"This Messagebox is from Thread 1.","Messagebox", NULL);  


  //wait for all threads to complete before continuing
  dwRetVal = WaitForMultipleObjects(3, hThread, TRUE, INFINITE);
  
  //Display a messagebox to show that the Wait state has finished
  MessageBox(NULL,"This Messagebox is to show that all threads have completed.","Messagebox", NULL);  

  
  //close handles
  CloseHandle(hThread[0]);
  CloseHandle(hThread[1]);
  CloseHandle(hThread[2]);


  //end the main function
  return 0;
}

long WINAPI ThreadTwo(long lParam)
{
  MessageBox(NULL,"This Messagebox is from Thread 2.","Messagebox", NULL);  
  return 0;
}

long WINAPI ThreadThree(long lParam)
{
  MessageBox(NULL,"This Messagebox is from Thread 3.","Messagebox", NULL);  
  return 0;
}

long WINAPI ThreadFour(long lParam)
{
  MessageBox(NULL,"This Messagebox is from Thread 4.","Messagebox", NULL);  
  return 0;
}
