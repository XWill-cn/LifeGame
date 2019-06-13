/**
 * @program: 生命游戏
 * @description: 此处实现
 * @author: xuwei
 * @create: 2019-05-03 15:47
 **/

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class WorldMap extends JPanel{

    private final int width = 20;
    private final int height = 20;
    private final char Dead = 'D';
    private final char Alive = 'A';

    private  static char[][] world =
            {

                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},


            };

    /**
     * 细胞下一状态
     */
    private char[][] nextStatus = new char[world.length][world[0].length];
    private char[][] tempStatus = new char[world.length][world[0].length];
    private Timer timer;
    //delay time


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < nextStatus.length; i++) {
            for (int j = 0; j < nextStatus[i].length; j++) {
                if (nextStatus[i][j] == Alive) {
                    g.fillRect( j * width, i * height, width, height);

                } else {
                    g.drawRect( j * width, i * height, width, height);
                }
            }
        }
    }

    public WorldMap() {
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[0].length; col++) {
                nextStatus[row][col] = world[row][col];
                tempStatus[row][col] = world[row][col];
            }
        }
        makenew();
    }

    public void startAnimation() {
        //Init WorldMap
        int dTime = LifeGame.value;//改变帧率变化速度
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[0].length; col++) {
                nextStatus[row][col] = world[row][col];
                tempStatus[row][col] = world[row][col];
            }
        }
        timer = new Timer(dTime, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Change the status of cell
                cellStatus();
                repaint();
            }
        });
        timer.start();
    }
    /**
     * 暂停函数
     */
    public void stoptime() {
        timer.stop();
    }


    public void makenew() {
        repaint();
    }
    /**
     *单个细胞周围细胞状态进行判断,，随后遍历
     */
    int neighbor(int row,int col) {
        int count=0;
        for(int i=row-1;i<=row+1;i++)
            for(int j=col-1;j<=col+1;j++) {
                if(i<0||i>=world.length||j<0||j>=world[0].length)
                continue;
                if(i==row&&j==col)
                    continue;
                else
                if(tempStatus[i][j]==Alive)
                    count++;//有活的细胞则计数
            }
        return count;
    }//考虑有的细胞周边有八个 处在边缘位置的细胞情况不一样

    private void cellStatus() {
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[0].length; col++) {
                switch(neighbor(row,col)) {
                    case 0:
                    case 1:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        nextStatus[row][col] = Dead;
                        break;
                    case 2:
                        nextStatus[row][col]=tempStatus[row][col];//状态维持不变
                        break;
                    case 3:
                        nextStatus[row][col]=Alive;
                        break;
                }
            }
        }
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[0].length; col++) {
                tempStatus[row][col]=nextStatus[row][col];//状态改变
            }
        }
    }

    /**
     * 细胞状态手动输入生成
     */
    public void setAlive( int i,int j)               //i，j 为所获的细胞坐标
    {
        world[i][j]='A';
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[0].length; col++) {
                nextStatus[row][col] = world[row][col];
                tempStatus[row][col] = world[row][col];
            }
        }
        makenew();
    }
    /**
     * 细胞状态随机生成
     */

    public void randomSquare()
    {
        Random rand = new Random();
        int x,y;
        for( int i=0; i<15; i++ )
            for(int j=0;j<15;j++){
                x = (int)(rand.nextInt(2)+1);//生成随机数给每个坐标赋值
                if(x==1)
                    world[i][j]='A';
                else
                    world[i][j]='D';                          //生成随机数x
            }
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[0].length; col++) {
                nextStatus[row][col] = world[row][col];
                tempStatus[row][col] = world[row][col];
            }
        }
        makenew();
    }
    /**
     * 以下部分用于设置给定的初始状态
     */
    public static void setArrow()
    {
        setShape(arrow);
    }
    public static void setSquare()
    {
        setShape(square);
    }
    /**
     * 置零函数
     */
    public  void reset()
    {
        setShape(world);
    }

    private static void setShape(char[][] shape)
    {

        for (int row = 0; row <world.length; row++) {
            for (int col = 0; col < world[0].length; col++) {
                world[row][col] = shape[row][col];
            }
        }

       new WorldMap();
    }
    private  static char[][] square =
            {

                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','A','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','A','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},

            };
    private  static char[][] arrow=
            {
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},
                    {'D','D','D','D','D','D','D','D','D','D','D','D','D','D','D'},

            };

}
