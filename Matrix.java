/*
  Matrix.java
  Maths Assignment part 2: Helper object "Matrix"
  Created by pirabarlen cheenaramen on 10/14/08.
  Copyright 2008 __ pcthegreat A_T gmail.com __.
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

public class Matrix {
	
	float[][] aMatrix;
	int matrixSize;
	char[] symbol;
	boolean solvable=true;
	int arranged[];
	
	public Matrix(int size)
	{
		aMatrix= new float[size][size+1];
		matrixSize=size;
		arranged= new int[size];
		symbol=new char[4]; //we should expand this list if we wish to cope for more unknowns
		symbol[0]='x';     //we could probably autogenerate those values from the ascii or unicode table itself
		symbol[1]='y';     //but its out of the scope of this assignment
		symbol[2]='z';
		symbol[3]='u';
				
	}
	public Matrix(int size, float[][] content)
	{
		aMatrix=content;
		matrixSize=size;
		symbol=new char[4];
		symbol[0]='x';
		symbol[1]='y';
		symbol[2]='z';
		symbol[3]='u';
	
	}

	public Matrix()
	{
		symbol=new char[4];
		symbol[0]='x';
		symbol[1]='y';
		symbol[2]='z';
		symbol[3]='u';
	}
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
		
	public void printMatrix(int rows, int cols)
	{
		boolean getOut=false;
	
		for(int i=0; i<matrixSize; i++)
		{
			System.out.print("\n");
			for(int j=0; j<matrixSize+1; j++)
			{	
				
				if(rows==i && (cols)==j)
				{getOut=true; break;}
				System.out.print(aMatrix[i][j]);
				
				if(j!=matrixSize)
				{
					System.out.print(symbol[j]);
					if(j<matrixSize-1 ) System.out.print(" +");
					if(j==matrixSize-1)
						System.out.print(" =");
				}
					
				System.out.print(" ");
			
			}
			
			if(getOut)
				break;
		}
	}
	public void setMatrix()
	{
		BufferedReader stdin =  new BufferedReader( new InputStreamReader(System.in) );
		String inputs;
		float a=0;
		this.clearScreen();
		System.out.println("\tFill in equations [note x,y,z.. and equal sign will automatically be inserted], + operator is assumed, bypass by inserting negative numbers if needed] ");
		for(int row=0; row<matrixSize; row++)
			for(int col=0; col<matrixSize+1; col++)
			{				
				
				
				try
				{
					inputs = stdin.readLine();
					a = Float.parseFloat(inputs);
					
				}catch(Exception e){
					System.out.print("\nI/O exception occured");
				}
				aMatrix[row][col]=a;
				this.clearScreen();
				System.out.println("\tFill in equations [note x,y,z.. and equal sign will automatically be inserted], + operator is assumed, bypass by inserting negative numbers if needed] ");
				if(((col+1)==matrixSize+1) && (row+1<matrixSize))
					printMatrix(row+1, 0);
				else
					printMatrix(row, col+1);
					
				
			}
	}

	public void replaceMatrix(float[][] newArray)
	{
		
		aMatrix=newArray;
	}
	public float[][] getMatrix()
	{
	
		return aMatrix;
	}
	
	public int getSize()
	{
	
		return matrixSize;
	}
	public void setSolvable(boolean a)
	{
		solvable=a;
	}
	public boolean getSolvable()
	{
		return solvable;
	}
	public void setArranged(int[] a)
	{
		arranged=a;
	}
	public int[] getArranged()
	{
		return arranged;
	}
	public char[] getSymbol()
	{
		return symbol;
	}

}
