package View;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.LineBorder;

import CustomComponents.ButtonBoard;
import CustomComponents.CustomPanel;
import CustomComponents.CustomPanelGradients;
import CustomComponents.CustomTextFiled;
import CustomComponents.RadiusButton;

public class CaroBoard extends JFrame {
    private JPanel CaroBoard;
    private ButtonBoard[][] squares;
    boolean playerPlay = false;
    JLabel lblTimePlay;
    private CustomTextFiled textField;
    JLabel lblAvatarPlayer1;
    JLabel lblAvatarPlayer2;
    JLabel lblNamePlayer1;
    JLabel lblNamePlayer2;
    JLabel lblPointPlayer2;
    JLabel lblPointPlayer1;
    JLabel lblResult;
    private int time;
	private Image img_setting = new ImageIcon(LobbyFrame.class.getResource("/img/setting_img.png")).getImage().getScaledInstance(60, 60,Image.SCALE_SMOOTH);
	private Image img_logout = new ImageIcon(LobbyFrame.class.getResource("/img/logout_img.png")).getImage().getScaledInstance(59, 60,Image.SCALE_SMOOTH);
	private Image img_man = new ImageIcon(LobbyFrame.class.getResource("/img/man_img.png")).getImage().getScaledInstance(60, 60,Image.SCALE_SMOOTH);
	private Image img_girl = new ImageIcon(LobbyFrame.class.getResource("/img/girl_img.png")).getImage().getScaledInstance(60, 60,Image.SCALE_SMOOTH);

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
        panelContainer.setBounds(0, 0, 1000, 1000);
        getContentPane().add(panelContainer);
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
        CaroBoard.setBorder(new LineBorder(new Color(0, 0, 0,40), 2));
        CaroBoard.setBounds(0, 0, 800,600);
        
        squares = new ButtonBoard[19][19];
       // Map<JButton, Boolean> map = new HashMap<>();
        // Create chess board with buttons representing squares
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j <19; j++) {
            	squares[i][j] = new ButtonBoard();
            	squares[i][j].setContentAreaFilled(false);
           // 	squares[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
            	squares[i][j].setPreferredSize(new Dimension(30,35)); 
            	squares[i][j].setMargin(new Insets(10, 10, 10, 10));
            	squares[i][j].setContentAreaFilled(false);
            	squares[i][j].setFocusable(false);
            	squares[i][j].setBorder(null);
        		squares[i][j].setFont(new Font("Tahoma",Font.PLAIN,20));
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
        panelContainer.setLayout(null);
          //      textField.setColumns(10);
                
	        RadiusButton btnGui = new RadiusButton();
	        btnGui.setRadius(30);
	        btnGui.setText("SEND");
	        btnGui.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		System.out.println(textField.getText());
	        	}
	        });
	        btnGui.setBounds(715, 710, 80, 34);
	        panelContainer.add(btnGui);
  
          textField = new CustomTextFiled();
          textField.setForeground(Color.WHITE);
          textField.setBounds(30, 710, 685, 34);        
          textField.setFont(new Font("Tahoma",Font.PLAIN,20));
          panelContainer.add(textField);
         
          lblPointPlayer2 = new JLabel("1,000 point");
         lblPointPlayer2.setBounds(856, 447, 138, 51);
         lblPointPlayer2.setHorizontalAlignment(JLabel.CENTER);
         lblPointPlayer2.setVerticalAlignment(JLabel.CENTER);
         panelContainer.add(lblPointPlayer2);
         
         
         lblNamePlayer2 = new JLabel();        
         lblNamePlayer2.setBounds(844,432, 162, 28);
         lblNamePlayer2.setText("MASTER JAVA");
         lblNamePlayer2.setFont(new Font("Tahoma",Font.BOLD,10));
         lblNamePlayer2.setHorizontalAlignment(JLabel.CENTER);
         lblNamePlayer2.setVerticalAlignment(JLabel.CENTER);
         panelContainer.add(lblNamePlayer2);
         
         
         
         lblPointPlayer1 = new JLabel("1,000 point");
         lblPointPlayer1.setBounds(856,279, 138, 51);
         lblPointPlayer1.setHorizontalAlignment(JLabel.CENTER);
         lblPointPlayer1.setVerticalAlignment(JLabel.CENTER);
         panelContainer.add(lblPointPlayer1);
         
          
         lblNamePlayer1 = new JLabel("MASTER ");
         lblNamePlayer1.setBounds(843,264, 162, 28);
         lblNamePlayer1.setHorizontalAlignment(JLabel.CENTER);
         lblNamePlayer1.setVerticalAlignment(JLabel.CENTER);
         lblNamePlayer1.setFont(new Font("Tahoma",Font.BOLD,10));
         panelContainer.add(lblNamePlayer1);
         
         
         lblAvatarPlayer1 = new JLabel("New label");
         lblAvatarPlayer1.setBounds(828,264,55,55);
         lblAvatarPlayer1.setIcon(new ImageIcon(img_girl));
         panelContainer.add(lblAvatarPlayer1);
         
         lblAvatarPlayer2 = new JLabel("New label");
         lblAvatarPlayer2.setBounds(828,431, 55,55);
         lblAvatarPlayer2.setIcon(new ImageIcon(img_man));
         panelContainer.add(lblAvatarPlayer2);

         lblResult = new JLabel("3-2");
         lblResult.setBounds(825, 320, 155, 104);
         lblResult.setFont(new Font("Tahoma",Font.BOLD,25));
         lblResult.setHorizontalAlignment(JLabel.CENTER);
         lblResult.setVerticalAlignment(JLabel.CENTER);
         panelContainer.add(lblResult);
        
         lblTimePlay = new JLabel();
        lblTimePlay.setBounds(857, 180,89,17);
        lblTimePlay.setForeground(new Color(0xfffffff));
        lblTimePlay.setFont(new Font("Tahoma",Font.BOLD,11));
        lblTimePlay.setHorizontalAlignment(JLabel.CENTER);
        lblTimePlay.setVerticalAlignment(JLabel.CENTER); 
        panelContainer.add(lblTimePlay);

        
        
        JLabel lblTilteTimePlay = new JLabel("THỜI GIAN TRẬN ĐẤU");
        lblTilteTimePlay.setFont(new Font("Tahoma",Font.BOLD,11));
        lblTilteTimePlay.setForeground(new Color(0xfffffff));
        lblTilteTimePlay.setBounds(842,161, 138, 20);
        lblTilteTimePlay.setHorizontalAlignment(JLabel.CENTER);
        lblTilteTimePlay.setVerticalAlignment(JLabel.CENTER); 
        panelContainer.add(lblTilteTimePlay);
        
        JLabel lblLogout = new JLabel();
        lblLogout.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
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
        lblLogout.setBounds(933, 33, 60,60);
        lblLogout.setIcon(new ImageIcon(img_logout));
       
        panelContainer.add(lblLogout);
        
        JLabel lblSetting = new JLabel("");
        lblSetting.setBounds(850, 33, 60, 60);
        lblSetting.setIcon(new ImageIcon(img_setting));
        panelContainer.add(lblSetting);

        panelContainer.add(CaroBoard);
        
        btnXoa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(btnXoa.getBackground()==Color.WHITE)
        		{
        			if(!playerPlay)
            		{
            			System.err.println("Người chơi X vừa dùng tính năng đặc biệt =))");
            		}
            		else {
            			System.err.println("Người chơi O vừa dùng tính năng đặc biệt =))");
            		}
            		btnXoa.setBackground(Color.RED);
            		
        		}
        		else {
        			btnXoa.setBackground(Color.WHITE);
        		}
        		
        	}
        });
        btnXoa.setBounds(828, 622, 148, 42);
        panelContainer.add(btnXoa);
        
        CustomPanel panel_1 = new CustomPanel();
        panel_1.setBounds(821, 19,179, 89);
        panelContainer.add(panel_1);
        panel_1.setLayout(null);
        

        
        CustomPanel panel = new CustomPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(10, 610, 792, 143);
        panelContainer.add(panel);
        panel.setLayout(null);
        
        CustomPanel panel_2 = new CustomPanel();
        panel_2.setBounds(821, 146, 162, 68);
        panelContainer.add(panel_2);
        panel_2.setLayout(null);
        
        CustomPanelGradients panel_3 = new CustomPanelGradients();
        
        panel_3.setBounds(815, 241, 165 ,99); 
        panelContainer.add(panel_3);
        panel_3.setLayout(null);

        CustomPanelGradients panel_4 = new CustomPanelGradients();
        panel_4.setBounds(815, 408, 165 ,99);  
        panelContainer.add(panel_4);
        panel_4.setLayout(null);
        
      
        setVisible(true);
        setupClock();
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
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
           g2d.fillRoundRect(0,0, getWidth(), getHeight(),30,30);	
           int shadowOpacity = 90;
           Color shadowColor = new Color(0, 0, 0, shadowOpacity);
           g2d.setColor(shadowColor);
           
		 
     }
    private void setupClock() {
    		
			Timer timer = new Timer(1000, e -> {
	            time++;
	            int hours = time / 3600;
	            int minutes = (time % 3600) / 60;
	            int seconds = time % 60;
	            String timeString = String.format("%02d:%02d", minutes, seconds);
	            lblTimePlay.setText(timeString);
	        });

	        timer.setInitialDelay(0);
	        timer.start();
	
        
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CaroBoard();
            }
        });
    }
}