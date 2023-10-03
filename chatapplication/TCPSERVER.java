import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
/*import java.util.*;
import java.lang.*;*/
import java.awt.event.*;
import javax.swing.*;


class TCPSERVER{
	static JTextField tf;
	static JTextArea ta;
	static ServerSocket ss;
	static Socket s;
	public static void main(String args[]) throws IOException{
		JFrame f=new JFrame("SERVER");
		tf=new JTextField();
		ta=new JTextArea();
		JButton b=new JButton("SEND");
		
		tf.setBounds(0,400,350,50);
		ta.setBounds(0,0,400,400);
		b.setBounds(350,400,100,50);
		
		f.add(ta);
		f.add(tf);
		f.add(b);
		
		f.setLayout(null);			
		f.setSize(500,500);
		f.setVisible(true);
		
		ss=new ServerSocket(3333);
		s=ss.accept();
		
		b.addActionListener(new ButAction());	

		InputStream is=s.getInputStream();
		
		
		while(true){
			
			byte[] buffer=new byte[1024];
			int bytesread=is.read(buffer);
			String message=new String(buffer,0,bytesread);
			String message1=decrypt(message);

			
			ta.setText(ta.getText()+"\nClient: "+message1);
		}
			
				
	}

	static String decrypt(String ct){
		String pt="",con="abcdefghijklmnopqrstuvwxyz";
		for (int i=0;i<ct.length();i++){
			if ( ct.charAt(i)==' ' ){
				pt=pt+" ";
			}
			else{
				int x=(con.indexOf(ct.charAt(i))-3);
				if (x<0){x=x+26;}
				pt=pt+con.charAt(x%26);			
			}
		}
		return pt;	

	}
}

class ButAction extends TCPSERVER implements ActionListener {
	public void actionPerformed(ActionEvent ae){
		try{
			OutputStream os=s.getOutputStream();
			String pt=tf.getText();
			
			String ct=encrypt(pt);
			
			os.write(ct.getBytes());
			ta.setText(ta.getText()+"\nSERVER(ME): "+tf.getText());
			tf.setText("");
		}
		catch(IOException e){
			System.out.println("problem");
		}
	}
	String encrypt(String pt){
		String ct="",con="abcdefghijklmnopqrstuvwxyz";
		for (int i=0;i<pt.length();i++){
			if ( pt.charAt(i)==' ' ){
				ct=ct+" ";
			}
			else{
				ct=ct+con.charAt((con.indexOf(pt.charAt(i))+3)%26);			
			}
		}
		return ct;
			
	}	

}