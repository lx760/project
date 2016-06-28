package com.ecc;

//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ActionListenerTest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7534535203968566859L;

	public static void main(String[] args) {
		JFrame frame = new JFrame("Button Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JButton jbClose = new JButton("Close the Frame");
		jbClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(jbClose)) {
					System.exit(0);
				}
			}
		});

		frame.add(jbClose);
		frame.pack();
		frame.setVisible(true);
	}
}
