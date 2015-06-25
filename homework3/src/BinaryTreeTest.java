import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;

public class BinaryTreeTest {
	
	BinaryTree<String> tree;

	String[] noRotateTraversals = { "M", "H", "S", "D", "K", "N", "T", "A", "L", "U" };
	String[] inorderTraversal = {"A", "D", "H", "K", "L", "M", "N", "S", "T", "U" };
	String[] postorderTraversal = {"A", "D", "L", "K", "H", "N", "U", "T", "S", "M" };
	String[] preorderTraversal = {"M", "H", "D", "A", "K", "L", "S", "N", "T", "U" };
	String[] levelorderTraversal = {"M", "H", "S", "D", "K", "N", "T", "A", "L", "U" };
	String[] levelorderRemoveOne = {"M", "H", "S", "D", "L", "N", "T", "A", "U" };
	String[] levelorderRemoveRoot = {"N", "H", "S", "D", "K", "T", "A", "L", "U" };
	String[] levelorderRemoveMultiChild = {"M", "K", "S","D", "L", "N", "T", "A", "U"};
	String[] levelorderRemoveMultiChildRightSide = {"M", "H", "T","D", "K", "N", "U", "A", "L"};

	@Before
	public void setup() {
		tree = new BST<String>();
	}
	
	@Test
	public void testAddNull() {

		assertFalse(tree.add(null));
	}

	@Test
	public void testAddOne() {
		assertTrue(tree.add("A"));
		assertEquals(1, tree.size());
		assertFalse(tree.isEmpty());
	}

	@Test
	public void testAddMany() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			assertTrue(tree.add(noRotateTraversals[i]));
		}
		assertEquals(noRotateTraversals.length, tree.size());
		assertFalse(tree.isEmpty());
	}


	@Test
	public void testAddDuplicate() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}

		assertFalse(tree.add("A"));
		assertEquals(noRotateTraversals.length, tree.size());
	}

	@Test
	public void testMaxEmptyTree() {
		assertNull(tree.max());
	}

	@Test
	public void testMaxOne() {
		tree.add("D");
		assertEquals("D", tree.max());
	}

	@Test
	public void testMaxMany() {
		for (int i = 0; i < noRotateTraversals.length; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertEquals("U", tree.max());
	}
	@Test
	public void  testMinOne() {
		tree.add("D");
		assertEquals("D", tree.min());
	}
	@Test
	public void testMinEmptyTree() {
		assertNull(tree.min());
	}
	@Test
	public void testMinMany() {
		for (int i = 0; i < noRotateTraversals.length; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertEquals("A", tree.min());
	}
	@Test
	public void testSizeEmpty() {
		assertEquals(0, tree.size());
	}

	@Test
	public void testSizeNotEmpty() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertEquals(noRotateTraversals.length, tree.size());
	}

	@Test
	public void testIsEmptyEmpty() {
		assertTrue(tree.isEmpty());
	}


	@Test
	public void testMinEmpty() {
		assertNull(tree.min());
	}

	@Test
	public void testMaxEmpty() {
		assertNull(tree.max());
	}

	@Test
	public void testMinNotEmpty() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}

		assertEquals("A", tree.min());
	}

	@Test
	public void testContainsEmpty() {
		assertFalse(tree.contains("A"));
	}

	@Test
	public void testContainsNotEmptyThere() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertTrue(tree.contains("N"));
	}

	@Test
	public void testContainsNotEmptyNotThere() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertFalse(tree.contains("R"));
	}


	@Test
	public void testRemoveEmpty() {
		assertFalse(tree.remove("A"));
	}

	@Test
	public void testRemoveNotThere() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertFalse(tree.remove("Q"));
	}

	@Test
	public void testRemoveLeafNode() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertTrue(tree.remove("L"));
		List<String> list = tree.getLevelOrder();
		int j = 0;
		for (int i = 0; i < levelorderTraversal.length; ++i) {
			if (levelorderTraversal[i].equals("L")) continue;
			assertEquals(list.get(j), levelorderTraversal[i]);
			j++;
		}
	}

	@Test
	public void testRemoveOneChildNode() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertTrue(tree.remove("K"));
		List<String> list = tree.getLevelOrder();

		for (int i = 0; i < levelorderRemoveOne.length; ++i) {
			assertEquals(list.get(i), levelorderRemoveOne[i]);
		}
	}

	@Test
	public void testRemoveRoot() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertTrue(tree.remove("M"));
		List<String> list = tree.getLevelOrder();

		for (int i = 0; i < levelorderRemoveRoot.length; ++i) {
            String s = list.get(i);
            System.out.println(s + " ");
			assertEquals(s, levelorderRemoveRoot[i]);
		}
	}

	@Test
	public void testRemoveMultipleChildLeftSide() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertTrue(tree.remove("H"));
		List<String> list = tree.getLevelOrder();

		for (int i = 0; i < levelorderRemoveMultiChild.length; i++) {
			String s = list.get(i);
			System.out.println(s + " ");
			assertEquals(s, levelorderRemoveMultiChild[i]);
		}
	}
	@Test
	public void testRemoveMultiplechildRightSide() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		assertTrue(tree.remove("S"));
		List<String> list = tree.getLevelOrder();

		for (int i = 0; i < levelorderRemoveMultiChildRightSide.length; i++) {
			String s = list.get(i);
			System.out.println(s + " ");
			assertEquals(s, levelorderRemoveMultiChildRightSide[i]);
		}
	}

	@Test
	public void testRemoveNull() {
		tree.add("A");

		assertFalse(tree.remove(null));
	}

	@Test
	public void testRemoveLastItem() {
		tree.add("A");
		assertTrue(tree.remove("A"));
		assertTrue(tree.isEmpty());
		assertEquals(0, tree.size());
	}

	@Test
	public void testIteratorEmpty() {
		Iterator<String> iter = tree.iterator();
		assertFalse(iter.hasNext());
	}

	@Test
	public void testIteratorNotEmpty() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}
		Iterator<String> iter = tree.iterator();

		for (int i = 0; i < inorderTraversal.length; ++i) {
			assertTrue(iter.hasNext());
			assertEquals(iter.next(), inorderTraversal[i]);
		}
	}

	@Test
	public void testGetPostOrderEmpty() {
		List<String> list = tree.getPostOrder();
		assertTrue(list.isEmpty());
	}

	@Test
	public void testGetPostOrderNotEmpty() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}

		List<String> list = tree.getPostOrder();

		for (int i = 0; i < postorderTraversal.length; ++i) {
			assertEquals(list.get(i), postorderTraversal[i]);
		}
	}

	@Test
	public void testGetPreOrderEmpty() {
		List<String> list = tree.getPreOrder();
		assertTrue(list.isEmpty());
	}

	@Test
	public void testGetPreOrderNotEmpty() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}

		List<String> list = tree.getPreOrder();

		for (int i = 0; i < preorderTraversal.length; ++i) {
			assertEquals(list.get(i), preorderTraversal[i]);
		}
	}

	@Test
	public void testInOrderEmpty() {
		List<String> list = tree.getInOrder();
		assertTrue(list.isEmpty());
	}
	@Test
	public void testInOrderNotEmpty() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}

		List<String> list = tree.getInOrder();

		for (int i = 0; i < preorderTraversal.length; ++i) {
			assertEquals(list.get(i),inorderTraversal[i]);
		}
	}
	@Test
	public void testGetLevelOrderEmpty() {
		List<String> list = tree.getLevelOrder();
		assertTrue(list.isEmpty());
	}

	@Test
	public void testGetLevelOrderNotEmpty() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}

		List<String> list = tree.getLevelOrder();

		for (int i = 0; i < levelorderTraversal.length; ++i) {
			assertEquals(list.get(i), levelorderTraversal[i]);
		}
	}

	@Test
	public void testInOrderOne() {
		tree.add("A");
		List<String> list = tree.getInOrder();
		assertEquals(list.get(0), "A");
	}

	@Test
	public void testClearEmpty() {
		tree.clear();
		assertTrue(tree.size() == 0);
		assertTrue(tree.isEmpty());

	}

	@Test
	public void testClearNotEmpty() {
		for (int i = 0; i < noRotateTraversals.length ; ++i) {
			tree.add(noRotateTraversals[i]);
		}

		tree.clear();
		assertTrue(tree.size() == 0);
		assertTrue(tree.isEmpty());

	}

}
