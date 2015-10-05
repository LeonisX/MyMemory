package md.leonis.MyMemory;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;

import java.awt.GridLayout;

import javax.swing.JCheckBox;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Settings extends JFrame {

	private JPanel contentPane;
	static final long serialVersionUID=9999L;
	
	JRadioButton fullRadioButton, halfRadioButton, quaterRadioButton;
	JCheckBox centerCheckBox;
	public JTextField figuresTextField;
	JCheckBox timerCheckBox;


	/**
	 * Create the frame.
	 */
	public Settings() {
		setResizable(false);
		setTitle("Настройки программы");
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("Принять");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Prefs.checkFigures();
				setVisible(false);
			}
		});
		panel.add(okButton);
		
		JPanel gabaritePanel = new JPanel();
		gabaritePanel.setBorder(new TitledBorder(null, " Габариты доски ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(gabaritePanel, BorderLayout.NORTH);
		gabaritePanel.setLayout(new GridLayout(3, 0, 0, 0));
		
		fullRadioButton = new JRadioButton("Полный размер (10x10)");
		gabaritePanel.add(fullRadioButton);
		
		 halfRadioButton = new JRadioButton("1/2");
		gabaritePanel.add(halfRadioButton);
		
		quaterRadioButton = new JRadioButton("1/4");
		gabaritePanel.add(quaterRadioButton);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(fullRadioButton);
		bg.add(halfRadioButton);
		bg.add(quaterRadioButton);

		JPanel othersPanel = new JPanel();
		contentPane.add(othersPanel, BorderLayout.CENTER);
		othersPanel.setLayout(new GridLayout(3, 0, 0, 0));
		
		JPanel figuresPanel = new JPanel();
		figuresPanel.setBorder(new TitledBorder(null, " Количество фигур ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		othersPanel.add(figuresPanel);
		figuresPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		figuresTextField = new JTextField();
		figuresPanel.add(figuresTextField);
		figuresTextField.setColumns(10);
		figuresTextField.addKeyListener(new KeyAdapter() {
	        public void keyTyped(KeyEvent e) {
	          char a = e.getKeyChar();
	          if ((a == KeyEvent.VK_BACK_SPACE) && figuresTextField.getText().length() == 0 ) figuresTextField.setText("1");
	          if (!Character.isDigit(a)) e.consume();
	        }
	      });
		
		JPanel randomPanel = new JPanel();
		randomPanel.setBorder(new TitledBorder(null, " При случайном расположении предметов ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		othersPanel.add(randomPanel);
		randomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		centerCheckBox = new JCheckBox("Помещать их строго в центре цветной области");
		randomPanel.add(centerCheckBox);
		centerCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Prefs.centerFigures=centerCheckBox.isSelected();
			}
		});
		
		JPanel timerPanel = new JPanel();
		timerPanel.setBorder(new TitledBorder(null, " Другие настройки ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		othersPanel.add(timerPanel);
		timerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		timerCheckBox = new JCheckBox("По истечении времени закрывать окно автоматически");
		timerCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Prefs.timerClose=timerCheckBox.isSelected();
			}
		});
		timerPanel.add(timerCheckBox);
	}

}
