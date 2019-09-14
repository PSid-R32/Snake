public class PriorityQueueNode {

	private HuffmanNode data;
	private PriorityQueueNode heapLeft;
	private PriorityQueueNode heapRight;
	private PriorityQueueNode heapParent;

	public PriorityQueueNode(HuffmanNode data) {
		this.data = data;
	}

	public HuffmanNode getData() {
		return data;
	}

	public void setData(HuffmanNode data) {
		this.data = data;
	}

	public PriorityQueueNode getHeapLeft() {
		return heapLeft;
	}

	public void setHeapLeft(PriorityQueueNode heapLeft) {
		this.heapLeft = heapLeft;
	}

	public PriorityQueueNode getHeapRight() {
		return heapRight;
	}

	public void setHeapRight(PriorityQueueNode heapRight) {
		this.heapRight = heapRight;
	}

	public PriorityQueueNode getHeapParent() {
		return heapParent;
	}

	public void setHeapParent(PriorityQueueNode heapParent) {
		this.heapParent = heapParent;
	}

	public boolean isLeaf() {
		return (heapLeft == null && heapRight == null);
	}
}
