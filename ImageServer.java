/*
*   @version 1.2 2012-06-29
*   @author wanghai
*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.*;
import java.lang.*;

import javax.imageio.*;
import javax.swing.*;
import java.net.Socket;
import java.net.ServerSocket;

public class ImageServer {  
    public static ServerSocket ss = null;
    
    public static void main(String args[]) throws IOException{
            ImageThread imageThread = new ImageThread();
            imageThread.start();
    }
}
