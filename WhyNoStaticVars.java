class A
 
	  {
 
			public static String staticVariableCopiedFromB = B.staticVariable;
 
			public static String staticVariable = "Thing is a string defined in A";
 
	  }
 
 
 
	  class B
 
	  {
 
			public static String staticVariableCopiedFromA = A.staticVariable;
 
			public static String staticVariable = "Thing is a string defined in B";
 
	  }
 
 
 
	  public class WhyNoStaticVars
 
	  {
 
			public static void main(String[] args)
 
			{
 
				  System.out.println(A.staticVariableCopiedFromB);                  
 
				  System.out.println(B.staticVariableCopiedFromA);
 
			}
 
	  }
