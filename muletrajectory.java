import java.util.*;
import java.util.Arrays;

class DijkstrasAlgorithm {

	private static final int NO_PARENT = -1;
	static int[] parents = new int[15];
	static int[] tower =new int[]{0,3,5};
	static int[] tempclusters=new int[]{0,0,3,3,5,5,5,0,0,5,0,0,0,0,0};
	static int[] mules=new int[3];
	static int[][] counts=new int[3][15];
	static int[][] counts1=new int[3][3];
	static int[][] counts2=new int[3][15];
	static int p=0;
	static int[] visited=new int[15];


	public static void choosepath(int[][] adjacencyMatrix)
	{
	    for(int i=0;i<3;i++)
	    {
	        Arrays.sort(counts[i]);
	    }
	
	    for(int i=0;i<3;i++)
	    {
	       p=0;
	       for(int j=15-mules[i];j<15;j++)
	       {
	           counts1[i][p]=counts[i][j];
	           p++;
	       }
	    }
	    int pos=0;
	    for(int i=0;i<3;i++)
	    {
	        for(int j=0;j<3;j++)
	        {
	            pos=counts1[i][j];  
	            if(pos!=0)
	            for(int m=0;m<15;m++)
	            {
	               if(counts2[i][m]==pos)
	               {
	                   counts2[i][m]=0;
	                   counts1[i][j]=m;
	                   break;
	               }
	            }
	        }
	    }
	}
	
	private static void dijkstra(int[][] adjacencyMatrix,
										int startVertex)
	{
		int nVertices = adjacencyMatrix[0].length;
		int[] shortestDistances = new int[nVertices];
		boolean[] added = new boolean[nVertices];
		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
		{
			shortestDistances[vertexIndex] = Integer.MAX_VALUE;
			added[vertexIndex] = false;
		}
		
		shortestDistances[startVertex] = 0;
		
		parents[startVertex] = NO_PARENT;

		for (int i = 1; i < nVertices; i++)
		{
			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
			{
				if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) 
				{
					nearestVertex = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];
				}
			}

			added[nearestVertex] = true;

			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
			{
				int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
				
				if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) 
				{
					parents[vertexIndex] = nearestVertex;
					shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
				}
			}
		}

		printSolution(startVertex, shortestDistances, parents);
	}


	private static void printSolution(int startVertex,int[] distances,int[] parents)
	{
		int nVertices = distances.length;
		System.out.println();
		System.out.print("Vertex\t Distance\tPath");
		
		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
		{
			if (vertexIndex != startVertex && startVertex==tempclusters[vertexIndex]) 
			{
				System.out.print("\n" + startVertex + " -> ");
				System.out.print(vertexIndex + " \t\t ");
				System.out.print(distances[vertexIndex] + "\t\t");
				counts[p][vertexIndex]=printPath(vertexIndex, parents,startVertex);
				counts2[p][vertexIndex]=counts[p][vertexIndex];
			}
		}
		
		System.out.println();
	}


	private static int printPath(int currentVertex,	int[] parents,int startVertex)
	{
	   int count=0;
		while(currentVertex!=-1)
		{
		    count++;
		    System.out.print(currentVertex+" ");
		    //visited[currentVertex]=1;
		    currentVertex=parents[currentVertex];
		}
		return count;
	}
	
	
	
	private static void dijkstra1(int[][] adjacencyMatrix,int startVertex)
	{
		int nVertices = adjacencyMatrix[0].length;

		int[] shortestDistances = new int[nVertices];
        	Arrays.fill(parents,0);
		boolean[] added = new boolean[nVertices];

		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
		{
			shortestDistances[vertexIndex] = Integer.MAX_VALUE;
			added[vertexIndex] = false;
		}
		
		
		shortestDistances[startVertex] = 0;


		parents[startVertex] = NO_PARENT;

		for (int i = 1; i < nVertices; i++)
		{

			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
			{
				if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) 
				{
					nearestVertex = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];
				}
			}


			added[nearestVertex] = true;


			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
			{
				int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
				
				if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) 
				{
					parents[vertexIndex] = nearestVertex;
					shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
				}
			}
		}

		printSolution1(startVertex, shortestDistances, parents);
	}


	private static void printSolution1(int startVertex,int[] distances,int[] parents)
	{
		int nVertices = distances.length;
			System.out.println();
		System.out.print("Vertex\t Distance\tPath");
		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
		{
			if (vertexIndex != startVertex && startVertex==tempclusters[vertexIndex]) 
			{
			    for(int i=0;i<3;i++)
			    {
			        for(int j=0;j<3;j++)
			        {
			            int pos=counts1[i][j];
			            if(pos==vertexIndex)
			            {
			                System.out.print("\n" + startVertex + " -> ");
				            System.out.print(vertexIndex + " \t\t ");
			                System.out.print(distances[vertexIndex] + "\t\t");
			                printPath1(vertexIndex, parents,startVertex);
			            }
			        }
			    }
			}
		}
		
		System.out.println();
	}
    

	private static void printPath1(int currentVertex,int[] parents,int startVertex)
	{
	    //System.out.println();
		while(currentVertex!=-1)
		{
		    System.out.print(currentVertex+" ");
		    visited[currentVertex]=1;
		    currentVertex=parents[currentVertex];
		}
	}

    



    public static void markvisit(int[][] adjacencyMatrix)
    {
         for(int i=0;i<3;i++)
    	 {
	        //System.out.print("for tower at "+tower[i]);
	    	dijkstra1(adjacencyMatrix, tower[i]);
	    	//p++;
	     }
    }
    
    
    
     public static void check(int[][] adjacencyMatrix)
     {
         int index=0,index1=0;
        for(int i=0;i<15;i++)
        {
            if(visited[i]==0)
            {
		for(int j=0;j<3;j++)
		{   
			int m=0;
		        int parents1[]=new int[15];
		        if(tempclusters[i]==tower[j])
		    	{
			    	dijkstra1(adjacencyMatrix,tower[j]);
			    	int min=99999;
			    	index=j;
			    	
			    	for(m=0;m<15;m++)
			    	{
			    	    if(adjacencyMatrix[i][m]!=0 && adjacencyMatrix[i][m]<=min && visited[m]==1 && tempclusters[m]==tempclusters[i])
			    	    {
			    	        min=adjacencyMatrix[i][m];
			    	       parents1[m]=i;
			    	       index1=m;
			    	       break;
			    	    }
			    	}
			 }
			 else
			 {
			        continue;
			 }
		        //System.out.println(i+" "+j+" "+m);
		        int currentVertex=0;
		        for(int q=0;q<3;q++)
	         	{
			         if(parents[counts1[index][q]]==index1 || parents[counts1[index][q]]==tower[index])
			         {
			              currentVertex=counts1[index][q];
			              break;
			         }
		        }
				    //int currentVertex=counts1[j][1];
		        int flag=0;
		        System.out.println();
		        System.out.println("change in path of mule denoted by last left node");
		        
			while(currentVertex!=-1)
	  	        {
		                System.out.print(currentVertex+" ");
		                
		                if(currentVertex==index1 && flag==0)
		                {
		                    currentVertex=parents1[currentVertex];
		                    visited[currentVertex]=1;
		                    flag=1;
		                }
		                else
		                {
		                    currentVertex=parents[currentVertex];
		                    //visited[currentVertex]=1;
		                }
		        }
		        System.out.println();
		        break;
		}
	    }
        }
     }
    

     public static void main(String[] args)
     {
         Scanner scan=new Scanner(System.in);
	int[][] adjacencyMatrix = {{0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0},
                                   {5, 0, 4, 0, 5, 0, 0, 0, 0, 4, 3, 0, 0, 0, 0},
                                   {0, 4, 0, 4, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                   {0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                   {0, 5, 3, 0, 0, 2, 0, 0, 0, 4, 0, 0, 0, 0, 0},
                                   {0, 0, 0, 0, 2, 0, 1, 0, 0, 7, 0, 0, 0, 0, 0}, 
                                   {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                   {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                                   {0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 3, 0, 0, 2},
                                   {0, 4, 0, 0, 4, 7, 0, 0, 2, 0, 0, 3, 0, 0, 0},
                                   {3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0},
                                   {0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 2},
                                   {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0},
                                   {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 3},
                                   {0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 3, 0} };
	
	System.out.println("enter mules for each cluster based on number of nodes in cluster");
	System.out.println("for two nodes assume one mule");
	
	for(int i=0;i<3;i++)
	{
	    mules[i]=scan.nextInt();
	}
	
	for(int i=0;i<3;i++)
    	{
    	    System.out.println();
	        System.out.print("for tower at "+tower[i]);
	    	dijkstra(adjacencyMatrix, tower[i]);
	    	p++;
	}
	
	choosepath(adjacencyMatrix);
	    
	System.out.println();
	    
	Arrays.fill(visited, 0);
	System.out.println("Paths as per number of mules allocated and number of nodes covered for cluster "+tower[0]+" "+tower[1]+" "+tower[2]);
	markvisit(adjacencyMatrix);
	
	System.out.println();
	System.out.println("remaining nodes denoted by 0");    
	
	for(int j=0;j<15;j++)
	{
		System.out.print( visited[j]+" ");
	}
	System.out.println();
	
	check(adjacencyMatrix);
	
	System.out.println();
	System.out.println("All nodes are covered");
	for(int j=0;j<15;j++)
	{
	        System.out.print( visited[j]+" ");
	}
      }
}
