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

package net.java.swingfx.rubberband.demo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.java.swingfx.rubberband.canvas.RubberBandCanvas;
import net.java.swingfx.rubberband.rubberbands.AnimatedRectangularRubberBand;
import net.java.swingfx.rubberband.rubberbands.OvalRubberBand;
import net.java.swingfx.rubberband.rubberbands.RectangularRubberBand;
import net.java.swingfx.rubberband.rubberbands.RubberBand;

/**
 * A simple class with a main method to try out the different rubber band's.
 * The idea is to make the rubber bands flexible and easy to use.
 * Currently, in order for it to work you need to do three things:
 * 
 * 1) Create a JComponent which implements the RubberBandCanvas interface
 *
 * 2) Override the canvas' paintComponent(Graphics g) method so that it calls back
 *    to RubberBand.draw(Graphics g)
 *    
 * 3) Create a {@link net.java.swingfx.rubberband.rubberbands.RubberBand}
 * 
 * @author rwickesser
 * $Revision: 1.1 $
 */
public class Main {
    private static final Dimension SIZE = new Dimension(300, 300);
    
    public static void main(String[] args) {
        // Create RubberBandCanvas
        RubberBandCanvas canvas = new MyPanel();
        
        // Create rubber band
        RubberBand rb = new RectangularRubberBand(canvas);
        
        // Setup demo frame
        JFrame f = new JFrame("Rubber Band Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(SIZE);
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (scrSize.width / 2) - (SIZE.width / 2);
        int y = (scrSize.height / 2) - (SIZE.height / 2);
        f.setLocation(x, y);
        // add canvas to content pane
        f.getContentPane().add(canvas.getCanvas());
        f.setVisible(true);
    }
    
    /**
     * A demonstration on how to create a {@link RubberBandCanvas}.  Note
     * that paintComponent(Graphics g) has been overridden.
     * 
     * @author rwickesser
     * $Revision: 1.1 $
     */
    private static class MyPanel extends JPanel implements RubberBandCanvas, ActionListener {
        private static final long serialVersionUID = 3256445806658466864L;
        
        private RubberBand rubberband;
        
        private JRadioButton radioRect;
        private JRadioButton radioOval;
        private JRadioButton radioAnimRect;
        
        public MyPanel() {
            radioRect = new JRadioButton("Rectangle");
            radioRect.setSelected(true);
            radioRect.addActionListener(this);
            radioOval = new JRadioButton("Oval");
            radioOval.addActionListener(this);
            radioAnimRect = new JRadioButton("Animated Rectangle");
            radioAnimRect.addActionListener(this);
            ButtonGroup bg = new ButtonGroup();
            bg.add(radioRect);
            bg.add(radioOval);
            bg.add(radioAnimRect);
            
            add(radioRect);
            add(radioOval);
            add(radioAnimRect);
        }

        /*************** [ IMPORTANT - override paintComponent ] *******************/
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            rubberband.draw(g);
        }

        /* (non-Javadoc)
         * @see RubberBandCanvas#setRubberBand(RubberBand)
         */
        public void setRubberBand(RubberBand rubberband) {
            this.rubberband = rubberband;
        }

        /* (non-Javadoc)
         * @see RubberBandCanvas#getCanvas()
         */
        public JComponent getCanvas() {
            return this;
        }

        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            if (radioRect.isSelected()) {
                rubberband = new RectangularRubberBand(this);
            }
            else if (radioOval.isSelected()) {
                rubberband = new OvalRubberBand(this);
            }
            else if (radioAnimRect.isSelected()) {
            	rubberband = new AnimatedRectangularRubberBand(this);
            }
        }
    }
}