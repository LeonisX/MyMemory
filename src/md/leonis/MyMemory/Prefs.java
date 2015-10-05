package md.leonis.MyMemory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

public class Prefs {
	static int width;
	static int halfWidth;
	static int figureWidth;
	static int boardWidth;
	static int boardHeight;
	static int innerBoardWidth;
	static int innerBoardHeight;
	static int hor;//=10
	static int vert;//=10
	static int mode;
	static int phase;
	static int figures;
	static int screenHeight;
	static int screenWidth;
	static int top, left;
	static boolean centerFigures;
	static MainPanel mainPanel;
	static ImagePanel imagePanel;
	static Settings settings;
	static JFrame mainFrame;
	static FigureManager figureManager;
	static JLayeredPane layeredPane;
	static HelpDialog dialog;
	static Preferences myPrefs;
	static ArrayList<Figure> fl;
	static int action;
	static Timer timer;
	static double delay;
	static boolean timerClose;
	
	static {
		fl = new ArrayList<Figure>();
		myPrefs = Preferences.userRoot().node("mymemory");
		figureManager = new FigureManager();
		settings=new Settings();
		settings.pack();

        loadPrefs();
				
		imagePanel = new ImagePanel();
		mainPanel=new MainPanel();
		dialog=new HelpDialog();
		mainFrame = new MainFrame();		
	}
		
	private static void loadPrefs(){
        action=0;
		calcWidth();
		int k=myPrefs.getInt("screenHeight", 0);
		if(k==screenHeight){
			width=myPrefs.getInt("width", width);
			halfWidth=width/2;
		}
		mode=myPrefs.getInt("mode", 0);
		phase=myPrefs.getInt("phase", 1);
		figures=myPrefs.getInt("figures", 1);
		top=myPrefs.getInt("top", -1);
		left=myPrefs.getInt("left", -1);
		centerFigures=myPrefs.getBoolean("centerFigures", true);
		timerClose=myPrefs.getBoolean("timerClose", true);
		calcBoard();
		
		settings.figuresTextField.setText(String.valueOf(figures));
		switch(mode){
			case 0:settings.fullRadioButton.setSelected(true);break;
			case 1:settings.halfRadioButton.setSelected(true);break;
			case 2:settings.quaterRadioButton.setSelected(true);break;
		}
		settings.centerCheckBox.setSelected(centerFigures);
		settings.timerCheckBox.setSelected(timerClose);
		
		if(phase==2) action=10;
			
		//phase 2
//		System.out.println("load"+phase);
		if(phase==1)return;
		fl.clear();
		figureManager.createFigures();
		for(int i=0;i<figures;i++){
			int x=myPrefs.getInt("figure"+i+"x", 0);
			int y=myPrefs.getInt("figure"+i+"y", 0);
			k=myPrefs.getInt("figure"+i+"c",0);
			Color c=new Color(k);
			Figure fig = new Figure(c);
			fig.setVisible(false);
			fig.moveTo(x,y);
			fig.setType(2);
			fl.add(fig);
			figureManager.fl.get(i).newColor(c,0);
		}
	}
	
	public static void savePrefs(){
		try{
			myPrefs.putInt("width", width);
			myPrefs.putInt("mode", mode);
			myPrefs.putInt("phase", phase);
			myPrefs.putInt("figures", figures);
			Point p=mainFrame.getLocation();
			myPrefs.putInt("top", p.y);
			myPrefs.putInt("left", p.x);
			myPrefs.putBoolean("centerFigures", centerFigures);
			myPrefs.putBoolean("timerClose", timerClose);
//			System.out.println("save"+phase);
			for(int i=0;i<figureManager.fl.size();i++){
				myPrefs.putInt("figure"+i+"x", figureManager.fl.get(i).x);
				myPrefs.putInt("figure"+i+"y", figureManager.fl.get(i).y);
				myPrefs.putInt("figure"+i+"c", figureManager.fl.get(i).color.getRGB());
			}
			myPrefs.putInt("screenHeight", screenHeight);
			//phase 2
			//for(int i=0;i<figures;i++)
			
			myPrefs.flush();
//			myPrefs.removeNode();
		} catch (BackingStoreException e){
			System.out.println("Не удалось сохранить настройки программы!!!");
		}
	}
	
	public static void calcWidth(){
		//1440x900
		//68.
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenHeight=(int)dim.getHeight();
		screenWidth=(int)dim.getWidth();
		halfWidth=screenHeight/900*34;
		width=halfWidth*2;
	}
	
	public static void calcBoard(){
		//0 - полная. 1 - 1/2, 2 - 1/4
		hor=10;
		vert=10;
		if (mode>0) hor=5;
		if (mode==2) vert=5;
		innerBoardWidth=hor*width;
		innerBoardHeight=vert*width;
		boardWidth=innerBoardWidth+width;
		boardHeight=innerBoardHeight+width;
		figureWidth=(int) (width/1.8);
	}

	//самое начало работы I-й фазы.
	public static void action0() {
		action=0;
		mainPanel.repaint();
		figureManager.createFigures();
		mainPanel.createAction(" Расположите фишки ", "на доске", "");
		mainPanel.oneButton.setVisible(true);
		if (mainFrame!=null) mainFrame.pack();
		mainPanel.showFigures();
	}

	//нажата oneButton. теперь фигуры будут расположены случайно
	public static void action1() {
		action=1;
		figureManager.rand();
	}
	
	//все фишки на доске, их положение утверждено. остаётся только закрыть окно
	public static void action2() {
		action=2;
		mainPanel.oneButton.setVisible(false);
		mainPanel.twoButton.setVisible(false);
		mainPanel.createAction(" Внимательно запомните ", "положение фишек","");
		mainPanel.threeButton.setVisible(true);
		mainPanel.repaint();
		if (mainFrame!=null) mainFrame.pack();
		delay=21.0d;
		timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.textLabel3.setText(String.format("осталось %.1f секунд", delay));
				delay-=0.1d;
				if(delay<0){
					timer.stop();
					if(timerClose) action3();
				}
			}
		});
        timer.setInitialDelay(0);
        timer.start(); 
	}

	//тут мы уже просто сохраняемся и выходим.
	public static void action3() {
		action=3;
		phase=2;
		action13();
   	 	//savePrefs();
   	 	//mainFrame.dispose();
   	 	//System.exit(0);
  }


	//самое начало работы II-й фазы.
	public static void action10() {
		//phase II
		action=10;
		mainPanel.createAction(" Повторно расположите ", "фишки на доске", "");
		if (mainFrame!=null) mainFrame.pack();
		mainPanel.showFigures();		
	}
	
	//фишки расположены и подтверждены
	public static void action11() {
		action=11;
		mainPanel.fourButton.setVisible(false);
		mainPanel.createAction(" Оцените результат ", "","");
		mainPanel.fifthButton.setVisible(true);
		mainPanel.sixButton.setVisible(true);
		for(int i=0;i<figureManager.fl.size();i++){
			fl.get(i).setVisible(true);
			layeredPane.add(Prefs.fl.get(i), JLayeredPane.DRAG_LAYER,10);
		}
    	phase=1;
    	mainPanel.repaint();
		if (mainFrame!=null) mainFrame.pack();
   }
	
	public static void action12() {
		action=12;
		System.out.println("action12");
		for(int i=0;i<figureManager.fl.size();i++){
			int k=layeredPane.getIndexOf(figureManager.fl.get(i));
			if(k>=0)layeredPane.remove(k);
		}
		for(int i=0;i<Prefs.fl.size();i++){
			int k=layeredPane.getIndexOf(fl.get(i));
			if(k>=0)layeredPane.remove(k);
		}
		mainPanel.fifthButton.setVisible(false);
		mainPanel.sixButton.setVisible(false);
		action=0;
    	action0();
   }
	
	public static void action13() {
		Prefs.savePrefs();
		System.exit(0);
	}
	
	public static void checkFigures() {
		boolean flag=true;
		int k=Integer.parseInt(settings.figuresTextField.getText());
		if(k!=figures) {
			if (k>hor*vert)k=hor*vert;
			figures=k;
			flag=false;
		}
		
		if (settings.fullRadioButton.isSelected()) k=0;
		if (settings.halfRadioButton.isSelected()) k=1;
		if (settings.quaterRadioButton.isSelected()) k=2;
		if(k!=mode){
			mode=k;
			flag=false;
		}
		if(phase==2)return;
		if(flag)return; 
		mainPanel.twoButton.setVisible(false);
		mainPanel.threeButton.setVisible(false);
		mainPanel.fifthButton.setVisible(false);
		mainPanel.sixButton.setVisible(false);
		mainPanel.newSize();
		if (mainFrame!=null) mainFrame.pack();
		action12();
	}
	
}
