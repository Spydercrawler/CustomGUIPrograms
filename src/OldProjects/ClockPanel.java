package OldProjects;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ClockPanel extends JPanel {
	private static final int width = 800;
	private int localHeight;
	private int localWidth;
	//Lengths of hands (Ratios):
	private static final float hourHandLength = 0.5f;
	private static final float minuteHandLength = 0.75f;
	private static final float secondHandLength = 0.75f;
	private static final float numberDistance = 0.85f;
	//Lengths of hands (Pixels):
	private int pixelHourHandLength;
	private int pixelMinuteHandLength;
	private int pixelSecondHandLength;
	private int pixelNumberDistance;
	//Keep track of the time:
	int minute;
	int hour;
	int second;
	
	Calendar calendar;
	Date startDate;
	long previousMillisecondTime;
	final ScheduledExecutorService clockUpdater;
	Runnable clockUpdateScript = new Runnable() {
		@Override
        public void run() {
			second += 1;
        	if(second >= 60) {
        		minute += 1;
        		second -= 60;
        	}
        	if (minute >= 60) {
        		hour += 1;
        		minute -= 60;
        	}
        	if (hour >= 24) {
        		hour = 0;
        	}
            System.out.println(String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second));
            repaint();
        }
	};
	private static void OpenFrame() {
		System.out.println("Created GUI on EDT? "+
		        SwingUtilities.isEventDispatchThread());
		JFrame f = new JFrame();
		// f.setUndecorated(true);
		// f.setBackground(new Color(0, 0, 0, 0));
		f.setTitle("Clock");
        f.add(new ClockPanel());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
	}
	public Dimension getPreferredSize() {
        return new Dimension(width,width);
    }
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                OpenFrame();
            }
        });
	}
	public ClockPanel() {
		setOpaque(false);
		clockUpdater = Executors.newSingleThreadScheduledExecutor();
		Calendar calendar = Calendar.getInstance();
		startDate = new Date();
		calendar.setTime(startDate);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		try {
			Thread.sleep(1000 - calendar.get(Calendar.MILLISECOND));
		} catch (InterruptedException e) {return;}
		second = calendar.get(Calendar.SECOND);
		clockUpdater.scheduleWithFixedDelay(clockUpdateScript, 0, 1, TimeUnit.SECONDS);
		pixelHourHandLength = (int)(width * hourHandLength)/2;
		pixelMinuteHandLength = (int)(width * minuteHandLength)/2;
		pixelSecondHandLength = (int)(width * secondHandLength)/2;
		pixelNumberDistance = (int)(numberDistance * width) / 2;
		localHeight = width;
		localWidth = width;
		this.addComponentListener(new ResizeListener());
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.scale(((float)localWidth)/width, ((float)localHeight) / width);
		draw_hands(g2);
		draw_numbers(g2);
	}
	private void draw_numbers(Graphics2D g2) {
		g2.setFont(new Font("default", Font.BOLD, (width/400) * 16));
		for(int i = 1;i<=12;i++) {
			float angle = (float) ((i * (Math.PI / 6.0f)) - (Math.PI / 2.0));
			int number_x = (width/2) + (int)(pixelNumberDistance * Math.cos(angle));
			int number_y = (width/2) + (int)(pixelNumberDistance * Math.sin(angle));
			g2.drawString(""+i, number_x, number_y);
		}
	}
	private void draw_hands(Graphics2D g2) {
		float secondHandDegree = (float)((second/60.0f) * Math.PI * 2.0f) - (float)(Math.PI / 2);
		int second_x = (width/2) + (int)(pixelSecondHandLength * Math.cos(secondHandDegree));
		int second_y = (width/2) + (int)(pixelSecondHandLength * Math.sin(secondHandDegree));
		g2.setStroke(new BasicStroke(width/400));
		g2.drawLine(width/2, width/2, second_x, second_y);
		float minuteHandDegree = (float)((minute/60.0f) * Math.PI * 2.0f) - (float)(Math.PI / 2);
		int minute_x = (width/2) + (int)(pixelMinuteHandLength * Math.cos(minuteHandDegree));
		int minute_y = (width/2) + (int)(pixelMinuteHandLength * Math.sin(minuteHandDegree));
		g2.setStroke(new BasicStroke((width/400) * 3));
		g2.drawLine(width/2, width/2, minute_x, minute_y);
		int newHour = (hour>12)?hour-12:hour;
		float hourDegree = (float)((newHour/12.0f) * Math.PI * 2.0f) - (float)(Math.PI / 2);
		float hourHandOffset = (float)((minute/60.0f) * Math.PI / 6.0f);
		float hourHandDegree = hourDegree + hourHandOffset;
		int hour_x = (width/2) + (int)(pixelHourHandLength * Math.cos(hourHandDegree));
		int hour_y = (width/2) + (int)(pixelHourHandLength * Math.sin(hourHandDegree));
		g2.setStroke(new BasicStroke((width/400) * 3));
		g2.drawLine(width/2, width/2, hour_x, hour_y);
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
