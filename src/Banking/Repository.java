package Banking;

import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Repository<T> {
	private String fileName;

	public Repository(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<T> readAll() {
		ArrayList<T> list = new ArrayList<T>();
		File file = new File(fileName);
		if (!file.exists()) {
			return list;
		}
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
				ObjectInputStream ois = new ObjectInputStream(bis)) {
			while (true) {
				try {
					list.add((T) ois.readObject());
				} catch (EOFException eof) {
					break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error reading " + fileName + ": " + e.getMessage());
		}
		return list;
	}

	public void writeAll(ArrayList<T> list) {
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));
				ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			for (T item : list) {
				oos.writeObject(item);
			}
		} catch (IOException e) {
			System.out.println("Error writing " + fileName + ": " + e.getMessage());
		}
	}
}