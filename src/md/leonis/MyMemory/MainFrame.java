package md.leonis.MyMemory;

import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class MainFrame extends JFrame implements ComponentListener, WindowListener, WindowStateListener {
	static final long serialVersionUID=55677L;
	
	MainFrame() {
		setTitle("Тренажёр памяти");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false);
		//setUndecorated(true);
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(getToolkit().getImage(getClass().getResource("images/image16.png")));
		images.add(getToolkit().getImage(getClass().getResource("images/image24.png")));
		images.add(getToolkit().getImage(getClass().getResource("images/image32.png")));
		setIconImages(images);

//		setBounds(Prefs.top, Prefs.left, 100, 100);

		setContentPane(Prefs.mainPanel);
		Prefs.mainPanel.addContext();
		//width=contentPane.imagePanel.width;
		pack();
		
		checkDimensions();
			
		Prefs.layeredPane = getLayeredPane();
		addComponentListener(this);
		addWindowListener(this);
}

	private void checkDimensions() {
		if((getWidth()+Prefs.left>Prefs.screenWidth)||(getHeight()+Prefs.top>Prefs.screenHeight-30)) {
			Prefs.calcWidth();
			Prefs.calcBoard();
			Prefs.imagePanel.drawBoard();
			Prefs.top=-1;
		}
		repaint();
		pack();
		if(Prefs.top<0){
			Prefs.top=(Prefs.screenHeight-getHeight())/2;
			Prefs.left=(Prefs.screenWidth-getWidth())/2;
		}
		setLocation(Prefs.left,Prefs.top);
	}

	public void componentResized(ComponentEvent evt) {
		removeComponentListener(this);
		int oldWidth=Prefs.width;
		
		Prefs.width=Prefs.imagePanel.getHeight()/(Prefs.vert+1);
		Prefs.halfWidth=Prefs.width/2;

		Prefs.mainPanel.newSize();
		
		Prefs.figureManager.newSize(oldWidth);
		
		double d = 1.0d*Prefs.width/oldWidth;
		int k=(int)(Prefs.width/4.5*d);
		if (k<16) k=16;
		Font font=new Font("Dialog", Font.PLAIN, k);
		Prefs.mainPanel.textLabel.setFont(font);
		Prefs.mainPanel.textLabel2.setFont(font);
		font=UIManager.getFont("Label.font");
		font=new Font(font.getName(), Font.PLAIN, font.getSize());
		Prefs.mainPanel.textLabel3.setFont(font);

		pack();
		checkDimensions();
		addComponentListener(this);
		addWindowStateListener(this);
	}
	public void componentHidden(ComponentEvent evt) {}
	
	public void componentMoved(ComponentEvent evt) {
		Point p=this.getLocation();
		Prefs.top=p.y;
		Prefs.left=p.x;
	}
	
	public void componentShown(ComponentEvent evt) {}
	
    public void windowClosing(WindowEvent event) {
    	if(Prefs.action==2) Prefs.action3(); else{
    		Prefs.action13();
    	}
    }
    public void windowActivated(WindowEvent event) {}
    public void windowClosed(WindowEvent event) {}
    public void windowDeactivated(WindowEvent event) {}
    public void windowDeiconified(WindowEvent event) {}
    public void windowIconified(WindowEvent event) {}
    public void windowOpened(WindowEvent event) {}

	public void windowStateChanged(WindowEvent e) {
		  //перехват события сворачивания окна
		//System.out.println(e);
		//setExtendedState(NORMAL);
		//e.get
		if(getExtendedState() == MAXIMIZED_BOTH )
		    setExtendedState(NORMAL);
	}
}
