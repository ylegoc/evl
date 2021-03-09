package eu.daproject.evl.examples.treewalker;

import eu.daproject.evl.examples.common.Room;
import eu.daproject.evl.examples.common.RoomBuilder;

public class MainWalker {

	public static void main(String[] args) throws Throwable {

		Room room = RoomBuilder.build();
		
		{
			Walker walker = new Walker();
			
			TreeTraversal traversal = new TreeTraversal();
			TreePrinter printer = new TreePrinter();
	
			walker.traverse().add(traversal, "traverse");
			walker.enter().add(printer, "enter");
			walker.leave().add(printer, "leave");
			
			walker.walk(room);
		}
		
		{
			Walker walker = new Walker();
			
			TreeTraversal traversal = new TreeTraversal();
			CarCounter counter = new CarCounter();
	
			walker.traverse().add(traversal, "traverse");
			walker.enter().add(counter, "enter");
			walker.leave().add(counter, "leave");
			
			walker.walk(room);
		}
		
	}
}
