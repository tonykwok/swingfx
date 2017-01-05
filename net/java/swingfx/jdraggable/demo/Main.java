/*
 * Copyright (c) 2005, romain guy (romain.guy@jext.org) and craig wickesser (craig@codecraig.com)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <ORGANIZATION> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package net.java.swingfx.jdraggable.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.java.swingfx.jdraggable.DefaultDraggableManager;
import net.java.swingfx.jdraggable.DragPolicy;
import net.java.swingfx.jdraggable.DraggableManager;


/**
 * A simple class which shows how JDraggable can be used.
 * 
 * @author craig
 * @since v0.1
 * <br>
 * $Header: /cvs/swingfx/src/net/java/swingfx/jdraggable/demo/Main.java,v 1.1 2005/02/26 22:55:45 codecraig Exp $
 */
public class Main {
	public static void main(String[] args) {
		// Create a Container, in this case a JPanel
		JPanel draggableContainer = new JPanel();
		
		// Register the container as the "draggable container" with a DraggableManager
		// in this example the DefaultDraggableManager is used.
		// REMEMBER, you must register the container prior to adding any components to it!!
		DraggableManager draggableManager = new DefaultDraggableManager(draggableContainer);
		
		// set a STRICT Drag Policy, so that only components implementing
		// the Draggable interface can be dragged.  Try chaning this to DragPolicy.DEFAULT
		// or DragPolicy.OPEN for a variation.
		draggableManager.setDragPolicy(DragPolicy.STRICT);

		// Create a panel which implements the Draggable interface
		DraggablePanel dragPanel = new DraggablePanel();
		dragPanel.setPreferredSize(new Dimension(150, 100));
		
		// Create a label which implements the Draggable interface
		DraggableLabel dragLabel = new DraggableLabel("Drag Me!");
		
		// Create a normal Swing JLabel
		JLabel plainLabel = new JLabel("Plain Label");
		
		// Create a normal Swing JButton, try chaning the Drag Policy (above)
		// to something like DragPolicy.OPEN...and watch the button become
		// draggable!!
		JButton b = new JButton("Click");

		// add an ActionListener to prove the button can be dragged and still
		// receive it's events
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked..");
			}
		});
		
		// A normal Swing JTextField
		JTextField jtf = new JTextField();
		jtf.setPreferredSize(new Dimension(100, 20));

		// Add all the components to the "draggable container"
		draggableContainer.add(dragPanel);
		draggableContainer.add(dragLabel);
		draggableContainer.add(plainLabel);
		draggableContainer.add(b);
		draggableContainer.add(jtf);
		
		// create a frame to display our "draggable container"
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(draggableContainer, BorderLayout.CENTER);		
		f.setSize(800, 600);
		f.setVisible(true);
	}
}