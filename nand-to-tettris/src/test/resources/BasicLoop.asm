
// push constant 0 
	@0 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop local 0 
	@0 // addr = LCL + 0
	D=A
	@LCL
	D=M+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

// label LOOP_START 
(LOOP_START)
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

// add 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D+M

// pop local 0 
	@0 // addr = LCL + 0
	D=A
	@LCL
	D=M+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

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

// pop argument 0 
	@0 // addr = LCL + 0
	D=A
	@ARG
	D=M+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

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

// if-goto LOOP_START 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@LOOP_START
	D;JNE

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
