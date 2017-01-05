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
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.EventObject;

import net.java.swingfx.rubberband.AnimatedStroke;
import net.java.swingfx.rubberband.canvas.RubberBandCanvas;

/**
 * A {@link RubberBand} which is rectangular and animated
 * 
 * @author rwickesser
 * @since 1.0
 * $Revision: 1.1 $
 */
public class AnimatedRectangularRubberBand extends RectangularRubberBand {

	/** the object responsible for animating the rubber band */
	private AnimatedStroke animStroke;

	/**
	 * Creates a new rubber band which is rectangular and animated
	 * 
	 * @param canvas	the canvas to draw the rubber band on
	 */
    public AnimatedRectangularRubberBand(RubberBandCanvas canvas) {
        super(canvas);
        animStroke = new AnimatedStroke(canvas);
    }
	
    /* (non-Javadoc)
     * @see gui.rubberband.RubberBand#draw(java.awt.Graphics)
     */
	public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(animStroke.getStroke());
        g2.draw(rubberband);
        g2.setStroke(oldStroke);
	}

    /* (non-Javadoc)
     * @see RubberBand#startRubberBand(java.util.EventObject)
     */
    public void startRubberBand(EventObject event) {
    	animStroke.startAnimation();
    }

    /* (non-Javadoc)
     * @see RubberBand#stopRubberBand(java.util.EventObject)
     */
    public void stopRubberBand(EventObject event) {
    	animStroke.stopAnimation();
    }

    /* (non-Javadoc)
     * @see RubberBand#updateRubberBand(java.util.EventObject)
     */
    public void updateRubberBand(EventObject event) {
        // don't need to do anything specific here for this rubber band
    }
}