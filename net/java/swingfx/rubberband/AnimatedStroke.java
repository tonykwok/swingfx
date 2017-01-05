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

package net.java.swingfx.rubberband;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import net.java.swingfx.rubberband.canvas.RubberBandCanvas;
import net.java.swingfx.rubberband.rubberbands.RubberBand;

/**
 * Handles the necessary work of animating the stroke of a {@link RubberBand}.
 *
 * The idea for having an animated rubber band came from the java.net project,
 * <a href="https://rubber-band.dev.java.net/">Rubber-Band</a> 
 * 
 * The code was extracted from the project and modified to work with this
 * Rubber Band API.
 *
 * @author rwickesser
 * @since 1.0
 * $Revision: 1.1 $
 */
public class AnimatedStroke {    
    private static final int DASH_WIDTH = 3;
    private static final int STROKE_COUNT = 2 * DASH_WIDTH;
    private static final int DASH_SPEED = 50;

    private int strokeIndex;
    private final Stroke[] strokes;
    private final Timer animationTimer;
    
    /** the canvas which the rubber band will be drawn on */
    private RubberBandCanvas canvas;
    
    public AnimatedStroke(RubberBandCanvas canvas) {
        this.canvas = canvas;
        animationTimer = new Timer(DASH_SPEED, getAnimationActionListener());
        strokes = new Stroke[STROKE_COUNT];
        initStrokes();
    }
    
    /**
     * Initializes the array of <code>Stroke</code>'s
     */
    private void initStrokes() {
        float[] dash = {DASH_WIDTH, DASH_WIDTH};
        for(int i = 0; i < STROKE_COUNT; i++) {
            strokes[i] = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, dash, i);
        }
    }
    
    /**
     * Returns the <code>Stroke</code> at <code>strokeIndex</code>
     * 
     * @return  the <code>Stroke</code> at <code>strokeIndex</code>
     */
    public Stroke getStroke() {
        return strokes[strokeIndex];
    }
    
    /**
     * Starts the animated stroke
     */
    public void startAnimation() {
        animationTimer.start();
    }
    
    /**
     * Stops the animated stroke
     */
    public void stopAnimation() {
        animationTimer.stop();
    }
    
    /**
     * Returns a new <code>ActionListener</code> that updates the stroke
     * as needed
     * 
     * @return  a new <code>ActionListener</code> that updates the stroke
     *          as needed
     */
    private ActionListener getAnimationActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                strokeIndex++;
                strokeIndex %= STROKE_COUNT;
                canvas.getCanvas().repaint();
            }
        };
    }
}