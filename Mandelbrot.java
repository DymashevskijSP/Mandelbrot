//Dymashevskij Sergey 9a
//homework 05.02.2018
//picture of mandelbrot set(with zoom and buttons)


import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Mandelbrot {
    public static Random r = new Random();

    public static void main(String[] args) {


        JFrame window = new JFrame("Mandelbrot");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(700, 500);
        JTabbedPane tab = new JTabbedPane();

        MyPanel Panel = new MyPanel();
        GridLayout layout = new GridLayout();
        JRadioButton button = new JRadioButton();
        TextField fieldRe = new TextField();
        TextField fieldIm = new TextField();
        Panel mainSettings = new Panel();
        Panel.setLayout(new FlowLayout());
        mainSettings.add(fieldRe);
        mainSettings.add(fieldIm);
        fieldIm.setText("0");
        fieldRe.setText("0");
        button.setText("MandelBrot");
        JRadioButton button1 = new JRadioButton("Julia");
        JButton repaint = new JButton("repaint");
        ButtonGroup bg = new ButtonGroup();
        bg.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Panel.zoom = 1;

                Panel.X = -2.0;
                Panel.Y = -2.0;
                fieldIm.setVisible(true);
                fieldRe.setVisible(true);
                if (button.isSelected()) {

                    Panel.flag = false;
                    fieldIm.setVisible(false);
                    fieldRe.setVisible(false);

                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Panel.zoom = 1;

                Panel.X = -2.0;
                Panel.Y = -2.0;
                fieldIm.setVisible(true);
                fieldRe.setVisible(true);
                if (!button1.isSelected()) {
                    Panel.flag = !Panel.flag;
                    fieldIm.setVisible(false);
                    fieldRe.setVisible(false);

                } else {
                    Panel.flag = true;
                }
            }
        });

        button.setVisible(true);
        button1.setVisible(true);
        repaint.setVisible(true);
        mainSettings.add(repaint);
        repaint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Panel.zoom = 1;
                Panel.X = -2.0;
                Panel.Y = -2.0;
                try {
                    Panel.delta = new ComplexNumber(Double.parseDouble(fieldRe.getText()),
                            Double.parseDouble(fieldIm.getText()));
                } catch (Exception e1) {
                }
            }
        });
        bg.add(button1);
        button1.setSelected(true);
        mainSettings.add(button);
        mainSettings.add(button1);
        Panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int size = Math.min(Panel.getHeight(), Panel.getWidth());
                Panel.X = 4.0 / size / Panel.zoom * e.getX() + Panel.X - 1.0 / Panel.zoom;
                Panel.Y = 4.0 / size / Panel.zoom * e.getY() + Panel.Y - 1.0 / Panel.zoom;
                Panel.zoom *= 2;
                Panel.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        Timer t = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Panel.repaint();

            }
        });
        tab.add(Panel, "Picture");
        window.pack();
        t.start();
        window.add(mainSettings, BorderLayout.NORTH);

        window.add(tab);
        window.setVisible(true);
    }
}

class ComplexNumber {
    double re;
    double im;

    public ComplexNumber(double a, double b) {
        this.re = a;
        this.im = b;
    }

    public ComplexNumber multiply(ComplexNumber z) {
        return new ComplexNumber(re * z.re - im * z.im, re * z.im + im * z.re);
    }

    public ComplexNumber add(ComplexNumber z) {
        re += z.re;
        im += z.im;
        return this;
    }

    public double abs() {
        return im * im + re * re;
    }

}

class MyPanel extends JPanel {
    int con = 2;
    public int state = 248;
    public double X = -2.0;
    public double Y = -2.0;
    public int zoom = 1;
    public boolean flag = true;
    public ComplexNumber delta = new ComplexNumber(0, 0);

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int p = 0; p <= getWidth(); p += 1) {


            for (int q = 0; q <= getHeight(); q += 1) {
                double x = (p * 4.0 / Math.min(getWidth(), getHeight())) / zoom + X;
                double y = (q * 4.0 / Math.min(getWidth(), getHeight())) / zoom + Y;
                ComplexNumber z = new ComplexNumber(x, y);
                int k = 0;
                ComplexNumber z0 = new ComplexNumber(x, y);
                for (int i = 0; i < 250; i++, k++) {
                    if (flag) {
                        z = z.multiply(z).add(delta);
                    } else {
                        z = z.multiply(z).add(z0);
                    }

                    if (z.abs() > 4) {
                        break;
                    }
                }

                if (z.abs() > 4) {
                    g2d.setColor(new Color((k * k + state) % 255, (k + state) % 255, (k * k * k + state) % 255));
                    g2d.drawLine(p, q, p + 1, q);
                } else {

                    g2d.setColor(new Color(0, 0, 0));
                    g2d.drawLine(p, q, p + 1, q);
                }

            }
        }


//        delta = delta.add(new ComplexNumber(0, 0.0001));


    }
}


