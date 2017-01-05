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
import java.util.EventObject;

import net.java.swingfx.rubberband.canvas.RubberBandCanvas;

/**
 * A {@link RubberBand} which is rectangular
 * 
 * @author rwickesser
 * @since 1.0
 * $Revision: 1.1 $
 */
public class RectangularRubberBand extends AbstractRubberBand {

	/**
	 * Creates a new rubber band which is rectangular
	 * 
	 * @param canvas	the canvas to draw the rubber band on
	 */
    public RectangularRubberBand(RubberBandCanvas canvas) {
        super(canvas);
    }

    /* (non-Javadoc)
     * @see gui.rubberband.RubberBand#update(int, int, int, int)
     */
    public void update(int x, int y, int width, int height) {
        rubberband.setBounds(x, y, width, height);
    }
    
    /* (non-Javadoc)
     * @see gui.rubberband.RubberBand#draw(java.awt.Graphics)
     */
    public void draw(Graphics g) {
        g.drawRect(rubberband.x, rubberband.y, rubberband.width, rubberband.height);
    }

    /* (non-Javadoc)
     * @see RubberBand#startRubberBand(java.util.EventObject)
     */
    public void startRubberBand(EventObject event) {
    	// don't need to do anything here
    }

    /* (non-Javadoc)
     * @see RubberBand#stopRubberBand(java.util.EventObject)
     */
    public void stopRubberBand(EventObject event) {
    	// don't need to do anything here
    }

    /* (non-Javadoc)
     * @see RubberBand#updateRubberBand(java.util.EventObject)
     */
    public void updateRubberBand(EventObject event) {
        // don't need to do anything specific here for this rubber band
    }
}