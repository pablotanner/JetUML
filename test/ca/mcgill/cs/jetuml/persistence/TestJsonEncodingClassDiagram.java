/*******************************************************************************
 * JetUML - A desktop application for fast UML diagramming.
 *
 * Copyright (C) 2018 by the contributors of the JetUML project.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ca.mcgill.cs.jetuml.persistence;

import static ca.mcgill.cs.jetuml.persistence.PersistenceTestUtils.assertHasKeys;
import static ca.mcgill.cs.jetuml.persistence.PersistenceTestUtils.build;
import static ca.mcgill.cs.jetuml.persistence.PersistenceTestUtils.findRootNode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Iterator;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.JavaFXLoader;
import ca.mcgill.cs.jetuml.diagram.ClassDiagram;
import ca.mcgill.cs.jetuml.diagram.Node;
import ca.mcgill.cs.jetuml.diagram.nodes.ClassNode;
import ca.mcgill.cs.jetuml.diagram.nodes.PackageNode;

public class TestJsonEncodingClassDiagram
{
	private ClassDiagram aGraph;
	
	@BeforeAll
	public static void setupClass()
	{
		JavaFXLoader.load();
	}
	
	@BeforeEach
	public void setup()
	{
		aGraph = new ClassDiagram();
	}
	
	/*
	 * Initializes a graph with a class node contained in a package node.
	 */
	private void initiGraph1()
	{
		PackageNode p = new PackageNode();
		p.setName("package");
		ClassNode c = new ClassNode();
		c.setName("class");
		p.addChild(c);
		aGraph.addRootNode(p);
	}
	
	@Test
	public void testEmpty()
	{
		JSONObject object = JsonEncoder.encode(aGraph);
		assertHasKeys(object, "diagram", "nodes", "edges", "version");
		assertEquals("ClassDiagram", object.getString("diagram"));
		assertEquals(0, object.getJSONArray("nodes").length());	
		assertEquals(0, object.getJSONArray("edges").length());				
	}
	
	@Test
	public void testEncodeDecodeGraph1()
	{
		initiGraph1();
		ClassDiagram diagram = (ClassDiagram) JsonDecoder.decode(JsonEncoder.encode(aGraph));
		
		Iterator<Node> iter = diagram.rootNodes().iterator();
		iter.next();
		assertFalse(iter.hasNext());
		
		PackageNode p = (PackageNode) findRootNode(diagram, PackageNode.class, build("name", "package"));

		assertEquals(1, p.getChildren().size());
		
		ClassNode node = (ClassNode) p.getChildren().get(0);
		assertSame(p, node.getParent());
		assertEquals("class", node.getName());
	}
}
