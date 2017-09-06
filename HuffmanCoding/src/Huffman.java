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
	
	public Huffman(char[] symbols, float[] probs) throws Exception{
		
		this.symbols = symbols;
		this.probs = probs;
		locations = new ArrayList<BinaryTree<Float>>(symbols.length);
	
		if(symbols.length == 1){
			BinaryTree<Float> leaf = new BinaryTree<Float>();
			leaf.makeRoot(0f);
			locations.add(leaf);
			huffman = new BinaryTree<Float>();
			huffman.makeRoot(probs[0]);
			huffman.addLeft(leaf);
			leaf.parent = huffman;
			return;
		}
		
		buildLeaves();
		
		BinaryTree<Float> leaf1 = leaves.remove();
		BinaryTree<Float> leaf2 = leaves.remove();
		
		buildTree(leaf1, leaf2);
		
		buildAll();
		
		huffman = trees.remove();
		
		processCodes();
		
	}
	
	public String decode(char code){
		return null;
		
	}

	public String getCode(char symbol){
		return null;
	}
	
	
	public void processCodes(){
		int height = treeHeight(huffman);
		char[] codeString = new char[height];
		
		codes = new String[locations.size()];
		
		for(int i = 0; i < locations.size(); i++){
			int index = height;
			BinaryTree<Float> node = locations.get(i);
			BinaryTree<Float> parent = node.parent;
			
			while(parent != null){
				index--;
				if(parent.left == node){
					codeString[index] = '0';
				}else{
					codeString[index] = '1';
				}
				node = parent;
				parent = node.parent;
			}
			
			codes[i] = new String(codeString, index, height - index);
		}
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
	
	/*
	 * buildAll builds the entirety of the binary tree by finding the smallest the probability 
	 * between leaves and trees and then combining them until a single binary tree is formed
	 */
	
	public void buildAll() throws Exception{
		
		while(!leaves.isEmpty()){
			
			BinaryTree<Float> left = selectMin();
			BinaryTree<Float> right = null;
			
			if(leaves.isEmpty()){
				right = trees.remove();
			}else{
				right = selectMin();
			}
			
			buildTree(left, right);
		}
		
		while(!trees.isEmpty()){
			if(trees.size() == 1){
				return;
			}
			
			BinaryTree<Float> left = trees.remove();
			BinaryTree<Float> right = trees.remove();
			
			buildTree(left, right);
		}
		
	}
	
	/*
	 * treeHeight returns the height of a tree through recursion 
	 */
	
	public int treeHeight(BinaryTree<Float> tree){
		if(tree == null){
			return -1;
		}
		
		return Math.max(treeHeight(tree.left), treeHeight(tree.right)) + 1;
	}
	
	/*
	 * getProb returns the probability attached to the leaf node that it
	 * was called on
	 */
	
	public float getProb(BinaryTree<Float> tree){
		if(tree.left == null && tree.right == null){
			float index = tree.getData();
			return probs[(int)index];
		}
		
		return tree.getData();
	}
}
