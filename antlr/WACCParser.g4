parser grammar WACCParser;

options {
  tokenVocab = WACCLexer;
}

// EOF indicates that the program must consume to the end of the input.
program: BEGIN_STAT func* stat END_STAT EOF
       ;

func: type IDENT OPEN_PARENTHESES (param (COMMA param)*)? CLOSE_PARENTHESES IS stat END_STAT
    ;

param: type IDENT
     ;

stat: SKIP_STAT                                           #SkipStat
    | type IDENT ASSIGN_STAT assignRhs                    #AssignPrimitiveStat
    | assignLhs ASSIGN_STAT assignRhs                     #AssignPairArrayStat
    | READ_STAT assignLhs                                 #ReadStat
    | FREE_STAT expr                                      #FreeStat
    | RETURN_STAT expr                                    #ReturnStat
    | EXIT_STAT expr                                      #ExitStat
    | PRINT_STAT expr                                     #PrintStat
    | PRINTLN_STAT expr                                   #PrintlnStat
    | IF_STAT expr THEN_STAT stat ELSE_STAT stat FI_STAT  #IfStat
    | DO_STAT stat WHILE_STAT expr DONE_STAT              #DoWhileStat
    | WHILE_STAT expr DO_STAT stat DONE_STAT              #WhileStat
    | BEGIN_STAT stat END_STAT                            #ScopeBlockStat
    | stat COMPOSITION_STAT stat                          #CompositionStat
    ;

assignLhs: IDENT      #AssignLhsIdent
         | arrayElem  #AssignLhsArrayElem
         | pairElem   #AssignLhsPairElem
         ;

assignRhs: expr                                                                      #AssignRhsExpr
         | OPEN_SQUARE_BRACKET (expr (COMMA expr)*)? CLOSE_SQUARE_BRACKET            #AssignRhsArrayLiteral
         | NEWPAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES                #AssignRhsNewPair
         | pairElem                                                                  #AssignRhsPairElem
         | CALL_FUNC IDENT OPEN_PARENTHESES (expr (COMMA expr)*)? CLOSE_PARENTHESES  #AssignRhsCallFunc
         ;

pairElem: FST expr
        | SND expr
        ;

type: baseType
    | type OPEN_SQUARE_BRACKET CLOSE_SQUARE_BRACKET
    | PAIR OPEN_PARENTHESES pairElemType COMMA pairElemType CLOSE_PARENTHESES
    ;

baseType: INT
        | BOOL
        | CHAR
        | STRING
        ;

pairElemType: baseType
			| type OPEN_SQUARE_BRACKET CLOSE_SQUARE_BRACKET
			| PAIR
			;

expr: OPEN_PARENTHESES expr CLOSE_PARENTHESES            #ParanthesisExpr
    | PLUS INT_LITERAL                                   #UnPlusExpr
	| (MINUS|NOT|LEN|CHR|ORD) expr                       #UnExpr
	| expr (MULTIPLY|DIVISION|MODULO) expr               #BinMulDivModExpr
	| expr (PLUS|MINUS) expr                             #BinPlusMinusExpr
	| expr (GREATER|GREATER_EQ|SMALLER|SMALLER_EQ) expr  #BinCompExpr
	| expr (EQ|NOT_EQ) expr                              #BinEqExpr
	| expr AND expr                                      #BinAndExpr
	| expr OR expr                                       #BinOrExpr
	| arrayElem                                          #ArrayExpr
	| literal                                            #LiteralExpr
	| IDENT                                              #IdentExpr
	;

literal: INT_LITERAL   #IntLiteral
	   | BOOL_LITERAL  #BoolLiteral
	   | CHAR_LITERAL  #CharLiteral
	   | STR_LITERAL   #StrLiteral
	   | PAIR_LITERAL  #PairLiteral
	   ;

arrayElem: IDENT (OPEN_SQUARE_BRACKET expr CLOSE_SQUARE_BRACKET)+
         ;
