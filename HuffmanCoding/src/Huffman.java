import java.util.ArrayList;
import java.util.Queue;

public class Huffman {
	char[] symbols;
	String[] codes;
	float[] probs;
	
	BinaryTree<Float> huffman;
	Queue<BinaryTree<Float>> leaves;
	Queue<BinaryTree<Float>> trees;
	ArrayList<BinaryTree<Float>> locations;
	
	public Huffman(char[] symbols, float[] probs){
		
	}
	
	public String decode(char code){
		return null;
		
	}

	public String getCode(char symbol){
		return null;
		
	}
	
	
	public void processCodes(){
		
		
		
	}
	
	/*
	 * selectMin chooses the leaf with the smaller probability from the queue
	 * and returns it to be combined
	 */
	
	public BinaryTree<Float> selectMin(){
		
			BinaryTree<Float> leaf1 = leaves.peek();
			BinaryTree<Float> leaf2 = trees.peek();
			float prob1 = getProb(leaf1);
			float prob2 = getProb(leaf2);
			
			if(prob1 < prob2){
				leaves.remove();
				return leaf1;
			}else{
				trees.remove();
				return leaf2;
			}
	}
	
	/*
	 * buildLeaves creates the leaves and places them in the queue that will be used
	 * to create the final tree
	 */
	
	public void buildLeaves() throws Exception{
		for(int i = 0; i < symbols.length; i++){
			BinaryTree<Float> temp = new BinaryTree<Float>();
			temp.makeRoot((float)i);
			locations.add(temp);
			leaves.add(temp);
		}
	}
	
	/*
	 * buildTree builds the first tree in the trees queue and will allow the 
	 * buildAll method to run and create the rest of the tree
	 */
	
	public void buildTree(BinaryTree<Float> left, BinaryTree<Float> right) throws Exception{
		BinaryTree<Float> root = new BinaryTree<Float>();
		float prob1 = getProb(left);
		float prob2 = getProb(right);
		root.makeRoot(prob1 + prob2);
		
		root.addLeft(left);
		root.addRight(right);
		left.parent = root;
		right.parent = root;
		
		trees.add(root);
	}
	
	public void BuildAll(){
		
	}
	
	public void treeHeight(){
		
	}
	
	public float getProb(BinaryTree<Float> tree){
		if(tree.left == null && tree.right == null){
			float index = tree.getData();
			return probs[(int)index];
		}
		
		return tree.getData();
	}
}
