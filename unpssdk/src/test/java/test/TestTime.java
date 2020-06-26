package test;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TestTime {
	private static ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<String, AtomicInteger>();
	private static final Integer mapLocker = new Integer(0);

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		int interval = 10;
		Date date = new Date();
		System.out.println(date.getMinutes() / interval);
		System.out.println(date.getMinutes() % interval);
		System.out.println(date.getHours());
		String key = "" + date.getHours() + (date.getMinutes() / interval * interval);
		System.out.println(key);
		//
		if (map.get(key) == null) {
			synchronized (mapLocker) {
				if (map.get(key) == null) {
					map.clear();
					AtomicInteger count = new AtomicInteger(0);
					count.getAndIncrement();
					map.put(key, count);
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			AtomicInteger count = map.get(key);
			System.out.println(count.get());
			System.out.println(count.getAndIncrement());
		}
	}
}
