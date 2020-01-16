package lab10;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;

public class ClockWithGui extends JPanel {

    LocalTime time;
    ClockThread thread;

    public ClockWithGui(){
        time = LocalTime.now();
        thread = new ClockThread();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        ClockWithGui cl = new ClockWithGui();
        frame.setContentPane(cl);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

        cl.thread.run();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;
        g2d.translate(getWidth()/2,getHeight()/2);

        for(int i=1;i<13;i++){
            AffineTransform at = new AffineTransform();
            at.rotate(2*Math.PI/12*i);
            Point2D src = new Point2D.Float(0,-120);
            Point2D trg = new Point2D.Float();
            at.transform(src,trg);
            if(i >= 10){
                g2d.drawString(Integer.toString(i),(int)trg.getX()-6,(int)trg.getY()+6);
            } else {
                g2d.drawString(Integer.toString(i),(int)trg.getX()-3,(int)trg.getY()+3);
            }
        }

        AffineTransform saveAT = g2d.getTransform();
        for(int i=0;i<60;i++){
            AffineTransform at = new AffineTransform();
            g2d.rotate(2*Math.PI/60);
            g2d.drawLine(0,-100,0,-108);
        }
        g2d.setTransform(saveAT);

        g2d.rotate(time.getHour()%12*2*Math.PI/12);
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g2d.drawLine(0,0,0,-70);
        g2d.setTransform(saveAT);

        g2d.rotate(time.getMinute()*2*Math.PI/60);
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g2d.drawLine(0,0,0,-90);
        g2d.setTransform(saveAT);

        g2d.rotate(time.getSecond()*2*Math.PI/60);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g2d.drawLine(0,0,0,-100);
        g2d.setTransform(saveAT);
    }

    class ClockThread extends Thread{
        @Override
        public void run() {
            while(true){
                time = LocalTime.now();

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }
    }
}