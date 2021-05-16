
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.*;

public class BadGuy {
	
	Image myImage;
	int x=0,y=0;
	boolean hasPath=false;
	
	Stack finalPath = new Stack(); //stack for the final path
	LinkedList openList = new LinkedList(); //linked list for all open nodes
	LinkedList closedList = new LinkedList(); //linked list for all closed nodes

	int index; //index to track position of node with lowest f value

	public BadGuy( Image i ) {
		myImage=i;
		x = 30;
		y = 10;
		
	}
	
	public void reCalcPath(boolean map[][],int targx, int targy) {
		finalPath.clear(); //clear the stack and both lists so the bad guy can recalculate the bets path each move
		openList.clear();
		closedList.clear();
		
		// TO DO: calculate A* path to targx,targy, taking account of walls defined in map[][]
		Node startNode = new Node(x, y, null, targx, targy); //start node is the node which the bad guy is currently on
		openList.add(startNode); //add it to the open list
		
		Node currentNode; //create a new node reference called currentNode for later use
		
		while(1 == 1) //infinite loop which breaks once there is no path or a path is found
		{	
			int lowestF = 1000; //very high integer which the lowest F of a node will be lower than
			Node nextNode; //reference variable to a node in the open list
			
			Iterator i = openList.iterator(); //go through the open list
			int counter = 0; //set a counter which increments by 1
			while (i.hasNext()) 
			{
				nextNode = (Node)i.next(); //set next node to the next node in the open list
				if(nextNode.f < lowestF) //if the next node's f is lower than lowestF, set lowest F to the f of that node and set index the the current value of counter
				{
					lowestF = nextNode.f;
					index = counter;
				}
				counter++;
			}
			
			
			
			currentNode = (Node)openList.get(index); //set currentNode to the node in the open list with the lowest f value
			closedList.add(currentNode); 
			openList.remove(index);//remove this node from the open list and add it to the closed list
			
			Iterator pathCounter = closedList.iterator(); //go through the closed list and find the one with the lowest fCost
			int count = 0;
			int position = 0;
			int fCost = 1000;
			Node finalNode;
			while (pathCounter.hasNext()) 
			{
				finalNode = (Node)pathCounter.next(); //when the while loop is done, finalNode will be equal to the node with the lowest f
				if(finalNode.f < fCost)
				{
					position = count;
				}
				count++;
			}
			
			
			Node targetNode = new Node(targx, targy, null, targx, targy); //the target node which we are trying to reach
			Node parentNode; //new reference node variable
			if(currentNode.equals(targetNode)) //check if the x and y coordinates are equal of currentNode to the target node
			{
				Node sourceNode = new Node(x, y, null, targx, targy); //
				finalPath.add(currentNode.getParentNode());
				
				while(1 == 1) //go back through the parent nodes until you reach the initial node
				{
					
					parentNode = currentNode.getParentNode();
					
					if(parentNode == null)
					{
						break;
					}
					
					finalPath.add(parentNode);
					
				
					currentNode = parentNode;
					
					if(currentNode.equals(sourceNode)) break;
				}
				
				hasPath = true;
				break;
			}
			
			
			Node checkNode;
			for(int k = -1; k <= 1; k++) //check the neigbours of the currentNode
			{
				for(int j = -1; j <= 1; j++)
				{
					int neighbourX = currentNode.x + k;
					int neighbourY = currentNode.y + j;
					
					if(neighbourX > 39 || neighbourX < 0)
					{
						continue;
					}
					if(neighbourY > 39 || neighbourY < 0)
					{
						continue;
					}
					if(map[neighbourX][neighbourY])
					{
						continue;
					}
					
					
					checkNode = new Node(neighbourX, neighbourY, currentNode, targx, targy);//checkNode is equal to the currentNeighbour

					
					Node closedNode;
					boolean isInClosedList = false; //boolean to check if a node is in the closed list
					Iterator closedCounter = closedList.iterator(); //go through the closed list
					
					while (closedCounter.hasNext()) 
					{
						closedNode = (Node)closedCounter.next();
						if(checkNode.equals(closedNode)) //if the current neighbour is equal to the node in the closed list set isInClosedList to true
						{
							isInClosedList = true;
							break;
						}
					}
					if(isInClosedList) continue; //if isInClosedList is true, go onto next step of the loop
				
					
						Node openNode;
						boolean isInOpenList = false;
						Iterator openCounter = openList.iterator(); // go through the open list
						
						while (openCounter.hasNext()) 
						{
							openNode = (Node)openCounter.next();
							if(checkNode.equals(openNode)) //if the current neighbour is not in the open list add it to the open list
							{
								isInOpenList = true;
							}
						}
						if(!isInOpenList) openList.add(checkNode);

				}
			}
				
		}
		
		
		
		
		
		
	}
	
	public void move(boolean map[][],int targx, int targy) {
		reCalcPath(map, targx, targy); //call reCalcPath every time he moves
		if (hasPath) { //if has path is true pop the nodes from the finalPath stack and make the x and y values of those nodes, the x and y values of the badguy
			
			// TO DO: follow A* path, if we have one defined
				//System.out.println("Size: " + finalPath.size());
				Node node = (Node)finalPath.get(finalPath.size()-2);
				x = node.x;
				y = node.y;
			
		}
		else{
			// no path known, so just do a dumb 'run towards' behaviour
			int newx=x, newy=y;
			if (targx<x)
				newx--;
			else if (targx>x)
				newx++;
			if (targy<y)
				newy--;
			else if (targy>y)
				newy++;
			if (!map[newx][newy]) {
				x=newx;
				y=newy;
			}
		}
	}
	
	public void paint(Graphics g) {
		/* if this section is uncommented then a visual representation of the algorithm will appear
		Iterator i = openList.iterator();
		Node currentNode;
		
		while (i.hasNext()) 
		{
			
			
			g.setColor(Color.green);
			currentNode = (Node)i.next();
			g.fillRect((currentNode.x * 20), (currentNode.y * 20), 20, 20);
			

		}
		
		Iterator j = closedList.iterator();
		g.setColor(Color.red);
		while (j.hasNext()) 
		{
			currentNode = (Node)j.next();
			g.fillRect((currentNode.x * 20), (currentNode.y * 20), 20, 20);
		}
		
		Iterator k = finalPath.iterator();
		g.setColor(Color.blue);
		while (k.hasNext()) 
		{
			currentNode = (Node)k.next();
			g.fillRect((currentNode.x * 20), (currentNode.y * 20), 20, 20);
		}
		*/
		g.drawImage(myImage, x*20, y*20, null);
	}
	
}

