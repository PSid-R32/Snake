class HuffmanNode {
  String text;
  int count;
  String code;
  HuffmanNode huffLeft;
  HuffmanNode huffRight;
  HuffmanNode huffParent;

  // constructs a single node based on text and count
  public HuffmanNode(String t, int c) {
    text = t;
    count = c;
  }

  public HuffmanNode(HuffmanNode left, HuffmanNode right) {
    text = left.text + right.text;
    count = left.count + right.count;
    huffLeft = left;
    left.huffParent = this;
    huffRight = right;
    right.huffParent = this;
  }

  public String toString() {
    return text + ":" + count;
  }

  public void print() {
    recursivePrint(this, "");
  }

  public void generateCode() {
	  recursiveGenerateCode(this, "");
  }
  
  private void recursiveGenerateCode(HuffmanNode n, String s) {
	  if(n == null)
		  return;
	  if(n.getHuffLeft() == null && n.getHuffRight() == null)
		  n.code = s;
	  recursiveGenerateCode(n.getHuffLeft(), s+"0");
	  recursiveGenerateCode(n.getHuffRight(), s+"1");
  }
  
  private void recursivePrint(HuffmanNode n, String s) {
    if (n == null)
      return;
    recursivePrint(n.huffLeft, s + "0");
    if (n.text.length() == 1) { // is leaf
      System.out.println("<"+n.text + "> " + n.code);
    }
    recursivePrint(n.huffRight, s + "1");
  }

  public boolean lowerCount(HuffmanNode otherNode) {
    return (this.count < otherNode.count);
  }

/**
 * @return the huffLeft
 */
public HuffmanNode getHuffLeft() {
	return huffLeft;
}

/**
 * @param huffLeft the huffLeft to set
 */
public void setHuffLeft(HuffmanNode huffLeft) {
	this.huffLeft = huffLeft;
}

/**
 * @return the huffRight
 */
public HuffmanNode getHuffRight() {
	return huffRight;
}

/**
 * @param huffRight the huffRight to set
 */
public void setHuffRight(HuffmanNode huffRight) {
	this.huffRight = huffRight;
}

/**
 * @return the huffParent
 */
public HuffmanNode getHuffParent() {
	return huffParent;
}

/**
 * @param huffParent the huffParent to set
 */
public void setHuffParent(HuffmanNode huffParent) {
	this.huffParent = huffParent;
}
  
  
}
