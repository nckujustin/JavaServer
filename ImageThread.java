import java.lang.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.*;
import javax.swing.*;
import java.net.Socket;
import java.net.ServerSocket;

class ImageThread extends Thread{

	public static ServerSocket ss = null;

	public void run(){
	//create a new socket to accept connection
	try{
		ss = new ServerSocket(6000);
	} catch(IOException e){
		System.out.println("ERR!");
	}

	//create a image frame for this program
	final ImageFrame frame = new ImageFrame(ss);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	//get the next jpeg img from client
	while(true){
		try{
			frame.panel.getimage();
		} catch(IOException e){
			System.out.println("ERR!");
		}
		//due to our frame need to be repaint
		frame.repaint();
	}
  }
}

/** 
    A frame with an image panel
*/
@SuppressWarnings("serial")
class ImageFrame extends JFrame{
    //use this to display the image from client
    public ImagePanel panel;

    public ImageFrame(ServerSocket ss){
        // get screen dimensions       
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // center frame in screen
        setTitle("ImageWindow");
        setLocation((screenWidth / 2) - 195, (screenHeight - DEFAULT_HEIGHT) / 2);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.getContentPane().setBackground( Color.GRAY );

        // add panel to frame
        this.getContentPane().setLayout(null);
        panel = new ImagePanel(ss);
        //set the size of image
        panel.setSize(640,480); //640 480
        panel.setLocation((DEFAULT_WIDTH-640), 110);
        add(panel);
    }

    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 700;
}

/**
    A panel that displays a tiled image
*/
@SuppressWarnings("serial")
class ImagePanel extends JPanel {     
    private ServerSocket ss;
    private Image image;
    private InputStream input_stream;
    
    static int ImageCount = 0;
    static long current_time = 0;
    static long last_time = 0;
    static double fps = 0;

    public ImagePanel(ServerSocket ss) {  
        this.ss = ss;
    }
    
    public void getimage() throws IOException{
        Socket s = this.ss.accept();
        //while(this.ss.accept)
        if(ImageCount % 30 == 0){
            current_time = System.currentTimeMillis();
            if((current_time - last_time) != 0){
                fps = 30/((double)(current_time - last_time)/1000);
            }
            System.out.println("Got image! fps : " + fps);
            last_time = current_time;
        }
        this.input_stream = s.getInputStream();
        this.image = ImageIO.read(input_stream);
        this.input_stream.close();
        ImageCount++;
    }
   
    public void paintComponent(Graphics g){  
        super.paintComponent(g);    
        if (image == null) return;
        
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(Math.toRadians(90), 320, 240);
        //g2.rotate(Math.toRadians(0),bi.getWidth(), bi.getHeight()-bi.getWidth());
        g2.drawImage(image, 0, 0, null);
        //g.drawImage(image, 0, 0, null);
    }

}