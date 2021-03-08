package eu.daproject.evl.examples.treewalker;

import eu.daproject.evl.examples.common.Room;
import eu.daproject.evl.examples.common.RoomBuilder;

public class MainWalker {

	public static void main(String[] args) throws Throwable {
	
		Walker walker = new Walker();
		
		TreeTraversal traversal = new TreeTraversal();
		TreePrinter printer = new TreePrinter();

		walker.traverse().add(traversal, "traverse");
		walker.enter().add(printer, "enter");
		walker.leave().add(printer, "leave");
		
		Room room = RoomBuilder.build();
		
		walker.walk(room);
	}
}
