package eu.daproject.evl.examples.queue;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import eu.daproject.evl.Method1;
import eu.daproject.evl.examples.common.Box;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Doll;
import eu.daproject.evl.examples.common.Element;
import eu.daproject.evl.examples.common.Game;
import eu.daproject.evl.examples.common.Room;
import eu.daproject.evl.examples.common.Tallboy;

public class ProducerConsumer {

	private Random random = new Random();
	private int lostElements;
	
	private Element createElement() {
		int type = random.nextInt(6);
		switch (type) {
		case 0:
			return new Car("blue");
		case 1:
			return new Doll(18);
		case 2:
			return new Game(5);
		case 3:
			return new Box(10);
		case 4:
			return new Room("white");
		case 5:
			return new Tallboy("wood");
		}
		
		return null;
	}
	
	public void run(Method1<Void> counterMethod) {
		
		LinkedBlockingQueue<Element> queue = new LinkedBlockingQueue<Element>();
		
		Thread producer = new Thread(() -> {
			
			for (int i = 0; i < 50; ++i) {
				try {
					queue.put(createElement());
				}
				catch (InterruptedException e) {
				}
			}
		});
		
		Thread consumer = new Thread(() -> {
		    
			for (int i = 0; i < 50; ++i) {
				Element element = null;
				try {
					element = queue.take();
				}
				catch (InterruptedException e) {
				}
				
				try {
					counterMethod.invoke(element);
				}
				catch (Throwable e) {
					lostElements++;
					System.err.println("Error, " + element + " cannot be counted.");
				}
			}
		});
		
		lostElements = 0;
		
		producer.start();
		consumer.start();
		
		try {
			producer.join();
			consumer.join();
		}
		catch (InterruptedException e) {
		}
	}
	
	public int getLostElements() {
		return lostElements;
	}

	public static void main(String[] args) {
		
		ProducerConsumer runner = new ProducerConsumer();
		
		Method1<Void> counterMethod = new Method1<Void>();
		
		ToyCounter toyCounter = new ToyCounter();
		counterMethod.add(toyCounter, "count");
		
		System.out.println("Run the counter...");
		runner.run(counterMethod);
		
		System.out.println(toyCounter.getCarCount() + " cars received.");
		System.out.println(toyCounter.getDollCount() + " dolls received.");
		System.out.println(toyCounter.getGameCount() + " games received.");
		System.out.println(runner.getLostElements() + " elements lost.\n");
		
		toyCounter.reset();
		
		ContainerCounter containerCounter = new ContainerCounter();
		counterMethod.add(containerCounter, "count");
		
		System.out.println("Run the counter...");
		runner.run(counterMethod);
		
		System.out.println(toyCounter.getCarCount() + " cars received.");
		System.out.println(toyCounter.getDollCount() + " dolls received.");
		System.out.println(toyCounter.getGameCount() + " games received.");
		System.out.println(containerCounter.getBoxCount() + " boxes received.");
		System.out.println(containerCounter.getRoomCount() + " rooms received.");
		System.out.println(containerCounter.getTallboyCount() + " tallboys received.");
		System.out.println(runner.getLostElements() + " elements lost.");
	}
}
