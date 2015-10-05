package md.leonis.MyMemory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.text.html.HTMLEditorKit;

public class MainPanel extends JPanel{
	static final long serialVersionUID=222L;
	JLabel textLabel, textLabel2, textLabel3;
	JButton oneButton, twoButton, threeButton;
	JButton fourButton, fifthButton, sixButton;
	JPanel topPanel;
	
	MainPanel(){
		setLayout(new BorderLayout(0, 0));		
	}
	
	public void addContext(){
		add(Prefs.imagePanel, BorderLayout.CENTER);
	
		JPanel menuPanel=new JPanel();
		JPanel menuPanel2=new JPanel();
		JButton sButton=new JButton("Настройки");
		sButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Prefs.settings.setLocationRelativeTo(Prefs.mainFrame);
    			Prefs.settings.setVisible(true);
           }
        });
		JButton hButton=new JButton("Помощь");
		hButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try	{
            		Prefs.dialog.editorPane.setEditorKit(new HTMLEditorKit());
            		
            		//Prefs.dialog.editorPane.setContentType("text/html");
            		
                    Prefs.dialog.editorPane.setPage(this.getClass().getResource("readme.html"));
//                    Prefs.dialog.editorPane.setEditorKit(new HTMLEditorKit());
                } catch(IOException e2) {
                	Prefs.dialog.editorPane.setContentType("text/plain");
                	Prefs.dialog.editorPane.setText("Не удалось прочесть файл справки!!!");
                }
            	Prefs.dialog.setSize((int)(Prefs.boardWidth*1.2), Prefs.boardHeight);
            	Prefs.dialog.setLocationRelativeTo(Prefs.mainFrame);
    			Prefs.dialog.setVisible(true);
           }
        });	
		menuPanel.setLayout(new BorderLayout(0,0));
		menuPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		menuPanel.add(menuPanel2, BorderLayout.SOUTH);
		menuPanel2.add(sButton);
		menuPanel2.add(hButton);

		Font font=new Font("Dialog", Font.PLAIN, (int)(Prefs.width/4.5));
		//setFont(font);
		
		topPanel=new JPanel();
		topPanel.setLayout(new GridLayout(4, 0, 0, 0));
		topPanel.setBounds(0,0,300, 300);
		menuPanel.add(topPanel, BorderLayout.NORTH);
		textLabel=new JLabel();
		textLabel2=new JLabel();
		textLabel3=new JLabel();
		textLabel.setFont(font);
		textLabel2.setFont(font);
		textLabel3.setFont(font);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(textLabel);
		topPanel.add(textLabel2);
		topPanel.add(textLabel3);
		
		JPanel fPanel=new JPanel();
		fPanel.setLayout(new FlowLayout(FlowLayout.CENTER,3,0));
		oneButton=new JButton("Расположить случайно");
		oneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {Prefs.action1();}
		});
		twoButton=new JButton("Всё");
		twoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {Prefs.action2();}
		});
		threeButton=new JButton("Закрыть окно");
		threeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {Prefs.action3();}
		});
	
		fourButton=new JButton("Подтвердить");
		fourButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {Prefs.action11();}
		});
		fifthButton=new JButton("Начать сначала");
		fifthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {Prefs.action12();}
		});
		sixButton=new JButton("Закрыть");
		sixButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {Prefs.action13();}
		});
		fPanel.add(oneButton);
		fPanel.add(twoButton);
		fPanel.add(threeButton);
		fPanel.add(fourButton);
		fPanel.add(fifthButton);
		fPanel.add(sixButton);
		oneButton.setVisible(false);
		twoButton.setVisible(false);
		threeButton.setVisible(false);
		fourButton.setVisible(false);
		fifthButton.setVisible(false);
		sixButton.setVisible(false);
		topPanel.add(fPanel);
		
		add(menuPanel, BorderLayout.EAST);
	}
	
	public void newSize(){
		Prefs.calcBoard();
		Prefs.imagePanel.drawBoard();
	}

	public void createAction(String string, String string2, String string3) {
		textLabel.setText(string);
		textLabel2.setText(string2);
		textLabel3.setText(string3);
	}

	public void showFigures() {
		int k=topPanel.getWidth()/2;
			for(int i=0;i<Prefs.figureManager.fl.size();i++){
				Prefs.figureManager.fl.get(i).moveTo(Prefs.boardWidth+k-Prefs.halfWidth,topPanel.getHeight()+Prefs.halfWidth);
				Prefs.figureManager.fl.get(i).setVisible(true);
				Prefs.layeredPane.add(Prefs.figureManager.fl.get(i), JLayeredPane.DRAG_LAYER,10);
			}
	}
}
