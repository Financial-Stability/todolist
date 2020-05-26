package application.old;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TreeSerializer implements Serializable {

	Tree tree = null;

	public TreeSerializer() {
	}

	public void serializeTree(Tree tree, String fileName) {

		// Serialization
		try {
			// Saving of object in a file
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Method for serialization of object
			out.writeObject(tree);

			out.close();
			file.close();

			System.out.println("Tree has been serialized");

		}

		catch (IOException ex) {
			System.out.print("IOException is caught: ");
			System.out.println(ex);
		}
	}

	public Tree deserializeTree(String fileName) {
		Tree object1 = null;
		// Deserialization
		try {
			// Reading the object from a file
			FileInputStream file = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			object1 = (Tree) in.readObject();

			in.close();
			file.close();

			System.out.println("Tree has been deserialized ");
			System.out.println("Database title: " + object1.getTitle());
			System.out.println("Database description: " + object1.getDescription());
		}

		catch (IOException ex) {
			return null;
		}

		catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught");
		}

		return object1;
	}

}
