package md.leonis.MyMemory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class ImagePanel extends JPanel {
	
	static final long serialVersionUID=556772L;

	private final Color[] colors={new Color(244,242,244), new Color(244,26,28), new Color(244,90,52),
			new Color(228,214,44), new Color(36,154,36), new Color(36,138,204), new Color(28,42,124), 
			new Color(68,26,100), new Color(52,30,20), new Color(4,2,4)};
	
	private BufferedImage img;
		
	/**
	 * @param arg0
	 */
	public ImagePanel() {
		drawBoard();
	}

	
	public void drawBoard() {
		img = new BufferedImage(Prefs.boardWidth, Prefs.boardHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();

		g2.setColor(colors[0]);
		g2.fillRect(0, 0, Prefs.boardWidth, Prefs.boardHeight);
		g2.setColor(colors[9]);
		g2.fillRect(0,0,Prefs.halfWidth, Prefs.halfWidth);
		g2.fillRect(Prefs.hor*Prefs.width+Prefs.halfWidth,0,Prefs.halfWidth, Prefs.halfWidth);
		g2.fillRect(0,Prefs.vert*Prefs.width+Prefs.halfWidth,Prefs.halfWidth, Prefs.halfWidth);
		g2.fillRect(Prefs.hor*Prefs.width+Prefs.halfWidth,Prefs.vert*Prefs.width+Prefs.halfWidth,Prefs.halfWidth, Prefs.halfWidth);		
		g2.translate(Prefs.halfWidth,Prefs.halfWidth);
		for(int i=0;i<Prefs.hor;i++)
			for(int j=0;j<Prefs.vert;j++){
				if ((j%2)==0) g2.setColor(colors[i]);
					else g2.setColor(colors[9-i]);
				g2.fillRect(i*Prefs.width, j*Prefs.width, Prefs.width, Prefs.width);
			}
		g2.translate(0, -Prefs.halfWidth);
		g2.setColor(new Color(130,130,130,100));
		for(int i=0;i<=Prefs.hor;i++) g2.drawLine(i*Prefs.width-1,0,i*Prefs.width-1,(Prefs.vert+1)*Prefs.width-1);
		g2.translate(-Prefs.halfWidth,Prefs.halfWidth);
		for(int i=0;i<=Prefs.vert;i++) g2.drawLine(0,i*Prefs.width-1,(Prefs.hor+1)*Prefs.width-1,i*Prefs.width-1);
		g2.translate(0,-Prefs.halfWidth);
		g2.setColor(colors[9]);
		//Font font=UIManager.getLookAndFeelDefaults().getFont("defaultFont");
		//Font font=getFont();
		//Font font=UIManager.getFont("Label.font");
		Font font=new Font("Dialog", Font.BOLD, (int) (Prefs.width/3.3));
		g2.setFont(font);
		FontMetrics fm = g2.getFontMetrics(font);		
		g2.setRenderingHint ( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON ); 
		for(int i=0;i<Prefs.hor;i++) {
			String s=Integer.toString(i+1);
			Rectangle2D k = fm.getStringBounds(s, g2);
			g2.drawString(s, (i+1)*Prefs.width-(int) k.getCenterX(), Prefs.halfWidth/2 - (int) k.getCenterY());
			g2.drawString(s, (i+1)*Prefs.width-(int) k.getCenterX(), Prefs.vert*Prefs.width+Prefs.halfWidth*3/2 - (int) k.getCenterY());
		}
		for(int i=0;i<Prefs.vert;i++) {
			String s=Integer.toString(i+1);
			Rectangle2D k = fm.getStringBounds(s, g2);
			g2.drawString(s, Prefs.halfWidth/2 - (int) k.getCenterX(), (i+1)*Prefs.width-(int) k.getCenterY());
			g2.drawString(s, Prefs.hor*Prefs.width+ Prefs.halfWidth*3/2 - (int) k.getCenterX(), (i+1)*Prefs.width-(int) k.getCenterY());
		}
		setPanelDimensions();
	}

	private void setPanelDimensions() {
		Dimension size = new Dimension(img.getWidth(), img.getHeight());
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);		
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(img != null) g.drawImage(img, 0, 0, null);     
    }
}
