package moon.compile.util.analyst.constant;

import java.util.Random;

/**
 * Created by Administrator on 2016/5/9.
 */
public interface BaseDataConstant {
	int High = 1;
	int Low = -1;
	int Equal = 0;
	int Undefined = -2;
	char CODE = 'X';
	char Data = 'Z';
	char VAR = '@';

	char ARRAY = 0x2e;
	char FUNCTION = 0x30;

	char Auxiliary = 0x1000;//Defined operators may have a conflict between。
	char Add = Auxiliary + 1;
	char Subtract = Add + 1;
	char Multiply = Subtract + 1;
	char Divide = Multiply + 1;
	char Power = Divide + 1;
	char Percent = Power + 1;
	char SquareRoot = Percent + 1;
	char Absolute = SquareRoot + 1;
	char LeftParenthesis = Absolute + 1;
	char RightParenthesis = LeftParenthesis + 1;
	char Negative = RightParenthesis + 1;
	char Factorial = Negative + 1;
	char AnyThPowerRoot = Factorial + 1;
	char Modulo = AnyThPowerRoot + 1;
	char ArcSin = Modulo + 1;
	char ArcCos = ArcSin + 1;
	char ArcTan = ArcCos + 1;
	char Sin = ArcTan + 1;
	char Cos = Sin + 1;
	char Tan = Cos + 1;
	char Ln = Tan + 1;
	char Log = Ln + 1;
	char ToDegree = Log + 1;
	char ToRadian = ToDegree + 1;
	char Random = ToRadian + 1;
	char Rand = Random + 1;
	char Bigger = Rand + 1;
	char Smaller = Bigger + 1;
	char Same = Smaller + 1;
	char Or = Same + 1;
	char And = Or + 1;
	char Not = And + 1;
	char Assign = Not + 1;
	char Comma = Assign + 1;
	char Increment = Comma + 1;
	char Decrement = Increment + 1;
	char Int = Decrement + 1;

	char BiggerOrSame = Int + 1;
	char LowerOrSame = BiggerOrSame + 1;

	char AddAssign = LowerOrSame + 1;
	char SubAssign = AddAssign + 1;
	char MulAssign = SubAssign + 1;
	char DivAssign = MulAssign + 1;
	char ModAssign = DivAssign + 1;
	char SizeOf = ModAssign + 1;
	char New = SizeOf + 1;
	char NotEqual = New + 1;
	char LeftBracket = NotEqual + 1;
	char RightBracket = LeftBracket + 1;
	char InvokeFunction = RightBracket + 1;
	char Get = InvokeFunction + 1;

	char[] AllOperators = new char[]{Auxiliary, Add, Subtract, Multiply,
			Divide, Power, Percent, SquareRoot, Absolute, LeftParenthesis,
			RightParenthesis, Negative, Factorial, AnyThPowerRoot, Modulo,
			ArcSin, ArcCos, ArcTan, Sin, Cos, Tan, Ln, Log, ToDegree, ToRadian,
			Random, Rand, Bigger, Smaller, Same, Or, And, Not, Assign, Comma,
			Increment, Decrement, Int, BiggerOrSame, LowerOrSame, AddAssign,
			SubAssign, MulAssign, DivAssign, ModAssign, NotEqual, SizeOf, New, ARRAY,
			FUNCTION, LeftBracket, RightBracket, InvokeFunction, Get,};
	// char[] LogicalOperators = new char[] { Bigger, Smaller, Same, Or, And,
	// Not, };
	char[] BinaryOperators = new char[]{Add, Subtract, Multiply, Divide,
			Power, AnyThPowerRoot, Modulo, Bigger, Smaller, Same, Or, And,
			Assign, Comma, BiggerOrSame, LowerOrSame, AddAssign, SubAssign,
			MulAssign, DivAssign, ModAssign, NotEqual,};
	char[] UnaryOperators = new char[]{Percent, Factorial, SquareRoot,
			Absolute, Negative, Sin, Cos, Tan, ArcSin, ArcCos, ArcTan, Ln, Log,
			ToDegree, ToRadian, Random, Not, Increment, Decrement, Int, SizeOf,
			New, ARRAY, FUNCTION, InvokeFunction,Get,};
	char[] LeftAssociativityUnaryOperators = new char[]{Percent, Factorial,
			RightParenthesis, RightBracket,};
	char[] RightAssociativityUnaryOperators = new char[]{Negative,
			SquareRoot, Absolute, LeftParenthesis, ArcSin, ArcCos, ArcTan, Sin,
			Cos, Tan, Ln, Log, ToDegree, ToRadian, Random, Not, Increment,
			Decrement, Int, SizeOf, New, ARRAY, FUNCTION, LeftBracket, InvokeFunction, Get,};
	char[] VarOperator = new char[]{AddAssign, SubAssign, MulAssign,
			DivAssign, ModAssign, Assign, Increment, Decrement,};
	java.util.Random RANDOM = new Random();

	String operators = "+-*/^%~!()[]&:|><=, ";
	String[][] constantName = new String[][]{{/* "E", */"e",},
			{/* "PI", */"pi",},};

	double[] constantValue = new double[]{Math.E, Math.PI};
}
