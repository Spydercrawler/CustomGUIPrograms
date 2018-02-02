package OldProjects;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CubePanel extends JPanel {
	private float cameraRotX = 0;
	private float cameraRotY = 0;
	private final float fovX = (float)Math.PI / 2;
	private final float fovY = (float)Math.PI / 4;
	private final float focalLength = 1.0f;
	private float xDegreeChange;
	private float yDegreeChange;
	
	private static final int defaultWidth = 400;
	private static final int defaultHeight = 400;
	private int localWidth;
	private int localHeight;
	
	public CubePanel() {
		localWidth = defaultWidth;
		localHeight = defaultHeight;
		xDegreeChange = fovX / localWidth;
		yDegreeChange = fovY / localWidth;
		this.addComponentListener(new ResizeListener());
	}
	Runnable UpdateScript = new Runnable() {
		@Override
        public void run() {
			
        }
	};
	public Dimension getPreferredSize() {
        return new Dimension(defaultWidth,defaultHeight);
    }
	private static void OpenFrame() {
		JFrame f = new JFrame();
		// f.setUndecorated(true);
		// f.setBackground(new Color(0, 0, 0, 0));
		f.setTitle("Clock");
        f.add(new CubePanel());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                OpenFrame();
            }
        });
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if(localHeight == 0 || localWidth == 0)
			return;
		BufferedImage bi = new BufferedImage(localWidth, localHeight, BufferedImage.TYPE_INT_RGB);
		float i = (localWidth)/2.0f *-1;
		for(int r = 0;r<bi.getHeight();r+=1) {
			float j = (localHeight)/2.0f *-1;
			for(int c = 0;c<bi.getWidth();c+=1) {
				double x = focalLength * Math.cos(i);
				double y = focalLength * Math.sin(i);
				double z = focalLength * Math.sin(j);
				if(Math.abs(x) > Math.abs(y) && Math.abs(x) > Math.abs(z)) {
					if(x>0) {
						bi.setRGB(c, r, Color.RED.getRGB());
					} else {
						bi.setRGB(c, r, Color.GREEN.getRGB());
					}
				} else if (Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(z)) {
					if(y>0) {
						bi.setRGB(c, r, Color.BLUE.getRGB());
					} else {
						bi.setRGB(c, r, Color.ORANGE.getRGB());
					}
				} else {
					if(z>0) {
						bi.setRGB(c, r, Color.MAGENTA.getRGB());
					} else {
						bi.setRGB(c, r, Color.YELLOW.getRGB());
					}
				}
				j+=yDegreeChange;
			}
			i+=xDegreeChange;
		}
		g2.drawImage(bi, 1, 1, null);
	}
	private class ResizeListener implements ComponentListener {

		@Override
		public void componentHidden(ComponentEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentMoved(ComponentEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentResized(ComponentEvent arg0) {
			Rectangle Bounds = arg0.getComponent().getBounds();
			localWidth = Bounds.width;
			localHeight = Bounds.height;
		}

		@Override
		public void componentShown(ComponentEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
