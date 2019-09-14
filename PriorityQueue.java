import java.util.ArrayList;
import java.util.List;

public class PriorityQueue {

	private PriorityQueueNode root;
	private int size;

	public PriorityQueue() {
		root = null;
		size = 0;
	}

	public void insert(HuffmanNode n) {
		PriorityQueueNode huffNode = new PriorityQueueNode(n);
		insert(huffNode);
	}

	// inserting a new node into the heap
	public void insert(PriorityQueueNode n) {
		if(root == null) {
			root = n;
			size++;
			return;
		}
		List<PriorityQueueNode> nodes = new ArrayList<PriorityQueueNode>();
		nodes.add(root);
		while(!nodes.isEmpty()) {
			PriorityQueueNode next = nodes.remove(0);
			if(next.getHeapLeft() == null) {
				next.setHeapLeft(n);
				n.setHeapParent(next);
				break;
			}
			if(next.getHeapRight() == null) {
				next.setHeapRight(n);
				n.setHeapParent(next);
				break;
			}
			if(next.getHeapLeft() != null) {
				nodes.add(next.getHeapLeft());
			}
			if(next.getHeapRight() != null) {
				nodes.add(next.getHeapRight());
			}
		}
		heapUp(n);
		size++;
	}

	private void heapUp(PriorityQueueNode n) {
		if (n.getHeapParent() == null)
			return;

		PriorityQueueNode parent = n.getHeapParent();
		if (n.getData().lowerCount(parent.getData())) {
			swap(n, parent);
			heapUp(parent);
		}
	}

	private void heapDown(PriorityQueueNode n) {
		if (n == null)
			return;

		if (n.getHeapLeft() == null && n.getHeapRight() == null)
			return;

		PriorityQueueNode lowerChild;

		if (n.getHeapLeft() == null)
			lowerChild = n.getHeapRight();
		else if (n.getHeapRight() == null)
			lowerChild = n.getHeapLeft();
		else if (n.getHeapLeft().getData().lowerCount(n.getHeapRight().getData()))
			lowerChild = n.getHeapLeft();
		else
			lowerChild = n.getHeapRight();

		if (lowerChild.getData().lowerCount(n.getData())) {
			swap(lowerChild, n);
			heapDown(lowerChild);
		}
	}

	// removing the min node from the heap
	public HuffmanNode removeMin() {
		if (root == null)
			return null;

		HuffmanNode min = root.getData();
		if(root.getHeapLeft() == null && root.getHeapRight() == null) {
			root = null;
		}
		else {
			List<PriorityQueueNode> nodes = new ArrayList<PriorityQueueNode>();
			nodes.add(root);
			while(!nodes.isEmpty()) {
				PriorityQueueNode next = nodes.remove(0);
				if(next.getHeapLeft() == null && next.getHeapRight() == null) {
					swap(next,root);
					PriorityQueueNode parent = next.getHeapParent();
					if(parent.getHeapLeft() == next)
						parent.setHeapLeft(null);
					else
						parent.setHeapRight(null);
					heapDown(root);
					break;
				}
				if(next.getHeapLeft() != null) {
					nodes.add(next.getHeapLeft());
				}
				if(next.getHeapRight() != null) {
					nodes.add(next.getHeapRight());
				}
			}
		}		
		size--;
		return min;
	}

	// peek at the min node from the heap but do not remove
	public PriorityQueueNode peekMin() {
		return root;
	}

	// helper function: swap the data of 2 nodes
	private void swap(PriorityQueueNode a, PriorityQueueNode b) {
		HuffmanNode temp = a.getData();
		a.setData(b.getData());
		b.setData(temp);
	}

	// helper function: find out the path to a position
	public String path(int position) {
		if (position < 1)
			return null;
		position++;
		String answer = "";
		while (position > 1) {
			if (position % 2 == 0) { // left branch
				answer = "L" + answer; // prepend
			} else { // right branch
				answer = "R" + answer;
			}
			position /= 2;
		}
		return answer;
	}

	// helper function: get a pointer to the node at the specified position
	public PriorityQueueNode Node(int position) {
		if (position < 0)
			return null;
		if (position == 0)
			return root;
		position--;
		String path = path(position);
		PriorityQueueNode iterator = root;
		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == 'L') {
				iterator = iterator.getHeapLeft();
			} else {
				iterator = iterator.getHeapRight();
			}
		}
		return iterator;
	}

	// helper function: print the entire heap inorder for easier debugging
	public void printHeap() {
		String[] s = { "Heap(inorder)=" };
		recursivePrintHeap(root, s);
		System.out.println(s[0]); // array is passed by reference, string is not
	}

	private void recursivePrintHeap(PriorityQueueNode n, String[] s) {
		// prints the heap in order
		if (n == null)
			return;
		recursivePrintHeap(n.getHeapLeft(), s);
		s[0] += "(" + n.getData().text + ":" + n.getData().count + ")";
		recursivePrintHeap(n.getHeapRight(), s);
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

}
