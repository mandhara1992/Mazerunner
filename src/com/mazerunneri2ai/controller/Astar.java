package com.mazerunneri2ai.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

class Node {
	int x, y, Gfunc; double Func;
	Node(int x, int y, double Hfunc){
		this.x = x;
		this.y = y;
		this.Gfunc = 0;
		this.Func = this.Gfunc + Hfunc;
	}

	void update_Gfunc(int Gfunc){
		this.Gfunc = Gfunc;
	}
}

@Controller
public class Astar extends MazeGenerator{

	private int dim; //Dimension input
	private float prob; //Probability of Block
	private int maze[][]; //TO generate a Maze
	private int visited_maze[][];	//Maze updating
	private int manhattanDist[][];	//Manhattan Heuristic
	private int euclideanDist[][];	//Euclidean Heuristic
	private int number_of_iterations = 1;
	private int maxfringe =1;

	Node goal_node = new Node(dim-1, dim-1, 0);	//Goal node storage
	Node source_node = new Node(0, 0, 0);	//Source node Storage

	private Queue<Node> queue;	//Priority Queue global declaration.
	private Map<Node, Node> visited;	//Hashmap for Bcktracking.

	private int flag = 0;	//Toggle Flag to understand when goal node is reached.

	@RequestMapping(value = "/performAstar", method=RequestMethod.POST)
	public  @ResponseBody String performAstar(@RequestParam("generated_maze") String generated_maze, @RequestParam("dim") String dimension, @RequestParam("prob") String probability){

		Random rand = new Random();
		dim = Integer.parseInt(dimension);
        System.out.println("dimension - "+dim);
        prob = Float.parseFloat(probability);		
			int k=0;
			int maze_temp[][] = new int[dim][dim];
	        String maze_cells[] = generated_maze.trim().split(",");
	        System.out.println(generated_maze);
	        System.out.println("size - "+maze_cells.length);
        	for(int i=0;i<dim;i++)
        	{
        		for (int j=0;j<dim;j++)
        		{
        			System.out.println("blah ");
        			maze_temp[i][j] = Integer.parseInt(maze_cells[k]);
        			
        			k++;
        		}
        	}
        	maze = maze_temp;
        	displayMaze(maze_temp,dim);
        	
			visited_maze = maze;
			euclideanDist = calcManhattanDistance(maze, dim);

			queue = new PriorityQueue<Node>(idComparator);
			visited = new HashMap<Node, Node>();

			long start_time = System.nanoTime();
			findPath();
			long end_time = System.nanoTime();
			float time_to_compute = 0;
			time_to_compute = (float)((end_time - start_time)/Math.pow(10,9));
			System.out.println("Time taken for finding if path exists :" + (end_time - start_time)/1000 + " microseconds.");
			//Back Tracking & Path Formation
			long start_bctrk = System.nanoTime();
			end_time = System.nanoTime();
			String path = "";
			String back_tracked_path = "";
			if(flag == 0)
				System.out.println("No Path Found");	
			
			else if(flag == 1)
			{									
				
				back_tracked_path = backTrack();
				path = path + "0,0;";
				String path_temp[] = back_tracked_path.split(";");
				for(int t=path_temp.length-1;t>=0;t--){
					if(t!=0)
							path = path + path_temp[t] + ";";
						else
							path = path + path_temp[t];
				}
			}
			
			long end_bctrk = System.nanoTime();
			float time_to_backtrack = 0;
			time_to_backtrack = (float)((end_bctrk - start_bctrk)/Math.pow(10,9));
			
			float total_time = (float)(time_to_compute + time_to_backtrack);
			
			System.out.println("Time taken for backtracking: " + (end_bctrk - start_bctrk)/1000 + " microseconds.");
			System.out.println("Number of visited nodes: "+ visited.size());
			System.out.println(path);
			String json = generated_maze+"#"+path+"#"+total_time+"#"+visited.size()+"#"+maxfringe;
			return json;
	}

	private void findPath(){
		Node current_node = source_node;
		queue.add(current_node);

		int i = 0, j = 0;
		if(visited_maze[i][j]!=2){
			visited_maze[i][j] = 2;
			visited.put(current_node, current_node);
		}

		while(!queue.isEmpty() && flag!=1){
			if(maxfringe < queue.size())
				maxfringe = queue.size();
			current_node = queue.poll();
			/*
			if(flag != 1){}
			*/
			i = current_node.x;
			j = current_node.y;

			//Down
			if(i+1<=dim-1){
				if(maze[i+1][j]==0){
					flag = checkGoalNode(i+1, j);//function to check goal node
					populateFringe(i+1, j, current_node);
					if (flag == 1)
						break;
				}
			}

			//Right
			if(j+1<=dim-1){
				if(maze[i][j+1]==0){
					flag = checkGoalNode(i, j+1);
					populateFringe(i, j+1, current_node);
					if (flag == 1)
						break;
				}
			}

			//UP
			if(i-1>=0){
				if(maze[i-1][j]==0){
					populateFringe(i-1, j, current_node);
					if (flag == 1)
						break;
				}
			}

			//LEFT
			if(j-1>=0){
				if(maze[i][j-1]==0){
					populateFringe(i, j-1, current_node);
					if (flag == 1)
						break;
				}
			}
		}
	}


	private static Comparator<Node> idComparator = new Comparator<Node>(){

		@Override
		public int compare(Node n1, Node n2){
			return (int) (n1.Func - n2.Func);
		}
	};

	//To Populate the priority queue based on the Anonymous class above
	private void populateFringe(int row_number, int col_number, Node current_node){
		
		if(visited_maze[row_number][col_number]!=2){
			visited_maze[row_number][col_number] = 2;
			Node new_node = new Node(row_number, col_number, euclideanDist[row_number][col_number]);			
			new_node.update_Gfunc(current_node.Gfunc + 1);
			visited.put(new_node, current_node);
			queue.add(new_node);
			if (flag == 1)
				goal_node = new_node;
		}
	}

	private int checkGoalNode(int row_number, int col_number){
		if(row_number == dim-1 && col_number == dim-1){
			System.out.println("Goal Reached.");
			return 1;
		}
		else
			return 0;
	}

	private String backTrack(){
		String path="";
		Node bctrk = goal_node;
		while(!(bctrk.x == 0 && bctrk.y == 0)){
			path = path + bctrk.x + "," + bctrk.y;
			bctrk = visited.get(bctrk);
			path = path+";";
		}
		return path;
	}
}