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

package net.java.swingfx.rubberband.rubberbands;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import net.java.swingfx.rubberband.canvas.RubberBandCanvas;

/**
 * An abstract implementation of {@link RubberBand} which handles
 * the basic drawing/setup of the rubber band.
 *
 * @author rwickesser
 * @since 1.0
 * $Revision: 1.2 $
 */
public abstract class AbstractRubberBand extends MouseInputAdapter implements RubberBand {    
    /** the canvas where the rubber band will be drawn onto */
    protected RubberBandCanvas canvas;
    
    /** maintains the size and location of the rubber band */
    protected Rectangle rubberband;

    /** stores the x coordinate of the mouse pressed event */
    private int pressX;
    /** stores the y coordinate of the mouse pressed event */
    private int pressY;

    /** 
     * if <code>true</code> then the rubber band will disappear
     * after the mouse is released, otherwise the rubber band stays
     * visible until a new rubber band is drawn.  Subclasses
     * should override 
     */
    private boolean hideOnRelease;
    
    /**
     * Creates a new <code>RubberBand</code> and sets the canvas
     * 
     * @param canvas    the <code>RubberBandCanvas</code> on which the rubber band
     *                  will be drawn
     *                  
     * @see #setCanvas(RubberBandCanvas)
     */
    public AbstractRubberBand(RubberBandCanvas canvas) {
        this.canvas = canvas;
        init();
    }
    
    /**
     * Initializes the rubber band
     */
    private void init() {
        rubberband = new Rectangle();
        setHideOnRelease(true);
        
        if (canvas != null) {
            canvas.setRubberBand(this);
            addMouseListeners();
        }
    }

    /* (non-Javadoc)
     * @see gui.rubberband.RubberBand#addMouseListeners()
     */
    public void addMouseListeners() {
        canvas.getCanvas().addMouseListener(this);
        canvas.getCanvas().addMouseMotionListener(this);
   }

    /* (non-Javadoc)
	 * @see net.java.swingfx.rubberband.RubberBand#getBounds()
	 */
	public Rectangle getBounds() {
		return rubberband.getBounds();
	}

	/* (non-Javadoc)
     * @see gui.rubberband.RubberBand#setCanvas(RubberBandCanvas)
     */
    public void setCanvas(RubberBandCanvas canvas) {
        this.canvas = canvas;
        this.canvas.setRubberBand(this);
        addMouseListeners();
    }

    /* (non-Javadoc)
     * @see javax.swing.event.MouseInputAdapter#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
        updateRubberBand(e);
        
        int x = e.getX();
        int y = e.getY();
        int w = 0;
        int h = 0;

        // adjust x and width
        if (pressX < x) {
            int tmp = x;
            x = pressX;
            w = tmp - x;
        }
        else {
            w = pressX - x;
        }
        
        // adjust y and height
        if (pressY < y) {
            int tmp = y;
            y = pressY;
            h = tmp - y;
        }
        else {
            h = pressY - y;
        }
        
        // update rubber band size and location
        update(x, y, w, h);
        
        // repaint the canvas so the rubber band is updated visually
        updateCanvas();
    }

    /* (non-Javadoc)
     * @see javax.swing.event.MouseInputAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        startRubberBand(e);
        pressX = e.getX();
        pressY = e.getY();

        // update rubber band size and location
        update(pressX, pressY, 0, 0);
    }

    /* (non-Javadoc)
     * @see javax.swing.event.MouseInputAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        stopRubberBand(e);

        // erase the rubber band if hideOnRelease is true
        if (isHideOnRelease()) {
	        // update rubber band size and location
	        update(-1, -1, 0, 0);
	        
	        // repaint the canvas so the rubber band disappears
	        updateCanvas();
        }
    }
    
    /**
     * Makes a call to the canvas' repaint method
     */
    private void updateCanvas() {
        canvas.getCanvas().repaint();
    }
    
    /**
     * Sets whether the rubber band should disappear when the mouse
     * is released or not
     * 
     * @param hideOnRelease	if <code>true</code> the rubber band will
     * 						disappear when the mouse is released, if
     * 						<code>false</code> the rubber band will remain
     * 						visible until a new rubber band is created
     */
    protected void setHideOnRelease(boolean hideOnRelease) {
    	this.hideOnRelease = hideOnRelease;
    }
    
    /**
     * Returns whether or not the rubber band should disappear
     * after the mouse is released
     * 
     * @return	<code>true</code> if the rubber band should
     * 			disappear when the mouse is released, else if
     * 			<code>false</code> the rubber band should remain
     * 			visible until a new rubber band is created
     */
    protected boolean isHideOnRelease() {
    	return hideOnRelease;
    }
}