/*
  gauss.java
  Maths Assignment part 2
  Created by pirabarlen cheenaramen on 10/14/08.

  BUG: at times rearranging doesn't seem to work, though its arbitrary
       Retrying the option again makes it works. bump!

  Tried & tested on OSX leopard & FreeBSD 7 

Copyright (c)2008,  Pirabarlen Cheenaramen
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
import java.util.*;
import java.io.*;
import java.lang.Math;

public class gauss {

	/*------------------------------------------------------------*/
	//Gauss sum : helper function
	public static float sumRow(float[][] arrayA, int row, int col, int size)
	{
		float sum=0;
		for(int j=0; j<size; j++)
		{
			if(j!=col)
				sum+=Math.abs(arrayA[row][j]);
		}
		
		
		return sum;
	}
	

	
	/*------------------------------------------------------------*/
	//Gauss solvable
	public static Matrix verify(Matrix matrixA)
	{
		int size=matrixA.getSize();
		float[][] arrayA=matrixA.getMatrix();
		int[] arranged=new int[size];
		
		for(int a=0; a<size; a++)
		{
			arranged[a]=-1; //initializing
		}
		
		for(int i=0; i<size; i++)
		{			
			for(int j=0; j<size; j++)
			{
				//System.out.println(arrayA[i][j]+">"+sumRow(arrayA, i, j, size));
				if(Math.abs(arrayA[i][j])>sumRow(arrayA, i, j, size))
				{
					arranged[j]=i;
				}
			}
		}
		
		/*for(int u=0; u<size; u++)
			System.out.println("arranged:"+arranged[u]);*/
		for(int k=0; k<size; k++)
		{
			for(int b=0; b<size; b++)
			{
				if(b!=k)
				{
					if(arranged[k]==arranged[b])
					{matrixA.setSolvable(false);}
				
				}
			}
			if(arranged[k]==-1)
				matrixA.setSolvable(false); 
		}
		
		matrixA.setArranged(arranged);
		return matrixA;
	}
	
	/*------------------------------------------------------------*/
	//Gauss rearrange: helper function
	public static Matrix rearrange(Matrix matrixA)
	{
		int size=matrixA.getSize();
		float[][] arrayA=matrixA.getMatrix();
		int[] arranged=matrixA.getArranged();
		float[][] newarray=new float[size][size+1];
		for(int i=0; i<size; i++)
		{
		
			for(int j=0; j<=size; j++)
			{
				newarray[i][j]=arrayA[arranged[i]][j];
				//System.out.println("newarray["+i+"]["+j+"]=arrayA["+arranged[i]+"]["+j+"]");
			}
			System.out.println("\n");
		}
		
		matrixA.replaceMatrix(newarray);
		return matrixA;
	}
	
	/*------------------------------------------------------------*/
	//Gauss sumRest: helper function
	public static float sumRest(float[][] arrayA, int i, float[] prev, int size)
	{
		//System.out.println("PREV:");
		
		/*for(int z=0; z<size; z++)
			System.out.println(prev[z]);
		*/				   
		float sum=0;
		for(int j=0; j<size; j++)
		{
			if(j!=i)
				sum+=arrayA[i][j]*prev[j];					
		}
		return sum;
	}
	
	/*------------------------------------------------------------*/
	//round : helper function
	public static double round(double val, int places)
	{
		long factor = (long)Math.pow(10,places);
		val = val * factor;
		long tmp = Math.round(val);
		return (double)tmp / factor;

	}
	/*-------------------------------------------------------------*/
	//float rounding off
	public static float round(float val, int places) {
		return (float)round((double)val, places);
    }
	
	/*------------------------------------------------------------*/
	//Gauss jacobi
	public static float[] gaussJacobi(Matrix matrixA)
	{
		int size=matrixA.getSize();
		float[][] arrayA=matrixA.getMatrix();
		char[] symbol= matrixA.getSymbol();
		float[] prev=new float[size];
		float answer[]=new float[size+1]; //at the last position we'll save whether calculation succeeded or not
		boolean stop=false;
		int count=1;
		
		symbol[0]='x';
		symbol[1]='y';
		symbol[2]='z';
		symbol[3]='u';
		
		for(int a=0; a<size; a++)
			prev[a]=0;
		
		while((!stop) && count<=15)
		{
			System.out.print("\niteration "+count+" ::> ");
			for(int i=0; i<size; i++)
			{
				answer[i]=(arrayA[i][size]-sumRest(arrayA, i, prev, size))/arrayA[i][i];
				//System.out.println(answer[i]);
				answer[i]=round(answer[i],3);
				System.out.print(symbol[i]+" = "+answer[i]+"  \t");
				
			}
			
			stop=true;
			
			for(int j=0; j<size; j++)
			{
				if(answer[j]!=prev[j])
				{
					stop=false;
				}
				prev[j]=answer[j];
			}
			count++;
		}
		if (stop)
			answer[size]=count-1;
		return answer;
	}
	
	/*------------------------------------------------------------*/
	//Gauss seidel
	public static float[] gaussSeidel(Matrix matrixA)
	{
		int size=matrixA.getSize();
		float[][] arrayA=matrixA.getMatrix();
		char[] symbol= matrixA.getSymbol();
		float[] prev=new float[size];
		float answer[]=new float[size+1]; //at the last position we'll save whether calculation succeeded or not
		boolean stop=false;
		int count=1;
		
		symbol[0]='x';
		symbol[1]='y';
		symbol[2]='z';
		symbol[3]='u';
		
		for(int a=0; a<size; a++)
		{
			prev[a]=0;
			answer[a]=0;
		}
		answer[size]=-1; //assume we got no result until proven otherwise
		
		while((!stop) && count<=15)
		{
			System.out.print("\niteration "+count+" ::> ");
			for(int i=0; i<size; i++)
			{
				answer[i]=(arrayA[i][size]-sumRest(arrayA, i, answer, size))/arrayA[i][i];
				//System.out.println(answer[i]);
				answer[i]=round(answer[i],3);
				System.out.print(symbol[i]+" = "+answer[i]+"  \t");
				
			}
			
			stop=true;
			
			for(int j=0; j<size; j++)
			{
				if(answer[j]!=prev[j])
				{
					stop=false;
				}
				prev[j]=answer[j];
			}
			count++;
		}
		if (stop)
			answer[size]=count-1;
			
		return answer;
	}
	
	
	/*-------------------------------------------------------------
	 | End of interesting stuffs, begins boring stuffs             |
	 |                                                             |
	 *-------------------------------------------------------------/
	 /*------------------------------------------------------------*/
	//Helper function to clear screen
	public static void clearScreen()
	{
		
		String clearScreenCommand = null;
		if( System.getProperty( "os.name" ).startsWith( "Window" ) )
		{
			clearScreenCommand = "cls";
			try{
				Runtime.getRuntime().exec( clearScreenCommand );
			}catch(Exception e)
			{
				System.out.print("Error cleaning screen");
			}
			
		}
		else
			System.out.println(((char) 27)+"[2J");
		
	}
	
	/*------------------------------------------------------------*/
	//The Menu : boring stuff
	public static int menu()
	{
		BufferedReader stdin =  new BufferedReader( new InputStreamReader(System.in) );
		String inputs;
		boolean chosen=false;
		int choice=0;
		
		while(!chosen)
		{
			System.out.println("\n");
			System.out.println("\t1) Enter number of unknowns and Coefficient details");
			System.out.println("\t2) Verify if system can be solved using iteration");
			System.out.println("\t3) Calculate result using Gauss-Seidel");
			System.out.println("\t4) Calculate result using Gauss-Jacobi");
			System.out.println("\t5) Both methods");
			System.out.println("\t6) Exit\n");
			System.out.print("\n\t ::::::Enter your choice:::::>");
			
			try
			{
				inputs = stdin.readLine();
				choice=Integer.parseInt(inputs);
			}catch(Exception e){
				System.out.print("\nI/O exception occured");
			}
			
			if((choice==1)||(choice==2)||(choice==3)||(choice==4)||(choice==5)||(choice==6))
			{
				chosen=true;
			}
			else
			{
				clearScreen();
				System.out.println("\t<Your last input was invalid>\n");
			}
		}
		
		return choice;
	}
	
	/*------------------------------------------------------------*/
	//data entry stuffs
	public static Matrix dataEntry()
	{
	
		BufferedReader stdin =  new BufferedReader( new InputStreamReader(System.in) );
		String inputs;
		Matrix matrixA;
		int size=0;
		
		clearScreen();
		System.out.print("\n\tPlease enter the number of unknowns [x,y,z makes 3 unknowns]:");
		try
		{
			inputs = stdin.readLine();
			size=Integer.parseInt(inputs);
		}catch(Exception e){
			System.out.print("\nI/O exception occured");
		}
		
		if(size>2 && size<=4)
			matrixA=new Matrix(size);
		else
		{
			System.out.println("While this program can work with any sized input, not characters have been defined other than x,y,z and u to represent unknowns, so assuming 3.");
			matrixA=new Matrix(3); //assuming 3
		}
		
		matrixA.setMatrix();
		System.out.println("\n");
		return matrixA;
		
	}
	
	/*------------------------------------------------------------*/
	//the abyss
	public static void main(String args[])
	{
		Matrix matrixA;
		int choice=0;
		choice=menu();
		char[] symbol=new char[4]; //for the sake of initialization
		float[] answer=new float[4]; //for the sake of initialization
		float[] answer2=new float[4]; //pfft again for the sake of initilization
		 
		matrixA=new Matrix(3);//for the sake of initialization
		
		while(choice!=6)
		{
			
			if(choice==1)
			{
				clearScreen();
				matrixA=dataEntry();
				choice=menu();
			}
			else if(choice==2)
			{
				clearScreen();
				matrixA=verify(matrixA);
				clearScreen();
				if(matrixA.getSolvable())
				{
					System.out.println("\n\tSystem is solvable using iteration");
					matrixA=rearrange(matrixA);
				}
				else
				{
					System.out.println("\n\tSystem is not solvable using iteration");
				}
				try{Thread.sleep(1000);} catch(Exception E){System.out.println("Oops error happened while trying to sleep");}
				
				choice=menu();
			}
			else if(choice==3)
			{
				
				if(matrixA.getSolvable())
				{
					clearScreen();
					answer=gaussSeidel(matrixA);
					if(answer[matrixA.getSize()]>0)
					{
						matrixA=rearrange(matrixA);
						System.out.println("\n\tWith Gauss-seidel we reached an answer after "+answer[matrixA.getSize()]+" iterations");
						for(int i=0; i<matrixA.getSize(); i++)
						{
							System.out.print("\t"+symbol[i]+" = "+answer[i]);
						}
						System.out.println("\n");
					}
					
				}
				System.out.println("Displaying results for 8 seconds");
				try{Thread.sleep(8000);} catch(Exception E){System.out.println("Oops error happened while trying to sleep");}
				choice=menu();
			}
			else if(choice==4)
			{
				
				if(matrixA.getSolvable())
				{
					clearScreen();
					matrixA=rearrange(matrixA);
					answer=gaussJacobi(matrixA);
					if(answer[matrixA.getSize()]>0)
					{
						
						System.out.println("\n\tWith Gauss-Jacobi we reached an answer after "+answer[matrixA.getSize()]+" iterations");
						for(int i=0; i<matrixA.getSize(); i++)
						{
							System.out.print("\t"+symbol[i]+" = "+answer[i]);
						}
						System.out.println("\n");
					}
					
				}
				System.out.println("Displaying results for 8 seconds");
				try{Thread.sleep(8000);} catch(Exception E){System.out.println("Oops error happened while trying to sleep");}

				choice=menu();
			}
			else if(choice==5)
			{
				
				if(matrixA.getSolvable())
				{
					clearScreen();
					matrixA=rearrange(matrixA);
					matrixA=rearrange(matrixA);
					System.out.println("\nGauss-Seidel");
					answer=gaussSeidel(matrixA);
					System.out.println("\nGauss-Jacobi");
					answer2=gaussJacobi(matrixA);
					
					System.out.println("\n--------SUMMARY [scroll up to see details]-----------\n");
					if(answer[matrixA.getSize()]>0)
					{
						System.out.println("\n\tWith Gauss-Seidel we reached an answer after "+answer[matrixA.getSize()]+" iterations");
						for(int i=0; i<matrixA.getSize(); i++)
						{
							System.out.print("\t"+symbol[i]+" = "+answer[i]);
						}
						System.out.println("\n");
					}
					
					
					
					if(answer2[matrixA.getSize()]>0)
					{
						System.out.println("\n\tWith Gauss-Jacobi we reached an answer after "+answer2[matrixA.getSize()]+" iterations");
						for(int i=0; i<matrixA.getSize(); i++)
						{
							System.out.print("\t"+symbol[i]+" = "+answer2[i]);
						}
						System.out.println("\n");
					}
					
					System.out.println("Displaying results for 8 seconds");
					try{Thread.sleep(8000);} catch(Exception E){System.out.println("Oops error happened while trying to sleep");}
					matrixA=rearrange(matrixA);

				}
				
				choice=menu();
			}
			
		}
		
		
	
	}
}
