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
import java.awt.event.ContainerListener;


/**
 * An interface which defines a manager whose responsibility is to enable
 * dragging for {@link java.awt.Component}'s of a {@link java.awt.Container}
 * which registers itself as the "draggable container"
 * 
 * @author craig
 * @since v0.1
 * <br>
 * $Header: /cvs/swingfx/src/net/java/swingfx/jdraggable/DraggableManager.java,v 1.1 2005/02/26 22:55:46 codecraig Exp $
 */
public interface DraggableManager extends ContainerListener {
	/**
	 * the state of the component is unknown
	 */
	byte STATE_UNKNOWN	= 1 << 0;
	/**
	 * the component is not being dragged
	 */
	byte STATE_STILL	= 1 << 1;
	/**
	 * the component is being dragged
	 */
	byte STATE_DRAGGING = 1 << 2;

	/**
	 * Returns the {@link Container} which has been registered as the "draggable container"
	 * 
	 * @return	the <code>Container</code> which was registered as the "draggable container"
	 * 			or <code>null</code> if no <code>Container</code> is registered
	 */
	Container getDraggableContainer();
	
	/**
	 * Called before a {@link Component} is actually dragged
	 * 
	 * @param componentToDrag	the component which was chosen to be dragged
	 * 
	 * @return	<code>true</code> if the "drag" can continue, <code>false</code> otherwise
	 */
	boolean startDrag(Component componentToDrag);
	
	/**
	 * Called while the {@link Component} is being dragged
	 * 
	 * @return	<code>true</code> if the dragging can continue,
	 * 			<code>false</code> otherwise
	 */
	boolean dragging();
	
	/**
	 * Called when a {@link Component} has stopped being dragged
	 * 
	 * @return	<code>true</code> if no errors occurred when the drag completed,
	 * 			<code>false</code> otherwise
	 */
	boolean stopDrag();
	
	/**
	 * Registers the given {@link Container} with this manager to enable the
	 * container's {@link Draggable} components to be draggable
	 * 
	 * @param draggableContainer	the <code>Container</code> whose <code>Draggable</code>
	 * 								components should be able to be dragged
	 */
	void registerDraggableContainer(Container draggableContainer);
	
	/**
	 * Un-Registers the given {@link Container} from this manager which stops
	 * this manager from managing the container
	 * 
	 * @param draggableContainer	the <code>Container</code> to unregister
	 */
	void unregisterDraggableContainer(Container draggableContainer);
	
	/**
	 * Returns the state of the given {@link Draggable} component
	 * 
	 * @param draggableComponent	the <code>Draggable</code> whose state
	 * 								is of interest
	 * 
	 * @return	the state of <code>draggableComponent</code> as defined in this
	 * 			interface
	 * 
	 * @see #STATE_DRAGGING
	 * @see #STATE_STILL
	 * @see #STATE_UNKNOWN
	 */
	byte getState(Draggable draggableComponent);
	
	/**
	 * Set the policy for which components are eligible for dragging
	 * 	
	 * @param dragPolicy	the policy to set
	 * 
	 * @see #getDragPolicy()
	 * @see DragPolicy
	 */
	void setDragPolicy(DragPolicy dragPolicy);
	
	/**
	 * Returns the {@link DragPolicy} for which this manager obides by
	 * 
	 * @return	the <code>DragPolicy</code> for this manager
	 * 
	 * @see #setDragPolicy(DragPolicy)
	 * @see DragPolicy
	 */
	DragPolicy getDragPolicy();
	
	/**
	 * Returns the "nullify layout" value
	 * 
	 * @return	<code>true</code> to nullify the layout manager of the "draggable
	 * 			container", <code>false</code> otherwise
	 */
	boolean shouldNullifyLayout();
	
	/**
	 * Sets whether the "draggable container" layout manager should be set to
	 * <code>null</code> once a component is dragged, or not.
	 * <br>
	 * By not setting the layout manager to <code>null</code>, the components
	 * may lose their "dragged" position if the container is resized.
	 * <br>
	 * By default this value is <code>true</code>
	 * 
	 * @param nullifyLayout	<code>true</code> to nullify the layout manager,
	 * 						<code>false</code> otherwise
	 */
	void setNullifyLayout(boolean nullifyLayout);
}