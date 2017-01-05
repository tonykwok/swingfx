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

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.EventObject;

import net.java.swingfx.rubberband.canvas.RubberBandCanvas;

/**
 * Defines the required functionality for creating a rubber band
 * 
 * @author rwickesser
 * @since 1.0
 * $Revision: 1.1 $
 */
public interface RubberBand {

    /**
     * Enforces that the mouse listeners are added to the canvas
     */
    public void addMouseListeners();
	
    /**
     * Draws the rubber band on the given <code>Graphics</code> object
     * 
     * @param g the <code>Graphics</code> object to draw the rubber band on
     */
    public void draw(Graphics g);
    
    /**
     * Returns an integer <code>Rectangle</code> which contains the
     * size and location of this rubber band
     * 
     * @return	an integer <code>Rectangle</code> which contains the size and
     * 			location of this rubber band
     */
    public Rectangle getBounds();
    
    /**
     * Sets the canvas which the rubber band will be drawn onto
     * 
     * @param canvas    the canvas which the rubber band will be drawn onto
     */
    public void setCanvas(RubberBandCanvas canvas);
    
    /**
     * Updates the parameters of the rubber band
     * 
     * @param x         the x coordinate
     * @param y         the y coordinate
     * @param width     the width of the rubber band
     * @param height    the height of the rubber band
     */
    public void update(int x, int y, int width, int height);

    /**
     * Called when the rubber band is first created, typically on a mouse pressed
     * event
     * 
     * @param event the event that started the rubber band
     */
    public void startRubberBand(EventObject event);
    
    /**
     * Called when the rubber band is done being created, typically on a mouse released
     * event
     * 
     * @param event the event that stopped the rubber band
     */
    public void stopRubberBand(EventObject event);
    
    /**
     * Called when the rubber band is being updated, typically on a mouse dragged
     * event
     * 
     * @param event the event that started the rubber band
     */
    public void updateRubberBand(EventObject event);
}