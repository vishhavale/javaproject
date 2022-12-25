package vproject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class javaproject {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					javaproject window = new javaproject();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public javaproject() {
		initialize();
		Connect();
		table_load();
		
	}
	Connection con;
	PreparedStatement pat;
	ResultSet rs;
	public void Connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud","root","");
		}
		catch (ClassNotFoundException ex) {
			
		}
		catch (SQLException ex) {
		
		}
	}
	public void table_load()
	{
		try {
			pat= con.prepareStatement("select * from book");
			rs = pat.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 850, 625);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Books shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(342, 29, 266, 51);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(96, 117, 370, 291);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblBook = new JLabel("Book name");
		lblBook.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBook.setBounds(34, 27, 114, 51);
		panel.add(lblBook);
		
		JLabel lblEdition = new JLabel("Edition");
		lblEdition.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEdition.setBounds(34, 98, 114, 51);
		panel.add(lblEdition);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPrice.setBounds(34, 174, 114, 51);
		panel.add(lblPrice);
		
		txtbname = new JTextField();
		txtbname.setBounds(158, 41, 159, 25);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(158, 118, 159, 25);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(158, 191, 159, 25);
		panel.add(txtprice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname,edition,price;
				bname=txtbname.getText();
				edition=txtedition.getText();
				price=txtprice.getText();
				try
				{
					pat= con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
					pat.setString(1, bname);
					pat.setString(2, edition);
					pat.setString(3, price);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedd!!!!");
					table_load();
					txtbname.setText(" ");
					txtedition.setText(" ");
					txtprice.setText(" ");
					txtbname.requestFocus( );
				}
				catch (SQLException ex) {
					
				}
			}
		});
		btnNewButton.setBounds(130, 420, 101, 35);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(241, 420, 101, 35);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtbname.setText(" ");
				txtedition.setText(" ");
				txtprice.setText(" ");
				txtbname.requestFocus( );
				
			}
		});
		btnClear.setBounds(352, 420, 101, 35);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(494, 122, 306, 333);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(96, 474, 397, 77);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBookId.setBounds(30, 13, 114, 51);
		panel_1.add(lblBookId);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					String id = txtbid.getText();
					pat = con.prepareStatement("select name,edition,price from book where id = ?");
					pat.setString(1, id);
					ResultSet rs = pat.executeQuery();
				if(rs.next()==true)
				{
					String name=rs.getString(1);
					String edition=rs.getString(2);
					String price=rs.getString(3);
					
					txtbname.setText(name);
					txtedition.setText(edition);
					txtprice.setText(price);
				}
				else 
					{
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");
					}
				
				}
				catch (SQLException ex) {
					
				}
			}
		});
		txtbid.setColumns(10);
		txtbid.setBounds(158, 30, 159, 25);
		panel_1.add(txtbid);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname,edition,price,bid;
				bname=txtbname.getText();
				edition=txtedition.getText();
				price=txtprice.getText();
				bid=txtbid.getText();
				try
				{
					pat= con.prepareStatement("update book set name=?, edition=?, price=? where id=?");
					pat.setString(1, bname);
					pat.setString(2, edition);
					pat.setString(3, price);
					pat.setString(4, bid);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated!!!!");
					table_load();
					txtbname.setText(" ");
					txtedition.setText(" ");
					txtprice.setText(" ");
					txtbname.requestFocus( );
				}
				catch (SQLException ex) {
					
				}
				
				
			}
		});
		btnUpdate.setBounds(521, 485, 111, 45);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bid;
				
				bid=txtbid.getText();
				try
				{
					pat= con.prepareStatement("delete from book where id=?");
					
					pat.setString(1, bid);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted!!!!");
					table_load();
					txtbname.setText(" ");
					txtedition.setText(" ");
					txtprice.setText(" ");
					txtbname.requestFocus( );
				}
				catch (SQLException ex) {
					
				}
			}
		});
		btnDelete.setBounds(668, 485, 101, 45);
		frame.getContentPane().add(btnDelete);
	}
}
