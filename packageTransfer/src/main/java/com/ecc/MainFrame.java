package com.ecc;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 界面
 * @author liuxin
 *
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton jb_begin;
	private JButton jb_end;
	private JTextField jt_localPort;
	private JTextField jt_targetIp;
	private JTextField jt_targetPort;
	private PortRedirect redirect = null;

	public MainFrame() throws IOException {
		JLabel titile = new JLabel("报文中转服务器", 0);
		Font font = new Font("宋体", 1, 24);
		titile.setFont(font);
		titile.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(titile, "North");

		font = new Font("宋体", 0, 12);

		JLabel localPort = new JLabel("本机监听端口：", 4);
		localPort.setFont(font);

		JLabel targetIp = new JLabel("转发地址 IP ：", 4);
		targetIp.setFont(font);

		JLabel targetPort = new JLabel("转发地址端口：", 4);
		targetPort.setFont(font);

		JPanel jp_center_left = new JPanel();
		jp_center_left.setLayout(new GridLayout(4, 1));
		jp_center_left.add(localPort);
		jp_center_left.add(targetIp);
		jp_center_left.add(targetPort);

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
		Properties p = new Properties();

		p.load(inputStream);

		this.jt_localPort = new JTextField(p.getProperty("localPort"));
		this.jt_targetIp = new JTextField(p.getProperty("targetIp"));
		this.jt_targetPort = new JTextField(p.getProperty("targetPort"));

		JPanel jp_center_right = new JPanel();
		jp_center_right.setLayout(new GridLayout(4, 1));
		jp_center_right.add(this.jt_localPort);
		jp_center_right.add(this.jt_targetIp);
		jp_center_right.add(this.jt_targetPort);

		JPanel jp_center = new JPanel();
		jp_center.setLayout(new GridLayout(1, 2));
		jp_center.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));
		jp_center.add(jp_center_left);
		jp_center.add(jp_center_right);

		this.jb_begin = new JButton("启动");
		this.jb_end = new JButton("停止");

		JPanel jp_south = new JPanel();

		jp_south.add(this.jb_begin);
		jp_south.add(this.jb_end);
		jp_south.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		add(jp_center);
		add(jp_south, "South");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(370, 290);
		setResizable(false);
		setLocationRelativeTo(null);

		this.jb_begin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jb_begin.setEnabled(false);
				jb_end.setEnabled(true);
				System.out.println("begin");
				try {
					redirect = new PortRedirect(Integer.parseInt(jt_localPort.getText()), new InetSocketAddress(jt_targetIp.getText(), Integer.parseInt(jt_targetPort.getText())), true);
				} catch (IOException ioe) {
					jb_begin.setEnabled(true);
					jb_end.setEnabled(true);
					ioe.printStackTrace();
				}
				redirect.start();
			}
		});

		this.jb_end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (redirect != null) {
					redirect.close();
				}
				System.out.println("end");
				System.exit(0);
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

}