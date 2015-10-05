package md.leonis.MyMemory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

public class Figure extends JComponent implements MouseMotionListener, MouseListener {
		static final long serialVersionUID=224L;
		public Color color, color2, color3, color4, color5;
		private int type;
		boolean bBeginDrag = false;
		int imX = 0;
		int imY = 0;
		int x = 500;
		int y = 00;

		Figure(Color color) {
			//super();
			this.setSize(Prefs.figureWidth+3, Prefs.figureWidth+3);
			newColor(color,0);
			//this.type = 0;
			addMouseMotionListener(this);
			addMouseListener(this);
		}

		public void newColor(Color color, int alpha){
			this.color = new Color((int) (color.getRed()), (int) (color.getGreen()), (int) (color.getBlue()), 255-alpha);
			color2=new Color((int) (color.getRed()*0.5), (int) (color.getGreen()*0.5), (int) (color.getBlue()*0.5), 255-alpha);
			color3=new Color((int) (color.getRed()*0.8), (int) (color.getGreen()*0.8), (int) (color.getBlue()*0.8), 255-alpha);
			color4=new Color((int) (color.getRed()*0.8), (int) (color.getGreen()*0.8), (int) (color.getBlue()*0.8), 255-200);
			color5=color;
			repaint();
		}
		
		public void moveTo(int x, int y){
			this.x=x;
			this.y=y;
		}
		
		public void moveTo(double x, double y){
			this.x=(int) x;
			this.y=(int) y;
		}
		
		public void mousePressed(MouseEvent e)
		{
			bBeginDrag = true;
			imX = e.getX();
			imY = e.getY();
			Prefs.layeredPane.setLayer(this, 500);
		}

		public void mouseDragged(MouseEvent e) {
			if (bBeginDrag) inBounds(e.getX(),e.getY());
			repaint();
		}

		public void mouseReleased(MouseEvent e)	{
			if(x<Prefs.halfWidth)x=Prefs.halfWidth+1;
			if(y<Prefs.halfWidth)y=Prefs.halfWidth+1;
			if(x>Prefs.innerBoardWidth) x=Prefs.innerBoardWidth+Prefs.halfWidth-Prefs.figureWidth-2;
			if(y>Prefs.innerBoardHeight) y=Prefs.innerBoardHeight+Prefs.halfWidth-Prefs.figureWidth-2;
		  if (!Prefs.figureManager.allCorrect()) return;
		  if (!inBounds(e.getX(),e.getY())) return;
		  bBeginDrag = false;
		  Prefs.layeredPane.setLayer(this, 0);
		  Prefs.figureManager.released();
		  if (Prefs.centerFigures){
			  x=(x-Prefs.figureWidth/2)/Prefs.width*Prefs.width+Prefs.width-Prefs.figureWidth/2-1;
			  y=(y-Prefs.figureWidth/2)/Prefs.width*Prefs.width+Prefs.width-Prefs.figureWidth/2-1;
		  }
		  repaint();
		}
		
		public void mouseMoved(MouseEvent e) {
			if (bBeginDrag)	{
				//inBounds(e.getX(),e.getY());
				x = -imX + x + e.getX();
			    y = -imY + y + e.getY();
				if (Prefs.figureManager.allCorrect())
					if (inBounds(e.getX(),e.getY())){
						if (x>Prefs.boardWidth-Prefs.figureWidth) return;
						bBeginDrag=false;
						Prefs.layeredPane.setLayer(this, 0);
						Prefs.figureManager.released();
					}
				}
			repaint();
		}
		
		private boolean inBounds(int ex, int ey){
			boolean result=true;
			int tx=x, ty=y;
		    x = -imX + x + ex;
		    y = -imY + y + ey;
		    int wx=Prefs.boardWidth-Prefs.figureWidth;
		    int wy=Prefs.boardHeight-Prefs.figureWidth;
		    if(!((x>=0)&&(x<=wx) || ((x>wx)&&(ex<imX)))) { x=tx; result=false; }
		    if(!((y>=0)&&(y<=wy))) { y=ty; result=false; }
			return result;
		}
		
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
	public void paintComponent(Graphics g) {
		setBounds(x,y,Prefs.figureWidth+3,Prefs.figureWidth+3);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint ( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		if(type!=2){
		g2.setPaint(color4);
		g2.fillOval(1, 1, Prefs.figureWidth+1, Prefs.figureWidth+1);
	
		Point2D center = new Point2D.Float(Prefs.figureWidth/2, Prefs.figureWidth/2);
		float radius = Prefs.figureWidth/2;
		Point2D focus = new Point2D.Float(Prefs.figureWidth/3, Prefs.figureWidth/3);
		float[] dist = {0.0f, 0.6f, 0.9f};
		Color[] colors = {new Color(235,235,235), color, color2};
		RadialGradientPaint p =
				new RadialGradientPaint(center, radius, focus, dist, colors, CycleMethod.NO_CYCLE);
		g2.setPaint(p);
    
		g2.fillOval(0, 0, Prefs.figureWidth, Prefs.figureWidth);
		g2.setPaint(color3);
		g2.drawOval(0, 0, Prefs.figureWidth, Prefs.figureWidth);
		g2.setPaint(color2);
		g2.drawOval(1, 1, Prefs.figureWidth-1, Prefs.figureWidth-1);
	}else{
			g2.setPaint(color5);
			Stroke s = new BasicStroke(3.0f,                      // Width
                    BasicStroke.CAP_ROUND,    // End cap
                    BasicStroke.JOIN_MITER,    // Join style
                    10.0f,                     // Miter limit
                    new float[] {3.0f,4.0f}, // Dash pattern
                    0.0f);                     // Dash phase
			g2.setStroke(s);
			g2.drawOval(1, 1, Prefs.figureWidth-2, Prefs.figureWidth-2);
		}
	}

	public void setType(int i) {
		type=i;
		newColor(color,150);
	}
}