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

import net.java.swingfx.rubberband.canvas.RubberBandCanvas;

/**
 * An Oval {@link RubberBand} which subclasses {@link RectangularRubberBand}
 * The only difference between the two rubber bands is the
 * {@link RubberBand#draw(Graphics)} method
 * 
 * @author rwickesser
 * @since 1.0
 * $Revision: 1.1 $
 */
public class OvalRubberBand extends RectangularRubberBand {
    
	/**
	 * Creates a new rubber band which is oval
	 * 
	 * @param canvas	the canvas to draw the rubber band on
	 */
    public OvalRubberBand(RubberBandCanvas canvas) {
        super(canvas);
    }

    /* (non-Javadoc)
     * @see gui.rubberband.RubberBand#draw(java.awt.Graphics)
     */
    public void draw(Graphics g) {
        g.drawOval(rubberband.x, rubberband.y, rubberband.width, rubberband.height);
    }
}