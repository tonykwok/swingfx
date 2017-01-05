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
import java.awt.event.ContainerEvent;
import java.util.HashSet;
import java.util.Set;


/**
 * A default implementation of the {@link DraggableManager} interface.  This
 * implementation provides the basic functionality to enable {@link java.awt.Component}
 * 's which implement the {@link Draggable} interface to actually be dragged
 * when a user selects and drags a component.  Depending on the {@link net.java.swingfx.jdraggable.DragPolicy}
 * that is set, not all components must implement the <code>Draggable</code> interface.
 * This implementation only supports one component to be dragged at a time.
 * <br>
 * <br>
 * Another idea for an implementation of {@link DraggableManager} is one which
 * registers a {@link javax.swing.JLayeredPane} as the "Draggable Container"
 * such that when a component is dragged, it's depth (or Z-Order) within the
 * "Draggable Container" is changed such that it is on top of the other components.
 * 
 * @author craig
 * @since v0.1
 * <br>
 * $Header: /cvs/swingfx/src/net/java/swingfx/jdraggable/DefaultDraggableManager.java,v 1.2 2006/05/27 00:41:19 codecraig Exp $
 */
public class DefaultDraggableManager implements DraggableManager {
	/**
	 * the {@link Container} which contains {@link Component}'s, which may
	 * or may not, implement the {@link Draggable} interface
	 */
	private Container draggableContainer;
	/**
	 * maintains whether a "Draggable Container" has been registered or not
	 */
	private boolean draggableContainerRegistered;
	/**
	 * the component which was chosen to be dragged
	 */
	private Draggable hitDraggable;
	/**
	 * maintains the state of <code>hitDraggable</cdoe>
	 */
	private byte draggableState;
	/**
	 * the {@link DragPolicy} to obide by
	 */
	private DragPolicy dragPolicy;
	/**
	 * the listener which provides the real ability for a component to
	 * change its location during a "drag"
	 */
    private DraggableListener dragListener;
    /**
     * maintains a {@link java.util.Set} of the components
     * which have had a {@link DraggableListener} added to them.
     * This is only used for "cleanup".
     * This implementation stores the hash code's of each component.
     */
    private Set hearingComponents;
    /**
     * determines whether the "draggable container" layout manager should be
     * set to <code>null</code> once a component is dragged (this allows the components
     * to maintain their position even if the container is resized), or not.
     */
    private boolean nullifyLayout = true;
    
	/**
	 * Creates a new {@link DraggableManager} with no "Draggable Container"
	 * registered
	 *
	 * @see #DefaultDraggableManager(Container)
	 * @see #registerDraggableContainer(Container)
	 */
	public DefaultDraggableManager() {
	}
	
	/**
	 * Creates a new {@link DraggableManager} and registers
	 * <code>draggableContainer</code> as the "Draggable Container"
	 * 
	 * @param draggableContainer	the "Draggable Container" to register
	 * 
	 * @throws IllegalArgumentException	if <code>draggableContainer</code> is
	 * 									<code>null</code>
	 * 
	 * @see #DefaultDraggableManager()
	 * @see #registerDraggableContainer(Container)
	 */
	public DefaultDraggableManager(Container draggableContainer) {
		if (draggableContainer == null) {
			throw new IllegalArgumentException("Can not register a null Draggable Container");
		}
		registerDraggableContainer(draggableContainer);
	}

	/* (non-Javadoc)
	 * @see com.codecraig.jdraggable.DraggableManager#setNullifyLayout(boolean)
	 */
	public void setNullifyLayout(boolean nullifyLayout) {
		this.nullifyLayout = nullifyLayout;
	}
	
	/* (non-Javadoc)
	 * @see com.codecraig.jdraggable.DraggableManager#shouldNullifyLayout()
	 */
	public boolean shouldNullifyLayout() {
		return nullifyLayout;
	}
	
	/* (non-Javadoc)
	 * @see com.codecraig.jdraggable.DraggableManager#startDrag(java.awt.Component)
	 */
	public boolean startDrag(Component componentToDrag) {
		if (isDraggableContainerRegistered()) {
			if (getDragPolicy().isDraggable(componentToDrag)) {
				hitDraggable = (componentToDrag instanceof Draggable ? (Draggable) componentToDrag : new DraggableMask(componentToDrag));
				setState(STATE_STILL);
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.codecraig.jdraggable.DraggableManager#dragging()
	 */
	public boolean dragging() {
		if (isDraggableContainerRegistered()) {
			if (hitDraggable != null) {
				setState(STATE_DRAGGING);
				return true;
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.codecraig.jdraggable.DraggableManager#stopDrag()
	 */
	public boolean stopDrag() {
		if (isDraggableContainerRegistered()) {
			hitDraggable = null;
			setState(STATE_UNKNOWN);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the {@link Container} which registered itself as the
	 * "Draggable Container" with this {@link DraggableManager}
	 * 
	 * @return	the "Draggable Container" or <code>null</code> if not
	 * 			<code>Container</code> has been registered as the
	 * 			"Draggable Container"
	 */
	public Container getDraggableContainer() {
		return draggableContainer;
	}
	
	private boolean isDraggableContainerRegistered() {
		return draggableContainerRegistered;
	}
	
	/**
	 * Sets the state of <code>hitDraggable</code>
	 * 
	 * @param state	the state of <code>hitDraggable</code>
	 */
	private void setState(byte state) {
		draggableState = state;
	}

	/**
	 * Returns the state of the current {@link Draggable} component which this
	 * manager is handling
	 * 
	 * @return the state of the current <code>Draggable</code> component
	 *  
	 * @see net.java.swingfx.jdraggable.DraggableManager#getState(net.java.swingfx.jdraggable.Draggable)
	 */
	public byte getState(Draggable draggableComponent) {
		return draggableState;
	}

	/**
	 * Registers the given {@link Container} as the "Draggable Container"
	 * 
	 * @param draggableContainer	the <code>Container</code> whose <code>Draggable</code>
	 * 								components should be able to be dragged
	 * 
	 * @see DraggableManager#registerDraggableContainer(java.awt.Container)
	 * 
	 * @throws IllegalArgumentException	if a <code>Container</code> has already
	 * 									been registered
	 */
	public void registerDraggableContainer(Container draggableContainer) {
		if (this.draggableContainer == null) {
			this.draggableContainer = draggableContainer;
			draggableContainer.addContainerListener(this);
			dragListener = new DraggableListener(this);
			hearingComponents = new HashSet();
			draggableContainerRegistered = true;
		}
		else {
			throw new IllegalArgumentException("A Draggable Container has already been registered");
		}
	}
	
	/**
	 * Un-Registers the given {@link Container} from being the "Draggable Container"
	 * 
	 * @param draggableContainer	the <code>Container</code> to unregister
	 * 
	 * @see DraggableManager#unregisterDraggableContainer(Container)
	 * 
	 * @throws IllegalArgumentException	if the given container is not the same
	 * 									as the already registered container
	 * @throws IllegalStateException	if no container is currently registered
	 */
	public void unregisterDraggableContainer(Container draggableContainer) {
		if (this.draggableContainer == null) {
			throw new IllegalStateException("Failed to unregister draggable container," +
											" since no draggable container was registered");
		}
		if (this.draggableContainer.equals(draggableContainer)) {
			this.draggableContainer.removeContainerListener(this);
			cleanupHearingComponents();
			this.dragListener = null;
			this.draggableContainer = null;
			draggableContainerRegistered = false;
		}
		else {
			throw new IllegalArgumentException("Failed to unregister draggable container," +
												" the given Container is not the same as the" +
												" register draggable container");					
		}
	}
	
	/**
	 * Removes the listeners from "hearing components"
	 */
	private void cleanupHearingComponents() {
		int count = draggableContainer.getComponentCount();
		for (int i = count - 1; i >= 0 && hearingComponents.size() > 0; i--) {
			Component c = draggableContainer.getComponent(i);
			Integer code = new Integer(c.hashCode());
			if (c != null && hearingComponents.contains(code)) {
				hearingComponents.remove(code);
			}
		}
	}

	/**
	 * Returns the {@link DragPolicy} which this manager obides by
	 * 
	 * @return the <code>DragPolicy</code> for this manager.  If no
	 * 		   policy has been set the default policy is used.
	 * 
	 * @see net.java.swingfx.jdraggable.DraggableManager#getDragPolicy()
	 * @see #setDragPolicy(DragPolicy)
	 * @see DragPolicy#DEFAULT
	 */
	public DragPolicy getDragPolicy() {
		if (dragPolicy == null) {
			setDragPolicy(DragPolicy.DEFAULT);
		}
		return dragPolicy;
	}
	
	/* (non-Javadoc)
	 * @see com.codecraig.jdraggable.DraggableManager#setDragPolicy(com.codecraig.jdraggable.DragPolicy)
	 */
	public void setDragPolicy(DragPolicy dragPolicy) {
		this.dragPolicy = dragPolicy;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ContainerListener#componentAdded(java.awt.event.ContainerEvent)
	 */
	public void componentAdded(ContainerEvent e) {
		if (dragListener == null || isDraggableContainerRegistered() == false) {
			// this should not occur, since we listening to a container in the first place
			throw new IllegalStateException("Draggable Container must be registered prior to adding components");
		}
		Component c = e.getChild();
		Integer code = new Integer(c.hashCode());
		if (hearingComponents.contains(code) == false) {
			hearingComponents.add(code);
			c.addMouseListener(dragListener);
			c.addMouseMotionListener(dragListener);	
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ContainerListener#componentRemoved(java.awt.event.ContainerEvent)
	 */
	public void componentRemoved(ContainerEvent e) {
		Component c = e.getChild();
		Integer code = new Integer(c.hashCode());
		if (hearingComponents.contains(code)) {
			hearingComponents.remove(code);
		}
	}
	
	/**
	 * Returns the <code>Set</code> of components added to the
	 * <code>DraggableContainer</code>
	 *  
	 * @return	the <code>Set</code> of components added to the
	 * 			<code>DraggableContainer</code>
	 */
	protected Set getDraggableComponents() {
		return hearingComponents;
	}
}