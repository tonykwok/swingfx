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

/**
 * Abstract class used to define a "Drag Policy".
 * <br>
 * <br>
 * A "Drag Policy" allows the ability for some restrictions to be put in place
 * by a developer.  Currently <code>DragPolicy</code> is setup to only modify
 * the behavior of which {@link java.awt.Component}'s can and can not be dragged.
 * It may be feasible to add more features to <code>DragPolicy</code> in the future.
 * 
 * @author craig
 * @since v0.1
 * <br>
 * $Header: /cvs/swingfx/src/net/java/swingfx/jdraggable/DragPolicy.java,v 1.1 2005/02/26 22:55:46 codecraig Exp $
 */
abstract public class DragPolicy {
	/**
	 * any {@link java.awt.Component} is eligible to be dragged
	 */
	private static final byte POLICY_OPEN	= 1 << 0;
	/**
	 * only {@link Draggable} objects are eligible to be dragged 
	 */
	private static final byte POLICY_STRICT	= 1 << 1;
	
	private byte policy;
	
	private DragPolicy(byte policy) {
		this.policy = policy;
	}
	
	/**
	 * Determines if the given <code>Component</code> is an eligible 
	 * component to be dragged
	 * 
	 * @param comp	the component to check for dragability
	 * 
	 * @return	<code>true</code> if the <code>Component</code> is
	 * 			draggable, <code>false</code> otherwise
	 * 
	 * @see Draggable
	 */
	abstract public boolean isDraggable(Component comp);
	
	/**
	 * The default drag policy which suggests that all components
	 * are eligible to be dragged
	 * 
	 * @see #OPEN
	 * @see #STRICT
	 */
	public static final DragPolicy DEFAULT = new DragPolicy(POLICY_OPEN) {
		public boolean isDraggable(Component comp) {
			return OPEN.isDraggable(comp);
		}
	};
	/**
	 * The drag policy which suggests that all components are eligible to be
	 * dragged
	 * 
	 * @see #DEFAULT
	 * @see #STRICT
	 */
	public static final DragPolicy OPEN = new DragPolicy(POLICY_OPEN) {
		public boolean isDraggable(Component comp) {
			return !(comp == null);
		}
	};
	/**
	 * The drag policy which suggests that only {@link Draggable} components
	 * are eligible to be dragged
	 * 
	 * @see #DEFAULT
	 * @see #OPEN
	 */
	public static final DragPolicy STRICT = new DragPolicy(POLICY_STRICT) {
		public boolean isDraggable(Component comp) {
			if (comp instanceof Draggable) {
				if (((Draggable) comp).getComponent() != null) {
					return true;
				}
			}
			return false;
		}
	};
}