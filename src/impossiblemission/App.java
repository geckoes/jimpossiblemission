package impossiblemission;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class App
{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Questo \u00E8 un titolo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try
        {
            frame.setIconImage(ImageIO.read(new File("icon.png")));
        } catch (Exception e) { }

        JPanel panel = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                int density = 5;
                g.setColor(Color.decode("#ffec99"));
                for (int  x = 0; x <= getWidth() + getHeight(); x += density)
                    g.drawLine(x, 0, 0, x);
            }
        };
        panel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel("Questa \u00E8 una finestra");
        label.setForeground(Color.decode("#f08c00"));
        label.setFont(new Font("Cascadia Code", Font.PLAIN, 22));
        
        panel.add(label);
        frame.add(panel);
        
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
}
