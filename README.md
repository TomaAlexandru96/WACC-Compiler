# WACC-Compiler (2nd Year Compilers Group Project)

Implemented a procedural while like language compiler called WACC in a team of 4 people. 
Compiles the code into a subset of ARM11 instructions. We have made use of the ANTLR4 library for building the
abstract syntax tree, but we have created an enriched AST based on the generated one by using the visitor 
pattern technique provided by the library.
The project was divided into 3 parts:

1. Syntax and Semantic Checking with Error Reporting
2. Code Generation (ARM11 instructions)
3. Extension (Created two additional control flow insttructions)
