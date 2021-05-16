import java.awt.Graphics;
import java.util.*;

public class Node {
	
	private boolean map[][] = new boolean[40][40];
	private Node parentNode;
	public int f, g, h, x, y;
	
	public Node(int x, int y, Node n, int targx, int targy) {
		parentNode = n;
		this.x = x;
		this.y = y;
		
		if(parentNode == null)
		{
			g = 1;
		}
		else
		{
			g = parentNode.g + 1;
		}
		h = Math.abs(x - targx) + Math.abs(y - targy);
		f = g+h;
		
	}
	
	public boolean equals(Node n)
	{
		if(n.x == this.x && n.y == this.y)
		{
			return true;
		}
		return false;
	}
	
	public Node getParentNode()
	{
		return parentNode;
	}
	
	public void paint(Graphics g) {
		
	}
	
	
}
