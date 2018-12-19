	@256
	D=A
	@SP
	M=D

// call for Sys.init 
	@b$ret.0
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@5
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Sys.init
	0;JMP
(b$ret.0)

// File Name: Main.vm 

// function Main.fibonacci 0 
(Main.fibonacci)

// push argument 0 
	@0 // -- addr = LCL + 0
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push constant 2 
	@2 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// lt 
	@SP
	M=M-1
	M=M-1
	A=M+1
	D=M
	A=A-1
	D=D-M
	@COMPARIZON.0
	D;JLE
	D=-1
	@COMPARIZON.0.END
	0;JMP
	(COMPARIZON.0)
	D=0
	(COMPARIZON.0.END)
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// if-goto IF_TRUE 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@IF_TRUE
	D;JNE

// goto IF_FALSE 
	@IF_FALSE
	0;JMP

// label IF_TRUE 
(IF_TRUE)

// push argument 0 
	@0 // -- addr = LCL + 0
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// return 

// return 
	@LCL
	D=M
	@endFrame
	M=D
	@5
	A=D-A
	D=M
	@retAddr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@ARG
	A=M
	M=D
	D=A+1
	@SP
	M=D
	@4
	D=A
	@endFrame
	A=M-D
	D=M
	@LCL
	M=D
	@3
	D=A
	@endFrame
	A=M-D
	D=M
	@ARG
	M=D
	@2
	D=A
	@endFrame
	A=M-D
	D=M
	@THIS
	M=D
	@1
	D=A
	@endFrame
	A=M-D
	D=M
	@THAT
	M=D
	@retAddr
	A=M
	0;JMP

// label IF_FALSE 
(IF_FALSE)

// push argument 0 
	@0 // -- addr = LCL + 0
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push constant 2 
	@2 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// sub 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D-M
	M=-M

// call Main.fibonacci 1 

// call for Main.fibonacci 
	@b$ret.1
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@6
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Main.fibonacci
	0;JMP
(b$ret.1)

// push argument 0 
	@0 // -- addr = LCL + 0
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push constant 1 
	@1 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// sub 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D-M
	M=-M

// call Main.fibonacci 1 

// call for Main.fibonacci 
	@b$ret.2
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@6
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Main.fibonacci
	0;JMP
(b$ret.2)

// add 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D+M

// return 

// return 
	@LCL
	D=M
	@endFrame
	M=D
	@5
	A=D-A
	D=M
	@retAddr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@ARG
	A=M
	M=D
	D=A+1
	@SP
	M=D
	@4
	D=A
	@endFrame
	A=M-D
	D=M
	@LCL
	M=D
	@3
	D=A
	@endFrame
	A=M-D
	D=M
	@ARG
	M=D
	@2
	D=A
	@endFrame
	A=M-D
	D=M
	@THIS
	M=D
	@1
	D=A
	@endFrame
	A=M-D
	D=M
	@THAT
	M=D
	@retAddr
	A=M
	0;JMP

// File Name: Sys.vm 

// function Sys.init 0 
(Sys.init)

// push constant 4 
	@4 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// call Main.fibonacci 1 

// call for Main.fibonacci 
	@b$ret.3
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@6
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Main.fibonacci
	0;JMP
(b$ret.3)

// label WHILE 
(WHILE)

// goto WHILE 
	@WHILE
	0;JMP
