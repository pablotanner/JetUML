/*******************************************************************************
 * JetUML - A desktop application for fast UML diagramming.
 *
 * Copyright (C) 2020 by McGill University.
 *     
 * See: https://github.com/prmr/JetUML
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.
 *******************************************************************************/
package org.jetuml.viewers.nodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jetuml.JavaFXLoader;
import org.jetuml.diagram.Diagram;
import org.jetuml.diagram.DiagramType;
import org.jetuml.diagram.nodes.UseCaseNode;
import org.jetuml.geom.Point;
import org.jetuml.rendering.nodes.UseCaseNodeRenderer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUseCaseNodeViewer
{
	private UseCaseNode aNode; 
	private final UseCaseNodeRenderer aViewer = new UseCaseNodeRenderer(DiagramType.newRendererInstanceFor(new Diagram(DiagramType.USECASE)));
	
	@BeforeAll
	public static void setupClass()
	{
		JavaFXLoader.load();
	}
	
	@BeforeEach
	public void setup()
	{
		aNode = new UseCaseNode();
	}
	
	@Test
	public void testGetBounds_NoName()
	{
		aNode.setName("");
		assertEquals(new Point(0,0), aViewer.getBounds(aNode).getOrigin());
		assertEquals(110, aViewer.getBounds(aNode).getWidth());
		assertEquals(40, aViewer.getBounds(aNode).getHeight());
	}
	
	@Test
	public void testGetBounds_ShortName()
	{
		aNode.setName("X");
		assertEquals(new Point(0,0), aViewer.getBounds(aNode).getOrigin());
		assertEquals(110, aViewer.getBounds(aNode).getWidth());
		assertEquals(40, aViewer.getBounds(aNode).getHeight());
	}
	
	@Test
	public void testGetBounds_LongName()
	{
		aNode.setName("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		assertEquals(new Point(0,0), aViewer.getBounds(aNode).getOrigin());
		assertTrue(aViewer.getBounds(aNode).getWidth() > 110);
		assertEquals(40, aViewer.getBounds(aNode).getHeight());
	}
}
