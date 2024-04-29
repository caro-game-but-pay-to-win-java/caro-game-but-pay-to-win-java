package View;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.LineBorder;

public class CaroBoard extends JFrame {
    private JPanel CaroBoard;
    private JButton[][] squares;
    boolean playerPlay = false;
    private JTextField textField;
    public CaroBoard() {
        setTitle("Caro Game");
        setSize(1000,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JButton btnXoa = new JButton("Xóa nước đi đối phương");
		btnXoa.setBackground(Color.WHITE);
        CaroBoard = new JPanel(new GridLayout(19, 19));
        CaroBoard.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        CaroBoard.setBounds(0, 0, 800,600);
        squares = new JButton[19][19];
       // Map<JButton, Boolean> map = new HashMap<>();
        // Create chess board with buttons representing squares
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j <19; j++) {
            	squares[i][j] = new JButton();
            	squares[i][j].setPreferredSize(new Dimension(40, 40)); 
            	squares[i][j].setMargin(new Insets(0, 0, 0, 0)); // Tắt margin
            	squares[i][j].setContentAreaFilled(false); // Tắt padding
            	squares[i][j].setFocusable(false);
        		squares[i][j].setFont(new Font("Arial",Font.PLAIN,20));
              //  map.put(squares[i][j], false);
                CaroBoard.add(squares[i][j]);
                	
                // Set button colors based on chess board pattern
               
                    squares[i][j].setBackground(Color.WHITE);        
              
                // Add action listener to handle button clicks
                final int row = i;
                final int col = j;
                squares[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) { 
                    	if(btnXoa.getBackground()==Color.RED)
                    	{
                    		if(	squares[row][col].getText()!= " ")
                    		{
                        		String text = playerPlay != false && playerPlay ? "O" : "X";
                        		System.out.println("Người chơi "+text+" vừa nạp 100k để xóa nước cờ: [" + row +","+ col +"] của bạn");
                    	   		squares[row][col].setText(" ");
                        		squares[row][col].setBackground(Color.WHITE);
                    		}
                    		else {
                    			
                    		}
                    	}
                    	else {
                    		Color color;
                        	boolean temp = playerPlay;
                        	if(!playerPlay)
                        	{
                        		color = Color.red;
                        		playerPlay=true;
                        	}
                        	else {
                        		color = Color.black;
                        		playerPlay=false;
                        	}
                        	if(squares[row][col].getBackground()==Color.WHITE)
                    		{           
                        		String text = playerPlay != false && playerPlay ? "X" : "O";
                        		Color colorText = text.equals("X") ? Color.red : Color.black;
                        		squares[row][col].setText(text);
                        		squares[row][col].setBackground(color);
                        		squares[row][col].setForeground(colorText);
                        		System.out.println("Người chơi đánh: "+ squares[row][col].getText());
                        		//map.put(squares[row][col], true);                    	
                    		}
                        	else
                        	{
                        		playerPlay= temp;
                        		return;
                        	}
                    	}                                                   	
                    }
                });
            }
        }
        getContentPane().setLayout(null);

        getContentPane().add(CaroBoard);
        
        btnXoa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(btnXoa.getBackground()==Color.WHITE)
        		{
        			if(!playerPlay)
            		{
            			System.out.println("Người chơi X vừa dùng tính năng đặc biệt =))");
            		}
            		else {
            			System.out.println("Người chơi O vừa dùng tính năng đặc biệt =))");
            		}
            		btnXoa.setBackground(Color.RED);
            		
        		}
        		else {
        			btnXoa.setBackground(Color.WHITE);
        		}
        		
        	}
        });
        btnXoa.setBounds(828, 23, 148, 42);
        getContentPane().add(btnXoa);
        
        JButton btnThoatTran = new JButton("Thoát trận");
        btnThoatTran.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		   int result = JOptionPane.showConfirmDialog(
        		            new JFrame(),
        		            "Bạn có chắc chắn muốn thoát trận đấu chứ?",
        		            "Confirm Exit",
        		            JOptionPane.YES_NO_OPTION, 
        		            JOptionPane.QUESTION_MESSAGE 
        		        );   
        		   
        		   if (result == JOptionPane.YES_OPTION) {
        	  
        				LobbyFrame lobby = new LobbyFrame();
            		   	lobby.setVisible(true);
            	        dispose();

        		   }        		   

        	}
        });
        btnThoatTran.setBounds(828, 558, 148, 42);
        getContentPane().add(btnThoatTran);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(10, 610, 792, 143);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        textField = new JTextField();
        textField.setBounds(10, 99, 685, 34);
        panel.add(textField);
        textField.setColumns(10);
        
        JButton btnNewButton = new JButton("GỬI");
        btnNewButton.setBounds(697, 96, 85, 39);
        panel.add(btnNewButton);
        
        Label label = new Label("Thời gian");
        label.setBounds(828, 101, 134, 42);
        getContentPane().add(label);
        setVisible(true);
    }

    private void squareClicked(int row, int col) {
        JOptionPane.showMessageDialog(this, "Square clicked: " + (char)('A' + col) + (19 - row));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CaroBoard();
            }
        });
    }
}