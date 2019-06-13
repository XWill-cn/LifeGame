/**
 * @program: 生命游戏
 * @description: none
 * @author: xuwei
 * @create: 2019-05-03 15:47
 **/


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class LifeGame extends JFrame {
    JLabel lblNewLabel = new JLabel("操作按钮");
    boolean open = false;
    JPanel container = new JPanel();
    WorldMap world = new WorldMap();
    JPanel controlPane = new JPanel();
    protected static int value;

    LifeGame() {
        JLabel l = new JLabel("请输入速度大小：");
        JTextField f = new JTextField(5);
        f.setEnabled(true);
        controlPane.add(l);
        controlPane.add(f);

        world.setBounds(0, 0, 300, 300);
        this.setTitle("生命游戏");
        this.setSize(439, 353);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(container);
        container.setLayout(null);
        container.add(world);
        container.add(controlPane);
        controlPane.setLocation(301, 0);
        controlPane.setSize(new Dimension(117, 303));
        controlPane.add(lblNewLabel);

        JButton button = new JButton("   开始   ");
        controlPane.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String a = f.getText();
                value = Integer.parseInt(a);//将字符串转换成int类型的数

                world.startAnimation();

            }
        });

        //random 函数随机初始化
        JButton button1 = new JButton("随机生成");
        controlPane.add(button1);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                world.randomSquare();
            }
        });
        //鼠标响应事件构建
        JButton button2 = new JButton("手动输入");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                world.addMouseListener(new MouseListener() {
                    public void mouseClicked(MouseEvent event) {
                        int x = event.getX() / 20;
                        int y = event.getY() / 20;
                        if (x < 300 && x >= 0 && y < 300 && y >= 0) {            //  将鼠标点击处的生命设为活状态
                            world.setAlive(y, x);
                        }
                    }

                    public void mousePressed(MouseEvent e) {
                    }

                    public void mouseReleased(MouseEvent e) {
                    }

                    public void mouseEntered(MouseEvent e) {
                    }

                    public void mouseExited(MouseEvent e) {
                    }
                });
            }
        });
        controlPane.add(button2);

        JButton button3 = new JButton("   暂停   ");
        controlPane.add(button3);
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                world.stoptime();
            }
        });

        JButton button4 = new JButton("   清空   ");
        controlPane.add(button4);
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                world.reset();
            }
        });
    }

    //main 函数
    public static void main(String[] args) {
        LifeGame a = new LifeGame();

        JMenuBar menu = new JMenuBar();
        a.setJMenuBar(menu);

        JMenu options = new JMenu("初始状态选项");
        menu.add(options);

        JMenuItem arrow = options.add("Arrow");
        arrow.addActionListener(a.new ArrowActionListener());

        JMenuItem square = options.add("Square");
        square.addActionListener(a.new SquareActionListener());

        a.setVisible(true);
    }

class ArrowActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        WorldMap.setArrow();
    }
}

class SquareActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        WorldMap.setSquare();
    }
}
}