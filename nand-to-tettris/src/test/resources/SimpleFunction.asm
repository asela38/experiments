
// function SimpleFunction.test 2 
(SimpleFunction.test)
// push local 0 
	@0 // -- addr = LCL + 0
	D=A
	@LCL
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push local 1 
	@1 // -- addr = LCL + 1
	D=A
	@LCL
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// add 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D+M

// not 
	@SP
	A=M-1
	M=!M

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

// add 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D+M

// push argument 1 
	@1 // -- addr = LCL + 1
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
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

// return 

// return 
