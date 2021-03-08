package eu.daproject.evl.examples.treewalker;

import eu.daproject.evl.Cases;
import eu.daproject.evl.examples.common.Container;
import eu.daproject.evl.examples.common.Element;
import eu.daproject.evl.examples.common.Room;
import eu.daproject.evl.examples.common.RoomBuilder;

public class MainPrint {

	public static void main(String[] args) throws Throwable {
	
		Print.method().add(new Cases() {
			
			void match(Container container, Space space) {
				
				System.out.println(space.get() + container);
				
				space.increment();
				
				for (Element element : container.getElements()) {
					try {
						Print.method().invoke(element, space);
					}
					catch (Throwable e) {
						System.out.println(space.get() + "?");
					}
				}
				
				space.decrement();
			}
			
			void match(Element element, Space space) {
				System.out.println(space.get() + element);
			}
		});
	
		
		Room room = RoomBuilder.build();
		
		Print.print(room);
	}
}
