package com.mazerunneri2ai.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

@Controller
class BFS extends MazeGenerator{

	static int dim;
	static float prob;
	static int maze[][];
	static int visited_maze[][];
	static int count = 0;
	//static int number_of_iterations = 1;
	static int iter = 0;
	static int number_of_explored_nodes;
	static int maxfringe = 1;

	static Queue<ArrayList<Integer>> queue;
	static Map<ArrayList<Integer>,ArrayList<Integer>> visited;
	static ArrayList<Integer> current_node;
	
		
	static void populateFringe(int row_number, int col_number, ArrayList<Integer> current_node){

		ArrayList<Integer> new_node = new ArrayList<>();
		new_node.add(row_number);
		new_node.add(col_number);
		if(visited_maze[row_number][col_number]!=2){
			visited_maze[row_number][col_number] = 2;
			visited.put(new_node,current_node);
			queue.add(new_node);
		}

	} 

	static int checkGoalNode(int row_number, int col_number){


			if(row_number == dim-1 && col_number == dim-1){
				System.out.println("\nPath Found.\n");
				
				return 1;
			}
			else{
				return 0;
			}
		
		}
	
	@RequestMapping(value = "/mazeGenerate", method=RequestMethod.POST)
	public  @ResponseBody String mazeGenerate(@RequestParam("dim") String dimension, @RequestParam("prob") String probability){
        int temp;
        int dim = Integer.parseInt(dimension);
        System.out.println(dim);
        float prob = Float.parseFloat(probability);
        Random rand = new Random();
        maze = new int[dim][dim];
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {

                    if(i==0 && j==0 || i==dim-1 && j==dim-1){
                        maze[i][j] = 0;
                        continue;
                    }

                    temp = rand.nextInt(10) + 1;
                    if (temp <= prob * 10)
                        //1 being occupied
                        maze[i][j] = 1;
                    else
                        //0 being unoccupied
                        maze[i][j] = 0;
                }
            }
            StringBuilder sb = new StringBuilder("");

			for(int x=0;x<dim;x++){
				for(int y=0;y<dim;y++){

					if(x==dim-1 && y==dim-1)
						sb.append(maze[x][y]);
					else{
						sb.append(maze[x][y]);
						sb.append(",");
					}
				}
			}

			String maze_str = sb.toString();
    return maze_str;
    }
	
	@RequestMapping(value = "/performBFS", method=RequestMethod.POST)
	public  @ResponseBody String performBFS(@RequestParam("generated_maze") String generated_maze, @RequestParam("dim") String dimension, @RequestParam("prob") String probability){

		Random rand = new Random();

			iter=0;
	        dim = Integer.parseInt(dimension);
	        System.out.println(dim);
	        prob = Float.parseFloat(probability);
	        System.out.println(prob);
	        while(prob>1 || prob<0){
	            System.out.println("Invalid 'p'.");
	            System.out.print("Enter 'p' (between 0 and 1): ");
	        }

	        float total_time = 0;

	        while(iter<1){

	        	queue = new LinkedList<>();
	        	visited = new HashMap<ArrayList<Integer>,ArrayList<Integer>>();
	        	current_node = new ArrayList<Integer>();
	        	visited_maze = maze;

		        number_of_explored_nodes = 0;

		        long start_time = System.nanoTime();


		        //BFS  
				current_node.add(0);
				current_node.add(0);
				queue.add(current_node);

				int flag = 0;
				int i=0,j=0;

				while(!queue.isEmpty() && flag!=1){
					if(maxfringe < queue.size())
						maxfringe = queue.size();
					current_node = queue.poll();
					number_of_explored_nodes += 1;
					if(visited_maze[i][j]!=2){
						visited_maze[i][j] = 2;
						visited.put(current_node,current_node);
					}

					if(flag!=1){

						i = current_node.get(0);
						j = current_node.get(1);

						//Down
						if(i+1<=dim-1){
							
							if (maze[i+1][j]==0){	//If the next node is unoccupied
								
								flag = checkGoalNode(i+1,j);
								populateFringe(i+1,j,current_node);
								
							}
						}

						//Right
						if(j+1<=dim-1){
							
							if (maze[i][j+1]==0){	//If the next node is unoccupied
								
								flag = checkGoalNode(i,j+1);
								populateFringe(i,j+1,current_node);

							}
						}
						
						//Up
						if(i-1>=0){
							
							if (maze[i-1][j]==0){
								populateFringe(i-1,j,current_node);
							}
						}

						//Left
						if(j-1>=0){
							
							if (maze[i][j-1]==0){
								populateFringe(i,j-1,current_node);
							}
						}
					}
				}
				
				long end_time = System.nanoTime();

				double time_to_compute = (end_time - start_time)/Math.pow(10,9);
				
				double time_to_backtrack = 0;

				String path = "";

				ArrayList<Integer> src = new ArrayList<Integer>();

				start_time = System.nanoTime();

		        if(flag==0){
		            System.out.println("\nNo Path Found.\n");
		            src.add(current_node.get(0));
		            src.add(current_node.get(1));
		        }
		        
		        else {	//Backtrack and find path.

					src.add(dim-1);
					src.add(dim-1);	

				}

				while(!(src.get(0)==0 && src.get(1) == 0)){

						path = path + src.get(0)+","+src.get(1);
						src = visited.get(src);
						path = path + ";";

					}

					path = path + "0,0";

					end_time = System.nanoTime();

					time_to_backtrack = (end_time - start_time)/Math.pow(10,9);

					total_time = (float)(time_to_compute + time_to_backtrack);

					System.out.println("Time taken: "+total_time+"s\n");
					
					//Store path in correct order.
					String path_temp[] = path.split(";");
					path = "";
					for(int t=path_temp.length-1;t>=0;t--){
						if(t!=0)
							path = path + path_temp[t] + ";";
						else
							path = path + path_temp[t];
				}

				iter++;

				if(dim<100)
					dim = dim + 10;
				else if(100<=dim && dim<1000)
					dim = dim + 100;
				else if(1000<=dim && dim<10000)
					dim = dim + 500;				

				String json = generated_maze+"#"+path+"#"+total_time+"#"+number_of_explored_nodes+"#"+maxfringe;
				return json;
			}
	return "";
	}

	public static void main(String args[]){


	}
}