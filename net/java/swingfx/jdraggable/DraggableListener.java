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

package net.java.swingfx.jdraggable;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 * A listener which receives {@link java.awt.event.MouseEvent}'s to aid
 * in the "dragging" of a {@link java.awt.Component}
 * 
 * @author craig
 * @since v0.1
 * <br>
 * $Header: /cvs/swingfx/src/net/java/swingfx/jdraggable/DraggableListener.java,v 1.1 2005/02/26 22:55:45 codecraig Exp $
 */
public class DraggableListener extends MouseInputAdapter {
    private Point componentTopLeft;
    private Point componentTopRight;
    private Point componentBottomLeft;
    private Point componentBottomRight;
    private Point containerTopLeft;
    private Point containerTopRight;
    private Point containerBottomLeft;
    private Point containerBottomRight;
	
	private DraggableManager dragManager;
	private Component draggableComponent;
	private Container draggableContainer;
	private int offsetX;
	private int offsetY;
	
	/**
	 * Creates a new listener which communicates with the given {@link DraggableManager}
	 * during "dragging"
	 * 
	 * @param dragManager	the <code>DraggableManager</code> to communicate with
	 */
	public DraggableListener(DraggableManager dragManager) {
		this.dragManager = dragManager;
		this.draggableContainer = dragManager.getDraggableContainer();
	}
	
	/**
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {
		if (dragManager.dragging()) {
			//TODO: come up with a better solution than this, and once we do, remove
			// shouldNullifyLayout() from the interface
			if (dragManager.shouldNullifyLayout() && draggableContainer.getLayout() != null) {
				draggableContainer.setLayout(null);
			}
			int x = e.getX();
	      	int y = e.getY();
	      	Point pt = SwingUtilities.convertPoint(draggableComponent, x, y, draggableContainer);
	      	draggableComponent.setLocation(pt.x - offsetX, pt.y - offsetY);
	      	stayInContainer();
		}
	}
	
	/**
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		draggableComponent = e.getComponent();
		if (dragManager.startDrag(draggableComponent)) {
			offsetX = e.getX();
			offsetY = e.getY();
		}
	}

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		dragManager.stopDrag();
		draggableComponent = null;
	}
	
	/**
	 * Determines if the current "draggable component" is in the 
	 * "draggable container"
	 * 
	 * @return	<code>true</code> if the component is in the container,
	 * 			<code>false</code> otherwise
	 */
	private boolean isInContainer() {
        Rectangle r = new Rectangle( draggableContainer.getWidth(), draggableContainer.getHeight() );
        int wC = (int)r.getWidth();
        int hC = (int)r.getHeight();
        containerTopLeft = r.getLocation();
        containerTopRight = new Point( (int)containerTopLeft.getX()+wC, (int)containerTopLeft.getY() );
        containerBottomLeft = new Point( (int)containerTopLeft.getX(), (int)containerTopLeft.getY()+hC );
        containerBottomRight = new Point( (int)containerTopLeft.getX()+wC, (int)containerTopLeft.getY()+hC );
        int w = draggableComponent.getWidth();
        int h = draggableComponent.getHeight();
        componentTopLeft = draggableComponent.getLocation();
        componentTopRight = new Point( (int)componentTopLeft.getX()+w, (int)componentTopLeft.getY() );
        componentBottomLeft = new Point( (int)componentTopLeft.getX(), (int)componentTopLeft.getY()+h );
        componentBottomRight = new Point( (int)componentTopLeft.getX()+w, (int)componentTopLeft.getY()+h );
        if( !r.contains(componentTopLeft) ) return false;
        if( !r.contains(componentTopRight) ) return false;
        if( !r.contains(componentBottomLeft) ) return false;
        if( !r.contains(componentBottomRight) ) return false;
        return true;
    }
	
	/**
	 * Assures that the "draggable component" stays within the "draggable container"
	 */
    private void stayInContainer(){
        /* if the plate goes is too far in right-direction, shift it back*/
        if( !isInContainer() ){    
            double x = componentTopRight.getX();
            double xC = containerTopRight.getX();
            if( x > xC ){
            	draggableComponent.setLocation( (int)(componentTopLeft.getX()+xC-x), (int)componentTopLeft.getY());
            }
        }
        /* if the plate goes is too far in left-direction, shift it back */
        if( !isInContainer() ){
            double x = componentTopLeft.getX();
            double xC = containerTopLeft.getX();
            if( x < xC ){
            	draggableComponent.setLocation( (int)(componentTopLeft.getX()+xC-x), (int)componentTopLeft.getY());
            }
        }
        /* if the plate goes is too far in top-direction, shift it back */
        if( !isInContainer() ){
            double y = componentTopLeft.getY();
            double yC = containerTopLeft.getY();
            if( y < yC ){
            	draggableComponent.setLocation( (int)componentTopLeft.getX(), (int)(componentTopLeft.getY()+yC-y));
            }
        }
        /* if the plate goes is too far in bottom-direction, shift it back */
        if( !isInContainer() ){
            double y = componentBottomLeft.getY();
            double yC = containerBottomLeft.getY();
            if( y > yC ){
            	draggableComponent.setLocation( (int)componentTopLeft.getX(), (int)( componentTopLeft.getY()+yC-y) );
            }
        }
    }
}