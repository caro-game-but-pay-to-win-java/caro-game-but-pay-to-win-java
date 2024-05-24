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
    private JPanel panel_TimeBorderPlayer1;
    private JPanel panel_TimeBorderPlayer2;
    private ButtonBoard[][] squares;
    JButton btnXoa; 
    private boolean playerPlay = false; // sử dụng để đánh dấu mốc đến lượt người chơi , false là x , true là o
    JLabel lblTimePlay; // Hiển thị thời gian trận đấu
    private CustomTextFiled textField;
    JLabel lblAvatarPlayer1;
    JLabel lblAvatarPlayer2;
    JLabel lblNamePlayer1;
    JLabel lblNamePlayer2;
    JLabel lblPointPlayer2;
    JLabel lblPointPlayer1;
    JLabel lblResult;
    boolean flagTimeWarning = false; // sử dụng để cảnh báo thời gian
    private int timePlay; // sử dụng cho bàn cờ
    private int timeLess; //sử dụng để đếm thời gian đánh của người chơi
    Timer timerLess; // sử dụng để bắt đầu đếm thời gian
	private Image img_setting = new ImageIcon(LobbyFrame.class.getResource("/img/setting_img.png")).getImage().getScaledInstance(60, 60,Image.SCALE_SMOOTH);
	private Image img_logout = new ImageIcon(LobbyFrame.class.getResource("/img/logout_img.png")).getImage().getScaledInstance(59, 60,Image.SCALE_SMOOTH);
	private Image img_man = new ImageIcon(LobbyFrame.class.getResource("/img/man_img.png")).getImage().getScaledInstance(60, 60,Image.SCALE_SMOOTH);
	private Image img_girl = new ImageIcon(LobbyFrame.class.getResource("/img/girl_img.png")).getImage().getScaledInstance(60, 60,Image.SCALE_SMOOTH);

    public CaroBoard() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(CaroBoard.class.getResource("/img/logo.png")));
    
        setTitle("Caro Game");
        setSize(1200,850);      
        JPanel panelContainer = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                initBG(g);
            }
        };
        panelContainer.setBounds(0, 0,1200,850);
        getContentPane().add(panelContainer);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        btnXoa = new JButton("Xóa nước đi đối phương");
		btnXoa.setBackground(Color.WHITE);
		
        CaroBoard = new JPanel(new GridLayout(19, 19)){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                 
                    initBG(g);
                }    
            };
        CaroBoard.setBorder(new LineBorder(new Color(0, 0, 0,40), 2));
        CaroBoard.setBounds(0, 0, 800,800);
        
        squares = new ButtonBoard[19][19];

        initBoardCaro();
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
	        
	        panel_TimeBorderPlayer2 = new JPanel() {
	        	  @Override
	              protected void paintComponent(Graphics g) {
	                  super.paintComponent(g);
	                  if(playerPlay)
	                  {	  
	                	 borderClock(g,panel_TimeBorderPlayer2);
	                  }
	               
	              }
	        };
	        panel_TimeBorderPlayer2.setBounds(915, 408, 165, 100);
	        panel_TimeBorderPlayer2.setOpaque(false);
	        panelContainer.add(panel_TimeBorderPlayer2);
	        btnGui.setBounds(1085, 765, 80, 25);
	        panelContainer.add(btnGui);
  
          textField = new CustomTextFiled();
          textField.setForeground(Color.WHITE);
          textField.setBounds(825, 760, 340, 34);        
          textField.setFont(new Font("Tahoma",Font.PLAIN,20));
          panelContainer.add(textField);
         
         lblPointPlayer2 = new JLabel("1,000 point");
         lblPointPlayer2.setBounds(960, 447, 100, 51);
         lblPointPlayer2.setHorizontalAlignment(JLabel.CENTER);
         lblPointPlayer2.setVerticalAlignment(JLabel.CENTER);
         panelContainer.add(lblPointPlayer2);
         
       panel_TimeBorderPlayer1 = new JPanel() {
             @Override
             protected void paintComponent(Graphics g) {
                 super.paintComponent(g);          
                if(!playerPlay)
                {
                    borderClock(g,panel_TimeBorderPlayer1);
                }
             }
         };
         panel_TimeBorderPlayer1.setOpaque(false); 

       panel_TimeBorderPlayer1.setBounds(915, 241, 165, 100);
       panelContainer.add(panel_TimeBorderPlayer1);
         
         
         lblNamePlayer2 = new JLabel();        
         lblNamePlayer2.setBounds(935,432, 150, 28);
         lblNamePlayer2.setText("MASTER JAVA");
         lblNamePlayer2.setForeground(Color.WHITE);

         lblNamePlayer2.setFont(new Font("Tahoma",Font.BOLD,10));
         lblNamePlayer2.setHorizontalAlignment(JLabel.CENTER);
         lblNamePlayer2.setVerticalAlignment(JLabel.CENTER);
         panelContainer.add(lblNamePlayer2);
         
         
         
         lblPointPlayer1 = new JLabel("1,000 point");
         lblPointPlayer1.setBounds(960,279, 100, 51);
         lblPointPlayer1.setHorizontalAlignment(JLabel.CENTER);
         lblPointPlayer1.setVerticalAlignment(JLabel.CENTER);
         panelContainer.add(lblPointPlayer1);
         
          
         lblNamePlayer1 = new JLabel("MASTER ");
         lblNamePlayer1.setBounds(935,264, 150, 28);
         lblNamePlayer1.setForeground(Color.WHITE);
         lblNamePlayer1.setHorizontalAlignment(JLabel.CENTER);
         lblNamePlayer1.setVerticalAlignment(JLabel.CENTER);
         lblNamePlayer1.setFont(new Font("Tahoma",Font.BOLD,10));
         panelContainer.add(lblNamePlayer1);
         
         
         lblAvatarPlayer1 = new JLabel("New label");
         lblAvatarPlayer1.setBounds(925,264,55,55);
         lblAvatarPlayer1.setIcon(new ImageIcon(img_girl));
         panelContainer.add(lblAvatarPlayer1);
         
         lblAvatarPlayer2 = new JLabel("New label");
         lblAvatarPlayer2.setBounds(925,431, 55,55);
         lblAvatarPlayer2.setIcon(new ImageIcon(img_man));
         panelContainer.add(lblAvatarPlayer2);

         lblResult = new JLabel("3-2");
         lblResult.setBounds(925, 320, 155, 104);
         lblResult.setFont(new Font("Tahoma",Font.BOLD,25));
         lblResult.setHorizontalAlignment(JLabel.CENTER);
         lblResult.setVerticalAlignment(JLabel.CENTER);
         panelContainer.add(lblResult);
        
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
       			    timerLess.stop();
       				LobbyFrame lobby = new LobbyFrame();
           		   	lobby.setVisible(true);
           	        dispose();
       		   }    
        	}
        });
        lblLogout.setBounds(1133, 33, 60,60);
        lblLogout.setIcon(new ImageIcon(img_logout));
       
        panelContainer.add(lblLogout);
        
        JLabel lblSetting = new JLabel("");
        lblSetting.setBounds(1050, 33, 60, 60);
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
        btnXoa.setBounds(897, 543, 148, 42);
        panelContainer.add(btnXoa);
        
        CustomPanel panel_1 = new CustomPanel();
        panel_1.setBounds(1021, 19,179, 89);
        panelContainer.add(panel_1);
        panel_1.setLayout(null);
        

        
        CustomPanel panel = new CustomPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(820, 660, 350, 143);
        panelContainer.add(panel);
        panel.setLayout(null);
        
        CustomPanel panel_2 = new CustomPanel();
        panel_2.setBounds(825, 40, 162, 68);
        panelContainer.add(panel_2);
        panel_2.setLayout(null);
        
                
                
        JLabel lblTilteTimePlay = new JLabel("THỜI GIAN TRẬN ĐẤU");
        lblTilteTimePlay.setBounds(14, 10, 138, 20);
        panel_2.add(lblTilteTimePlay);
        lblTilteTimePlay.setFont(new Font("Tahoma",Font.BOLD,11));
        lblTilteTimePlay.setForeground(new Color(0xfffffff));
        lblTilteTimePlay.setHorizontalAlignment(JLabel.CENTER);
        lblTilteTimePlay.setVerticalAlignment(JLabel.CENTER);
        
		 lblTimePlay = new JLabel();
		 lblTimePlay.setBounds(35, 40, 89, 17);
		 panel_2.add(lblTimePlay);
		 lblTimePlay.setForeground(new Color(0xfffffff));
		 lblTimePlay.setFont(new Font("Tahoma",Font.BOLD,11));
		 lblTimePlay.setHorizontalAlignment(JLabel.CENTER);
		 lblTimePlay.setVerticalAlignment(JLabel.CENTER);
        
        CustomPanelGradients panel_3 = new CustomPanelGradients();       
        panel_3.setBounds(915, 241, 165 ,99); 
        panelContainer.add(panel_3);
        panel_3.setLayout(null);

        CustomPanelGradients panel_4 = new CustomPanelGradients();
        panel_4.setBounds(915, 408, 165 ,99);  
        panelContainer.add(panel_4);
        panel_4.setLayout(null);
      

        if(!playerPlay)
        {       	
        	initTimeBorder(panel_TimeBorderPlayer1);
        }        
        setVisible(true);
        setupClock();
    }

    private void squareClicked(int row, int col) {
        JOptionPane.showMessageDialog(this, "Square clicked: " + (char)('A' + col) + (19 - row));
    }
    protected void initTimeBorder(JPanel panel) {
            if (timerLess != null && timerLess.isRunning()) {
                timerLess.stop();
                timerLess.setInitialDelay(0);
                timeLess = 0;
            }           
                timeLess = 0;
                timerLess = new Timer(1000, e -> {
                    timeLess++;
                    panel.repaint();
                    System.out.println(timeLess);
                    panel.setVisible(true);
                });
            
       
            timerLess.start();
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
	            timePlay++;
	            int hours = timePlay / 3600;
	            int minutes = (timePlay % 3600) / 60;
	            int seconds = timePlay % 60;
	            String timeString = String.format("%02d:%02d", minutes, seconds);
	            lblTimePlay.setText(timeString);
	        });

	        timer.setInitialDelay(0);
	        timer.start();
	
        
    }
    private void borderClock(Graphics g,JPanel j) {   
    	g.setColor(Color.green);
        if (timeLess < 7) {
            g.fillRect(83, 0, timeLess * 13, 5);
        } else if (timeLess < 14) {
            g.fillRect(83, 0, 7 * 13, 5);
            g.fillRect(160, 0, 5, (timeLess - 7) * 13);
        } else if (timeLess < 27) {
            g.fillRect(83, 0, 7 * 13, 5);
            g.fillRect(160, 0, 5, (timeLess - 7) * 13);
            int rectX1 = 165 - (timeLess - 14) * 13;
            g.fillRect(rectX1, 94, (timeLess - 14) * 13, 5);
        } else if (timeLess < 35) {
            g.fillRect(83, 0, 7 * 13, 5);
            g.fillRect(160, 0, 5, (timeLess - 7) * 13);
            int rectX1 = 165 - (timeLess - 14) * 13;
            g.fillRect(rectX1, 94, (timeLess - 14) * 13, 5);
            int rectY2 = 99 - (timeLess - 27) * 13;
            g.fillRect(0, rectY2, 5, (timeLess - 27) * 13);
        } else {
        	if(flagTimeWarning==false)
    		{
    			j.setVisible(true);
    			flagTimeWarning=true;
    		}
    		else {
    			j.setVisible(false);
    			flagTimeWarning=false;
    		}   
            g.setColor(Color.red);
            g.fillRect(83, 0, 7 * 13, 5);
            g.fillRect(160, 0, 5, (timeLess - 7) * 13);
            int rectX1 = 165 - (timeLess - 14) * 13;
            g.fillRect(rectX1, 94, (timeLess - 14) * 13, 5);
            int rectY2 = 99 - (timeLess - 27) * 13;
            g.fillRect(0, rectY2, 5, (timeLess - 27) * 13);
            g.fillRect(0, 0, (timeLess - 35) * 13, 5);
            
        }
        if(timeLess==40)
        {
        	JOptionPane.showConfirmDialog(this,"Hết thời gian đánh");
        	return;
        }
    }
    private void switchPanel() {
        if (playerPlay) {
            initTimeBorder(panel_TimeBorderPlayer2);
        	panel_TimeBorderPlayer1.setVisible(false);
        } else {
            initTimeBorder(panel_TimeBorderPlayer1);
        	panel_TimeBorderPlayer2.setVisible(false);
       
        }       
    }
    void initBoardCaro()
    {
    	 for (int i = 0; i < 19; i++) {
             for (int j = 0; j <19; j++) {
             	squares[i][j] = new ButtonBoard();
             	squares[i][j].setContentAreaFilled(false);
             	squares[i][j].setSize(30,30);
            // 	squares[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
             	//squares[i][j].setPreferredSize(new Dimension(15,15)); 
             	squares[i][j].setMargin(new Insets(5,5,5,5));
             	squares[i][j].setContentAreaFilled(false);
             	squares[i][j].setFocusable(false);
             	squares[i][j].setBorder(new LineBorder(new Color(0, 0, 0,40), 2));
                 squares[i][j].setText("");
             
               //  map.put(squares[i][j], false);
                 CaroBoard.add(squares[i][j]);
                 	
                 // Set button colors based on chess board pattern
                
                // squares[i][j].setBackground(Color.WHITE);        

                 // Add action listener to handle button clicks
                 final int row = i;
                 final int col = j;
                 // click
                 squares[i][j].addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) { 
                    	 
                     	if(btnXoa.getBackground()==Color.RED)
                     	{
                     		if(!squares[row][col].getText().isEmpty())
                     		{
                         		String text = playerPlay != false && playerPlay ? "O" : "X";
                         		System.out.println("Người chơi DIỆU DƠ vừa nạp 100k để xóa nước cờ: [" + row +","+ col +"] của bạn");
                     	   		squares[row][col].setText("");
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
                         		Color colorText = text.equals("X") ? new Color(0xff0040) : Color.black;
                         		squares[row][col].setText(text);
                         		squares[row][col].setFont(new Font("Tahoma",Font.BOLD,25));                        		                       
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
                         	switchPanel();
                     	
                     	}                                                   	
                     }
                 });
             }
         }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CaroBoard();
            }
        });
    }
}