package View;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.LineBorder;

import CustomComponents.ButtonBoard;

public class CaroBoard extends JFrame {
    private JPanel CaroBoard;
    private ButtonBoard[][] squares;
    boolean playerPlay = false;
    private JTextField textField;
    public CaroBoard() {
    
        setTitle("Caro Game");
        setSize(1000,800);
        
        JPanel panelContainer = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                initBG(g);
            }
        };
        panelContainer.setBounds(0, 0, 1000, 800);
        this.add(panelContainer);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JButton btnXoa = new JButton("Xóa nước đi đối phương");
		btnXoa.setBackground(Color.WHITE);
		
        CaroBoard = new JPanel(new GridLayout(19, 19)){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    initBG(g);
                }
            };
        CaroBoard.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        CaroBoard.setBounds(0, 0, 800,600);
        squares = new ButtonBoard[19][19];
       // Map<JButton, Boolean> map = new HashMap<>();
        // Create chess board with buttons representing squares
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j <19; j++) {
            	squares[i][j] = new ButtonBoard();
            	squares[i][j].setContentAreaFilled(false);
            	squares[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
            	squares[i][j].setPreferredSize(new Dimension(40,40)); 
            	squares[i][j].setMargin(new Insets(0, 0, 0, 0)); // remove margin
            	squares[i][j].setContentAreaFilled(false); // remove padding
            	squares[i][j].setFocusable(false);
        		squares[i][j].setFont(new Font("Arial",Font.PLAIN,20));
              //  map.put(squares[i][j], false);
                CaroBoard.add(squares[i][j]);
                	
                // Set button colors based on chess board pattern
               
               // squares[i][j].setBackground(Color.WHITE);        
                 squares[i][j].setText("");
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
                        		System.out.println("Người chơi DIỆU DƠ vừa nạp 100k để xóa nước cờ: [" + row +","+ col +"] của bạn");
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
                        	if(squares[row][col].getText().isEmpty())
                    		{           
                        		String text = playerPlay != false && playerPlay ? "X" : "O";
                        		Color colorText = text.equals("X") ? Color.red : Color.black;
                        		squares[row][col].setText(text);
                        		squares[row][col].setFont(new Font("Tahoma",Font.PLAIN,25));
                       // 		squares[row][col].setBackground(color);
                       // 		squares[row][col].setForeground(colorText);
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
        panelContainer.setLayout(null);

        panelContainer.add(CaroBoard);
        
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
        panelContainer.add(btnXoa);
        
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
        panelContainer.add(btnThoatTran);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(10, 610, 792, 143);
        panelContainer.add(panel);
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
        panelContainer.add(label);
        setVisible(true);
    }

    private void squareClicked(int row, int col) {
        JOptionPane.showMessageDialog(this, "Square clicked: " + (char)('A' + col) + (19 - row));
    }
    void initBG(Graphics g)
    {
    	 Graphics2D g2d = (Graphics2D) g;
         GradientPaint gradient = new GradientPaint(0, 0, new Color(0x5170FF), // Màu bắt đầu (#ff8a00)#5170ff
         		getWidth(), getHeight(), new Color(0xff66c4));
		 // Sơn gradient trên JPanel
		 g2d.setPaint(gradient);
		 g2d.fillRect(0, 0, getWidth(), getHeight());
     }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CaroBoard();
            }
        });
    }
    
}