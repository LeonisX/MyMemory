package md.leonis.MyMemory;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class FigureManager {
	ArrayList<Figure> fl;
	final Random random = new Random();
	
	FigureManager(){
		fl = new ArrayList<Figure>();
	}

	public void createFigures() {
		fl.clear();
		for(int i=0;i<Prefs.figures;i++){
			Color c=new Color(random.nextInt(2)*150+100, random.nextInt(2)*150+100, random.nextInt(2)*150+100);
			Figure fig = new Figure(c);
			fig.setVisible(false);			
			fl.add(fig);
		}
	}

	public void released() {
		int wx=Prefs.boardWidth;
		for(int i=0;i<fl.size();i++)
		    if(((fl.get(i).x>wx-Prefs.figureWidth))) return;
    
		if(Prefs.phase==1) Prefs.mainPanel.twoButton.setVisible(true);
			else Prefs.mainPanel.fourButton.setVisible(true);
		if (Prefs.mainFrame!=null) Prefs.mainFrame.pack();
		Prefs.mainPanel.repaint();
	}

	public void rand() {
		do{
			for(int i=0;i<fl.size();i++){
				fl.get(i).bBeginDrag=false;
				if (Prefs.centerFigures){
					fl.get(i).x=random.nextInt(Prefs.hor)*Prefs.width+Prefs.width-Prefs.figureWidth/2-1;
					fl.get(i).y=random.nextInt(Prefs.vert)*Prefs.width+Prefs.width-Prefs.figureWidth/2-1;
				} else {
					fl.get(i).x=random.nextInt(Prefs.boardWidth-Prefs.width*3/2-8)+Prefs.width/2+4;
					fl.get(i).y=random.nextInt(Prefs.boardHeight-Prefs.width*3/2-8)+Prefs.width/2+4;
				}
			}
		}while(!allCorrect());
		Prefs.mainPanel.repaint();
		released();
	}
	
	public boolean allCorrect() {
		boolean result=true;
		if (fl.size()<2) return result;
		a: for(int i=0;i<fl.size()-1;i++)
			for(int j=i+1;j<fl.size();j++){
				if (fl.get(i).x>Prefs.boardWidth) continue;
				if (fl.get(j).x>Prefs.boardWidth) continue;
				int kx=fl.get(i).x-fl.get(j).x;
				int ky=fl.get(i).y-fl.get(j).y;
				if(Math.sqrt(kx*kx+ky*ky)<Prefs.figureWidth+1) {
					result=false;
					break a;
				}
			}
		return result;
	}

	public void newSize(int oldWidth) {
		double d = 1.0d*Prefs.width/oldWidth;
		for (int i=0;i<fl.size();i++){
			fl.get(i).moveTo(fl.get(i).x*d, fl.get(i).y*d);
			fl.get(i).repaint();
		}
		
	}

}
